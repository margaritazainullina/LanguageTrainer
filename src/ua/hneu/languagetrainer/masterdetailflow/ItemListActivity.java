package ua.hneu.languagetrainer.masterdetailflow;

import ua.hneu.edu.languagetrainer.R;
import ua.hneu.languagetrainer.App;
import ua.hneu.languagetrainer.pages.vocabulary.WordIntroductionActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getApplication().setTheme(R.style.AppTheme);
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_item_list);

		Log.i("ItemListActivity", "ItemListActivity.onCreate()");

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

			// set userlevel on main activity
			TextView textViewUserInfo = (TextView) findViewById(R.id.textViewUserInfo);
			textViewUserInfo.setText("Your level is: N" + App.userInfo.getUserLevel());
		} else {

		}
		Log.i("mTwoPane", "mTwoPane - " + mTwoPane);
		// TODO: If exposing deep links into your app, handle intents here.
	}

	/**
	 * Callback method from {@link ItemListFragment.Callbacks} indicating that
	 * the item with the given ID was selected.
	 */
	@Override
	public void onItemSelected(String id) {
		Log.i("ItemListActivity", "ItemDetailActivity.onItemSelected()");
		// switch themes
		switch (App.userInfo.getUserLevel()) {
		case 0: {
			getApplicationContext().setTheme(R.style.AppTheme);
			break;
		}
		case 1: {
			getApplicationContext().setTheme(R.style.N1Theme);
			break;
		}
		case 2: {
			getApplicationContext().setTheme(R.style.N2Theme);
			break;
		}
		case 3: {
			getApplicationContext().setTheme(R.style.N3Theme);
			break;
		}
		case 4: {
			getApplicationContext().setTheme(R.style.N4Theme);
			break;
		}
		case 5: {
			getApplicationContext().setTheme(R.style.N5Theme);
			break;
		}
		}

		if (mTwoPane) {
			// In two-pane mode, show the detail view in this activity by
			// adding or replacing the detail fragment using a
			// fragment transaction.
			Bundle arguments = new Bundle();
			arguments.putString(VocabularyActivityFragment.ARG_ITEM_ID, id);
			VocabularyActivityFragment vocabularyFragment = new VocabularyActivityFragment();
			GrammarActivityFragment grammarFragment = new GrammarActivityFragment();
			vocabularyFragment.setArguments(arguments);
			grammarFragment.setArguments(arguments);
			// Loading fragments accordingly to selected menu items
			// if selected Vocabulary
			if (id == "1") {
				getSupportFragmentManager()
						.beginTransaction()
						.replace(R.id.item_detail_container, vocabularyFragment)
						.commit();
			}
			// if selected Grammar
			else {
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.item_detail_container, grammarFragment)
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

			if (id == "1") {
				Intent detailIntent = new Intent(this,
						VocabularyActivityFragment.class);
				startActivity(detailIntent);
			}
		}
	}

	public void onClickPracticeVocabulary(View v) {
		// load vocabulary dictionary from db
		

		// DictionaryEntry de = VocabularyService.getEntryById(5,
		// getContentResolver());

		Intent intent = new Intent(this, WordIntroductionActivity.class);
		startActivity(intent);

		// stub!!!
		/*
		 * WordDictionary dict = new WordDictionary();
		 * App.setCurrentDictionary(new WordDictionary()); String xml =
		 * DictUtil.readFile(this, "test.xml"); dict =
		 * DictUtil.ParseVocabularyXml(xml); // replace fetching of entries with
		 * some more complicated method for (int i = 0; i < 7; i++) {
		 * App.getCurrentDictionary().add(dict.fetchRandom()); } Intent intent =
		 * new Intent(this, WordIntroductionActivity.class);
		 * startActivity(intent);
		 */
	}
}
