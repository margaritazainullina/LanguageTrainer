package ua.hneu.languagetrainer.pages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import ua.edu.hneu.test.R;
import ua.hneu.languagetrainer.App;
import ua.hneu.languagetrainer.xmlparcing.DictionaryEntry;
import ua.hneu.languagetrainer.xmlparcing.WordDictionary;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
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
	
	// has 3 fields - index of kanji, transcription, translation
	int[] currentAnswer = new int[]{-1,-1,-1};

	ArrayList<Integer> kanjiIndices;
	ArrayList<Integer> readingIndices;
	ArrayList<Integer> translationIndices;
	HashMap<String, Boolean> hasKanji;

	//lists with initial data from dictionary (not shuffled);
	ArrayList<String> kanji1;
	ArrayList<String> readings1;
	ArrayList<String> translations1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_match_the_words);

		// current dictionary with words for current session
		curDictionary = App.getCurrentDictionary();
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

		// initializing indices
		for (int i = 0; i < readingsNumber; i++) {
			String reading =curDictionary.get(i).getTranscription() + " " + curDictionary.get(i).getRomaji();
			if (i < kanjiNumber) {
				kanjiIndices.add(i);
				hasKanji.put(reading, true);
			}
			else {
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
		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, kanji);
		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, readings);
		ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, translations);

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
		}
	};
	final private transient OnItemClickListener readingListClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(final AdapterView<?> parent, final View view,
				final int position, final long itemID) {
			currentAnswer[1] = readingIndices.get(position);
		}
	};
	final private transient OnItemClickListener translationListClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(final AdapterView<?> parent, final View view,
				final int position, final long itemID) {
			currentAnswer[2] = translationIndices.get(position);
		}
	};

	public void buttonOkOnClick(View v) {
		// if the word don't have a kanji currentAnswer[0] isn't regarded
		boolean ifWordWithoutKanji = false;
		
		//get value from map - if reading the same, set hasKanji 
		String s1=readings1.get(currentAnswer[1]);
		if(hasKanji.get(s1)) ifWordWithoutKanji = true;

		//if all indices are the same
		//or hasKanji=false and 2nd and 3rd are the same
		if ((currentAnswer[0] == currentAnswer[1] && currentAnswer[1] == currentAnswer[2])
				|| (currentAnswer[0] == -1 && !ifWordWithoutKanji && currentAnswer[1] == currentAnswer[2]))
			isCorrectTextView.setText("Correct!");
		else
			isCorrectTextView.setText("Wrong");
		
		//reset values
		currentAnswer[0] = -1;
		currentAnswer[1] = -1;
		currentAnswer[2] = -1;
	}
	
	public void buttonSkipMatchOnClick(View v) {
		Intent matchWordsIntent = new Intent(this, SelectTestActivity.class);
		startActivity(matchWordsIntent);
	}
}
