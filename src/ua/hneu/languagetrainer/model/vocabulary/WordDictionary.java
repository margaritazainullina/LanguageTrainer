package ua.hneu.languagetrainer.model.vocabulary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class WordDictionary {

	private ArrayList<DictionaryEntry> entries;

	public DictionaryEntry getEntryById(int id) {
		for (DictionaryEntry entry : entries) {
			if (entry.getId() == id)
				return entry;
		}
		return null;
	}

	// all entries with kanji, transcription, romaji and translation
	public ArrayList<DictionaryEntry> getEntries() {
		return entries;
	}

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

	// returns Set with stated size of unique random entries from current
	// dictionary
	public Set<DictionaryEntry> getRandomEntries(int size,
			boolean kanjiIsNessesary) {
		Set<DictionaryEntry> random = new HashSet<DictionaryEntry>();
		Random rn = new Random();

		int numberOfEntriesWithKanji = 0;
		for (DictionaryEntry entry : entries) {
			if (entry.getKanji() != "")
				numberOfEntriesWithKanji++;
		}

		while (random.size() <= size) {
			int i = rn.nextInt(entries.size());
			boolean isEmpty = false;
			if(entries.get(i).getKanji().isEmpty())
				isEmpty=true;
			
			if (kanjiIsNessesary && !isEmpty) {
				random.add(entries.get(i));
			}
			if (!kanjiIsNessesary) {
				random.add(entries.get(i));
			}

			if (kanjiIsNessesary) {
				// if number of entries with kanji in current dictionary is less
				// than target size - return set of entries as is to avoid
				// eternal cycle
				if (numberOfEntriesWithKanji == random.size() - 1)
					return random;
			}
		}
		return random;
	}

	public void sort() {
		Collections.sort(entries);
	}

	public void reverse() {
		Collections.reverse(entries);
	}

	public void setEntries(ArrayList<DictionaryEntry> entries) {
		this.entries = entries;
	}

	public WordDictionary() {
		this.entries = new ArrayList<DictionaryEntry>();
	}

	public void add(DictionaryEntry e) {
		entries.add(e);
	}

	public void remove(DictionaryEntry e) {
		entries.remove(e);
	}

	public int size() {
		return entries.size();
	}

	public DictionaryEntry get(int idx) {
		return entries.get(idx);
	}

	public DictionaryEntry fetchRandom() {
		int a = new Random().nextInt(entries.size() - 1);
		return entries.get(a);
	}
	
	public ArrayList<String> getAllKanjiWithReadings() {
		ArrayList<String> readings = new ArrayList<String>();
		for (DictionaryEntry e : entries) {			
				readings.add(e.readingsToString());
		}
		return readings;
	}
}
