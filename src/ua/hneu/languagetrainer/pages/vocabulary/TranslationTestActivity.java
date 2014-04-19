package ua.hneu.languagetrainer.pages.vocabulary;

import java.util.Collections;
import java.util.Set;

import ua.edu.hneu.languagetrainer.R;
import ua.hneu.languagetrainer.App;
import ua.hneu.languagetrainer.ListViewAdapter;
import ua.hneu.languagetrainer.xmlparcing.DictionaryEntry;
import ua.hneu.languagetrainer.xmlparcing.WordDictionary;
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

public class TranslationTestActivity extends Activity {
	// dictionary with words of current session
	WordDictionary curDictionary;
	// dictionary with random words for possible answers
	Set<DictionaryEntry> randomDictionary;
	WordDictionary randomDictionaryList;
	// activity elements
	ListView answersListView;
	TextView wordTextView;
	TextView transcriptionTextView;
	TextView romajiTextView;
	TextView translationTextView;
	TextView isRight;
	DictionaryEntry rightAnswer;
	int answersNumber = 5;
	int currentWordNumber = -1;

	// custom adapter for ListView
	ListViewAdapter adapter;

	boolean ifWasWrong = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_translation_test);

		// Initialize
		wordTextView = (TextView) findViewById(R.id.wordTextView);
		transcriptionTextView = (TextView) findViewById(R.id.transcriptionTextView);
		romajiTextView = (TextView) findViewById(R.id.romajiTextView);
		translationTextView = (TextView) findViewById(R.id.translationTextView);
		answersListView = (ListView) findViewById(R.id.answersListView);
		isRight = (TextView) findViewById(R.id.isRight);

		// current dictionary with words for current session
		curDictionary = App.getCurrentDictionary();

		// at first show word and possible answers
		nextWord();
	}

	public void nextWord() {
		// move pointer to next word
		currentWordNumber++;
		// show word, reading and translations - set text to all TextViews
		DictionaryEntry e = curDictionary.get(currentWordNumber);
		wordTextView.setText(e.getWord());
		transcriptionTextView.setText(e.getTranscription());
		romajiTextView.setText(e.getRomaji());

		// get dictionary with random entries, add current one and shuffle
		randomDictionary = curDictionary.getRandomEntries(answersNumber - 1);
		randomDictionary.add(curDictionary.get(currentWordNumber));
		rightAnswer = curDictionary.get(currentWordNumber);

		// create List randomDictionaryList for ArrayAdapter from set
		// randomDictionary
		randomDictionaryList = new WordDictionary();
		randomDictionaryList.getEntries().addAll(randomDictionary);
		// shuffle list
		Collections.shuffle(randomDictionaryList.getEntries());

		// creating adapter for ListView with possible answers
		adapter = new ListViewAdapter(this,
				randomDictionaryList.getAllTranslations());
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

	// listeners for click on the list row
	final private transient OnItemClickListener answersListViewClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(final AdapterView<?> parent, final View view,
				final int position, final long itemID) {

			String selectedFromList = (String) (answersListView
					.getItemAtPosition(position));
			String rightAnswer1 = rightAnswer.getTranslationsToString();
			// comparing correct and selected answer
			if (selectedFromList.equals(rightAnswer1)) {

				// increment percentage
				if (!ifWasWrong)
					rightAnswer.setLearnedPercentage(rightAnswer.getLearnedPercentage()+ App.getPercentageIncrement());
					
				// change color to green and fade out
				isRight.setText("Correct!");
				adapter.changeColor(view, Color.GREEN);
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
								if (currentWordNumber < curDictionary.size() - 1) {
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
				adapter.changeColor(view, Color.RED);
				isRight.setText("Wrong");
				ifWasWrong = true;
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
		// go to resultActivity to show result of all vocabulary training
		Intent resultActivity = new Intent(this, ResultActivity.class);
		startActivity(resultActivity);
	}

	public void buttonSkipSelectOnClick(View v) {
		// TODO: go to next exercise activity or result?
		Intent matchWordsIntent = new Intent(this, ResultActivity.class);
		startActivity(matchWordsIntent);
	}
}