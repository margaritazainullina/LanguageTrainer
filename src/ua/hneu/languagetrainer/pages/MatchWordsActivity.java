package ua.hneu.languagetrainer.pages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ua.edu.hneu.test.R;
import ua.hneu.languagetrainer.App;
import ua.hneu.languagetrainer.xmlparcing.WordDictionary;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MatchWordsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_match_the_words);

		// current dictionary with words for current session
		WordDictionary curDictionary = App.getCurrentDictionary();

		// creating shuffled indices arrays for 3 columns with kanji, readings
		// and translations
		ArrayList<Integer> kanjiIndices = new ArrayList<Integer>(
				curDictionary.size());
		ArrayList<Integer> readingIndices = new ArrayList<Integer>(
				curDictionary.size());
		ArrayList<Integer> translationIndices = new ArrayList<Integer>(
				curDictionary.size());

		// initializing indices
		for (int i = 0; i < curDictionary.size(); i++) {
			kanjiIndices.add(i);
			readingIndices.add(i);
			translationIndices.add(i);
		}
		// shuffling indices
		Collections.shuffle(kanjiIndices);
		Collections.shuffle(readingIndices);
		Collections.shuffle(readingIndices);

		// get content of lists from curDictionary
		ArrayList<String> kanji1 = curDictionary.getAllKanji();
		ArrayList<String> readings1 = curDictionary.getAllReadings();
		ArrayList<String> translations1 = curDictionary.getAllTranslations();

		// and place it to new lists
		ArrayList<String> kanji = new ArrayList<String>(curDictionary.size());
		ArrayList<String> readings = new ArrayList<String>(curDictionary.size());
		ArrayList<String> translations = new ArrayList<String>(
				curDictionary.size());

		// shuffle it
		for (int i = 0; i < curDictionary.size(); i++) {
			kanji.add(kanji1.get(kanjiIndices.get(i)));
			readings.add(readings1.get(readingIndices.get(i)));
			translations.add(translations1.get(readingIndices.get(i)));
		}

		// creating adapters for columns - ListViews
		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, kanji);
		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, readings);
		ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, translations);

		// bindings adapters to ListViews
		ListView kanjiListView = (ListView) findViewById(R.id.kanjiListView);
		kanjiListView.setAdapter(adapter1);

		ListView readingListView = (ListView) findViewById(R.id.readingListView);
		readingListView.setAdapter(adapter2);

		ListView translationListView = (ListView) findViewById(R.id.translationListView);
		translationListView.setAdapter(adapter3);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.match_the_words, menu);
		return true;
	}

}
