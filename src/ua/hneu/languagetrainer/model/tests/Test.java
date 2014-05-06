package ua.hneu.languagetrainer.model.tests;

import java.util.ArrayList;

public class Test {
<<<<<<< HEAD
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	private ArrayList<Question> questions;
	private int level;
	private Type testType;

	public enum Type {
=======
	private ArrayList<Question> questions;
	private int level;
	private Type testType; 

	private enum Type {
>>>>>>> 247717119c4fb90211ef54b2a05b0d7cc885b4ec
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

<<<<<<< HEAD
=======
	public Type getTestType() {
		return testType;
	}

>>>>>>> 247717119c4fb90211ef54b2a05b0d7cc885b4ec
	public void setTestType(Type testType) {
		this.testType = testType;
	}

	public Test(ArrayList<Question> questions, int level, Type testType) {
		super();
		this.questions = questions;
		this.level = level;
		this.testType = testType;
	}
<<<<<<< HEAD

	public Test() {
		questions=new ArrayList<Question>();
	}

	public void addQuestion(Question q) {
		this.questions.add(q);
	}

	public String getTestType() {
		return testType.toString();
	}
=======
	
>>>>>>> 247717119c4fb90211ef54b2a05b0d7cc885b4ec
}
