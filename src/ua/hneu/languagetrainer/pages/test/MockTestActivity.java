package ua.hneu.languagetrainer.pages.test;

import java.util.ArrayList;

import ua.hneu.edu.languagetrainer.R;
import ua.hneu.languagetrainer.App;
import ua.hneu.languagetrainer.ListViewAdapter;
import ua.hneu.languagetrainer.model.tests.Answer;
import ua.hneu.languagetrainer.model.tests.Question;
import ua.hneu.languagetrainer.model.tests.Test;
import ua.hneu.languagetrainer.passing.TestPassing;
import ua.hneu.languagetrainer.service.TestService;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Chronometer.OnChronometerTickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MockTestActivity extends Activity {
	ListView answersListView;
	TextView titleTextView;
	TextView sectionTextView;
	TextView taskTextView;
	TextView textTextView;
	ProgressBar progressBar;
	TextView isRight;
	Button skipSection;
	Chronometer chronometer;

	// custom adapter for ListView
	ListViewAdapter adapter;
	int answersNumber = 5;
	int currentWordNumber = -1;
	boolean isLevelDef = false;
	Test t;
	Question q;
	ArrayList<Answer> answers;
	Answer rightAnswer;
	String testName = null;
	TestPassing tp = App.tp;
	// time limits for each section
	long timeLimits[];
	int currentSection = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		// Initialize
		sectionTextView = (TextView) findViewById(R.id.sectionTextView);
		taskTextView = (TextView) findViewById(R.id.taskTextView);
		titleTextView = (TextView) findViewById(R.id.titleTextView);
		textTextView = (TextView) findViewById(R.id.textTextView);
		answersListView = (ListView) findViewById(R.id.answersListView);
		progressBar = (ProgressBar) findViewById(R.id.testProgressBar);
		isRight = (TextView) findViewById(R.id.isCorrectTextView);
		skipSection = (Button) findViewById(R.id.buttonSkipSection);
		chronometer = (Chronometer) findViewById(R.id.chronometer);
		// clear test passing instance to clear previous results
		tp.clearInfo();
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			testName = extras.getString("testName");
		}
		// if it's a level definition test hide chronometer
		// else start count and set time limits
		if (!testName.equals("level_def")) {
			timeLimits = App.getTimeTestLimits();
			chronometer.setBase(SystemClock
					.elapsedRealtime());
			chronometer.start();
			chronometer
					.setOnChronometerTickListener(new OnChronometerTickListener() {

						@Override
						public void onChronometerTick(Chronometer chronometer) {
							boolean isElapsed = false;
							long elapsedMillis = SystemClock.elapsedRealtime()
									- chronometer.getBase();
							if (App.userInfo.getLevel() == 1
									|| App.userInfo.getLevel() == 2) {
								// if it is 1 or 2 section and time is elapsed
								if ((currentSection == 1 || currentSection == 2)
										&& elapsedMillis >= timeLimits[0])
									isElapsed = true;
								// if it is 3 section and time is elapsed
								if (currentSection == 3
										&& elapsedMillis >= timeLimits[2])
									isElapsed = true;
							} else {
								// if it is 1 section and time is elapsed
								if (currentSection == 1
										&& elapsedMillis >= timeLimits[0])
									isElapsed = true;
								// if it is 2 section and time is elapsed
								if (currentSection == 2
										&& elapsedMillis >= timeLimits[0])
									isElapsed = true;
								// if it is 3 section and time is elapsed
								if (currentSection == 3
										&& elapsedMillis >= timeLimits[2])
									isElapsed = true;
							}
							if (isElapsed) {
								// show toast, clear timer and go to next
								// section
								timeIsOver();
								chronometer.setBase(SystemClock
										.elapsedRealtime());
								chronometer.start();
								toТextSection();

							}
						}
					});
		} else {
			chronometer.setVisibility(View.INVISIBLE);
		}

		TestService ts = new TestService();
		// getting and loading test by name

		t = ts.getTestByName(testName, getContentResolver());
		isLevelDef = (t.getName().equals("level_def"));
		// if level defining test - hide skippSection button, because it has no
		// sections
		if (isLevelDef)
			skipSection.setVisibility(View.INVISIBLE);
		// at first show word and possible answers
		nextWord();
	}

	public void timeIsOver() {
		Toast.makeText(this, getString(R.string.your_time_is_over), 300).show();
	}

	public void nextWord() {
		// move pointer to next word
		currentWordNumber++;
		double progress = ((double) currentWordNumber / (double) t
				.getQuestions().size()) * 100;
		progressBar.setProgress((int) progress);
		if (currentWordNumber >= t.getQuestions().size())
			endTesting();

		q = t.getQuestions().get(currentWordNumber);
		// setting sections variable - vocabulary, reading and listening
		if (q.getSection().equals("文字・語彙"))
			currentSection = 1;
		else if (q.getSection().equals("読解・文法"))
			currentSection = 2;
		else
			currentSection = 3;
		answers = q.getAnswers();
		titleTextView.setText(q.getText());
		titleTextView.setText(q.getTitle());
		taskTextView.setText(q.getTask());
		textTextView.setText(q.getText());
		sectionTextView.setText(q.getSection());
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
				// if it's a level definition test - all results are wrote
				// in scoreInVoc, else - divided by sections
				if (isLevelDef) {
					tp.incrementScoreInVocGr(q.getWeight());
				} else {
					if (currentSection == 1)
						tp.incrementScoreInVocGr(q.getWeight());
					else if (currentSection == 2)
						tp.incrementScoreInReading(q.getWeight());
					else
						tp.incrementScoreInListening(q.getWeight());
				}
				tp.incrementNumberOfCorrectAnswers();

				// change color to green and fade out
				isRight.setText("Correct!");
				adapter.changeColor(view, Color.parseColor("#669900"));
			} else {
				// change color of row and set text
				adapter.changeColor(view, Color.parseColor("#CC0000"));
				isRight.setText("Wrong");
			}
			// fading out textboxes
			fadeOut(sectionTextView, 750);
			fadeOut(taskTextView, 750);
			fadeOut(titleTextView, 750);
			fadeOut(textTextView, 750);
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
		DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case DialogInterface.BUTTON_POSITIVE:
					// Yes button clicked
					// show recommendations or results
					Intent intent;
					if (testName.equals("level_def")) {
						intent = new Intent(getBaseContext(),
								LevelDefTestResultActivity.class);
					} else {
						intent = new Intent(getBaseContext(),
								TestResultActivity.class);
						intent.putExtra("testName", t.getName());
					}
					startActivity(intent);
					break;

				case DialogInterface.BUTTON_NEGATIVE:
					// No button clicked - do nothing
					break;
				}
			}
		};

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(getString(R.string.end_test_confirmation))
				.setPositiveButton(getString(R.string.yes), dialogClickListener)
				.setNegativeButton(getString(R.string.no), dialogClickListener)
				.show();

	}

	public void toТextSection() {
		String section = q.getSection();
		if (currentSection == 3)
			endTesting();
		else
			while (true) {
				String s = t.getQuestions().get(currentWordNumber).getSection();
				if (!section.equals(s))
					break;
				else
					currentWordNumber++;
			}
		currentWordNumber++;
		nextWord();
	}

	public void skipSectionOnClick(View v) {
		// dialog yes/no
		DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case DialogInterface.BUTTON_POSITIVE:
					// Yes button clicked - increment currentWordNumber until
					// question of next section
					toТextSection();
					break;
				case DialogInterface.BUTTON_NEGATIVE:
					// No button clicked - do nothing
					break;
				}
			}

		};
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(getString(R.string.skip_section_confirmation))
				.setPositiveButton(getString(R.string.yes), dialogClickListener)
				.setNegativeButton(getString(R.string.no), dialogClickListener)
				.show();
	}

}
