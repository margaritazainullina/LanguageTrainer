package ua.hneu.languagetrainer.xmlparcing;

import java.util.List;

public class WordMeaning {

    private String transcription;
    private List<String> translations;
    private List<String> examples;

    public WordMeaning(String transcription, List<String> translations, List<String> examples) {
        this.transcription = transcription;
        this.translations = translations;
        this.examples = examples;
    }

    public String translationsToString() {
        String s = "";
        for (String tr : translations) {
            s += tr + ", ";
        }        
        if(s.length()>0) s = s.substring(0, s.length() - 2);
        return s;
    }

    public String examplesToString() {
        String s = "";
        for (String ex : examples) {
            s += ex + "\n";
        }
        return s;
    }

    public String getTranscription() {
        return transcription;
    }

    public void setTranscription(String transcription) {
        this.transcription = transcription;
    }

    public List<String> getTranslations() {
        return translations;
    }

    public void setTranslations(List<String> translations) {
        this.translations = translations;
    }

    public List<String> getExamples() {
        return examples;
    }

    public void setExamples(List<String> examples) {
        this.examples = examples;
    }
}
