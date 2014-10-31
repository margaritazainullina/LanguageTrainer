package ua.hneu.languagetrainer.tabsswipe;

import ua.hneu.languagetrainer.masterdetailflow.CounterWordsActivityFragment;
import ua.hneu.languagetrainer.masterdetailflow.GiongoActivityFragment;
import ua.hneu.languagetrainer.masterdetailflow.GrammarActivityFragment;
import ua.hneu.languagetrainer.masterdetailflow.TestActivityFragment;
import ua.hneu.languagetrainer.masterdetailflow.VocabularyActivityFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			return new VocabularyActivityFragment();
		case 1:
			return new GrammarActivityFragment();
		case 2:
			return new TestActivityFragment();
		case 3:
			return new GiongoActivityFragment();
		case 4:
			return new CounterWordsActivityFragment();
		}

		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 5;
	}

}
