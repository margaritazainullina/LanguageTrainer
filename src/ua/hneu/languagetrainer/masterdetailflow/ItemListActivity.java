package ua.hneu.languagetrainer.masterdetailflow;

import ua.hneu.edu.languagetrainer.R;
import ua.hneu.languagetrainer.App;
import ua.hneu.languagetrainer.pages.vocabulary.WordIntroductionActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
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
			textViewUserInfo= (TextView) findViewById(R.id.textViewUserInfo);
			textViewUserInfo.setText(this.getString(R.string.your_level) + App.userInfo.getLevel());
			ratingBar = (RatingBar)findViewById(R.id.levelRatingBar);				
			ratingBar.setRating(6-App.userInfo.getLevel());
			
		} else {

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
			else {
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.item_detail_container, grammarFragment)
						.commit();
			}

		} else {
			// In single-pane mode, simply start the detail activity
			// for the selected item ID.
			
			  Intent detailIntent = new Intent(this,
			  VocabularyActivityFragment.class);
			  detailIntent.putExtra(VocabularyActivityFragment.ARG_ITEM_ID,
			 id); startActivity(detailIntent);
			 

			if (id == "1") {
				detailIntent = new Intent(this,
						VocabularyActivityFragment.class);
				startActivity(detailIntent);
			}
		}
	}

	public void onClickPracticeVocabulary(View v) {
		Intent intent = new Intent(this, WordIntroductionActivity.class);
		startActivity(intent);
	}
}
