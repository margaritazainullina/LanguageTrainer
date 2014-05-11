package ua.hneu.languagetrainer.model.grammar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import android.util.Log;

import ua.hneu.languagetrainer.model.vocabulary.VocabularyEntry;
import ua.hneu.languagetrainer.service.VocabularyService;

public class GrammarDictionary {

	private ArrayList<GrammarRule> entries;
/*
	public GrammarRule getEntryById(int id) {
		for (GrammarRule entry : entries) {
			if (entry.getId() == id)
				return entry;
		}
		return null;
	}*/

	// all entries with kanji, transcription, romaji and translation
	public ArrayList<GrammarRule> getEntries() {
		return entries;
	}
/*
	// returns ArrayList of all kanji in dictionary (without empty ones)
	public ArrayList<String> getAllKanji() {
		ArrayList<String> kanji = new ArrayList<String>();
		for (DictionaryEntry e : entries) {
			if (e.getKanji() != "")
				kanji.add(e.getKanji());
		}
		return kanji;
	}

	// returns ArrayList of all ids in dictionary (without empty ones)
	public ArrayList<Integer> getAllIds() {
		ArrayList<Integer> ids = new ArrayList<Integer>();
		for (DictionaryEntry e : entries) {
			if (e.getKanji() != "")
				ids.add(e.getId());
		}
		return ids;
	}

	// returns ArrayList of transcription and romaji in dictionary
	public ArrayList<String> getAllReadings() {
		ArrayList<String> readings = new ArrayList<String>();
		for (DictionaryEntry e : entries) {
			readings.add(e.readingsToString());
		}
		return readings;
	}

	// returns ArrayList of all translations in dictionary
	public ArrayList<String> getAllTranslations() {
		ArrayList<String> translation = new ArrayList<String>();
		for (DictionaryEntry e : entries) {
			translation.add(e.translationsToString() + "");
		}
		return translation;
	}

	public void addEntriesToDictionaryAndGetOnlyWithKanji(int size) {

		Random rn = new Random();
		// remove word without kanji to match target size
		int wordsWithKanji = 0;
		for (DictionaryEntry dictionaryEntry : this.entries) {
			if (!dictionaryEntry.getKanji().isEmpty())
				wordsWithKanji++;
		}
		if (wordsWithKanji >= size)
			return;
		else {
			for (int i = 0; i < this.entries.size(); i++) {
				// delete entries without kanji
				if (this.entries.get(i).getKanji().isEmpty()) {
					int j = rn.nextInt(VocabularyService.all.size());
					DictionaryEntry e = VocabularyService.all.get(j);
					if (e.getLearnedPercentage() != 1
							&& (!e.getKanji().isEmpty()))
						this.entries.set(i, e);
					wordsWithKanji++;
				}
			}
		}
		while (wordsWithKanji < size) {
			int i = rn.nextInt(VocabularyService.all.size());
			DictionaryEntry e = VocabularyService.all.get(i);
			// if the word is not learned
			if (e.getLearnedPercentage() != 1) {
				this.entries.add(e);
				wordsWithKanji++;
			}
		}
	}

	public void addEntriesToDictionaryAndGet(int size) {
		Set<DictionaryEntry> de = new HashSet<DictionaryEntry>();
		de.addAll(this.entries);
		Random rn = new Random();
		while (de.size() < size) {
			int i = rn.nextInt(VocabularyService.all.size());
			DictionaryEntry e = VocabularyService.all.get(i);
			// if the word is not learned
			if (e.getLearnedPercentage() != 1)
				de.add(e);
		}

		ArrayList<DictionaryEntry> l = new ArrayList<DictionaryEntry>();
		l.addAll(de);
		this.entries = l;
	}

	// returns Set with stated size of unique random entries from current
	// dictionary
	public Set<DictionaryEntry> getRandomEntries(int size,
			boolean kanjiIsNessesary) {
		Set<DictionaryEntry> random = new HashSet<DictionaryEntry>();
		Random rn = new Random();

		int numberOfEntriesWithKanji = 0;
		for (DictionaryEntry entry : entries) {
			if (!entry.getKanji().isEmpty())
				numberOfEntriesWithKanji++;
		}

		while (random.size() <= size) {
			int i = rn.nextInt(entries.size());
			boolean isEmpty = false;
			if (entries.get(i).getKanji().isEmpty())
				isEmpty = true;

			if (kanjiIsNessesary && !isEmpty) {
				random.add(entries.get(i));
			}
			if (!kanjiIsNessesary) {
				random.add(entries.get(i));
			}

			if (kanjiIsNessesary) {
				if (numberOfEntriesWithKanji == random.size() - 1) {
					this.addEntriesToDictionaryAndGetOnlyWithKanji(size);
					return random;
				}
			}
		}
		return random;
	}

	public void setEntries(ArrayList<DictionaryEntry> entries) {
		this.entries = entries;
	}*/

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
/*
	public ArrayList<String> getAllKanjiWithReadings() {
		ArrayList<String> readings = new ArrayList<String>();
		for (DictionaryEntry e : entries) {
			readings.add(e.readingsToString());
		}
		return readings;
	}*/

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
			Collections
					.sort(this.entries,
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


}
