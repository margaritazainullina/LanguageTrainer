package ua.hneu.languagetrainer.model.other;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Random;

import android.util.Log;

public class GiongoDictionary {
	private ArrayList<Giongo> entries = new ArrayList<Giongo>();

	public void add(Giongo giongo) {
		entries.add(giongo);
	}

	public void sortByLastViewedTime() {
		try {
			Collections.sort(this.entries, Giongo.GiongoComparator.LAST_VIEWED);
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
			Collections.sort(this.entries, Giongo.GiongoComparator.TIMES_SHOWN);
		} catch (Exception e) {
			Log.e("sortByTimesShown",
					e.getMessage() + " Caused:" + e.getCause());
		}
	}

	public void sortRandomly() {
		try {
			Collections.sort(this.entries, Giongo.GiongoComparator.RANDOM);
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

	public void remove(Giongo g) {
		entries.remove(g);
	}
	public Hashtable<GiongoExample, Giongo> getRandomExamplesWithWord(
			int size) {
		Hashtable<GiongoExample, Giongo> random = new Hashtable<GiongoExample, Giongo>();
		Random rn = new Random();
		//TODO: what if wasn't learned words number is less than size
		if (this.size() <= size) {
			for (Giongo gr : this.entries) {
				int j = rn.nextInt(gr.getExamples().size());
				if (!random.contains(gr.getExamples())) {
					random.put(gr.getExamples().get(j), gr);
				}
			}
		} else {
			int counter = 0;
			while (counter <= size) {
				int i = rn.nextInt(entries.size());
				Giongo g = entries.get(i);
				int j = rn.nextInt(g.getExamples().size());
				if (!random.contains(g.getExamples())) {
					random.put(g.getExamples().get(j), g);
					counter++;
				}
			}
		}
		return random;
	}

	public ArrayList<Giongo> getEntries() {
		return entries;
	}
}