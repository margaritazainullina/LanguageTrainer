package ua.hneu.languagetrainer.model.vocabulary;

import java.util.List;

import android.graphics.Color;

import ua.hneu.languagetrainer.App;

public class DictionaryEntry implements Comparable<DictionaryEntry> {

	private int id;
	private String kanji;
	private int level;
	private String examples;
	private String lastview;
	private int showntimes;
	private double learnedPercentage;
	private WordMeaning meaning;
	private String color;

	public DictionaryEntry(int id, String kanji, int level,
			String transcription, String romaji, List<String> translations,
			String examples, double percentage, String lastview,
			int showntimes, String color) {

		WordMeaning meaning = new WordMeaning(transcription, romaji,
				translations);
		this.id = id;
		this.kanji = kanji;
		this.level = level;
		this.examples = examples;
		this.lastview = lastview;
		this.showntimes = showntimes;
		this.learnedPercentage = percentage;
		this.meaning = meaning;
		this.color = color;
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
	
	public String getColor() {
		return this.color;
	}

	public int getIntColor() {
		String[] rgb = this.color.split(",");
		int color = Color.rgb(Integer.parseInt(rgb[0]), Integer.parseInt(rgb[1]), Integer.parseInt(rgb[2]));
		return color;
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

	public void setColor(String color) {
		this.color = color;
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

	public String translationsToString() {
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

	public double getLearnedPercentage() {
		return learnedPercentage;
	}

	public void setLearnedPercentage(double learnedPercentage) {
		this.learnedPercentage = learnedPercentage;
	}

	@Override
	public int compareTo(DictionaryEntry e) {
		DictionaryEntry e1 = (DictionaryEntry) e;
		return this.kanji.compareTo(e1.kanji);
	}

	public String readingsToString() {
		if (App.isShowRomaji)
			return this.getTranscription() + " - " + this.getRomaji();
		else
			return this.getTranscription();
	}
}
