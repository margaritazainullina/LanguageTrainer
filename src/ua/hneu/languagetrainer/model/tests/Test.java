package ua.hneu.languagetrainer.model.tests;

import java.util.ArrayList;

import ua.hneu.edu.languagetrainer.R;
import ua.hneu.languagetrainer.App;
import android.view.View;

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
		questions = new ArrayList<Question>();
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

	public boolean isPassed() {
		boolean passed = true;
		if (level == 4 || level == 5) {
			if (pointsPart1 + pointsPart2 < 38)
				passed = false;
			if (pointsPart3 < 19)
				passed = false;
			if (App.userInfo.getLevel() == 4)
				if (pointsPart1 + pointsPart2 + pointsPart3 < 90)
					passed = false;
			if (App.userInfo.getLevel() == 5)
				if (pointsPart1 + pointsPart2 + pointsPart3 < 80)
					passed = false;
		} else {
			if (pointsPart1 < 19)
				passed = false;
			if (pointsPart2 < 19)
				passed = false;
			if (pointsPart3 < 19)
				passed = false;
			if (App.userInfo.getLevel() == 3)
				if (pointsPart1 + pointsPart2 + pointsPart3 < 95)
					passed = false;
			if (App.userInfo.getLevel() == 2)
				if (pointsPart1 + pointsPart2 + pointsPart3 < 90)
					passed = false;
			if (App.userInfo.getLevel() == 1)
				if (pointsPart1 + pointsPart2 + pointsPart3 < 100)
					passed = false;
		}
		return passed;
	}
}
