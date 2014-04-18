package ua.hneu.languagetrainer.pages.vocabulary;

import ua.edu.hneu.languagetrainer.R;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ResultActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_word_practice_result);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.word_practice_result, menu);
		return true;
	}

}
