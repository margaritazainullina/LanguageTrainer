package ua.hneu.languagetrainer.model.other;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import ua.hneu.languagetrainer.App;
import ua.hneu.languagetrainer.service.CounterWordsService;
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

	public void remove(CounterWord g) {
		entries.remove(g);
	}

	public Set<CounterWord> getRandomEntries(int size) {
		Set<CounterWord> random = new HashSet<CounterWord>();
		Random rn = new Random();
	
		while (random.size() <= size) {
			int i = rn.nextInt(entries.size());
			if (entries.get(i).getLearnedPercentage()<1) {
				random.add(entries.get(i));
			}
		}
		return random;
	}

	public ArrayList<CounterWord> getEntries() {
		return entries;
	}

	public ArrayList<String> getAllTranslations() {
		ArrayList<String> translation = new ArrayList<String>();
		for (CounterWord e : entries) {
			translation.add(e.getTranslation() + "");
		}
		return translation;
	}
	
	public ArrayList<String> getAllWords() {
		ArrayList<String> words = new ArrayList<String>();
		for (CounterWord e : entries) {
			words.add(e.getWord());
		}
		return words;
	}
	
	public ArrayList<String> getAllTranscriptions() {
		ArrayList<String> translation = new ArrayList<String>();
		for (CounterWord e : entries) {
			translation.add(e.getTranslation());
		}
		return translation;
	}
	
	public void addEntriesToDictionaryAndGet(int size) {
		Random rn = new Random();

		while (this.size() < size) {
			int i = rn.nextInt(App.allCounterWordsDictionary.size());
			CounterWord cw = App.allCounterWordsDictionary.get(i);
			// if the word is not learned
			if (cw.getLearnedPercentage() < 1) {
				this.entries.add(cw);
			}
		}
	}

}
