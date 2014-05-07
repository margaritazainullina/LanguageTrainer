package ua.hneu.languagetrainer.model.tests;

import java.util.ArrayList;

public class Test {
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	private ArrayList<Question> questions;
	private int level;
	private String name;

	public ArrayList<Question> getQuestions() {
		return questions;
	}

	public int getLevel() {
		return level;
	}

	public void setQuestions(ArrayList<Question> questions) {
		this.questions = questions;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Test(ArrayList<Question> questions, int level, String name) {
		super();
		this.questions = questions;
		this.level = level;
		this.name = name;
	}

	public Test() {
		questions=new ArrayList<Question>();
	}

	public void addQuestion(Question q) {
		this.questions.add(q);
	}

	public String getTestName() {
		return name;
	}
}
