package ua.hneu.languagetrainer.passing;

import java.util.Date;
import java.util.Hashtable;

import ua.hneu.languagetrainer.App;
import ua.hneu.languagetrainer.model.vocabulary.DictionaryEntry;
import ua.hneu.languagetrainer.model.vocabulary.WordDictionary;

public class VocabularyPassing {
	private int numberOfCorrectAnswersInMatching = 0;
	private int numberOfCorrectAnswersInTranslation = 0;
	private int numberOfIncorrectAnswersInMatching = 0;
	private int numberOfIncorrectAnswersInTranslation = 0;
	private int numberOfPassingsInARow=0;

	// learned words while passing
	private WordDictionary learnedWords = new WordDictionary();
	// words and number of incorrect answers
	private Hashtable<DictionaryEntry, Integer> problemWords = new Hashtable<DictionaryEntry, Integer>();

	public int getNumberOfCorrectAnswersInMatching() {
		return numberOfCorrectAnswersInMatching;
	}

	public int getNumberOfCorrectAnswersInTranslation() {
		return numberOfCorrectAnswersInTranslation;
	}
	public int getNumberOfIncorrectAnswersInMatching() {
		return numberOfIncorrectAnswersInMatching;
	}

	public int getNumberOfIncorrectAnswersInTranslation() {
		return numberOfIncorrectAnswersInTranslation;
	}

	public int getNumberOfPassingsInARow() {
		return numberOfPassingsInARow;
	}

	public void incrementNumberOfCorrectAnswersInMatching() {
		this.numberOfCorrectAnswersInMatching++;
	}

	public void incrementNumberOfCorrectAnswersInTranslation() {
		this.numberOfCorrectAnswersInTranslation++;
	}
	public void incrementNumberOfIncorrectAnswersInMatching() {
		this.numberOfIncorrectAnswersInMatching++;
	}

	public void incrementNumberOfIncorrectAnswersInTranslation() {
		this.numberOfIncorrectAnswersInTranslation++;
	}

	public void incrementNumberOfPassingsInARow() {
		this.numberOfPassingsInARow++;
	}

	public WordDictionary getLearnedWords() {
		return learnedWords;
	}

	public Hashtable<DictionaryEntry, Integer> getProblemWords() {
		return problemWords;
	}

	public void makeWordLearned(DictionaryEntry de) {
		learnedWords.add(de);
		incrementNumberOfCorrectAnswersInMatching();
	}

	public void addProblemWord(DictionaryEntry de) {
		if (problemWords.containsKey(de)) {
			int oldValue = problemWords.get(de);
			problemWords.put(de, oldValue++);
		}
		else problemWords.put(de, 1);
	}
}
