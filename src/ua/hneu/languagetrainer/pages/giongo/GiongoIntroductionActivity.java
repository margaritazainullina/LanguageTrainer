package ua.hneu.languagetrainer.pages.giongo;

import java.util.List;

import ua.hneu.edu.languagetrainer.R;
import ua.hneu.languagetrainer.App;
import ua.hneu.languagetrainer.ExamplesListViewAdapter;
import ua.hneu.languagetrainer.model.other.Giongo;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class GiongoIntroductionActivity extends Activity {
	ExamplesListViewAdapter adapter;
	public static List<Integer> shownIndexes;
	public boolean isLast = true;
	public static Giongo curWord;
	public static int idx = -1;

	TextView giongoTextView;
	TextView translationTextView;
	ListView giongoExamplesListView;
	Button prevButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_giongo_introduction);
		// if it's not first time when user sees entries of current level
		// sort - entries shown less times will be first shown
		// else it makes no sense, all is sorted initially
		if (App.userInfo.isLevelLaunchedFirstTime == 0)
			App.giongoWordsDictionary.sortByTimesShown();
		// change this value
		App.userInfo.isLevelLaunchedFirstTime = 0;
		App.userInfo.updateUserData(getContentResolver());

		// Initialize views
		giongoTextView = (TextView) findViewById(R.id.giongoTextView);
		translationTextView = (TextView) findViewById(R.id.translationTextView);
		giongoExamplesListView = (ListView) findViewById(R.id.giongoExamplesListView);

		// increment number of
		App.vp.incrementNumberOfPassingsInARow();

		prevButton = (Button) findViewById(R.id.buttonPrevious);

		// show first entry
		curWord = App.giongoWordsDictionary.get(0);
		idx = 0;
		showEntry(curWord);
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
		if (idx >= App.userInfo.getNumberOfEntriesInCurrentDict()) {
			// if all words have been showed go to next activity
			goToNextPassingActivity();
		} else {
			curWord = App.giongoWordsDictionary.get(idx);
			showEntry(curWord);
		}
	}

	public void buttonSkipOnClick(View v) {
		goToNextPassingActivity();
	}

	public void goToNextPassingActivity() {
		Intent matchWordsIntent = new Intent(this, GiongoTestActivity.class);
		startActivity(matchWordsIntent);
	}

	@SuppressLint("SimpleDateFormat")
	private void showEntry(Giongo currentWord) {
		// set giongo info to the texViews
		giongoTextView.setText(currentWord.getRule());
		translationTextView.setText(currentWord.getTranslation());

		// set color of entry
		int color = curWord.getIntColor();
		giongoTextView.setTextColor(color);

		// and write information to db
		currentWord.setLastView();
		currentWord.incrementShowntimes();
		App.gs.update(currentWord, getContentResolver());

		adapter = new ExamplesListViewAdapter(this,
				curWord.getAllExamplesText(), curWord.getAllExamplesRomaji(),
				curWord.getAllTranslations(), curWord.getIntColor());
		giongoExamplesListView.setAdapter(adapter);
	}

	public void buttonPreviousOnClick(View v) {
		if (idx > 0) {
			idx--;
			curWord = App.giongoWordsDictionary.get(idx);
			showEntry(curWord);
		} else {
			prevButton.setEnabled(false);
		}
	}

}
