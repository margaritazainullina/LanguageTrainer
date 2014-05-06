package ua.hneu.languagetrainer.model.tests;

<<<<<<< HEAD
import java.util.ArrayList;

=======
>>>>>>> 247717119c4fb90211ef54b2a05b0d7cc885b4ec
public class Question {
	private int id;
	private String title;
	private String text;
	private double weight;
<<<<<<< HEAD
	private ArrayList<Answer> answers;
=======
>>>>>>> 247717119c4fb90211ef54b2a05b0d7cc885b4ec

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

<<<<<<< HEAD
	public ArrayList<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(ArrayList<Answer> answers) {
		this.answers = answers;
	}

	public Question(int id, String title, String text, double weight,
			ArrayList<Answer> answers) {
=======
	public Question(int id, String title, String text, double weight) {
>>>>>>> 247717119c4fb90211ef54b2a05b0d7cc885b4ec
		super();
		this.id = id;
		this.title = title;
		this.text = text;
		this.weight = weight;
<<<<<<< HEAD
		this.answers = answers;
	}

=======
	}
>>>>>>> 247717119c4fb90211ef54b2a05b0d7cc885b4ec
}
