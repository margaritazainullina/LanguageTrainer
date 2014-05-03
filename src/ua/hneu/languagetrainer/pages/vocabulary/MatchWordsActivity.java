package ua.hneu.languagetrainer.pages.vocabulary;

import java.util.ArrayList;
import java.util.Collections;

import ua.hneu.edu.languagetrainer.R;
import ua.hneu.languagetrainer.App;
import ua.hneu.languagetrainer.ListViewAdapter;
import ua.hneu.languagetrainer.model.vocabulary.DictionaryEntry;
import ua.hneu.languagetrainer.model.vocabulary.WordDictionary;
import android.annotation.SuppressLint;
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

	int entriesForMatchingNumber = 5;
	int translationsNumber;

	ListView kanjiListView;
	ListView readingListView;
	ListView translationListView;
	TextView isCorrectTextView;

	// has 3 elements - index of kanji, transcription, translation
	int[] currentAnswer = new int[] { -1, -1, -1 };
	int[] rowsSelected = new int[] { -1, -1, -1 };

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

		// initializing indices
		for (int i = 0; i < entriesForMatchingNumber; i++) {
			if (!curDictionary.get(i).getKanji().isEmpty())
				kanjiIndices.add(curDictionary.get(i).getId());
			readingIndices.add(curDictionary.get(i).getId());
			translationIndices.add(curDictionary.get(i).getId());
		}

		// shuffling indices
		Collections.shuffle(kanjiIndices);
		Collections.shuffle(readingIndices);
		Collections.shuffle(translationIndices);

		for (int i = 0; i < entriesForMatchingNumber; i++) {
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
			adapter1.setTextColorOfListViewRow((ListView) parent, position,
					Color.parseColor("#ffbb33"));
			// and remember this row for fading out if it is correct
			v1 = view;
			rowsSelected[0] = position;
		}
	};
	final private transient OnItemClickListener readingListClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(final AdapterView<?> parent, final View view,
				final int position, final long itemID) {
			currentAnswer[1] = readingIndices.get(position);
			adapter2.setTextColorOfListViewRow((ListView) parent, position,
					Color.parseColor("#ffbb33"));
			v2 = view;
			rowsSelected[1] = position;
		}
	};
	final private transient OnItemClickListener translationListClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(final AdapterView<?> parent, final View view,
				final int position, final long itemID) {
			currentAnswer[2] = translationIndices.get(position);
			adapter3.setTextColorOfListViewRow((ListView) parent, position,
					Color.parseColor("#ffbb33"));
			v3 = view;
			rowsSelected[2] = position;
		}
	};

	@SuppressLint("ResourceAsColor")
	public void buttonOkOnClick(View v) {

		// if user didn't answered at all - do nothing
		if (currentAnswer[1] == -1 || currentAnswer[2] == -1) {
			return;
		}

		DictionaryEntry currentEntry = curDictionary
				.getEntryById(currentAnswer[1]);
		boolean ifWordWithoutKanji = (currentEntry.getKanji().isEmpty());

		// if all indices are the same
		// or hasKanji=false and 2nd and 3rd are the same
		boolean isMatch = (currentAnswer[0] == currentAnswer[1] && currentAnswer[1] == currentAnswer[2]);
		boolean isMatchWithoutKanji = (currentAnswer[0] == -1
				&& ifWordWithoutKanji && currentAnswer[1] == currentAnswer[2]);
		if (isMatch || isMatchWithoutKanji) {
			numberOfAnswers++;
			// if all is done
			if (numberOfAnswers == entriesForMatchingNumber - 1) {
				goToNextPassingActivity();
			}
			// and write result to current dictionary if wrong answer was not
			// given
			if (!wrongAnswers.getEntries().contains(currentEntry)) {
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
					App.vp.makeWordLearned(currentEntry, getContentResolver());
				} else
					// and increment number of correct answers in current
					// session anyway
					App.vp.incrementNumberOfCorrectAnswersInMatching();
			}
			isCorrectTextView.setText("Correct!");
			isCorrectTextView.setTextColor(Color.parseColor("#669900"));
			// fade correct selected answers
			fadeout(v1, v2, v3);

		} else {
			// add given answer to wrong
			wrongAnswers.add(currentEntry);
			isCorrectTextView.setText("Wrong");
			isCorrectTextView.setTextColor(Color.parseColor("#CC0000"));
			// make selected items white
			if (currentAnswer[0] != -1)
				adapter1.changeColor(v1, Color.parseColor("#eaeaea"));
			adapter2.changeColor(v2, Color.parseColor("#eaeaea"));
			adapter3.changeColor(v3, Color.parseColor("#eaeaea"));
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
		Intent matchWordsIntent = new Intent(this,
				TranslationTestActivity.class);
		startActivity(matchWordsIntent);
	}

	public void fadeout(View view1, View view2, View view3) {
		Animation fadeOutAnimation = AnimationUtils.loadAnimation(this,
				android.R.anim.fade_out);
		if (view1 != null)
			adapter1.hideElement(view1, fadeOutAnimation, 350);
		adapter2.hideElement(view2, fadeOutAnimation, 350);
		adapter3.hideElement(view3, fadeOutAnimation, 350);
	}

	public void buttonIAlrKnow(View v) {
		boolean a1 = true;
		int a = -1;
		for (int i = 0; i < 3; i++) {
			if (currentAnswer[i] != -1)
				a = currentAnswer[i];
		}
		for (int i = 0; i < 3; i++) {
			if (currentAnswer[i] != -1 && currentAnswer[i] != a)
				a1 = false;
		}
		if (!a1 && a != -1)
			return;
		if (currentAnswer[0] != -1) {
			App.vp.makeWordLearned(
					curDictionary.getEntryById(currentAnswer[0]),
					getContentResolver());
		} else if (currentAnswer[1] != -1) {
			App.vp.makeWordLearned(
					curDictionary.getEntryById(currentAnswer[1]),
					getContentResolver());
		} else if (currentAnswer[2] != -1) {
			App.vp.makeWordLearned(
					curDictionary.getEntryById(currentAnswer[2]),
					getContentResolver());
		}
		int i1 = -1, i2 = -1, i3 = -1;
		for (int i = 0; i < readingIndices.size(); i++) {
			if (i < kanjiIndices.size())
				if (kanjiIndices.get(i) == a)
					i1 = i;
			if (readingIndices.get(i) == a)
				i2 = i;
			if (translationIndices.get(i) == a)
				i3 = i;
		}

		if (i1 != -1)
			v1 = kanjiListView.getChildAt(i1);
		v2 = readingListView.getChildAt(i2);
		v3 = translationListView.getChildAt(i3);
		numberOfAnswers++;
		fadeout(v1, v2, v3);

		currentAnswer[0] = -1;
		currentAnswer[1] = -1;
		currentAnswer[2] = -1;		
		
		if (numberOfAnswers >= entriesForMatchingNumber - 1) {
			goToNextPassingActivity();
		}
	}
}
