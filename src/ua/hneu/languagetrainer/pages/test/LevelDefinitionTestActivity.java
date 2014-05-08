package ua.hneu.languagetrainer.pages.test;

import java.util.ArrayList;

import ua.hneu.edu.languagetrainer.R;
import ua.hneu.languagetrainer.App;
import ua.hneu.languagetrainer.ListViewAdapter;
import ua.hneu.languagetrainer.model.tests.Answer;
import ua.hneu.languagetrainer.model.tests.Question;
import ua.hneu.languagetrainer.model.tests.Test;
import ua.hneu.languagetrainer.model.vocabulary.DictionaryEntry;
import ua.hneu.languagetrainer.service.AnswerService;
import ua.hneu.languagetrainer.service.QuestionService;
import ua.hneu.languagetrainer.service.TestService;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LevelDefinitionTestActivity extends Activity {
	ListView answersListView;
	TextView titleTextView;
	TextView descTextView;
	TextView textTextView;
	ProgressBar progressBar;
	TextView isRight;
	// custom adapter for ListView
	ListViewAdapter adapter;
	int answersNumber = 5;
	int currentWordNumber = -1;
	boolean wasWrong = false;
	int numberOfCorrectAnswers = 0;
	Test t;
	double score = 0;
	Question q;
	ArrayList<Answer> answers;
	Answer rightAnswer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_level_definition_test);

		// Initialize
		titleTextView = (TextView) findViewById(R.id.titleTextView);
		textTextView = (TextView) findViewById(R.id.textTextView);
		answersListView = (ListView) findViewById(R.id.answersListView);
		descTextView = (TextView) findViewById(R.id.descTextView);
		progressBar = (ProgressBar) findViewById(R.id.testProgressBar);
		isRight = (TextView) findViewById(R.id.isCorrectTextView);

		TestService ts = new TestService();
		QuestionService qs = new QuestionService();
		AnswerService as = new AnswerService();

		/*
		 * ts.dropTable(); qs.dropTable(); as.dropTable(); ts.createTable();
		 * qs.createTable();
		 * QuestionService.startCounting(getContentResolver());
		 * as.createTable(); ts.insertFromXml("level_def_test.xml", getAssets(),
		 * getContentResolver());
		 */

		t = ts.getTestByName("level_def", getContentResolver());
		// at first show word and possible answers
		nextWord();
	}

	public void nextWord() {
		wasWrong = false;
		// move pointer to next word
		currentWordNumber++;
		double progress = ((double) currentWordNumber / (double) t
				.getQuestions().size()) * 100;
		progressBar.setProgress((int) progress);
		if (currentWordNumber >= t.getQuestions().size())
			endTesting();
		q = t.getQuestions().get(currentWordNumber);
		answers = q.getAnswers();
		titleTextView.setText(q.getText());
		textTextView.setText(q.getTitle());
		// hide caption if it isn't needed
		if (q.getText().isEmpty())
			descTextView.setAlpha(0);
		else
			descTextView.setAlpha(1);
		adapter = new ListViewAdapter(this, q.getAllAnswers());
		answersListView.setAdapter(adapter);
		answersListView.setOnItemClickListener(answersListViewClickListener);
		rightAnswer = q.getRightAnswer();
	}

	// listeners for click on the list row
	final private transient OnItemClickListener answersListViewClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(final AdapterView<?> parent, final View view,
				final int position, final long itemID) {
			Answer selected = q.getAnswers().get(position);
			// comparing correct and selected answer
			if (selected == rightAnswer) {
				if (!wasWrong) {
					score += q.getWeight();
					numberOfCorrectAnswers++;
				}
				// change color to green and fade out
				isRight.setText("Correct!");
				adapter.changeColor(view, Color.parseColor("#669900"));
				// fading out textboxes
				fadeOut(titleTextView, 750);
				fadeOut(textTextView, 750);
				fadeOut(descTextView, 750);
				fadeOut(isRight, 750);

				// fading out listview
				ListView v = (ListView) view.getParent();
				fadeOut(v, 750);

				Animation fadeOutAnimation = AnimationUtils.loadAnimation(
						v.getContext(), android.R.anim.fade_out);
				fadeOutAnimation.setDuration(750);
				v.startAnimation(fadeOutAnimation);

				fadeOutAnimation
						.setAnimationListener(new Animation.AnimationListener() {

							@Override
							public void onAnimationEnd(Animation animation) {
								// when previous information faded out
								// show next word and possible answers or go to
								// next exercise
								if (currentWordNumber < t.getQuestions().size()) {
									nextWord();
								} else {
									endTesting();
								}
							}

							// doesn't needed, just implementation
							@Override
							public void onAnimationRepeat(Animation arg0) {
							}

							@Override
							public void onAnimationStart(Animation arg0) {
							}
						});
			} else {
				// change color of row and set text
				adapter.changeColor(view, Color.parseColor("#CC0000"));
				isRight.setText("Wrong");
				wasWrong = true;
			}
		}
	};

	public void fadeOut(View v, long duration) {
		Animation fadeOutAnimation = AnimationUtils.loadAnimation(
				v.getContext(), android.R.anim.fade_out);
		fadeOutAnimation.setDuration(750);
		v.startAnimation(fadeOutAnimation);
	}

	public void buttonIDontKnowOnClick(View v) {
		nextWord();
	}

	public void buttonEndTestOnClick(View v) {
		 endTesting();
	}

	public void endTesting() {
		Intent intent = new Intent(getBaseContext(), TestResultActivity.class);
		intent.putExtra("score", score);
		intent.putExtra("numberOfCorrectAnswers", numberOfCorrectAnswers);
		intent.putExtra("numberOfAnswers", t.getQuestions().size());
		startActivity(intent);
	}
}
