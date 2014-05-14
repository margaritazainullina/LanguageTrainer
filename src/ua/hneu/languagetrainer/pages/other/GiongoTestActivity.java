package ua.hneu.languagetrainer.pages.other;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import java.util.Set;

import ua.hneu.edu.languagetrainer.R;
import ua.hneu.languagetrainer.App;
import ua.hneu.languagetrainer.ListViewAdapter;
import ua.hneu.languagetrainer.model.other.Giongo;
import ua.hneu.languagetrainer.model.other.GiongoExample;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class GiongoTestActivity extends Activity {
	// dictionary with random words for possible answers
	Hashtable<GiongoExample, Giongo> randomExamplesDictionary;
	GiongoExample rightAnswer;
	Giongo rightWord;
	ArrayList<String> answers = new ArrayList<String>();
	// activity elements
	ListView answersListView;
	TextView part1TextView;
	TextView part2TextView;
	TextView part3TextView;
	TextView isRight;
	int answersNumber = 4;
	int currentWordNumber = -1;
	int color = 0;

	// custom adapter for ListView
	ListViewAdapter adapter;
	boolean ifWasWrong = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_grammar_giongo_test);

		// Initialize views
		part1TextView = (TextView) findViewById(R.id.part1TextView);
		part2TextView = (TextView) findViewById(R.id.part2TextView);
		part3TextView = (TextView) findViewById(R.id.part3TextView);
		answersListView = (ListView) findViewById(R.id.answersListView);
		isRight = (TextView) findViewById(R.id.isCorrectTextView);		
		// at first show word and possible answers
		nextWord();
	}

	public void nextWord() {
		// move pointer to next word
		currentWordNumber++;
		if (currentWordNumber >= App.giongoWordsDictionary.size() - 1)
			endTesting();
		//create random dictionary again for every show
		randomExamplesDictionary = App.giongoWordsDictionary
				.getRandomExamplesWithWord(App.userInfo
						.getNumberOfEntriesInCurrentDict());
		// iterator for looping over dictionary with random entries
		Set<Entry<GiongoExample, Giongo>> set = randomExamplesDictionary
				.entrySet();
		Iterator<Entry<GiongoExample, Giongo>> it = set.iterator();

		answers = new ArrayList<String>();
		// rightAnswer answer - the first entry
		// rightWord is stored for fetching color and incrementing of learned
		// percentage
		Random r = new Random();
		int idx = r.nextInt(answersNumber);
		int i=0;
		while (it.hasNext() && answers.size() < answersNumber) {
			Map.Entry<GiongoExample, Giongo> entry = (Map.Entry<GiongoExample, Giongo>) it
					.next();
			if (i==idx) {
				// string with right answer
				rightAnswer = entry.getKey();
				rightWord = entry.getValue();
				color = entry.getValue().getIntColor();
			}
			answers.add(entry.getKey().getPart2());
			i++;
		}

		part1TextView.setText(rightAnswer.getPart1());
		part3TextView.setText(rightAnswer.getPart3());
		part1TextView.setTextColor(color);
		part2TextView.setTextColor(color);
		part3TextView.setTextColor(color);
		// shuffling, because first line always stores right answer
		Collections.shuffle(answers);
		adapter = new ListViewAdapter(this, answers);
		// bindings adapter to ListView
		answersListView.setAdapter(adapter);
		answersListView.setOnItemClickListener(answersListViewClickListener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.match_the_words, menu);
		return true;
	}

	final private transient OnItemClickListener answersListViewClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(final AdapterView<?> parent, final View view,
				final int position, final long itemID) {
			String selected = answers.get(position);
			// comparing correct and selected answer
			if (selected.equals(rightAnswer.getPart2())) {
				App.gp.incrementNumberOfCorrectAnswers();
				// increment percentage of right answers if wrong answer wasn't
				// given
				if (!ifWasWrong)
					rightWord.setLearnedPercentage(rightWord
							.getLearnedPercentage()
							+ App.userInfo.getPercentageIncrement());

				if (rightWord.getLearnedPercentage() == 1) {
					App.gp.makeWordLearned(rightWord, getContentResolver());
				}

				App.gs.update(rightWord, getContentResolver());
				// change color to green and fade out
				isRight.setText("Correct!");
				adapter.changeColor(view, Color.parseColor("#669900"));
				// fading out textboxes
				fadeOut(part1TextView, 750);
				fadeOut(part2TextView, 750);
				fadeOut(part3TextView, 750);
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
								if (currentWordNumber < App.giongoWordsDictionary
										.size() - 1) {
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
				ifWasWrong = true;
				// set information about wrong answer in GPassing
				App.gp.incrementNumberOfIncorrectAnswers();
				App.gp.addProblemWord(rightWord);

			}
		}
	};

	public void fadeOut(View v, long duration) {
		Animation fadeOutAnimation = AnimationUtils.loadAnimation(
				v.getContext(), android.R.anim.fade_out);
		fadeOutAnimation.setDuration(750);
		v.startAnimation(fadeOutAnimation);
	}

	public void endTesting() {
		// go to ResultActivity		
		  Intent nextActivity = new Intent(this,
		  GiongoResultActivity.class); startActivity(nextActivity);		 
	}

	public void buttonSkipSelectOnClick(View v) {
		endTesting();
	}

	public void buttonIAlrKnow(View v) {
		App.gp.makeWordLearned(rightWord, getContentResolver());
		nextWord();

	}
}
