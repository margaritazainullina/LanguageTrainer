package ua.hneu.languagetrainer.model.grammar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

import ua.hneu.languagetrainer.App;
import ua.hneu.languagetrainer.App.Languages;
import ua.hneu.languagetrainer.model.vocabulary.VocabularyEntry;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.util.Log;

public class GrammarRule {
	private String rule;
	private int level;
	private String descEng;
	private String descRus;
	private double learnedPercentage;
	private String lastview;
	private int shownTimes;
	private String color;
	public ArrayList<GrammarExample> examples = new ArrayList<GrammarExample>();

	public GrammarRule(String rule, int level, String descEng, String descRus,
			double learnedPercentage, String lastview, int shownTimes,
			String color, ArrayList<GrammarExample> examples) {
		super();
		this.rule = rule;
		this.level = level;
		this.descEng = descEng;
		this.descRus = descRus;
		this.learnedPercentage = learnedPercentage;
		this.lastview = lastview;
		this.shownTimes = shownTimes;
		this.color = color;
		this.examples = examples;
	}

	public ArrayList<String> getAllExamplesText() {
		ArrayList<String> text = new ArrayList<String>();
		for (GrammarExample ge : examples) {
			text.add(ge.getText());
		}
		return text;
	}

	public ArrayList<String> getAllExamplesRomaji() {
		ArrayList<String> text = new ArrayList<String>();
		for (GrammarExample ge : examples) {
			text.add(ge.getRomaji());
		}
		return text;
	}

	public ArrayList<String> getAllTranslations() {
		ArrayList<String> text = new ArrayList<String>();
		boolean isEng = App.lang == Languages.ENG;
		for (GrammarExample ge : examples) {
			if (isEng)
				text.add(ge.getTranslationEng());
			else
				text.add(ge.getTranslationRus());
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

	public GrammarRule() {
	}

	public ArrayList<GrammarExample> getExamples() {
		return examples;
	}

	public void setExamples(ArrayList<GrammarExample> examples) {
		this.examples = examples;
	}

	public String getRule() {
		return rule;
	}

	public int getLevel() {
		return level;
	}

	public String getDescEng() {
		return descEng;
	}

	public String getDescRus() {
		return descRus;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setDescEng(String descEng) {
		this.descEng = descEng;
	}

	public void setDescRus(String descRus) {
		this.descRus = descRus;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	enum GrammarRuleComparator implements Comparator<GrammarRule> {
		LAST_VIEWED {
			public int compare(GrammarRule de1, GrammarRule de2) {
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
					}
				} catch (ParseException e) {
					Log.e("GrammarRuleComparator", "error in comparison: "
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
			public int compare(GrammarRule de1, GrammarRule de2) {
				if (de1.getLearnedPercentage() > de2.getLearnedPercentage())
					return 1;
				else
					return -1;
			}
		},
		TIMES_SHOWN {
			public int compare(GrammarRule de1, GrammarRule de2) {
				if (de1.getShownTimes() > de2.getShownTimes())
					return 1;
				else
					return -1;
			}
		},
		RANDOM {
			public int compare(GrammarRule de1, GrammarRule de2) {
				if (Math.random() < 0.5)
					return -1;
				else
					return 1;
			}
		};
	}

	public CharSequence getDescription() {
		if (App.lang == Languages.ENG)
			return descEng;
		else
			return descRus;
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
