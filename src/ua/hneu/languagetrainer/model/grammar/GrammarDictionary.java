package ua.hneu.languagetrainer.model.grammar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Random;
import java.util.Set;

import android.util.Log;

import ua.hneu.languagetrainer.service.GrammarService;
import ua.hneu.languagetrainer.service.VocabularyService;

public class GrammarDictionary {

	private ArrayList<GrammarRule> entries;

	public ArrayList<GrammarRule> getEntries() {
		return entries;
	}

	public GrammarDictionary() {
		this.entries = new ArrayList<GrammarRule>();
	}

	public void add(GrammarRule e) {
		entries.add(e);
	}

	public void remove(GrammarRule e) {
		entries.remove(e);
	}

	public int size() {
		return entries.size();
	}

	public GrammarRule get(int idx) {
		return entries.get(idx);
	}

	public GrammarRule fetchRandom() {
		int a = new Random().nextInt(entries.size() - 1);
		return entries.get(a);
	}

	public void sortByLastViewedTime() {
		try {
			Collections.sort(this.entries,
					GrammarRule.GrammarRuleComparator.LAST_VIEWED);
		} catch (Exception e) {
			Log.e("sortByLastViewedTime",
					e.getMessage() + " Caused:" + e.getCause());
		}
	}

	public void sortByPercentage() {
		try {
			Collections.sort(this.entries,
					GrammarRule.GrammarRuleComparator.LEARNED_PERCENTAGE);
		} catch (Exception e) {
			Log.e("sortByPercentage",
					e.getMessage() + " Caused:" + e.getCause());
		}
	}

	public void sortByTimesShown() {
		try {
			Collections.sort(this.entries,
					GrammarRule.GrammarRuleComparator.TIMES_SHOWN);
		} catch (Exception e) {
			Log.e("sortByTimesShown",
					e.getMessage() + " Caused:" + e.getCause());
		}
	}

	public void sortRandomly() {
		try {
			Collections.sort(this.entries,
					GrammarRule.GrammarRuleComparator.RANDOM);
		} catch (Exception e) {
			Log.e("sortRandomly", e.getMessage() + " Caused:" + e.getCause());
		}
	}

	public Set<GrammarRule> getRandomEntries(int size) {
		Set<GrammarRule> random = new HashSet<GrammarRule>();
		Random rn = new Random();

		while (random.size() <= size) {
			int i = rn.nextInt(entries.size());
			random.add(entries.get(i));

		}
		return random;
	}

	public Hashtable<GrammarExample, GrammarRule> getRandomExamplesWithRule(
			int size) {
		Hashtable<GrammarExample, GrammarRule> random = new Hashtable<GrammarExample, GrammarRule>();
		Random rn = new Random();
		//TODO: what if wasn't learned words number is less than size
		if (this.size() <= size) {
			for (GrammarRule gr : this.entries) {
				int j = rn.nextInt(gr.getExamples().size());
				if (!random.contains(gr.getExamples())) {
					random.put(gr.getExamples().get(j), gr);
				}
			}
		} else {
			int counter = 0;
			while (counter <= size) {
				int i = rn.nextInt(entries.size());
				GrammarRule g = entries.get(i);
				int j = rn.nextInt(g.getExamples().size());
				if (!random.contains(g.getExamples())) {
					random.put(g.getExamples().get(j), g);
					counter++;
				}
			}
		}
		return random;
	}

	public void addEntriesToDictionaryAndGet(int size) {
		Random rn = new Random();

		while (this.size() < size) {
			int i = rn.nextInt(GrammarService.all.size());
			GrammarRule gr = GrammarService.all.get(i);
			// if the word is not learned
			if (gr.getLearnedPercentage() < 1) {
				this.entries.add(gr);
			}
		}
	}
}
