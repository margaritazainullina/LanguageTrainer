package ua.hneu.languagetrainer.pages.test;

import ua.hneu.edu.languagetrainer.R;
import ua.hneu.languagetrainer.App;
import ua.hneu.languagetrainer.masterdetailflow.ItemListActivity;
import ua.hneu.languagetrainer.model.User;
import ua.hneu.languagetrainer.model.vocabulary.DictionaryEntry;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class TestResultActivity extends Activity {
	TextView recommendationsTextView;
	TextView percentageTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_result);
		int numberOfCorrectAnswers = 0;
		int numberOfAnswers = 0;
		double score = 0;
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			numberOfCorrectAnswers = extras.getInt("numberOfCorrectAnswers");
			numberOfAnswers = extras.getInt("numberOfAnswers");
			score = extras.getDouble("score");
		}

		recommendationsTextView = (TextView) findViewById(R.id.recommendationsTextView);
		percentageTextView = (TextView) findViewById(R.id.percentageTextView);

		StringBuilder sb1 = new StringBuilder();
		sb1.append(this.getString(R.string.percentage) + " ");
		sb1.append(numberOfCorrectAnswers + " ");
		sb1.append(this.getString(R.string.out_of) + " ");
		sb1.append(numberOfAnswers);
		recommendationsTextView.setText(sb1.toString());

		StringBuilder sb2 = new StringBuilder();
		sb1.append(this.getString(R.string.recommendation) + " N");
		if (score < 4.8)
			sb1.append("5");
		else if (score < 9.8)
			sb1.append("4");
		else if (score < 20.4)
			sb1.append("3");
		else if (score < 30)
			sb1.append("2");
		else
			sb1.append("1");
	}

}
