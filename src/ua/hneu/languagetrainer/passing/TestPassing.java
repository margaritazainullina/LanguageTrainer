package ua.hneu.languagetrainer.passing;

/**
 * @author Margarita Zainullina <margarita.zainullina@gmail.com>
 * @version 1.0
 */
public class TestPassing {
	private double scoreInVocGr = 0;
	private double scoreInReading = 0;
	private double scoreInListening = 0;
	private int numberOfCorrectAnswers = 0;

	/**
	 * Methods for getting and incrementing numbers of correct answers by test
	 * sections
	 */
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

	/*
	 * reset all values except for numberOfPassingsInARow for analyzing of how
	 * many times user passed tests in a row
	 */
	public void clearInfo() {
		scoreInVocGr = 0;
		scoreInReading = 0;
		scoreInListening = 0;
		numberOfCorrectAnswers = 0;
	}
}
