package ua.hneu.languagetrainer.pages.vocabulary;

import java.util.Collections;
import java.util.Set;

import ua.hneu.edu.languagetrainer.R;
import ua.hneu.languagetrainer.App;
import ua.hneu.languagetrainer.ListViewAdapter;
import ua.hneu.languagetrainer.model.vocabulary.VocabularyEntry;
import ua.hneu.languagetrainer.model.vocabulary.VocabularyDictionary;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class TranslationTestActivity extends Activity {
	// dictionary with random words for possible answers
	Set<VocabularyEntry> randomDictionary;
	VocabularyDictionary randomDictionaryList;
	// activity elements
	ListView answersListView;
	TextView wordTextView;
	TextView transcriptionTextView;
	TextView romajiTextView;
	TextView translationTextView;
	ImageView isCorrect;
	VocabularyEntry rightAnswer;
	int answersNumber = 5;
	int currentWordNumber = -1;

	// custom adapter for ListView
	ListViewAdapter adapter;
	boolean ifWasWrong = false;

	// sets direction of translation
	// true - question in Japanese, false - answers in Japanese
	// sets randomly for every question
	boolean isFromJapanese;

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
		isCorrect = (ImageView) findViewById(R.id.isCorrect);
		// at first show word and possible answers
		nextWord();
	}

	public void nextWord() {
		// move pointer to next word
		currentWordNumber++;
		if (currentWordNumber >= App.vocabularyDictionary.size() - 1)
			endTesting();

		// set randomly direction
		if (Math.random() < 0.5)
			isFromJapanese = false;
		else
			isFromJapanese = true;

		// show word, reading and translations - set text to all TextViews
		VocabularyEntry currentEntry = App.vocabularyDictionary
				.get(currentWordNumber);

		if (isFromJapanese) {
			wordTextView.setText(currentEntry.getKanji());
			transcriptionTextView.setText(currentEntry.getTranscription());
			if (App.isShowRomaji)
				romajiTextView.setText(currentEntry.getRomaji());
			wordTextView.setTypeface(App.kanjiFont, Typeface.NORMAL);
		} else {
			transcriptionTextView.setText("");
			romajiTextView.setText("");
			wordTextView.setTypeface(Typeface.SANS_SERIF);
			wordTextView.setText(currentEntry.translationsToString());
		}
		transcriptionTextView.setTypeface(App.kanjiFont, Typeface.NORMAL);

		// get dictionary with random entries, add current one and shuffle
		randomDictionary = App.vocabularyDictionary.getRandomEntries(
				answersNumber - 1, false);
		randomDictionary.add(App.vocabularyDictionary.get(currentWordNumber));
		rightAnswer = App.vocabularyDictionary.get(currentWordNumber);

		// create List randomDictionaryList for ArrayAdapter from set
		// randomDictionary
		randomDictionaryList = new VocabularyDictionary();
		randomDictionaryList.getEntries().addAll(randomDictionary);
		// shuffle list
		Collections.shuffle(randomDictionaryList.getEntries());

		// creating adapter for ListView with possible answers
		if (isFromJapanese) {
			adapter = new ListViewAdapter(this,
					randomDictionaryList.getAllTranslations(),false);
		} else {
			adapter = new ListViewAdapter(this,
					randomDictionaryList.getAllKanjiWithReadings(),true);
		}
		// bindings adapter to ListView
		answersListView.setAdapter(adapter);
		answersListView.setOnItemClickListener(answersListViewClickListener);
		// set colors
		int color = currentEntry.getIntColor();
		wordTextView.setTextColor(color);
		transcriptionTextView.setTextColor(color);
		romajiTextView.setTextColor(color);
		isCorrect.setImageResource(android.R.color.transparent);
		// set this word shown
		rightAnswer.setLastView();
		App.vs.update(rightAnswer, getContentResolver());

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
			VocabularyEntry selected = randomDictionaryList.get(position);
			// comparing correct and selected answer
			if (selected == rightAnswer) {
				App.vp.incrementNumberOfCorrectAnswersInTranslation();
				// increment percentage
				if (!ifWasWrong)
					rightAnswer.setLearnedPercentage(rightAnswer
							.getLearnedPercentage()
							+ App.getPercentageIncrement());

				if (rightAnswer.getLearnedPercentage() >= 1) {
					App.vp.makeWordLearned(rightAnswer, getContentResolver(),
							false);
				}
				App.vs.update(rightAnswer, getContentResolver());
				// change color to green and fade out
				isCorrect.setImageResource(R.drawable.yes);
				adapter.changeColor(view, Color.parseColor("#669900"));
				// fading out textboxes
				fadeOut(wordTextView, 750);
				fadeOut(transcriptionTextView, 750);
				fadeOut(romajiTextView, 750);
				fadeOut(isCorrect, 750);

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
								if (currentWordNumber < App.vocabularyDictionary
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
				isCorrect.setImageResource(R.drawable.no);
				ifWasWrong = true;
				// set information about wrong answer in VocabularyPassing
				App.vp.incrementNumberOfIncorrectAnswersInTranslation();
				App.vp.addProblemWord(App.vocabularyDictionary
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
		// go to TranscriptionActivity
		Intent nextActivity = new Intent(this, TranscriptionTestActivity.class);
		startActivity(nextActivity);
	}

	public void buttonSkipSelectOnClick(View v) {
		Intent matchWordsIntent = new Intent(this,
				TranscriptionTestActivity.class);
		startActivity(matchWordsIntent);
	}

	public void buttonIAlrKnow(View v) {
		App.vp.makeWordLearned(rightAnswer, getContentResolver(), false);
		nextWord();
	}
}
