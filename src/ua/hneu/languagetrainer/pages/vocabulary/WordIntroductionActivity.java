package ua.hneu.languagetrainer.pages.vocabulary;

import java.util.List;

import ua.hneu.edu.languagetrainer.R;
import ua.hneu.languagetrainer.App;
import ua.hneu.languagetrainer.TextToVoiceMediaPlayer;
import ua.hneu.languagetrainer.model.vocabulary.VocabularyEntry;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WordIntroductionActivity extends Activity {

	public static List<Integer> shownIndexes;
	public boolean isLast = true;
	public static VocabularyEntry curEntry;
	public static int idx = -1;

	TextView wordTextView;
	TextView transcriptionTextView;
	TextView romajiTextView;
	TextView translationTextView;
	Button prevButton;
	TextToVoiceMediaPlayer twmp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// if it's not first time when user sees entries of current level
		// sort - entries shown less times will be first shown
		// else it makes no sense, all is sorted initially
		if (App.userInfo.isLevelLaunchedFirstTime == 0)
			App.vocabularyDictionary.sortByTimesShown();
		// change this value
		App.userInfo.isLevelLaunchedFirstTime = 0;
		App.userInfo.updateUserData(getContentResolver());

		// Initialize views
		wordTextView = (TextView) findViewById(R.id.wordTextView);
		transcriptionTextView = (TextView) findViewById(R.id.transcriptionTextView);
		romajiTextView = (TextView) findViewById(R.id.romajiTextView);
		translationTextView = (TextView) findViewById(R.id.translationTextView);
		twmp = new TextToVoiceMediaPlayer();
		// increment number of
		App.vp.incrementNumberOfPassingsInARow();

		setContentView(R.layout.activity_word_introduction);
		prevButton = (Button) findViewById(R.id.buttonPrevious);

		// show first entry
		curEntry = App.vocabularyDictionary.get(0);
		idx = 0;
		showEntry();
		prevButton.setEnabled(false);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true); 
	}

	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            NavUtils.navigateUpFromSameTask(this);
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
	
	private void speakOut(final VocabularyEntry entry) {
		twmp.play(entry.getTranscription());
	}

	public void onPlayClick(View v) {
		speakOut(curEntry);
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
			curEntry = App.vocabularyDictionary.get(idx);
			showEntry();
		}
	}

	public void buttonSkipOnClick(View v) {
		goToNextPassingActivity();
	}

	public void goToNextPassingActivity() {
		Intent matchWordsIntent = new Intent(this, MatchWordsActivity.class);
		startActivity(matchWordsIntent);
	}

	@SuppressLint("SimpleDateFormat")
	private void showEntry() {
		wordTextView = (TextView) findViewById(R.id.wordTextView);
		transcriptionTextView = (TextView) findViewById(R.id.transcriptionTextView);
		romajiTextView = (TextView) findViewById(R.id.romajiTextView);
		translationTextView = (TextView) findViewById(R.id.translationTextView);

		// set word info to the texViews
		wordTextView.setText(curEntry.getKanji());
		transcriptionTextView.setText(curEntry.getTranscription());
		if (App.isShowRomaji)
			romajiTextView.setText(curEntry.getRomaji());
		translationTextView.setText(curEntry.translationsToString());

		// set color of entry
		int color = App.vocabularyDictionary.get(idx).getIntColor();
		wordTextView.setTextColor(color);
		transcriptionTextView.setTextColor(color);
		romajiTextView.setTextColor(color);

		// and write information to db
		curEntry.setLastView();
		curEntry.incrementShowntimes();
		App.vs.update(curEntry, getContentResolver());
		try {
			speakOut(curEntry);
		} catch (Exception e) {
			Log.e("Text to speecch error", "Text to speecch error");
		}
	}

	public void buttonPreviousOnClick(View v) {
		if (idx > 0) {
			idx--;
			curEntry = App.vocabularyDictionary.get(idx);
			showEntry();
		} else {
			prevButton.setEnabled(false);
		}
	}
}
