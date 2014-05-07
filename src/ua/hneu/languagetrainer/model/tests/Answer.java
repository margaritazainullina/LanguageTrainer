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

	public int isCorrectToInt() {
		if (isCorrect)
			return 1;
		else
			return 0;
	}

	public Answer(String text, boolean isCorrect) {
		super();
		this.text = text;
		this.isCorrect = isCorrect;
	}
}