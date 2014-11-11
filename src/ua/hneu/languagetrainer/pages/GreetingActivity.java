package ua.hneu.languagetrainer.pages;

import ua.hneu.edu.languagetrainer.R;
import ua.hneu.languagetrainer.App;
import ua.hneu.languagetrainer.masterdetailflow.MenuListActivity;
import ua.hneu.languagetrainer.pages.test.MockTestActivity;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GreetingActivity extends Activity {
	Button buttonTakeTest;
	Button buttonStart;
	int level = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_greeting);
		buttonTakeTest = (Button) findViewById(R.id.buttonTakeTest);
		buttonStart = (Button) findViewById(R.id.buttonStart);	
	}

	public void buttonTakeTestOnClick(View v) {
		Intent intent = new Intent(this, MockTestActivity.class);
		intent.putExtra("testName", "level_def");
		startActivity(intent);
	}

	public void buttonStartOnClick(View v) {
		if (level != -1) {
			App.goToLevel(level);
			//Intent intent = new Intent(this, MenuListActivity.class);
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
		}
	}

	public void radioButtonN5OnClick(View v) {
		level = 5;
	}

	public void radioButtonN4OnClick(View v) {
		level = 4;
	}

	public void radioButtonN3OnClick(View v) {
		level = 3;
	}

	public void radioButtonN2OnClick(View v) {
		level = 2;
	}

	public void radioButtonN1OnClick(View v) {
		level = 1;
	}

}
