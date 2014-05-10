package ua.hneu.languagetrainer.model.grammar;

import java.util.ArrayList;

public class GrammarRule {
	private String rule;
	private int level;
	private String descEng;
	private String descRus;
	private String color;
	public ArrayList<GrammarExample> examples = new ArrayList<GrammarExample>();

	public GrammarRule(String rule, int level, String descEng, String descRus,
			ArrayList<GrammarExample> examples, String color) {
		super();
		this.rule = rule;
		this.level = level;
		this.descEng = descEng;
		this.descRus = descRus;
		this.examples = examples;
		this.color = color;
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
}
