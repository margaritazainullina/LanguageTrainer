package ua.hneu.languagetrainer.model.vocabulary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DictionaryEntry implements Comparable{

	private int id;
	private String kanji;
	private int level;
	private String examples;
	private String lastview;
	private int showntimes;	
	private double learnedPercentage;
	private WordMeaning meaning;
	
	public DictionaryEntry(int id, String kanji, int level, String transcription,
			String romaji, List<String> translations, String examples, double percentage, String lastview, int  showntimes) {
						
		WordMeaning meaning = new WordMeaning(transcription, romaji,translations);
		this.id = id;
		this.kanji = kanji;
		this.level=level;
		this.examples=examples;
		this.lastview=lastview;
		this.showntimes=showntimes;	
		this.learnedPercentage=percentage;
		this.meaning = meaning;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		sb.append(meaning.transcription);
		sb.append(", ");
		sb.append(meaning.romaji);
		sb.append("]");
		sb.append("]");
		sb.append("\n");
		sb.append(meaning.translationsToString());
		sb.append("\n");	
		return sb.toString();
	}
	
	public int getId() {
		return id;
	}
		
	public String getKanji() {
		return kanji;
	}

	public int getLevel() {
		return level;
	}

	public String getExamples() {
		return examples;
	}

	public String getLastview() {
		return lastview;
	}

	public int getShowntimes() {
		return showntimes;
	}

	public WordMeaning getMeaning() {
		return meaning;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public void setKanji(String kanji) {
		this.kanji = kanji;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setExamples(String examples) {
		this.examples = examples;
	}

	public void setLastview(String lastview) {
		this.lastview = lastview;
	}

	public void incrementShowntimes() {
		this.showntimes++;
	}

	public void setMeaning(WordMeaning meaning) {
		this.meaning = meaning;
	}

	public WordMeaning getMeanings() {
		return meaning;
	}

	public List<String> getTranslations() {
		return this.meaning.translations;
	}
	
	public String getTranslationsToString() {		
		return this.meaning.translationsToString();
	}

	public String getTranscription() {
		return this.meaning.transcription;
	}

	public String getRomaji() {
		return this.meaning.romaji;
	}

	public void setMeanings(WordMeaning meaning) {
		this.meaning = meaning;
	}

	public void setTranslations(List<String> translations) {
		this.meaning.translations = translations;
	}

	public void setTranscription(String transcription) {
		this.meaning.transcription = transcription;
	}

	public void setRomaji(String romaji) {
		this.meaning.romaji = romaji;
	}

	public String translationsToString() {
		return this.meaning.translationsToString();
	}

	public double getLearnedPercentage() {
		return learnedPercentage;
	}

	public void setLearnedPercentage(double learnedPercentage) {
		this.learnedPercentage = learnedPercentage;
	}

	@Override
	public int compareTo(Object e) {
		DictionaryEntry e1 = (DictionaryEntry) e;
		return this.kanji.compareTo(e1.kanji);
	}
	
}
