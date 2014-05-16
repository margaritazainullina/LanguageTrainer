package ua.hneu.languagetrainer.model.tests;

import java.util.ArrayList;

public class Test {
	private int id;
	private ArrayList<Question> questions;
	private int level;
	private String name;
	private int pointsPart1;
	private int pointsPart2;
	private int pointsPart3;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public int getPointsPart1() {
		return pointsPart1;
	}

	public void setPointsPart1(int pointsPart1) {
		this.pointsPart1 = pointsPart1;
	}

	public int getPointsPart2() {
		return pointsPart2;
	}

	public void setPointsPart2(int pointsPart2) {
		this.pointsPart2 = pointsPart2;
	}

	public int getPointsPart3() {
		return pointsPart3;
	}

	public void setPointsPart3(int pointsPart3) {
		this.pointsPart3 = pointsPart3;
	}

	public String getName() {
		return name;
	}	
}
