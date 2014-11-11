package ua.hneu.languagetrainer.pages.counterwords;

import java.util.List;

import ua.hneu.edu.languagetrainer.R;
import ua.hneu.languagetrainer.App;
import ua.hneu.languagetrainer.TextToVoiceMediaPlayer;
import ua.hneu.languagetrainer.model.other.CounterWord;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CounterWordsIntroductionActivity extends Activity {
	public static List<Integer> shownIndexes;
	public boolean isLast = true;
	public static CounterWord curWord;
	public static int idx = -1;
	TextToVoiceMediaPlayer twmp;

	TextView wordTextView;
	TextView hiraganaTextView;
	TextView romajiTextView;
	TextView translationTextView;
	Button prevButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_counterwords_introduction);
		// if it's not first time when user sees entries of current level
		// sort - entries shown less times will be first shown
		// else it makes no sense, all is sorted initially
		if (App.userInfo.isLevelLaunchedFirstTime == 0)
			App.counterWordsDictionary.sortByTimesShown();
		// change this value
		App.userInfo.isLevelLaunchedFirstTime = 0;
		App.userInfo.updateUserData(getContentResolver());

		// Initialize views
		wordTextView = (TextView) findViewById(R.id.wordTextView);
		hiraganaTextView = (TextView) findViewById(R.id.hiraganaTextView);
		romajiTextView = (TextView) findViewById(R.id.romajiTextView);
		translationTextView = (TextView) findViewById(R.id.translationTextView);

		// increment number of
		App.vp.incrementNumberOfPassingsInARow();
		prevButton = (Button) findViewById(R.id.buttonPrevious);
		// show first entry
		curWord = App.counterWordsDictionary.get(0);
		idx = 0;
		showEntry(curWord);
		wordTextView.setTypeface(App.kanjiFont, Typeface.NORMAL);
		hiraganaTextView.setTypeface(App.kanjiFont, Typeface.NORMAL);
		prevButton.setEnabled(false);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.word_practice, menu);
		return true;
	}

	public void buttonNextOnClick(View v) {
		idx++;
		// if Next button was disabled
		prevButton.setEnabled(true);
		if (idx >= App.numberOfEntriesInCurrentDict) {
			// if all words have been showed go to next activity
			goToNextPassingActivity();
		} else {
			curWord = App.counterWordsDictionary.get(idx);
			showEntry(curWord);
		}
	}

	public void buttonSkipOnClick(View v) {
		goToNextPassingActivity();
	}

	public void goToNextPassingActivity() {
		Intent CounterWordsTestActivity = new Intent(this,
				CounterWordsTestActivity.class);
		startActivity(CounterWordsTestActivity);
	}

	@SuppressLint("SimpleDateFormat")
	private void showEntry(CounterWord currentWord) {
		// set info to the texViews
		wordTextView.setText(currentWord.getWord());
		hiraganaTextView.setText(currentWord.getHiragana());
		romajiTextView.setText(currentWord.getRomaji());
		translationTextView.setText(currentWord.getTranslation());

		// set color of entry
		int color = curWord.getIntColor();
		wordTextView.setTextColor(color);

		// and write information to db
		currentWord.setLastView();
		currentWord.incrementShowntimes();
		App.cws.update(currentWord, getContentResolver());
	}

	public void buttonPreviousOnClick(View v) {
		if (idx > 0) {
			idx--;
			curWord = App.counterWordsDictionary.get(idx);
			showEntry(curWord);
		} else {
			prevButton.setEnabled(false);
		}
	}
	public void onPlayClick3(View v) {
		// getting layout with text				
				twmp.play(curWord.getHiragana());
	}
}
