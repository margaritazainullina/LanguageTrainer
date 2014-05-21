package ua.hneu.languagetrainer.model.other;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

import ua.hneu.languagetrainer.App;
import ua.hneu.languagetrainer.App.Languages;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.util.Log;

public class Giongo {
	private String word;
	private String romaji;
	private String translEng;
	private String translRus;
	private double learnedPercentage;
	private int shownTimes;
	private String lastview;
	private String color;
	public ArrayList<GiongoExample> examples = new ArrayList<GiongoExample>();

	public Giongo(String word, String romaji, String translEng,
			String translRus, double learnedPercentage, int shownTimes,
			String lastview, String color, ArrayList<GiongoExample> examples) {
		super();
		this.word = word;
		this.romaji = romaji;
		this.translEng = translEng;
		this.translRus = translRus;
		this.learnedPercentage = learnedPercentage;
		this.shownTimes = shownTimes;
		this.lastview = lastview;
		this.color = color;
		this.examples = examples;
	}

	public ArrayList<String> getAllExamplesText() {
		ArrayList<String> text = new ArrayList<String>();
		for (GiongoExample ge : examples) {
			text.add(ge.getText().trim());
		}
		return text;
	}

	public ArrayList<String> getAllExamplesRomaji() {
		ArrayList<String> text = new ArrayList<String>();
		for (GiongoExample ge : examples) {
			text.add(ge.getRomaji().trim());
		}
		return text;
	}

	public ArrayList<String> getAllTranslations() {
		ArrayList<String> text = new ArrayList<String>();
		boolean isEng = App.lang == Languages.ENG;
		for (GiongoExample ge : examples) {
			if (isEng)
				text.add(ge.getTranslationEng().trim());
			else
				text.add(ge.getTranslationRus().trim());
		}
		return text;
	}

	public String getLastview() {
		return lastview;
	}

	public int getShownTimes() {
		return shownTimes;
	}

	public double getLearnedPercentage() {
		return learnedPercentage;
	}

	public void setLastview(String lastview) {
		this.lastview = lastview;
	}

	public void setShownTimes(int shownTimes) {
		this.shownTimes = shownTimes;
	}

	public void setLearnedPercentage(double learnedPercentage) {
		this.learnedPercentage = learnedPercentage;
	}

	public Giongo() {
		examples = new ArrayList<GiongoExample>();
	}

	public String getWord() {
		return word;
	}

	public String getRomaji() {
		return romaji;
	}

	public String getTranslEng() {
		return translEng;
	}

	public String getTranslRus() {
		return translRus;
	}

	public String getColor() {
		return color;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public void setRomaji(String romaji) {
		this.romaji = romaji;
	}

	public void setTranslEng(String translEng) {
		this.translEng = translEng;
	}

	public void setTranslRus(String translRus) {
		this.translRus = translRus;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public ArrayList<GiongoExample> getExamples() {
		return examples;
	}

	public void setExamples(ArrayList<GiongoExample> examples) {
		this.examples = examples;
	}

	public String getRule() {
		return word;
	}

	public void setRule(String rule) {
		this.word = rule;
	}

	public void setDescEng(String descEng) {
		this.translEng = descEng;
	}

	public void setDescRus(String descRus) {
		this.translRus = descRus;
	}

	enum GiongoComparator implements Comparator<Giongo> {
		LAST_VIEWED {
			@SuppressLint("SimpleDateFormat")
			public int compare(Giongo de1, Giongo de2) {
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
			public int compare(Giongo de1, Giongo de2) {
				if (de1.getLearnedPercentage() > de2.getLearnedPercentage())
					return 1;
				else
					return -1;
			}
		},
		TIMES_SHOWN {
			public int compare(Giongo de1, Giongo de2) {
				if (de1.getShownTimes() > de2.getShownTimes())
					return 1;
				else
					return -1;
			}
		},
		RANDOM {
			public int compare(Giongo de1, Giongo de2) {
				if (Math.random() < 0.5)
					return -1;
				else
					return 1;
			}
		};
	}

	public String getTranslation() {
		if (App.lang == Languages.ENG)
			return translEng;
		else
			return translRus;
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
}
