package ua.hneu.languagetrainer.passing;

import java.util.Hashtable;

import android.content.ContentResolver;

import ua.hneu.languagetrainer.App;
import ua.hneu.languagetrainer.model.User;
import ua.hneu.languagetrainer.model.vocabulary.VocabularyEntry;
import ua.hneu.languagetrainer.model.vocabulary.VocabularyDictionary;
import ua.hneu.languagetrainer.service.UserService;

public class VocabularyPassing {
	private int numberOfCorrectAnswersInMatching = 0;
	private int numberOfCorrectAnswersInTranslation = 0;
	private int numberOfCorrectAnswersInTranscription = 0;
	private int numberOfIncorrectAnswersInMatching = 0;
	private int numberOfIncorrectAnswersInTranslation = 0;
	private int numberOfIncorrectAnswersInTranscription = 0;
	private int numberOfPassingsInARow = 0;

	// learned words while passing
	private VocabularyDictionary learnedWords = new VocabularyDictionary();
	// words and number of incorrect answers
	private Hashtable<VocabularyEntry, Integer> problemWords = new Hashtable<VocabularyEntry, Integer>();

	/**
	 * Methods for getting and incrementing numbers of correct/incorrect answers
	 * while passing
	 */
	
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

	public int getTranscription() {
		return numberOfCorrectAnswersInTranscription;
	}

	public void incrementNumberOfCorrectAnswersInMatching() {
		this.numberOfCorrectAnswersInMatching++;
	}

	public void incrementNumberOfCorrectAnswersInTranslation() {
		this.numberOfCorrectAnswersInTranslation++;
	}

	public void incrementNumberOfCorrectAnswersInTranscription() {
		this.numberOfCorrectAnswersInTranscription++;
	}

	public int getNumberOfIncorrectAnswersInTranscription() {
		return numberOfIncorrectAnswersInTranscription;
	}

	public int getNumberOfCorrectAnswersInTranscription() {
		return numberOfCorrectAnswersInTranscription;
	}

	public void incrementNumberOfIncorrectAnswersInMatching() {
		this.numberOfIncorrectAnswersInMatching++;
	}

	public void incrementNumberOfIncorrectAnswersInTranslation() {
		this.numberOfIncorrectAnswersInTranslation++;
	}

	public void incrementNumberOfIncorrectAnswersInTranscription() {
		this.numberOfIncorrectAnswersInTranscription++;
	}

	public void incrementNumberOfPassingsInARow() {
		this.numberOfPassingsInARow++;
	}

	public VocabularyDictionary getLearnedWords() {
		return learnedWords;
	}

	public Hashtable<VocabularyEntry, Integer> getProblemWords() {
		return problemWords;
	}

	/**
	 * Marks word as learned and updates it in db
	 * 
	 * @param ve
	 *            target word
	 * @param cr
	 *            content resolver to database
	 */
	public void makeWordLearned(VocabularyEntry ve, ContentResolver cr,
			boolean isKanjiNeeded) {
		// update info in user table
		User u = App.userInfo;
		u.setLearnedVocabulary(u.getLearnedVocabulary() + 1);
		UserService us = new UserService();
		us.update(u, cr);
		// update current dictionary
		ve.setLearnedPercentage(1);
		learnedWords.add(ve);
		incrementNumberOfCorrectAnswersInMatching();
		App.vocabularyDictionary.remove(ve);
		// add entries to current dictionary to match target size
		if (!isKanjiNeeded)
			App.vocabularyDictionary
					.addEntriesToDictionaryAndGet(App.numberOfEntriesInCurrentDict);
		else
			App.vocabularyDictionary
					.addEntriesToDictionaryAndGetOnlyWithKanji(App.numberOfEntriesInCurrentDict);
		// update info in vocabulary table
		App.vs.update(ve, cr);

	}

	/**
	 * If user many times in a row answered incorrectly, this word should be
	 * added to a list of problem words
	 * 
	 * @param ve
	 *            target word
	 */
	public void addProblemWord(VocabularyEntry ve) {
		if (problemWords.containsKey(ve)) {
			problemWords.put(ve, problemWords.get(ve) + 1);
		} else
			problemWords.put(ve, 1);
	}

	/*
	 * reset all values except for numberOfPassingsInARow for analyzing of how
	 * many times user passed tests in a row
	 */
	public void clearInfo() {
		this.learnedWords = null;
		this.numberOfCorrectAnswersInMatching = 0;
		this.numberOfCorrectAnswersInTranslation = 0;
		this.numberOfIncorrectAnswersInMatching = 0;
		this.numberOfIncorrectAnswersInTranslation = 0;
	}
}
