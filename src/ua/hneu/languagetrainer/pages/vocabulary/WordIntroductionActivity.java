package ua.hneu.languagetrainer.pages.vocabulary;

import java.util.List;
import ua.edu.hneu.languagetrainer.R;
import ua.hneu.languagetrainer.App;
import ua.hneu.languagetrainer.xmlparcing.DictUtil;
import ua.hneu.languagetrainer.xmlparcing.DictionaryEntry;
import ua.hneu.languagetrainer.xmlparcing.WordDictionary;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WordIntroductionActivity extends Activity {
	public static WordDictionary dict = new WordDictionary();

	public static List<Integer> shownIndexes;
	public boolean isLast = true;
	public static DictionaryEntry curEntry;
	public static int idx = -1;
	public static int numberOfWordsInSample = 7;

	Button prevButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Log.d("Activ", ((App) getApplication()).str);
		App.setCurrentDictionary(new WordDictionary());
		
		setContentView(R.layout.activity_word_practice);

		prevButton = (Button) findViewById(R.id.buttonPrevious);

		// read file and parse to WordDictionary
		String xml = DictUtil.readFile(this, "test.xml");
		dict = DictUtil.ParseVocabularyXml(xml);
		// replace fetching of entries with some complicated method
		for (int i = 0; i < numberOfWordsInSample; i++) {
			App.getCurrentDictionary().add(dict.fetchRandom());
		}
		curEntry = App.getCurrentDictionary().get(0);
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
			showEntry(App.getCurrentDictionary().get(idx));
		}
	}
	public void buttonSkipOnClick(View v) {
		Intent matchWordsIntent = new Intent(this, MatchWordsActivity.class);
		startActivity(matchWordsIntent);
	}
	
	private void showEntry(DictionaryEntry dictionaryEntry) {
		TextView wordTextView = (TextView) findViewById(R.id.wordTextView);
		TextView transcriptionTextView = (TextView) findViewById(R.id.transcriptionTextView);
		TextView romajiTextView = (TextView) findViewById(R.id.romajiTextView);
		TextView translationTextView = (TextView) findViewById(R.id.translationTextView);

		// set word info to the texViews		
		wordTextView.setText(dictionaryEntry.getWord());
		transcriptionTextView.setText(dictionaryEntry.getTranscription());
		romajiTextView.setText(dictionaryEntry.getRomaji());
		translationTextView.setText(dictionaryEntry.translationsToString());
	}

	public void buttonPreviousOnClick(View v) {
		if (idx > 0) {
			idx--;
			curEntry = App.getCurrentDictionary().get(idx);
			showEntry(curEntry);
		} else {
			prevButton.setEnabled(false);
		}
	}

}
