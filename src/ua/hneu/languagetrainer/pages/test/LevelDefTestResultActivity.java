package ua.hneu.languagetrainer.pages.test;

import ua.hneu.edu.languagetrainer.R;
import ua.hneu.languagetrainer.App;
import ua.hneu.languagetrainer.masterdetailflow.MenuListActivity;
import ua.hneu.languagetrainer.passing.TestPassing;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LevelDefTestResultActivity extends Activity {
	TextView recommendationsTextView;
	TextView percentageTextView;
	int choosedLevel = -1;
	int recommendedLevel = -1;
	TestPassing tp = App.tp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_level_def_test_result);
		int numberOfCorrectAnswers = 0;
		double score = 0;
		numberOfCorrectAnswers = tp.getNumberOfCorrectAnswers();
		score = tp.getScoreInVocGr();

		recommendationsTextView = (TextView) findViewById(R.id.recommendationsTextView);
		percentageTextView = (TextView) findViewById(R.id.percentageTextView);
		StringBuilder sb1 = new StringBuilder();
		sb1.append(this.getString(R.string.recommendation) + " N");
		if (score < 4.8)
			recommendedLevel = 5;
		else if (score < 9.8)
			recommendedLevel = 4;
		else if (score < 20.4)
			recommendedLevel = 3;
		else if (score < 30)
			recommendedLevel = 2;
		else
			recommendedLevel = 1;
		sb1.append(recommendedLevel);
		recommendationsTextView.setText(sb1.toString());

		StringBuilder sb2 = new StringBuilder();
		sb2.append(this.getString(R.string.percentage) + " ");
		sb2.append(numberOfCorrectAnswers + " ");
		percentageTextView.setText(sb2.toString());
	}

	public void buttonChooseLevelOnClick(View v) {
		if (choosedLevel != -1)
			App.goToLevel(choosedLevel);
		Intent intent = new Intent(this, MenuListActivity.class);
		startActivity(intent);
	}

	public void buttonIAgreeOnClick(View v) {
		App.goToLevel(recommendedLevel);
		Intent intent = new Intent(this, MenuListActivity.class);
		startActivity(intent);
	}

	public void radioButtonN5OnClick(View v) {
		choosedLevel = 5;
	}

	public void radioButtonN4OnClick(View v) {
		choosedLevel = 4;
	}

	public void radioButtonN3OnClick(View v) {
		choosedLevel = 3;
	}

	public void radioButtonN2OnClick(View v) {
		choosedLevel = 2;
	}

	public void radioButtonN1OnClick(View v) {
		choosedLevel = 1;
	}
}
