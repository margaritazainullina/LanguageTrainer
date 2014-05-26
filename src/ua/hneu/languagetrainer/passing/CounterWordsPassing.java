package ua.hneu.languagetrainer.passing;

import java.util.Hashtable;

import android.content.ContentResolver;

import ua.hneu.languagetrainer.App;
import ua.hneu.languagetrainer.model.User;
import ua.hneu.languagetrainer.model.other.CounterWord;
import ua.hneu.languagetrainer.model.other.CounterWordsDictionary;
import ua.hneu.languagetrainer.service.UserService;

/**
 * @author Margarita Zainullina <margarita.zainullina@gmail.com>
 * @version 1.0
 */
public class CounterWordsPassing {
	private int numberOfCorrectAnswers = 0;
	private int numberOfIncorrectAnswers = 0;
	private int numberOfPassingsInARow = 0;

	private CounterWordsDictionary learnedWords = new CounterWordsDictionary();
	private Hashtable<CounterWord, Integer> problemWords = new Hashtable<CounterWord, Integer>();

	/**
	 * Methods for getting and incrementing numbers of correct/incorrect answers while
	 * passing
	 */
	public void incrementNumberOfCorrectAnswers() {
		this.numberOfCorrectAnswers++;
	}

	public void incrementNumberOfIncorrectAnswers() {
		this.numberOfIncorrectAnswers++;
	}

	public int getNumberOfCorrectAnswers() {
		return numberOfCorrectAnswers;
	}

	public int getNumberOfIncorrectAnswers() {
		return numberOfIncorrectAnswers;
	}

	public int getNumberOfPassingsInARow() {
		return numberOfPassingsInARow;
	}

	public void incrementNumberOfPassingsInARow() {
		this.numberOfPassingsInARow++;
	}

	public CounterWordsDictionary getLearnedWords() {
		return learnedWords;
	}

	public Hashtable<CounterWord, Integer> getProblemWords() {
		return problemWords;
	}

	public void setLearnedWords(CounterWordsDictionary learnedWords) {
		this.learnedWords = learnedWords;
	}

	public void setProblemWords(Hashtable<CounterWord, Integer> problemWords) {
		this.problemWords = problemWords;
	}

	/**
	 * Marks counter word as learned and updates it in db
	 * 
	 * @param cw
	 *            target word
	 * @param cr
	 *            content resolver to database
	 */
	public void makeWordLearned(CounterWord cw, ContentResolver cr) {
		// update info in user table
		User u = App.userInfo;
		u.setLearnedCounterWords(u.getLearnedCounterWords() + 1);
		UserService us = new UserService();
		us.update(u, cr);
		// update current dictionary
		cw.setLearnedPercentage(1);
		learnedWords.add(cw);
		incrementNumberOfCorrectAnswers();
		App.counterWordsDictionary.remove(cw);
		// add entries to current dictionary to match target size
		App.counterWordsDictionary
				.addEntriesToDictionaryAndGet(App.numberOfEntriesInCurrentDict);
		// update info in vocabulary table
		App.cws.update(cw, cr);
	}

	/**
	 * If user many times in a row answered incorrectly, this word should be
	 * added to a list of problem words
	 * 
	 * @param cw
	 *            target word
	 */
	public void addProblemWord(CounterWord cw) {
		if (problemWords.containsKey(cw)) {
			problemWords.put(cw, problemWords.get(cw) + 1);
		} else
			problemWords.put(cw, 1);
	}

	/*
	 * Resets all values except for numberOfPassingsInARow for analyzing of how
	 * many times user passed tests in a row
	 */
	public void clearInfo() {
		// TODO: add number of passing in a row for all
		this.learnedWords = null;
		this.numberOfCorrectAnswers = 0;
		this.numberOfIncorrectAnswers = 0;
	}
}
