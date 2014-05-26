package ua.hneu.languagetrainer.pages;

import ua.hneu.edu.languagetrainer.R;
import ua.hneu.languagetrainer.App;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceActivity;

public class SettingsActivity extends PreferenceActivity implements
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
		SharedPreferences settings = getSharedPreferences("appSettings",
				MODE_PRIVATE);
		Editor editor = settings.edit();
		if (key.equals("level")) {
			String levelStr = sharedPreferences.getString(key, "N5");
			levelStr = levelStr.replace("N", "");
			int level = Integer.parseInt(levelStr);
			if (level != App.userInfo.getLevel())
				App.goToLevel(level);
			editor.putInt("level", level);
		}
		if (key.equals("showRomaji")) {
			String value = sharedPreferences.getString(key, "only_4_5");
			if (value.equals("always")) {
				App.isShowRomaji = true;
				editor.putString("showRomaji", value);
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
			editor.putInt("numOfEntries", num);
			App.numberOfEntriesInCurrentDict = num;
		}
		if (key.equals("numOfRepetations")) {
			String s = sharedPreferences.getString(key, "7");
			int num = Integer.parseInt(s);
			editor.putInt("numOfRepetations", num);
			App.numberOfRepeatationsForLearning = num;
		}
		editor.apply();
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