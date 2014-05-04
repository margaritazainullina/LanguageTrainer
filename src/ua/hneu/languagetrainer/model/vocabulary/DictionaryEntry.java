package ua.hneu.languagetrainer.model.vocabulary;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.util.Log;

import ua.hneu.languagetrainer.App;
import ua.hneu.languagetrainer.App.Languages;

public class DictionaryEntry {

	private int id;
	private String kanji;
	private int level;
	private String examples;
	private String lastview;
	private int shownTimes;
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
		this.shownTimes = showntimes;
		this.learnedPercentage = percentage;
		this.meaningEng = meaning;
		this.meaningRus = meaningRus;
		this.color = color;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(kanji);
		sb.append(" - ");
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

	public int getShownTimes() {
		return shownTimes;
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

	// sets to current time
	public void setLastView() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss.SS");
		String now = dateFormat.format(new Date());
		this.lastview = now;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public void incrementShowntimes() {
		this.shownTimes++;
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
		if (App.lang == Languages.RUS)
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

	enum DictionaryEntryComparator implements Comparator<DictionaryEntry> {
		LAST_VIEWED {
			@SuppressLint("SimpleDateFormat")
			public int compare(DictionaryEntry de1, DictionaryEntry de2) {
				SimpleDateFormat dateFormat = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss.SSS");
				boolean isAfter = false;
				try {
					if (de1.getLastview().isEmpty()
							|| de2.getLastview().isEmpty()) {
						// gets randomly - entries wont' be in a row
						isAfter = (Math.random() < 0.5);
					} else {
						Log.i("DictionaryEntryComparator",
								"compared: " + de1.getLastview() + " and "
										+ de2.getLastview());
						Date date1 = dateFormat.parse(de1.getLastview());
						Date date2 = dateFormat.parse(de2.getLastview());
						isAfter = (date1.after(date2));
						/*
						 * Log.i("DictionaryEntryComparator", "compared: " +
						 * de1.getLastview() + " and " + de2.getLastview());
						 */
					}
				} catch (ParseException e) {
					Log.e("DictionaryEntryComparator", "error in comparison: "
							+ de1.getLastview() + " and " + de2.getLastview());
					return 1;
				}
				if (isAfter)
					return -1;
				else
					return 1;
			}
		},
		LEARNED_PERCENTAGE {
			public int compare(DictionaryEntry de1, DictionaryEntry de2) {
				if (de1.getLearnedPercentage() > de2.getLearnedPercentage())
					return 1;
				else
					return -1;
			}
		},
		TIMES_SHOWN {
			public int compare(DictionaryEntry de1, DictionaryEntry de2) {
				if (de1.getShownTimes() > de2.getShownTimes())
					return 1;
				else
					return -1;
			}
		},
		RANDOM {
			public int compare(DictionaryEntry de1, DictionaryEntry de2) {
				if (Math.random() < 0.5)
					return -1;
				else
					return 1;
			}
		};
	}

	public String readingsToString() {
		if (App.isShowRomaji)
			return this.getTranscription() + " - " + this.getRomaji();
		else
			return this.getTranscription();
	}
}
