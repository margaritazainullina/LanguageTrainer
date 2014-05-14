package ua.hneu.languagetrainer.model.other;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import ua.hneu.languagetrainer.App;
import ua.hneu.languagetrainer.App.Languages;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.util.Log;

public class CounterWord {
	private String sectionEng;
	private String sectionRus;
	private String word;
	private String hiragana;
	private String romaji;
	private String translationEng;
	private String translationRus;
	private double learnedPercentage;
	private int shownTimes;
	private String lastview;
	private String color;

	public CounterWord(String sectionEng, String sectionRus, String word,
			String hiragana, String romaji, String translationEng,
			String translationRus, double learnedPercentage, int shownTimes,
			String lastview, String color) {
		super();
		this.sectionEng = sectionEng;
		this.sectionRus = sectionRus;
		this.word = word;
		this.hiragana = hiragana;
		this.romaji = romaji;
		this.translationEng = translationEng;
		this.translationRus = translationRus;
		this.learnedPercentage = learnedPercentage;
		this.shownTimes = shownTimes;
		this.lastview = lastview;
		this.color = color;
	}

	public String getSectionEng() {
		return sectionEng;
	}

	public String getSectionRus() {
		return sectionRus;
	}

	public String getWord() {
		return word;
	}

	public String getHiragana() {
		return hiragana;
	}

	public String getRomaji() {
		return romaji;
	}

	public String getTranslationEng() {
		return translationEng;
	}

	public String getTranslationRus() {
		return translationRus;
	}

	public double getLearnedPercentage() {
		return learnedPercentage;
	}

	public int getShownTimes() {
		return shownTimes;
	}

	public String getLastview() {
		return lastview;
	}

	public String getColor() {
		return color;
	}

	public void setSectionEng(String sectionEng) {
		this.sectionEng = sectionEng;
	}

	public void setSectionRus(String sectionRus) {
		this.sectionRus = sectionRus;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public void setHiragana(String hiragana) {
		this.hiragana = hiragana;
	}

	public void setRomaji(String romaji) {
		this.romaji = romaji;
	}

	public void setTranslationEng(String translationEng) {
		this.translationEng = translationEng;
	}

	public void setTranslationRus(String translationRus) {
		this.translationRus = translationRus;
	}

	public void setLearnedPercentage(double learnedPercentage) {
		this.learnedPercentage = learnedPercentage;
	}

	public void setShownTimes(int shownTimes) {
		this.shownTimes = shownTimes;
	}

	public void setLastview(String lastview) {
		this.lastview = lastview;
	}

	public void setColor(String color) {
		this.color = color;
	}

	enum CounterWordComparator implements Comparator<CounterWord> {
		LAST_VIEWED {
			@SuppressLint("SimpleDateFormat")
			public int compare(CounterWord de1, CounterWord de2) {
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
			public int compare(CounterWord de1, CounterWord de2) {
				if (de1.getLearnedPercentage() > de2.getLearnedPercentage())
					return 1;
				else
					return -1;
			}
		},
		TIMES_SHOWN {
			public int compare(CounterWord de1, CounterWord de2) {
				if (de1.getShownTimes() > de2.getShownTimes())
					return 1;
				else
					return -1;
			}
		},
		RANDOM {
			public int compare(CounterWord de1, CounterWord de2) {
				if (Math.random() < 0.5)
					return -1;
				else
					return 1;
			}
		};
	}

	public String getTranslation() {
		if (App.lang == Languages.ENG)
			return translationEng;
		else
			return translationRus;
	}

	public int getIntColor() {
		String[] rgb = this.color.split(",");
		int color = Color.rgb(Integer.parseInt(rgb[0]),
				Integer.parseInt(rgb[1]), Integer.parseInt(rgb[2]));
		return color;
	}

	public void incrementShowntimes() {
		shownTimes++;

	}

	// sets current time
	public void setLastView() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss.SS");
		String now = dateFormat.format(new Date());
		this.lastview = now;
	}

	public String getTranscription() {
		if (App.isShowRomaji)
			return hiragana + " " + romaji;
		else
			return hiragana;
	}
}
