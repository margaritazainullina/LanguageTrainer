package ua.hneu.languagetrainer.passing;

import java.util.Date;

import ua.hneu.languagetrainer.model.vocabulary.WordDictionary;

public class VocabularyPassing {
	private int numberOfCorrectAnswersInMatching = 0;
	private int numberOfCorrectAnswersInTranslation = 0;
	
	private Date timeOfLastPasing;
	
	//learned words while passing
	private WordDictionary learnedWords=new WordDictionary();

	public int getNumberOfCorrectAnswersInMatching() {
		return numberOfCorrectAnswersInMatching;
	}

	public int getNumberOfCorrectAnswersInTranslation() {
		return numberOfCorrectAnswersInTranslation;
	}

	public Date getTimeOfLastPasing() {
		return timeOfLastPasing;
	}

	public void setNumberOfCorrectAnswersInMatching(
			int numberOfCorrectAnswersInMatching) {
		this.numberOfCorrectAnswersInMatching = numberOfCorrectAnswersInMatching;
	}

	public void setNumberOfCorrectAnswersInTranslation(
			int numberOfCorrectAnswersInTranslation) {
		this.numberOfCorrectAnswersInTranslation = numberOfCorrectAnswersInTranslation;
	}

	public void setTimeOfLastPasing(Date timeOfLastPasing) {
		this.timeOfLastPasing = timeOfLastPasing;
	}

}
