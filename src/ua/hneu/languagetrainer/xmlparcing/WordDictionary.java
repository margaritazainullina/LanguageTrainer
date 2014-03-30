package ua.hneu.languagetrainer.xmlparcing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
public class WordDictionary {

	private ArrayList<DictionaryEntry> entries;

	//all entries with kanji, transcription, romaji and translation
	public ArrayList<DictionaryEntry> getEntries() {
		return entries;
	}

	// returns ArrayList of all kanji in dictionary (without empty ones)
	public ArrayList<String> getAllKanji() {
		ArrayList<String> kanji = new ArrayList<String>();
		for (DictionaryEntry e : entries) {
			if (e.getWord() != "")
				kanji.add(e.getWord());
		}
		return kanji;
	}

	// returns ArrayList of transcription and romaji in dictionary
	public ArrayList<String> getAllReadings() {
		ArrayList<String> readings = new ArrayList<String>();
		for (DictionaryEntry e : entries) {
			readings.add(e.getTranscription() + " " + e.getRomaji());
		}
		return readings;
	}

	//returns ArrayList of all translations in dictionary 
	public ArrayList<String> getAllTranslations() {
		ArrayList<String> translation = new ArrayList<String>();
		for (DictionaryEntry e : entries) {
			translation.add(e.getTranslations().toString());
		}
		return translation;
	}
	
	public void sort(){
		  Collections.sort(entries);
	}
	
	public void reverse(){
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
