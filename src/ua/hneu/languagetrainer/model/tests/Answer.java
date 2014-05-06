package ua.hneu.languagetrainer.model.tests;

public class Answer {
	private String text;
	private boolean isCorrect;
	public String getText() {
		return text;
	}
	public boolean isCorrect() {
		return isCorrect;
	}
	public void setText(String text) {
		this.text = text;
	}
	public void setCorrect(boolean isCorrect) {
		this.isCorrect = isCorrect;
	}
	public Answer(String text, boolean isCorrect) {
		super();
		this.text = text;
		this.isCorrect = isCorrect;
	}	
}
