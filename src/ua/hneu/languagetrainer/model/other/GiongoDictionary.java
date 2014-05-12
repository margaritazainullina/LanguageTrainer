package ua.hneu.languagetrainer.model.other;

import java.util.ArrayList;
import java.util.Collections;

import android.util.Log;


public class GiongoDictionary {
	private ArrayList<Giongo> entries;

	public void add(Giongo giongo) {
		entries.add(giongo);
	}	
	public void sortByLastViewedTime() {
		try {
			Collections.sort(this.entries,
					Giongo.GiongoComparator.LAST_VIEWED);
		} catch (Exception e) {
			Log.e("sortByLastViewedTime",
					e.getMessage() + " Caused:" + e.getCause());
		}
	}

	public void sortByPercentage() {
		try {
			Collections.sort(this.entries,
					Giongo.GiongoComparator.LEARNED_PERCENTAGE);
		} catch (Exception e) {
			Log.e("sortByPercentage",
					e.getMessage() + " Caused:" + e.getCause());
		}
	}

	public void sortByTimesShown() {
		try {
			Collections.sort(this.entries,
					Giongo.GiongoComparator.TIMES_SHOWN);
		} catch (Exception e) {
			Log.e("sortByTimesShown",
					e.getMessage() + " Caused:" + e.getCause());
		}
	}

	public void sortRandomly() {
		try {
			Collections.sort(this.entries,
					Giongo.GiongoComparator.RANDOM);
		} catch (Exception e) {
			Log.e("sortRandomly", e.getMessage() + " Caused:" + e.getCause());
		}
	}

	public int size() {
		return entries.size();
	}

	public Giongo get(int i) {
		return entries.get(i);
	}
}
