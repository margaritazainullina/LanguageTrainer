package ua.hneu.languagetrainer.masterdetailflow;

import ua.hneu.edu.languagetrainer.R;
import ua.hneu.languagetrainer.App;
import ua.hneu.languagetrainer.pages.GreetingActivity;
import ua.hneu.languagetrainer.pages.SettingsActivity;
import ua.hneu.languagetrainer.pages.counterwords.CounterWordsIntroductionActivity;
import ua.hneu.languagetrainer.pages.giongo.GiongoIntroductionActivity;
import ua.hneu.languagetrainer.pages.grammar.GrammarIntroductionActivity;
import ua.hneu.languagetrainer.pages.test.MockTestActivity;
import ua.hneu.languagetrainer.pages.vocabulary.WordIntroductionActivity;
import ua.hneu.languagetrainer.service.CounterWordsService;
import ua.hneu.languagetrainer.service.GiongoService;
import ua.hneu.languagetrainer.service.GrammarService;
import ua.hneu.languagetrainer.service.VocabularyService;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

public class MenuListActivity extends FragmentActivity implements
		MenuListFragment.Callbacks {
	private static final int MY_DATA_CHECK_CODE = 0;
	private boolean mTwoPane;
	TextView textViewUserInfo;
	RatingBar ratingBar;
	ListView sectionsListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_list);

		// if it isn't first launch of app - start greeting activity
		if (App.userInfo == null) {
			// for first time when app is launched - set default preferences
			App.editor.putString("showRomaji", "only_4_5");
			App.editor.putInt("numOfRepetations", 7);
			App.editor.putInt("numOfEntries", 7);
			App.editor.apply();
			Intent greetingIntent = new Intent(this, GreetingActivity.class);
			startActivity(greetingIntent);
			return;
		}
		if (findViewById(R.id.item_detail_container) != null) {
			mTwoPane = true;
			((MenuListFragment) getSupportFragmentManager().findFragmentById(
					R.id.item_list)).setActivateOnItemClick(true);
			// set user level on main activity
			textViewUserInfo = (TextView) findViewById(R.id.textViewUserInfo);
			textViewUserInfo.setText(this.getString(R.string.your_level)
					+ App.userInfo.getLevel());
			ratingBar = (RatingBar) findViewById(R.id.levelRatingBar);
			ratingBar.setRating(6 - App.userInfo.getLevel());
		} else {
			mTwoPane = false;
			((MenuListFragment) getSupportFragmentManager().findFragmentById(
					R.id.item_list)).setActivateOnItemClick(true);

			// set user level on main activity
			textViewUserInfo = (TextView) findViewById(R.id.textViewUserInfo);
			textViewUserInfo.setText(this.getString(R.string.your_level)
					+ App.userInfo.getLevel());
			ratingBar = (RatingBar) findViewById(R.id.levelRatingBar);
			ratingBar.setRating(6 - App.userInfo.getLevel());
		}
		Log.i("mTwoPane", "mTwoPane - " + mTwoPane);
	}

	@Override
	public void onItemSelected(String id) {
		Log.i("ItemListActivity", "ItemDetailActivity.onItemSelected()");

		if (mTwoPane) {
			VocabularyActivityFragment vocabularyFragment = new VocabularyActivityFragment();
			GrammarActivityFragment grammarFragment = new GrammarActivityFragment();
			TestActivityFragment testFragment = new TestActivityFragment();
			CounterWordsActivityFragment counterWordsFragment = new CounterWordsActivityFragment();
			GiongoActivityFragment giongoFragment = new GiongoActivityFragment();

			// if selected Vocabulary
			if (id == "vocabulary") {
				getSupportFragmentManager()
						.beginTransaction()
						.replace(R.id.item_detail_container, vocabularyFragment)
						.commit();
			}
			// if selected Grammar
			if (id == "grammar") {
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.item_detail_container, grammarFragment)
						.commit();
			}
			// if selected Tests
			if (id == "mock_tests") {
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.item_detail_container, testFragment)
						.commit();
			}
			// if selected counter words
			if (id == "counter_words") {
				getSupportFragmentManager()
						.beginTransaction()
						.replace(R.id.item_detail_container,
								counterWordsFragment).commit();
			}
			// if selected counter words
			if (id == "giongo") {
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.item_detail_container, giongoFragment)
						.commit();
			}
			// if selected settings

			if (id == "settings") {
				Intent settings = new Intent(this, SettingsActivity.class);
				startActivity(settings);
			}

		} else {
			// if selected Vocabulary
			if (id == "vocabulary") {

				Intent intent = new Intent(this, VocabularyActivity.class);
				intent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
				startActivityForResult(intent, MY_DATA_CHECK_CODE);
			}
			// if selected Grammar
			if (id == "grammar") {
				Intent intent = new Intent(this, GrammarActivity.class);
				startActivity(intent);
			}
			// if selected Tests
			if (id == "mock_tests") {
				Intent intent = new Intent(this, TestActivity.class);
				startActivity(intent);
			}
			// if selected counter words
			if (id == "counter_words") {
				Intent intent = new Intent(this, CounterWordsActivity.class);
				startActivity(intent);
			}
			// if selected counter words
			if (id == "giongo") {
				Intent intent = new Intent(this, GiongoActivity.class);
				startActivity(intent);
			}
			// if selected settings

			if (id == "settings") {
				Intent settings = new Intent(this, SettingsActivity.class);
				startActivity(settings);
			}
		}
	}
//!!!
	public void onClickPracticeVocabulary(View v) {
		// load vocabulary
		App.vocabularyDictionary = VocabularyService.createCurrentDictionary(
				App.userInfo.getLevel(), App.numberOfEntriesInCurrentDict,
				App.cr);
		Intent intent = new Intent(this, WordIntroductionActivity.class);
		startActivity(intent);
	}

	public void onClickPracticeGrammar(View v) {
		// load grammar
		App.grammarDictionary = GrammarService.createCurrentDictionary(
				App.userInfo.getLevel(), App.numberOfEntriesInCurrentDict,
				App.cr);
		Intent intent = new Intent(this, GrammarIntroductionActivity.class);
		startActivity(intent);

	}

	public void onClickPracticeCounterWords(View v) {
		// load counter words
		CounterWordsService cws = new CounterWordsService();
		App.counterWordsDictionary = cws.createCurrentDictionary(
				CounterWordsActivityFragment.selectedSection,
				App.numberOfEntriesInCurrentDict, App.cr);

		Intent intent = new Intent(this, CounterWordsIntroductionActivity.class);
		startActivity(intent);

	}

	public void onClickPracticeGiongo(View v) {
		// load giongo
		GiongoService gs = new GiongoService();
		App.giongoWordsDictionary = gs.createCurrentDictionary(
				App.numberOfEntriesInCurrentDict, App.cr);

		Intent intent = new Intent(this, GiongoIntroductionActivity.class);
		startActivity(intent);

	}

	public void onClickPassTest(View v) {
		// load test
		Intent intent = new Intent(this, MockTestActivity.class);
		intent.putExtra("testName", TestActivityFragment.testName);
		startActivity(intent);

	}
}