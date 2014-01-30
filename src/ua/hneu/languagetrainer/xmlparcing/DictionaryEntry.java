package ua.hneu.languagetrainer.xmlparcing;

import java.util.List;

public class DictionaryEntry {

    private int id;
    private String word;
    private List<WordMeaning> meanings;

    public DictionaryEntry(int id, String word, List<WordMeaning> meanings) {
        this.id = id;
        this.word = word;
        this.meanings = meanings;
    }

    @Override
    public String toString() {
        String s = "";
        for (WordMeaning wm : meanings) {
            if (!"".equals(wm.getTranscription())) {
                s += " [" + wm.getTranscription() + "]" + "\n" + wm.translationsToString() + "\n" + wm.examplesToString();
            } else { 
                s += wm.translationsToString() + "\n" + wm.examplesToString();
            }

        }
        return s;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public List<WordMeaning> getMeanings() {
        return meanings;
    }

    public void setMeanings(List<WordMeaning> meanings) {
        this.meanings = meanings;
    }
}
