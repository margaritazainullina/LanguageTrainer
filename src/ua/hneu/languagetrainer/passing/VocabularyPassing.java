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

	public void makeWordLearned(VocabularyEntry de, ContentResolver cr, boolean isKanjiNeeded) {
		// update info in user table
		User u = App.userInfo;
		u.setLearnedVocabulary(u.getLearnedVocabulary() + 1);
		UserService us = new UserService();
		us.update(u, cr);
		// update current dictionary
		de.setLearnedPercentage(1);
		learnedWords.add(de);
		incrementNumberOfCorrectAnswersInMatching();
		App.vocabularyDictionary.remove(de);
		// add entries to current dictionary to match target size
		if(!isKanjiNeeded)
		App.vocabularyDictionary.addEntriesToDictionaryAndGet(
				App.userInfo.getNumberOfEntriesInCurrentDict());
		else App.vocabularyDictionary.addEntriesToDictionaryAndGetOnlyWithKanji(
				App.userInfo.getNumberOfEntriesInCurrentDict());
		// update info in vocabulary table
		App.vs.update(de, cr);

	}

	public void addProblemWord(VocabularyEntry de) {
		if (problemWords.containsKey(de)) {
			problemWords.put(de, problemWords.get(de) + 1);
		} else
			problemWords.put(de, 1);
	}

	public void clearInfo() {
		// reset all values except for numberOfPassingsInARow for analyzing of
		// how many times user passed tests in a row
		this.learnedWords = null;
		this.numberOfCorrectAnswersInMatching = 0;
		this.numberOfCorrectAnswersInTranslation = 0;
		this.numberOfIncorrectAnswersInMatching = 0;
		this.numberOfIncorrectAnswersInTranslation = 0;
	}
}
