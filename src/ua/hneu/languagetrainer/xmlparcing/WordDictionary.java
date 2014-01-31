package ua.hneu.languagetrainer.xmlparcing;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WordDictionary {

    private List<DictionaryEntry> entries;

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
