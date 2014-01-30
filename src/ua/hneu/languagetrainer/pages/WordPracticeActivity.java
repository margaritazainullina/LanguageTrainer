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
import ua.hneu.languagetrainer.xmlparcing.*;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class WordPracticeActivity extends Activity {
	public static WordDictionary dict = new WordDictionary();
	public static WordDictionary curDict = new WordDictionary();
	public static List<Integer> shownIndexes;
	public boolean isLast = true;
	public static DictionaryEntry curEntry;
	public static int idx = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_word_practice);
		String s = readFile("JLPT_N5_RUS.xml");
		this.dict = DictUtil.readXml(s);
		curEntry  = dict.fetchRandom();
		showEntry(curEntry);
	}

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
	
	public void buttonNextOnClick(View v){
		// if is on last entry - add to current dictionary and show entry
		// else - show next entry
		idx++;
		if (idx != curDict.size()) {
			isLast = false;
		} else {
			isLast = true;
		}

		if (isLast) {
			curEntry = dict.fetchRandom();
			curDict.add(curEntry);
			showEntry(curEntry);
		} else {
			showEntry(curDict.get(idx));
		}

		if (curDict.size() >= 10) {

		}
	}

	private void showEntry(DictionaryEntry dictionaryEntry) {
		TextView t1 = (TextView) findViewById(R.id.textView1);
		TextView t2 = (TextView) findViewById(R.id.textView2);

		t1.setText(curEntry.getWord());
		t2.setText(curEntry.toString());
		/*
		 * label.setText(curEntry.getWord()); label.setFont(new Font(36));
		 * info.setText(curEntry.toString()); info.setFont(new Font(18));
		 * info.setWrapText(true);
		 */
	}

	private void handlePreviousWord() {
		idx--;
		curEntry = curDict.get(idx);
		showEntry(curEntry);
	}

}
