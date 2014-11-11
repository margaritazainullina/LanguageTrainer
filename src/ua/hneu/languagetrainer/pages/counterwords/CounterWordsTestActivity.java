package ua.hneu.languagetrainer.pages.counterwords;

import java.util.Collections;
import java.util.Set;

import ua.hneu.edu.languagetrainer.R;
import ua.hneu.languagetrainer.App;
import ua.hneu.languagetrainer.ListViewAdapter;
import ua.hneu.languagetrainer.model.other.CounterWord;
import ua.hneu.languagetrainer.model.other.CounterWordsDictionary;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class CounterWordsTestActivity extends Activity {
	// dictionary with random words for possible answers
	Set<CounterWord> randomDictionary;
	CounterWordsDictionary randomDictionaryList;
	// activity elements
	ListView answersListView;
	TextView wordTextView;
	TextView transcriptionTextView;
	TextView romajiTextView;
	TextView translationTextView;
	TextView isRight;
	CounterWord rightAnswer;
	int answersNumber = 5;
	int currentWordNumber = -1;

	// custom adapter for ListView
	ListViewAdapter adapter;
	boolean ifWasWrong = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_translation_transcription_cw_test);

		// Initialize
		wordTextView = (TextView) findViewById(R.id.wordTextView);
		transcriptionTextView = (TextView) findViewById(R.id.transcriptionTextView);
		romajiTextView = (TextView) findViewById(R.id.romajiTextView);
		translationTextView = (TextView) findViewById(R.id.translationTextView);
		answersListView = (ListView) findViewById(R.id.answersListView);
		isRight = (TextView) findViewById(R.id.isCorrectTextView);

		// at first show word and possible answers
		nextWord();

		wordTextView.setTypeface(App.kanjiFont, Typeface.NORMAL);
		romajiTextView.setTypeface(App.kanjiFont, Typeface.NORMAL);
	}

	public void nextWord() {
		// move pointer to next word
		currentWordNumber++;
		if (currentWordNumber >= App.counterWordsDictionary.size() - 1)
			endTesting();

		// show word, reading and translations - set text to all TextViews
		CounterWord currentEntry = App.counterWordsDictionary
				.get(currentWordNumber);
			wordTextView.setText(currentEntry.getWord());
			transcriptionTextView.setText(currentEntry.getTranscription());
			if (App.isShowRomaji)
				romajiTextView.setText(currentEntry.getRomaji());
		

		// get dictionary with random entries, add current one and shuffle
		randomDictionary = App.counterWordsDictionary.getRandomEntries(
				answersNumber - 1);
		randomDictionary.add(App.counterWordsDictionary.get(currentWordNumber));
		rightAnswer = App.counterWordsDictionary.get(currentWordNumber);

		// create List randomDictionaryList for ArrayAdapter from set
		// randomDictionary
		randomDictionaryList = new CounterWordsDictionary();
		randomDictionaryList.getEntries().addAll(randomDictionary);
		// shuffle list
		Collections.shuffle(randomDictionaryList.getEntries());

		// creating adapter for ListView with possible answers
		
			adapter = new ListViewAdapter(this,
					randomDictionaryList.getAllTranslations(), true);
		
		// bindings adapter to ListView
		answersListView.setAdapter(adapter);
		answersListView.setOnItemClickListener(answersListViewClickListener);
		// set colors
		int color = currentEntry.getIntColor();
		wordTextView.setTextColor(color);
		transcriptionTextView.setTextColor(color);
		romajiTextView.setTextColor(color);
		isRight.setText("");
		// set this word shown
		rightAnswer.setLastView();
		App.cws.update(rightAnswer, getContentResolver());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.match_the_words, menu);
		return true;
	}

	// listeners for click on the list row
	final private transient OnItemClickListener answersListViewClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(final AdapterView<?> parent, final View view,
				final int position, final long itemID) {
			CounterWord selected = randomDictionaryList.get(position);
			// comparing correct and selected answer
			if (selected == rightAnswer) {
				App.cwp.incrementNumberOfCorrectAnswers();
				// increment percentage
				if (!ifWasWrong)
					rightAnswer.setLearnedPercentage(rightAnswer
							.getLearnedPercentage()
							+ App.getPercentageIncrement());

				if (rightAnswer.getLearnedPercentage() == 1) {
					App.cwp.makeWordLearned(rightAnswer, getContentResolver());
				}
				App.cws.update(rightAnswer, getContentResolver());
				// change color to green and fade out
				isRight.setText("Correct!");
				adapter.changeColor(view, Color.parseColor("#669900"));
				// fading out textboxes
				fadeOut(wordTextView, 750);
				fadeOut(transcriptionTextView, 750);
				fadeOut(romajiTextView, 750);
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
								if (currentWordNumber < App.counterWordsDictionary
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
				// set information about wrong answer in counter words passing
				App.cwp.incrementNumberOfIncorrectAnswers();
				App.cwp.addProblemWord(App.counterWordsDictionary
						.get(currentWordNumber));
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
		 //go to ResultActivity
		Intent nextActivity = new Intent(this, CounterWordsResultActivity.class);
		startActivity(nextActivity);
	}

	public void buttonSkipSelectOnClick(View v) {
		endTesting();
	}

	public void buttonIAlrKnow(View v) {
		App.cwp.makeWordLearned(rightAnswer, getContentResolver());
		nextWord();
	}
}
