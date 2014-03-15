package ua.hneu.languagetrainer.xmlparcing;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WordDictionary {

    private ArrayList<DictionaryEntry> entries;

    public ArrayList<DictionaryEntry> getEntries() {
		return entries;
	}
    
    public ArrayList<String> getAllKanji() {
    	ArrayList<String> kanji = new ArrayList<String>();
    	for (DictionaryEntry e : entries) {
    		kanji.add(e.getWord());
		}
		return kanji;
	}
    
    public ArrayList<String> getAllReadings() {
    	ArrayList<String> readings = new ArrayList<String>();
    	for (DictionaryEntry e : entries) {
    		readings.add(e.getTranscription()+" "+e.getRomaji());
		}
		return readings;
	}
    
    public ArrayList<String> getAllTranslations() {
    	ArrayList<String> translation = new ArrayList<String>();
    	for (DictionaryEntry e : entries) {
    		translation.add(e.getTranslations().toString());
		}
		return translation;
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
