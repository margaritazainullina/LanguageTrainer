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
	int kanjiNumber;
	int readingsNumber;
	int translationsNumber;
	ListView kanjiListView;
	ListView readingListView;
	ListView translationListView;
	TextView isCorrectTextView;

	// has 3 elements - index of kanji, transcription, translation
	int[] currentAnswer = new int[] { -1, -1, -1 };

	ArrayList<Integer> wordEntryIds;
	// lists with indices
	ArrayList<Integer> kanjiIndices;
	ArrayList<Integer> readingIndices;
	ArrayList<Integer> translationIndices;
	HashMap<String, Boolean> hasKanji;
	HashMap<Integer, Integer> wordIndexAndKanjiIndex;

	// lists with initial data from dictionary (not shuffled);
	ArrayList<String> kanji1;
	ArrayList<String> readings1;
	ArrayList<String> translations1;

	// custom adapters for ListViews
	ListViewAdapter adapter1;
	ListViewAdapter adapter2;
	ListViewAdapter adapter3;

	// views - rows with given answers
	View v1;
	View v2;
	View v3;

	// TODO: make this for every word
	boolean ifWasWrong = false;

	WordDictionary learnedWords = new WordDictionary();

	int numberOfRightAnswers = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_match_words);

		// current dictionary with words for current session
		curDictionary = App.currentDictionary;
		curDictionary.sort();
		curDictionary.reverse();

		isCorrectTextView = (TextView) findViewById(R.id.isCorrectTextView);

		kanjiNumber = curDictionary.getAllKanji().size();
		readingsNumber = curDictionary.size();
		translationsNumber = curDictionary.size();

		// creating shuffled indices arrays for 3 columns with kanji, readings
		// and translations
		kanjiIndices = new ArrayList<Integer>(kanjiNumber);
		readingIndices = new ArrayList<Integer>(readingsNumber);
		translationIndices = new ArrayList<Integer>(translationsNumber);
		hasKanji = new HashMap<String, Boolean>(readingsNumber);
		// map - real index (id of word entry) and readingIndiex for shuffling
		wordIndexAndKanjiIndex = new HashMap<Integer, Integer>(readingsNumber);

		// initializing indices
		for (int i = 0; i < readingsNumber; i++) {
			String reading = curDictionary.get(i).getTranscription() + " "
					+ curDictionary.get(i).getRomaji();
			if (i < kanjiNumber) {
				kanjiIndices.add(i);
				hasKanji.put(reading, true);
				wordIndexAndKanjiIndex.put(curDictionary.get(i).getId(), i);
			} else {
				hasKanji.put(reading, false);
			}
			readingIndices.add(i);
			translationIndices.add(i);
		}

		// shuffling indices
		Collections.shuffle(kanjiIndices);
		Collections.shuffle(readingIndices);
		Collections.shuffle(translationIndices);

		// get content of lists from curDictionary
		kanji1 = curDictionary.getAllKanji();
		readings1 = curDictionary.getAllReadings();
		translations1 = curDictionary.getAllTranslations();

		// and place it to new lists
		ArrayList<String> kanji = new ArrayList<String>(kanjiNumber);
		ArrayList<String> readings = new ArrayList<String>(readingsNumber);
		ArrayList<String> translations = new ArrayList<String>(
				translationsNumber);

		// shuffle it
		for (int i = 0; i < readingsNumber; i++) {
			if (i < kanjiNumber)
				kanji.add(kanji1.get(kanjiIndices.get(i)));
			readings.add(readings1.get(readingIndices.get(i)));
			translations.add(translations1.get(translationIndices.get(i)));
		}

		// creating adapters for columns - ListViews
		adapter1 = new ListViewAdapter(this, kanji);
		adapter2 = new ListViewAdapter(this, readings);
		adapter3 = new ListViewAdapter(this, translations);

		// bindings adapters to ListViews
		kanjiListView = (ListView) findViewById(R.id.kanjiListView);
		kanjiListView.setAdapter(adapter1);

		readingListView = (ListView) findViewById(R.id.readingListView);
		readingListView.setAdapter(adapter2);

		translationListView = (ListView) findViewById(R.id.translationListView);
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
		// if the word don't have a kanji currentAnswer[0] isn't regarded
		boolean ifWordWithoutKanji = false;
		// if user didn't answered at all - do nothing
		if (currentAnswer[1] == -1 || currentAnswer[2] == -1) {
			return;
		}

		// get value from map - if reading is the same, set hasKanji
		String s1 = readings1.get(currentAnswer[1]);
		if (hasKanji.get(s1) != null)
			ifWordWithoutKanji = true;

		DictionaryEntry currentEntry = curDictionary.get(currentAnswer[1]);

		// if all indices are the same
		// or hasKanji=false and 2nd and 3rd are the same
		if ((currentAnswer[0] == currentAnswer[1] && currentAnswer[1] == currentAnswer[2])
				|| (currentAnswer[0] == -1 && !ifWordWithoutKanji && currentAnswer[1] == currentAnswer[2])) {
			numberOfRightAnswers++;

			// if all is done
			if (numberOfRightAnswers == readingsNumber - 1) {
				Intent matchWordsIntent = new Intent(this,
						TranslationTestActivity.class);
				startActivity(matchWordsIntent);
			}

			isCorrectTextView.setText("Correct!");
			// fade correct selected answers
			Animation fadeOutAnimation = AnimationUtils.loadAnimation(this,
					android.R.anim.fade_out);
			if (currentAnswer[0] != -1)
				adapter1.hideElement(v1, fadeOutAnimation, 350);
			adapter2.hideElement(v2, fadeOutAnimation, 350);
			adapter3.hideElement(v3, fadeOutAnimation, 350);
			// and write result to current dictionary if wrong answer was not
			// given
			if (!ifWasWrong) {				
				// increment percentage
				currentEntry.setLearnedPercentage(currentEntry
						.getLearnedPercentage()
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
		} else {
			isCorrectTextView.setText("Wrong");			
				ifWasWrong = true;
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
