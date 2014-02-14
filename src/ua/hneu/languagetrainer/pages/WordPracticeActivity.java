package ua.hneu.languagetrainer.pages;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.List;

import ua.edu.hneu.test.R;
import ua.edu.hneu.test.R.layout;
import ua.edu.hneu.test.R.menu;
import ua.hneu.languagetrainer.VocabularyActivityFragment;
import ua.hneu.languagetrainer.xmlparcing.*;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
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
		setContentView(R.layout.activity_word_practice);
						
		prevButton = (Button) findViewById(R.id.buttonPrevious);
		
		String s = readFile("JLPT_N5_RUS.xml");
		dict = DictUtil.readXml(s);
		// replace fetching of entries with some complicated method
		for (int i = 0; i < numberOfWordsInSample; i++) {
			curDict.add(dict.fetchRandom());
		}
		curEntry = curDict.get(0);
		idx = 0;
		showEntry(curEntry);
		prevButton.setEnabled(false);
	}
	
	//stub - read file from local storage to the string
	String readFile(String path) {
		BufferedWriter bw;
		StringBuffer buffer = new StringBuffer();

		try {
			InputStream inputstream = openFileInput(path);

			if (inputstream != null) {
				InputStreamReader isr = new InputStreamReader(inputstream);
				BufferedReader reader = new BufferedReader(isr);
				String str;

				while ((str = reader.readLine()) != null) {
					buffer.append(str);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.word_practice, menu);
		return true;
	}

	public void buttonNextOnClick(View v) {
		idx++;
		//if was disabled
		prevButton.setEnabled(true);
		
		if (idx>= numberOfWordsInSample) {
			// go to next activity
			Intent matchWordsIntent = new Intent(this, MatchWordsActivity.class);
			startActivity(matchWordsIntent);
		} else{
		showEntry(curDict.get(idx));
		}
	}

	private void showEntry(DictionaryEntry dictionaryEntry) {
		TextView t1 = (TextView) findViewById(R.id.textView1);
		TextView t2 = (TextView) findViewById(R.id.textView2);

		//set word info to the texViews
		t1.setText(dictionaryEntry.getWord());
		t2.setText(dictionaryEntry.toString());

	}

	public void buttonPreviousOnClick(View v) {
		
		if(idx>0){
			idx--;
		curEntry = curDict.get(idx);
		showEntry(curEntry);}
		else{
			prevButton.setEnabled(false);
		}
	}

}
