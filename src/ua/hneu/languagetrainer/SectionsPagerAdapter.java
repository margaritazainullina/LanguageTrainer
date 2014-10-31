package ua.hneu.languagetrainer;

import ua.hneu.languagetrainer.masterdetailflow.GrammarActivityFragment;
import ua.hneu.languagetrainer.masterdetailflow.VocabularyActivityFragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.Fragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(android.support.v4.app.FragmentManager fm) {
        super(fm);  
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new VocabularyActivityFragment();
                break;
            case 1:
                fragment = new GrammarActivityFragment();
                break;  
                }       
		return fragment;
    }
    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}
