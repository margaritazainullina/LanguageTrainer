package ua.hneu.languagetrainer.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class WordDictionary {

	private ArrayList<DictionaryEntry> entries;

	public DictionaryEntry getWordWithId(int id){
		for (DictionaryEntry entry : entries) {
			if(entry.getId()==id) return entry;
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
			readings.add(e.getTranscription() + " " + e.getRomaji());
		}
		return readings;
	}

	// returns ArrayList of all translations in dictionary
	public ArrayList<String> getAllTranslations() {
		ArrayList<String> translation = new ArrayList<String>();
		for (DictionaryEntry e : entries) {
			translation.add(e.getTranslationsToString() + "");
		}
		return translation;
	}

	// returns Set with stated size of unique random entries from currrent
	// dictionary
	public Set<DictionaryEntry> getRandomEntries(int size) {
		Set<DictionaryEntry> random = new HashSet<DictionaryEntry>();

		Random rn = new Random();
		while (random.size() < size) {
			int i = rn.nextInt(entries.size());
			random.add(entries.get(i));
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
}
