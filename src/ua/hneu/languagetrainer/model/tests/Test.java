package ua.hneu.languagetrainer.model.tests;

import java.util.ArrayList;

public class Test {
	private ArrayList<Question> questions;
	private int level;
	private Type testType; 

	private enum Type {
		LEVEL_DEF, MOCK_TEST
	}

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

	public Type getTestType() {
		return testType;
	}

	public void setTestType(Type testType) {
		this.testType = testType;
	}

	public Test(ArrayList<Question> questions, int level, Type testType) {
		super();
		this.questions = questions;
		this.level = level;
		this.testType = testType;
	}
	
}
