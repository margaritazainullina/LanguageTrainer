package ua.hneu.languagetrainer.model.tests;

public class Answer {
	private String text;
	private boolean isCorrect;
<<<<<<< HEAD

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

	public void setText(String text) {
		this.text = text;
	}

	public void setCorrect(boolean isCorrect) {
		this.isCorrect = isCorrect;
	}

=======
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
>>>>>>> 247717119c4fb90211ef54b2a05b0d7cc885b4ec
	public Answer(String text, boolean isCorrect) {
		super();
		this.text = text;
		this.isCorrect = isCorrect;
<<<<<<< HEAD
	}

	public Answer() {
		super();
	}
=======
	}	
>>>>>>> 247717119c4fb90211ef54b2a05b0d7cc885b4ec
}
