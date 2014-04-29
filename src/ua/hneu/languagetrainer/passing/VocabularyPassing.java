package ua.hneu.languagetrainer.passing;

import java.util.Date;

import ua.hneu.languagetrainer.model.vocabulary.DictionaryEntry;
import ua.hneu.languagetrainer.model.vocabulary.WordDictionary;

public class VocabularyPassing {
	private int numberOfCorrectAnswersInMatching = 0;
	private int numberOfCorrectAnswersInTranslation = 0;

	private Date timeOfLastPasing;

	// learned words while passing
	private WordDictionary learnedWords = new WordDictionary();

	public int getNumberOfCorrectAnswersInMatching() {
		return numberOfCorrectAnswersInMatching;
	}

	public int getNumberOfCorrectAnswersInTranslation() {
		return numberOfCorrectAnswersInTranslation;
	}

	public Date getTimeOfLastPasing() {
		return timeOfLastPasing;
	}

	public void incrementNumberOfCorrectAnswersInMatching() {
		this.numberOfCorrectAnswersInMatching++;
	}

	public void incrementNumberOfCorrectAnswersInTranslation() {
		this.numberOfCorrectAnswersInTranslation++;
	}

	public void setTimeOfLastPasing(Date timeOfLastPasing) {
		this.timeOfLastPasing = timeOfLastPasing;
	}	

	public WordDictionary getLearnedWords() {
		return learnedWords;
	}

	public void makeWordLearned(DictionaryEntry de) {
		learnedWords.add(de);
	}

}
