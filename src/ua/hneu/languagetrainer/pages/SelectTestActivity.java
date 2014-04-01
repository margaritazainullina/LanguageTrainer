package ua.hneu.languagetrainer.pages;

import java.util.Collections;
import java.util.Set;

import ua.edu.hneu.test.R;
import ua.hneu.languagetrainer.App;
import ua.hneu.languagetrainer.xmlparcing.DictionaryEntry;
import ua.hneu.languagetrainer.xmlparcing.WordDictionary;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class SelectTestActivity extends Activity {
	WordDictionary curDictionary;
	Set<DictionaryEntry> randomDictionary;
	ListView answersListView;
	TextView wordTextView;
	TextView transcriptionTextView;
	TextView romajiTextView;
	TextView translationTextView;
	TextView isRight;
	DictionaryEntry rightAnswer;
	int answersNumber = 5;
	int currentWordNumber = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_test_activity);

		// Initialize
		wordTextView = (TextView) findViewById(R.id.wordTextView);
		transcriptionTextView = (TextView) findViewById(R.id.transcriptionTextView);
		romajiTextView = (TextView) findViewById(R.id.romajiTextView);
		translationTextView = (TextView) findViewById(R.id.translationTextView);
		answersListView = (ListView) findViewById(R.id.answersListView);
		isRight = (TextView) findViewById(R.id.isRight);

		// current dictionary with words for current session
		curDictionary = App.getCurrentDictionary();

		// at first show word and possible answers
		nextWord();
	}

	public void nextWord() {
		// move pointer
		currentWordNumber++;
		// set text to all TextViews above
		DictionaryEntry e = curDictionary.get(currentWordNumber);

		wordTextView.setText(e.getWord());
		transcriptionTextView.setText(e.getTranscription());
		romajiTextView.setText(e.getRomaji());
		String s = e.translationsToString();

		// translationTextView.setText(s);
		// get dictionary with random entries, add current one and shuffle
		randomDictionary = curDictionary.getRandomEntries(answersNumber - 1);
		randomDictionary.add(curDictionary.get(currentWordNumber));
		rightAnswer = curDictionary.get(currentWordNumber);

		// create List randomDictionaryList for ArrayAdapter from set
		// randomDictionary
		WordDictionary randomDictionaryList = new WordDictionary();
		randomDictionaryList.getEntries().addAll(randomDictionary);

		// shuffle list
		Collections.shuffle(randomDictionaryList.getEntries());

		// creating adapters ListView with possible answers
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1,
				randomDictionaryList.getAllTranslations());

		// bindings adapters to ListViews
		answersListView.setAdapter(adapter);
		answersListView.setOnItemClickListener(answersListViewClickListener);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.match_the_words, menu);
		return true;
	}

	// listeners for list
	final private transient OnItemClickListener answersListViewClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(final AdapterView<?> parent, final View view,
				final int position, final long itemID) {

			String selectedFromList = (String) (answersListView
					.getItemAtPosition(position));
			String rightAnswer1 = rightAnswer.getTranslationsToString();
			
			//replace it!!! 
			//TODO: replace symbols [ and ] from getTranslationsToString() method!
			selectedFromList = selectedFromList.substring(1, selectedFromList.length()-1);
			
			if (selectedFromList.equals(rightAnswer1))
								isRight.setText("Correct!");
				//isRight.setText(position);
				
			else
				isRight.setText("Wrong");
				//isRight.setText(position);

			if (currentWordNumber < curDictionary.size() - 1) {
				// show next word and possible answers
				nextWord();
			} else {
				endTesting();
			}
		}
	};

	public void endTesting() {
		// go to resultActivity to show result of all vocabulary training
		Intent resultActivity = new Intent(this,
				WordPracticeResultActivity.class);
		startActivity(resultActivity);
	}

	public void buttonSkipSelectOnClick(View v) {
		// change it
		Intent matchWordsIntent = new Intent(this, MatchWordsActivity.class);
		startActivity(matchWordsIntent);
	}
}
