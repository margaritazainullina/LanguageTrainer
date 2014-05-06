package ua.hneu.languagetrainer.model.tests;

public class Question {
	private int id;
	private String title;
	private String text;
	private double weight;

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

	public Question(int id, String title, String text, double weight) {
		super();
		this.id = id;
		this.title = title;
		this.text = text;
		this.weight = weight;
	}
}
