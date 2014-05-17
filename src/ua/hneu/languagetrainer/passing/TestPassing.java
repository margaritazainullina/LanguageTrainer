package ua.hneu.languagetrainer.passing;

public class TestPassing {
	private double scoreInVocGr = 0;
	private double scoreInReading = 0;
	private double scoreInListening = 0;
	private int numberOfCorrectAnswers = 0;

	public double getScoreInVocGr() {
		return scoreInVocGr;
	}

	public double getScoreInReading() {
		return scoreInReading;
	}

	public double getScoreInListening() {
		return scoreInListening;
	}

	public void incrementScoreInVocGr(double incr) {
		scoreInVocGr += incr;
	}

	public void incrementScoreInReading(double incr) {
		scoreInReading += incr;
	}

	public void incrementScoreInListening(double incr) {
		scoreInListening += incr;
	}

	public int getNumberOfCorrectAnswers() {
		return numberOfCorrectAnswers;
	}

	public void incrementNumberOfCorrectAnswers() {
		numberOfCorrectAnswers++;
	}

	public void clearInfo() {
		scoreInVocGr = 0;
		scoreInReading = 0;
		scoreInListening = 0;
		numberOfCorrectAnswers = 0;
	}
}
