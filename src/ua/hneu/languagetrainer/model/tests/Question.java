package ua.hneu.languagetrainer.model.tests;

import java.util.ArrayList;

public class Question {
	private int id;
	private String section;
	private String task;
	private String title;
	private String text;
	private double weight;
	private ArrayList<Answer> answers;
	private String imgRef;
	private String audioRef;

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getText() {
		return text;
	}

	public double getWeight() {
		return weight;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public ArrayList<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(ArrayList<Answer> answers) {
		this.answers = answers;
	}
	
	public String getImgRef() {
		return imgRef;
	}

	public void setImgRef(String imgRef) {
		this.imgRef = imgRef;
	}

	public String getAudioRef() {
		return audioRef;
	}

	public void setAudioRef(String audioRef) {
		this.audioRef = audioRef;
	}

	public Question(int id, String section, String task, String title,
			String text, double weight, ArrayList<Answer> answers, String imgRef, String audioRef) {
		super();
		this.id = id;
		this.section = section;
		this.task = task;
		this.title = title;
		this.text = text;
		this.weight = weight;
		this.answers = answers;
		this.imgRef = imgRef;
		this.audioRef = audioRef;
	}

	public Answer getRightAnswer() {
		for (Answer a : this.getAnswers()) {
			if (a.isCorrect())
				return a;
		}
		return null;
	}
	public ArrayList<String> getAllAnswers(){
		ArrayList<String> answers = new ArrayList<String>();
		for (Answer a : this.answers) {
			answers.add(a.getText());
		}
		return answers;
	}

	public String getSection() {
		return section;
	}

	public String getTask() {
		return task;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public void setTask(String task) {
		this.task = task;
	}
	
}
