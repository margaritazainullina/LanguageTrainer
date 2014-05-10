package ua.hneu.languagetrainer.model.other;

import java.util.ArrayList;

public class Giongo {
	private String word;
	private String romaji;
	private String translEng;
	private String translRus;
	private String color;
	public ArrayList<GiongoExample> examples = new ArrayList<GiongoExample>();

	public Giongo(String word, String romaji, String translEng,
			String translRus, String color, ArrayList<GiongoExample> examples) {
		examples = new ArrayList<GiongoExample>();
		this.word = word;
		this.romaji = romaji;
		this.translEng = translEng;
		this.translRus = translRus;
		this.color = color;
		this.examples = examples;
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
}
