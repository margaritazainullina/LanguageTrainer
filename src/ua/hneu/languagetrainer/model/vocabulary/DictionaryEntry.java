package ua.hneu.languagetrainer.model.vocabulary;

import java.util.List;

import android.graphics.Color;

import ua.hneu.languagetrainer.App;
import ua.hneu.languagetrainer.App.Languages;

public class DictionaryEntry implements Comparable<DictionaryEntry> {

	private int id;
	private String kanji;
	private int level;
	private String examples;
	private String lastview;
	private int showntimes;
	private double learnedPercentage;
	private WordMeaning meaningEng;
	private WordMeaning meaningRus;
	private String color;

	public DictionaryEntry(int id, String kanji, int level,
			String transcription, String romaji, List<String> translations,
			List<String> translationsRus, String examples, double percentage,
			String lastview, int showntimes, String color) {

		WordMeaning meaning = new WordMeaning(transcription, romaji,
				translations);
		WordMeaning meaningRus = new WordMeaning(transcription, romaji,
				translationsRus);
		this.id = id;
		this.kanji = kanji;
		this.level = level;
		this.examples = examples;
		this.lastview = lastview;
		this.showntimes = showntimes;
		this.learnedPercentage = percentage;
		this.meaningEng = meaning;
		this.meaningRus = meaningRus;
		this.color = color;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.getRomaji());
		sb.append(" [");
		sb.append(meaningEng.hiragana);
		sb.append("/");
		sb.append(meaningEng.romaji);
		sb.append("]");
		sb.append("\n");
		sb.append(meaningEng.translationsToString());
		sb.append("\n");
		sb.append(meaningRus.translationsToString());
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

	public WordMeaning getMeaningEng() {
		return meaningEng;
	}

	public String getColor() {
		return this.color;
	}

	public int getIntColor() {
		String[] rgb = this.color.split(",");
		int color = Color.rgb(Integer.parseInt(rgb[0]),
				Integer.parseInt(rgb[1]), Integer.parseInt(rgb[2]));
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
		this.meaningEng = meaning;
	}

	public WordMeaning getMeanings() {
		return meaningEng;
	}

	public List<String> getTranslationsEng() {
		return this.meaningEng.translations;
	}

	public List<String> getTranslationsRus() {
		return this.meaningRus.translations;
	}

	public String translationsEngToString() {
		return this.meaningEng.translationsToString();
	}

	public String translationsRusToString() {
		return this.meaningRus.translationsToString();
	}

	public String translationsToString() {
		if (App.lang==Languages.RUS)
			return this.meaningRus.translationsToString();
		return this.meaningEng.translationsToString();
	}

	public String getTranscription() {
		return this.meaningEng.hiragana;
	}

	public String getRomaji() {
		return this.meaningEng.romaji;
	}

	public void setMeanings(WordMeaning meaning) {
		this.meaningEng = meaning;
	}

	public void setTranslations(List<String> translations) {
		this.meaningEng.translations = translations;
	}

	public void setTranslationsRus(List<String> translations) {
		this.meaningRus.translations = translations;
	}

	public void setTranscription(String transcription) {
		this.meaningEng.hiragana = transcription;
	}

	public void setRomaji(String romaji) {
		this.meaningEng.romaji = romaji;
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
