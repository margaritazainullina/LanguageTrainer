package ua.hneu.languagetrainer.pages;

import ua.hneu.edu.languagetrainer.R;
import ua.hneu.languagetrainer.App;
import ua.hneu.languagetrainer.masterdetailflow.MenuListFragment;
import ua.hneu.languagetrainer.pages.vocabulary.AllVocabulary;
import ua.hneu.languagetrainer.pages.vocabulary.WordIntroductionActivity;
import ua.hneu.languagetrainer.service.VocabularyService;
import ua.hneu.languagetrainer.tabsswipe.TabsPagerAdapter;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

public class MainActivity extends FragmentActivity implements
		ActionBar.TabListener {

	private ViewPager viewPager;
	private TabsPagerAdapter mAdapter;
	private ActionBar actionBar;

	// Tab titles
	// TODO from resources

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		String[] tabs = { getResources().getString(R.string.vocabulary),
				getResources().getString(R.string.grammar),
				getResources().getString(R.string.mock_tests),
				getResources().getString(R.string.giongo),
				getResources().getString(R.string.counter_words) };

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

		// Initilization
		viewPager = (ViewPager) findViewById(R.id.pager);
		actionBar = getActionBar();
		mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

		viewPager.setAdapter(mAdapter);
		actionBar.setHomeButtonEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Adding Tabs
		for (String tab_name : tabs) {
			actionBar.addTab(actionBar.newTab().setText(tab_name)
					.setTabListener(this));
		}
		/**
		 * on swiping the viewpager make respective tab selected
		 * */
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// on changing the page
				// make respected tab selected
				actionBar.setSelectedNavigationItem(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}

	public void onClickPracticeVocabulary(View v) {
		// load vocabulary
		App.vocabularyDictionary = VocabularyService.createCurrentDictionary(
				App.userInfo.getLevel(), App.numberOfEntriesInCurrentDict,
				App.cr);
		Intent intent = new Intent(this, WordIntroductionActivity.class);
		startActivity(intent);
	}

	public void onClickAllVocabulary(View v) {
		// load vocabulary
		App.vocabularyDictionary = VocabularyService.createCurrentDictionary(
				App.userInfo.getLevel(), App.numberOfEntriesInCurrentDict,
				App.cr);
		Intent intent = new Intent(this, AllVocabulary.class);
		startActivity(intent);
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// on tab selected
		// show respected fragment view
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	}
}
