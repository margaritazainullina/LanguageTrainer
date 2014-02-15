package ua.hneu.languagetrainer.pages;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import ua.edu.hneu.test.R;
import ua.hneu.languagetrainer.App;
import ua.hneu.languagetrainer.xmlparcing.DictUtil;
import ua.hneu.languagetrainer.xmlparcing.DictionaryEntry;
import ua.hneu.languagetrainer.xmlparcing.WordDictionary;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WordPracticeActivity extends Activity {
	public static WordDictionary dict = new WordDictionary();
	public static WordDictionary curDict = new WordDictionary();
	public static List<Integer> shownIndexes;
	public boolean isLast = true;
	public static DictionaryEntry curEntry;
	public static int idx = -1;
	public static int numberOfWordsInSample = 10;

	Button prevButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("Activ", ((App) getApplication()).str);

		setContentView(R.layout.activity_word_practice);

		prevButton = (Button) findViewById(R.id.buttonPrevious);

		// read file and parse to WordDictionary
		String xml = DictUtil.readXml(this, "JLPT_N5_RUS.xml");
		dict = DictUtil.ParseVocabularyXml(xml);
		// replace fetching of entries with some complicated method
		for (int i = 0; i < numberOfWordsInSample; i++) {
			curDict.add(dict.fetchRandom());
		}
		curEntry = curDict.get(0);
		idx = 0;
		showEntry(curEntry);
		prevButton.setEnabled(false);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.word_practice, menu);
		return true;
	}

	public void buttonNextOnClick(View v) {
		idx++;
		// if Next button was disabled
		prevButton.setEnabled(true);

		if (idx >= numberOfWordsInSample) {
			// if all words have been showed go to next activity
			Intent matchWordsIntent = new Intent(this, MatchWordsActivity.class);
			startActivity(matchWordsIntent);
		} else {
			showEntry(curDict.get(idx));
		}
	}

	private void showEntry(DictionaryEntry dictionaryEntry) {
		TextView wordTextView = (TextView) findViewById(R.id.wordTextView);
		TextView transcriptionTextView = (TextView) findViewById(R.id.transcriptionTextView);
		TextView romajiTextView = (TextView) findViewById(R.id.romajiTextView);

		// set word info to the texViews
		//todo: make normal api forgetting word transcription/meaning etc.
		wordTextView.setText(dictionaryEntry.getWord());
		transcriptionTextView.setText(dictionaryEntry.getMeanings().get(0).getTranscription());
		romajiTextView.setText(dictionaryEntry.getMeanings().get(0).getRomaji());
	}

	public void buttonPreviousOnClick(View v) {

		if (idx > 0) {
			idx--;
			curEntry = curDict.get(idx);
			showEntry(curEntry);
		} else {
			prevButton.setEnabled(false);
		}
	}

}
