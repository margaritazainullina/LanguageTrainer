package ua.hneu.languagetrainer.masterdetailflow;

import ua.hneu.edu.languagetrainer.R;
import ua.hneu.languagetrainer.App;
import ua.hneu.languagetrainer.pages.GreetingActivity;
import ua.hneu.languagetrainer.pages.counterwords.CounterWordsIntroductionActivity;
import ua.hneu.languagetrainer.pages.giongo.GiongoIntroductionActivity;
import ua.hneu.languagetrainer.pages.grammar.GrammarIntroductionActivity;
import ua.hneu.languagetrainer.pages.test.TestActivity;
import ua.hneu.languagetrainer.pages.vocabulary.WordIntroductionActivity;
import ua.hneu.languagetrainer.service.CounterWordsService;
import ua.hneu.languagetrainer.service.GiongoService;
import ua.hneu.languagetrainer.service.GrammarService;
import ua.hneu.languagetrainer.service.TestService;
import ua.hneu.languagetrainer.service.VocabularyService;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * An activity representing a list of Items. This activity has different
 * presentations for handset and tablet-size devices. On handsets, the activity
 * presents a list of items, which when touched, lead to a
 * {@link ItemDetailActivity} representing item details. On tablets, the
 * activity presents the list of items and item details side-by-side using two
 * vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link ItemListFragment} and the item details (if present) is a
 * {@link VocabularyActivityFragment}.
 * <p>
 * This activity also implements the required {@link ItemListFragment.Callbacks}
 * interface to listen for item selections.
 */
public class ItemListActivity extends FragmentActivity implements
		ItemListFragment.Callbacks {

	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
	 * device.
	 */
	private boolean mTwoPane;
	TextView textViewUserInfo;
	RatingBar ratingBar;
	ListView sectionsListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_item_list);

		Log.i("ItemListActivity", "ItemListActivity.onCreate()");

		// if it isn't first launch of app - start greeting activity
		if (App.userInfo == null) {
			Intent greetingIntent = new Intent(this, GreetingActivity.class);
			startActivity(greetingIntent);
			return;
		}

		if (findViewById(R.id.item_detail_container) != null) {
			// The detail container view will be present only in the
			// large-screen layouts (res/values-large and
			// res/values-sw600dp). If this view is present, then the
			// activity should be in two-pane mode.
			mTwoPane = true;

			// In two-pane mode, list items should be given the
			// 'activated' state when touched.
			((ItemListFragment) getSupportFragmentManager().findFragmentById(
					R.id.item_list)).setActivateOnItemClick(true);

			// set userlevel on vocabulary fragment
			textViewUserInfo = (TextView) findViewById(R.id.textViewUserInfo);
			textViewUserInfo.setText(this.getString(R.string.your_level)
					+ App.userInfo.getLevel());
			ratingBar = (RatingBar) findViewById(R.id.levelRatingBar);
			ratingBar.setRating(6 - App.userInfo.getLevel());

			sectionsListView = (ListView) findViewById(R.id.sectionsListView);

		} else {
			mTwoPane = false;
			// In two-pane mode, list items should be given the
			// 'activated' state when touched.
			((ItemListFragment) getSupportFragmentManager().findFragmentById(
					R.id.item_list)).setActivateOnItemClick(true);

			// set userlevel on main activity
			textViewUserInfo = (TextView) findViewById(R.id.textViewUserInfo);
			textViewUserInfo.setText(this.getString(R.string.your_level)
					+ App.userInfo.getLevel());
			ratingBar = (RatingBar) findViewById(R.id.levelRatingBar);
			ratingBar.setRating(6 - App.userInfo.getLevel());
		}
		Log.i("mTwoPane", "mTwoPane - " + mTwoPane);
	}

	/**
	 * Callback method from {@link ItemListFragment.Callbacks} indicating that
	 * the item with the given ID was selected.
	 */
	@Override
	public void onItemSelected(String id) {
		Log.i("ItemListActivity", "ItemDetailActivity.onItemSelected()");

		if (mTwoPane) {
			// In two-pane mode, show the detail view in this activity by
			// adding or replacing the detail fragment using a
			// fragment transaction.
			Bundle arguments = new Bundle();
			arguments.putString(VocabularyActivityFragment.ARG_ITEM_ID, id);
			VocabularyActivityFragment vocabularyFragment = new VocabularyActivityFragment();
			GrammarActivityFragment grammarFragment = new GrammarActivityFragment();
			TestActivityFragment testFragment = new TestActivityFragment();
			CounterWordsActivityFragment counterWordsFragment = new CounterWordsActivityFragment();
			GiongoActivityFragment giongoFragment = new GiongoActivityFragment();
			vocabularyFragment.setArguments(arguments);
			grammarFragment.setArguments(arguments);
			// Loading fragments accordingly to selected menu items
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

		} else {
			// In single-pane mode, simply start the detail activity
			// for the selected item ID.

			/*
			 * Intent detailIntent = new Intent(this,
			 * VocabularyActivityFragment.class);
			 * detailIntent.putExtra(VocabularyActivityFragment.ARG_ITEM_ID,
			 * id); startActivity(detailIntent);
			 */

			// if vocabulary item selected
			if (id == "1") {
				Intent detailIntent = new Intent(this,
						VocabularyActivityFragment.class);
				startActivity(detailIntent);
			}
			if (id == "2") {
				Intent detailIntent = new Intent(this,
						GrammarActivityFragment.class);
				startActivity(detailIntent);
			}
		}
	}

	public void onClickPracticeVocabulary(View v) {
		// load vocabulary
		App.vocabularyDictionary = VocabularyService.createCurrentDictionary(
				App.userInfo.getLevel(),
				App.userInfo.getNumberOfEntriesInCurrentDict(), App.cr);
		Intent intent = new Intent(this, WordIntroductionActivity.class);
		startActivity(intent);
	}

	public void onClickPracticeGrammar(View v) {
		// load grammar
		App.grammarDictionary = GrammarService.createCurrentDictionary(
				App.userInfo.getLevel(),
				App.userInfo.getNumberOfEntriesInCurrentDict(), App.cr);
		Intent intent = new Intent(this, GrammarIntroductionActivity.class);
		startActivity(intent);

	}

	public void onClickPracticeCounterWords(View v) {
		// load counter words
		CounterWordsService cws = new CounterWordsService();
		App.counterWordsDictionary = cws.createCurrentDictionary(
				CounterWordsActivityFragment.selectedSection,
				App.userInfo.getNumberOfEntriesInCurrentDict(), App.cr);
		
		  Intent intent = new Intent(this, CounterWordsIntroductionActivity.class);
		  startActivity(intent);
		 
	}

	public void onClickPracticeGiongo(View v) {
		// load giongo
		GiongoService gs = new GiongoService();
		App.giongoWordsDictionary = gs.createCurrentDictionary(
				App.userInfo.getNumberOfEntriesInCurrentDict(), App.cr);
		
		Intent intent = new Intent(this, GiongoIntroductionActivity.class);
		  startActivity(intent);
		 
	}
	
	public void onClickPassTest(View v) {
		// load test
		Intent intent = new Intent(this, TestActivity.class);
		intent.putExtra("testName", TestActivityFragment.testName);
		startActivity(intent);
		 
	}
}