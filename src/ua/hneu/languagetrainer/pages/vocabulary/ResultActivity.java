package ua.hneu.languagetrainer.pages.vocabulary;

import ua.hneu.edu.languagetrainer.R;
import ua.hneu.languagetrainer.App;
import ua.hneu.languagetrainer.model.vocabulary.DictionaryEntry;
import ua.hneu.languagetrainer.model.vocabulary.WordDictionary;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class ResultActivity extends Activity {
	TextView resultTextView;
	WordDictionary curDictionary;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vocabulary_result);
		// current dictionary with words for current session
		curDictionary = App.getCurrentDictionary();

		resultTextView = (TextView) findViewById(R.id.resultTextView);
		int numberOfLearnedWords = 0;
		StringBuffer sb = new StringBuffer();
		for (DictionaryEntry entry : curDictionary.getEntries()) {
			if (entry.getLearnedPercentage() >= 1) {
				numberOfLearnedWords++;
				sb.append(entry.getKanji());
				sb.append(", ");
			}
		}
		StringBuffer sb1 = new StringBuffer();
		sb1.append("You've learned ");
		sb1.append(numberOfLearnedWords);
		sb1.append(" words:\n");
		sb1.append(sb);
		resultTextView.setText(sb1);
		
		//TODO: write to db!!
		//DictUtil.updateXmlWithResults(curDictionary);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.word_practice_result, menu);
		return true;
	}

}
