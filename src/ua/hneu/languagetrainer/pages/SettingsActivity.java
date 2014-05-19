package ua.hneu.languagetrainer.masterdetailflow;

import ua.hneu.edu.languagetrainer.R;
import ua.hneu.languagetrainer.App;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

public class SettingsFragment extends PreferenceFragment implements
		OnSharedPreferenceChangeListener {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Load the preferences from an XML resource
		addPreferencesFromResource(R.xml.settings);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {

		if (key.equals("level")) {
			int level = sharedPreferences.getInt(key, 5);
			App.goToLevel(level);
		}
		if (key.equals("showRomaji")) {
			String value = sharedPreferences.getString(key, "only_4_5");
			if (value.equals("always")) {
				App.isShowRomaji = true;
			} else if (value.equals("only_4_5")) {
				if (App.userInfo.getLevel() == 4
						|| App.userInfo.getLevel() == 5)
					App.isShowRomaji = true;
				else
					App.isShowRomaji = false;
			} else {
				App.isShowRomaji = false;
			}
		}
		if (key.equals("numOfEntries")) {
			String s = sharedPreferences.getString(key, "7");
			int num = Integer.parseInt(s);
			App.userInfo.setNumberOfEntriesInCurrentDict(num);
		}
		if (key.equals("numOfRepetations")) {
			String s = sharedPreferences.getString(key, "7");
			int num = Integer.parseInt(s);
			App.userInfo.setNumberOfRepeatationsForLearning(num);
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		getPreferenceManager().getSharedPreferences()
				.registerOnSharedPreferenceChangeListener(this);

	}

	@Override
	public void onPause() {
		getPreferenceManager().getSharedPreferences()
				.unregisterOnSharedPreferenceChangeListener(this);
		super.onPause();
	}
}