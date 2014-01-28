package ua.hneu.languagetrainer.pages;

import ua.edu.hneu.test.R;
import ua.edu.hneu.test.R.layout;
import ua.edu.hneu.test.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class WordPracticeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_word_practice);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.word_practice, menu);
		return true;
	}

}
