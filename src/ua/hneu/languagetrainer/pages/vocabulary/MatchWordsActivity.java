package ua.hneu.languagetrainer.pages.vocabulary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import ua.hneu.edu.languagetrainer.R;
import ua.hneu.languagetrainer.App;
import ua.hneu.languagetrainer.ListViewAdapter;
import ua.hneu.languagetrainer.model.vocabulary.DictionaryEntry;
import ua.hneu.languagetrainer.model.vocabulary.WordDictionary;
import ua.hneu.languagetrainer.service.VocabularyService;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class MatchWordsActivity extends Activity {
	WordDictionary curDictionary;

	int readingsNumber;
	int translationsNumber;

	ListView kanjiListView;
	ListView readingListView;
	ListView translationListView;
	TextView isCorrectTextView;

	// has 3 elements - index of kanji, transcription, translation
	int[] currentAnswer = new int[] { -1, -1, -1 };

	// lists with indices
	ArrayList<Integer> kanjiIndices = new ArrayList<Integer>();
	ArrayList<Integer> readingIndices = new ArrayList<Integer>();
	ArrayList<Integer> translationIndices = new ArrayList<Integer>();
	// dictionary with words with wrong answers
	WordDictionary wrongAnswers = new WordDictionary();
	WordDictionary learnedWords = new WordDictionary();

	ArrayList<String> kanji = new ArrayList<String>();
	ArrayList<String> readings = new ArrayList<String>();
	ArrayList<String> translations = new ArrayList<String>();

	// custom adapters for ListViews
	ListViewAdapter adapter1;
	ListViewAdapter adapter2;
	ListViewAdapter adapter3;

	// views - rows with given answers
	View v1;
	View v2;
	View v3;

	int numberOfAnswers = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_match_words);
		// current dictionary with words for current session
		curDictionary = App.currentDictionary;

		// Initialize views
		kanjiListView = (ListView) findViewById(R.id.kanjiListView);
		readingListView = (ListView) findViewById(R.id.readingListView);
		translationListView = (ListView) findViewById(R.id.translationListView);
		isCorrectTextView = (TextView) findViewById(R.id.isCorrectTextView);

		readingsNumber = curDictionary.size();

		// initializing indices
		for (int i = 0; i < readingsNumber; i++) {
			if (!curDictionary.get(i).getKanji().isEmpty())
				kanjiIndices.add(curDictionary.get(i).getId());
			readingIndices.add(curDictionary.get(i).getId());
			translationIndices.add(curDictionary.get(i).getId());
		}

		// shuffling indices
		Collections.shuffle(kanjiIndices);
		Collections.shuffle(readingIndices);
		Collections.shuffle(translationIndices);

		for (int i = 0; i < readingsNumber; i++) {
			if (i < kanjiIndices.size())
				kanji.add(curDictionary.getEntryById(kanjiIndices.get(i))
						.getKanji());
			readings.add(curDictionary.getEntryById(readingIndices.get(i))
					.readingsToString());
			translations.add(curDictionary.getEntryById(
					translationIndices.get(i)).translationsToString());
		}

		// creating adapters for columns - ListViews
		adapter1 = new ListViewAdapter(this, kanji);
		adapter2 = new ListViewAdapter(this, readings);
		adapter3 = new ListViewAdapter(this, translations);

		// bindings adapters to ListViews
		kanjiListView.setAdapter(adapter1);
		readingListView.setAdapter(adapter2);
		translationListView.setAdapter(adapter3);

		kanjiListView.setOnItemClickListener(kanjiListClickListener);
		readingListView.setOnItemClickListener(readingListClickListener);
		translationListView
				.setOnItemClickListener(translationListClickListener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.match_the_words, menu);
		return true;
	}

	// listeners for list
	final private transient OnItemClickListener kanjiListClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(final AdapterView<?> parent, final View view,
				final int position, final long itemID) {
			currentAnswer[0] = kanjiIndices.get(position);
			// change color of selected row
			// TODO: replace with colors from xml
			// adapter1.setBackgroundColorOfListViewRow((ListView) parent,
			// position,Color.argb(1, 40, 158, 181));
			adapter1.setTextColorOfListViewRow((ListView) parent, position,
					Color.YELLOW);

			// and remember this row for fading out if it is correct
			v1 = view;
		}
	};
	final private transient OnItemClickListener readingListClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(final AdapterView<?> parent, final View view,
				final int position, final long itemID) {
			currentAnswer[1] = readingIndices.get(position);
			adapter2.setTextColorOfListViewRow((ListView) parent, position,
					Color.YELLOW);
			v2 = view;
		}
	};
	final private transient OnItemClickListener translationListClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(final AdapterView<?> parent, final View view,
				final int position, final long itemID) {
			currentAnswer[2] = translationIndices.get(position);
			adapter3.setTextColorOfListViewRow((ListView) parent, position,
					Color.YELLOW);
			v3 = view;
		}
	};

	public void buttonOkOnClick(View v) {

		// if user didn't answered at all - do nothing
		if (currentAnswer[1] == -1 || currentAnswer[2] == -1) {
			return;
		}

		DictionaryEntry currentEntry = curDictionary.getEntryById(currentAnswer[1]);
		boolean ifWordWithoutKanji = (currentEntry.getKanji().isEmpty());

		// if all indices are the same
		// or hasKanji=false and 2nd and 3rd are the same
		boolean isMatch = (currentAnswer[0] == currentAnswer[1] && currentAnswer[1] == currentAnswer[2]);
		boolean isMatchWithoutKanji = (currentAnswer[0] == -1
				&& ifWordWithoutKanji && currentAnswer[1] == currentAnswer[2]);
		if (isMatch || isMatchWithoutKanji) {
			numberOfAnswers++;
			// if all is done
			if (numberOfAnswers == readingsNumber - 1) {
				Intent matchWordsIntent = new Intent(this,
						TranslationTestActivity.class);
				startActivity(matchWordsIntent);
			}
			// and write result to current dictionary if wrong answer was not
			// given
			if (!wrongAnswers.getEntries().contains(currentEntry)) {				
				// increment percentage
				currentEntry.setLearnedPercentage(currentEntry.getLearnedPercentage()
						+ App.userInfo.getPercentageIncrement());
				// if word becomes learned,
				if (currentEntry.getLearnedPercentage() == 1) {
					// remove word from current dictionary for learning
					learnedWords.add(currentEntry);
					// update information id db
					App.vs.update(currentEntry, getContentResolver());
					// and set it as learned (also increments number of words
					// learned)
					App.vp.makeWordLearned(currentEntry);
				} else
					// and increment number of correct answers in current
					// session anyway
					App.vp.incrementNumberOfCorrectAnswersInMatching();
			}
			isCorrectTextView.setText("Correct!");
			// fade correct selected answers
			Animation fadeOutAnimation = AnimationUtils.loadAnimation(this,
					android.R.anim.fade_out);
			if (currentAnswer[0] != -1)
				adapter1.hideElement(v1, fadeOutAnimation, 350);
			adapter2.hideElement(v2, fadeOutAnimation, 350);
			adapter3.hideElement(v3, fadeOutAnimation, 350);

		} else {
			// add given answer to wrong
			wrongAnswers.add(currentEntry);
			isCorrectTextView.setText("Wrong");
			// make selected items white
			if (currentAnswer[0] != -1)
				adapter1.changeColor(v1, Color.WHITE);
			adapter2.changeColor(v2, Color.WHITE);
			adapter3.changeColor(v3, Color.WHITE);
			// set information about wrong answer in VocabularyPassing
			App.vp.incrementNumberOfIncorrectAnswersInMatching();
			App.vp.addProblemWord(currentEntry);
		}
		// reset values
		currentAnswer[0] = -1;
		currentAnswer[1] = -1;
		currentAnswer[2] = -1;

	}

	public void buttonSkipMatchOnClick(View v) {
		goToNextPassingActivity();
	}

	public void goToNextPassingActivity() {
		// delete learned words from current
		// dictionaryApp.currentDictionary.remove(currentEntry);App.currentDictionary.remove(currentEntry);App.currentDictionary.remove(currentEntry);App.currentDictionary.remove(currentEntry);
		for (DictionaryEntry e : learnedWords.getEntries()) {
			App.currentDictionary.remove(e);
		}

		Intent matchWordsIntent = new Intent(this,
				TranslationTestActivity.class);
		startActivity(matchWordsIntent);
	}
}
