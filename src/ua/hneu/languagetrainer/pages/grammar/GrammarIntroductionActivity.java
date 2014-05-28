package ua.hneu.languagetrainer.pages.grammar;

import java.util.List;

import ua.hneu.edu.languagetrainer.R;
import ua.hneu.languagetrainer.App;
import ua.hneu.languagetrainer.ExamplesListViewAdapter;
import ua.hneu.languagetrainer.TextToVoiceMediaPlayer;
import ua.hneu.languagetrainer.model.grammar.GrammarRule;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class GrammarIntroductionActivity extends Activity {
	ExamplesListViewAdapter adapter;
	public static List<Integer> shownIndexes;
	public boolean isLast = true;
	public static GrammarRule curRule;
	public static int idx = -1;

	TextView ruleTextView;
	TextView descriptionTextView;
	ListView grammarExamplesListView;
	Button prevButton;
	TextToVoiceMediaPlayer twmp;
	String phrase = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// if it's not first time when user sees entries of current level
		// sort - entries shown less times will be first shown
		// else it makes no sense, all is sorted initially
		if (App.userInfo.isLevelLaunchedFirstTime == 0)
			App.grammarDictionary.sortByTimesShown();
		// change this value
		App.userInfo.isLevelLaunchedFirstTime = 0;
		App.userInfo.updateUserData(getContentResolver());

		// Initialize views
		ruleTextView = (TextView) findViewById(R.id.ruleTextView);
		descriptionTextView = (TextView) findViewById(R.id.transcriptionTextView);
		grammarExamplesListView = (ListView) findViewById(R.id.grammarExamplesListView);

		// increment number of
		App.vp.incrementNumberOfPassingsInARow();

		setContentView(R.layout.activity_grammar_introduction);
		prevButton = (Button) findViewById(R.id.buttonPrevious);

		// show first entry
		curRule = App.grammarDictionary.get(0);
		idx = 0;
		
		//media player for playing example
		twmp = new TextToVoiceMediaPlayer();
		showEntry(curRule);
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
			curRule = App.grammarDictionary.get(idx);
			showEntry(curRule);
		}
	}

	public void buttonSkipOnClick(View v) {
		goToNextPassingActivity();
	}

	public void goToNextPassingActivity() {

		Intent grammarTestActivity = new Intent(this, GrammarTestActivity.class);
		startActivity(grammarTestActivity);
	}

	@SuppressLint("SimpleDateFormat")
	private void showEntry(GrammarRule currentRule) {
		ruleTextView = (TextView) findViewById(R.id.ruleTextView);
		descriptionTextView = (TextView) findViewById(R.id.descriptionTextView);
		grammarExamplesListView = (ListView) findViewById(R.id.grammarExamplesListView);
		// set grammar info to the texViews
		ruleTextView.setText(currentRule.getRule());
		descriptionTextView.setText(currentRule.getDescription());

		// set color of entry
		int color = curRule.getIntColor();
		ruleTextView.setTextColor(color);

		// and write information to db
		currentRule.setLastView();
		currentRule.incrementShowntimes();
		App.grs.update(currentRule, getContentResolver());

		adapter = new ExamplesListViewAdapter(this,
				curRule.getAllExamplesText(), curRule.getAllExamplesRomaji(),
				curRule.getAllTranslations(), curRule.getIntColor());
		grammarExamplesListView.setAdapter(adapter);

	}

	public void buttonPreviousOnClick(View v) {
		if (idx > 0) {
			idx--;
			curRule = App.grammarDictionary.get(idx);
			showEntry(curRule);
		} else {
			prevButton.setEnabled(false);
		}
	}
	public void onPlayClick1(View v) {
		View v1 = (View) v.getParent();
		TextView romajiExample = (TextView) v1.findViewById(R.id.romaji);
		phrase = (String) romajiExample.getText();
		twmp.play(phrase);
	}
}
