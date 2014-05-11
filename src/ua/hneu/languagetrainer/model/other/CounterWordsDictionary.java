package ua.hneu.languagetrainer.model.other;

import java.util.ArrayList;
import java.util.Collections;

import ua.hneu.languagetrainer.model.vocabulary.VocabularyEntry;
import android.util.Log;

public class CounterWordsDictionary {
	private ArrayList<CounterWord> entries = new ArrayList<CounterWord>();
	
	public void add(CounterWord counterWord) {
		entries.add(counterWord);
	}

	public void sortByLastViewedTime() {
		try {
			Collections.sort(this.entries,
					CounterWord.CounterWordComparator.LAST_VIEWED);
		} catch (Exception e) {
			Log.e("sortByLastViewedTime",
					e.getMessage() + " Caused:" + e.getCause());
		}
	}

	public void sortByPercentage() {
		try {
			Collections.sort(this.entries,
					CounterWord.CounterWordComparator.LEARNED_PERCENTAGE);
		} catch (Exception e) {
			Log.e("sortByPercentage",
					e.getMessage() + " Caused:" + e.getCause());
		}
	}

	public void sortByTimesShown() {
		try {
			Collections.sort(this.entries,
					CounterWord.CounterWordComparator.TIMES_SHOWN);
		} catch (Exception e) {
			Log.e("sortByTimesShown",
					e.getMessage() + " Caused:" + e.getCause());
		}
	}

	public void sortRandomly() {
		try {
			Collections.sort(this.entries,
					CounterWord.CounterWordComparator.RANDOM);
		} catch (Exception e) {
			Log.e("sortRandomly", e.getMessage() + " Caused:" + e.getCause());
		}
	}

	public int size() {
		return entries.size();
	}

	public CounterWord get(int i) {
		return entries.get(i);
	}
}
