package ua.hneu.languagetrainer.passing;

import java.util.Hashtable;

import android.content.ContentResolver;

import ua.hneu.languagetrainer.App;
import ua.hneu.languagetrainer.model.User;
import ua.hneu.languagetrainer.model.other.Giongo;
import ua.hneu.languagetrainer.model.other.GiongoDictionary;
import ua.hneu.languagetrainer.service.UserService;

/**
 * @author Margarita Zainullina <margarita.zainullina@gmail.com>
 * @version 1.0
 */
public class GiongoPassing {
	private int numberOfCorrectAnswers = 0;
	private int numberOfIncorrectAnswers = 0;
	private int numberOfPassingsInARow = 0;

	private GiongoDictionary learnedWords = new GiongoDictionary();
	private Hashtable<Giongo, Integer> problemWords = new Hashtable<Giongo, Integer>();

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
		numberOfPassingsInARow++;
	}

	public GiongoDictionary getLearnedWords() {
		return learnedWords;
	}

	public Hashtable<Giongo, Integer> getProblemWords() {
		return problemWords;
	}

	public void setLearnedWords(GiongoDictionary learnedWords) {
		this.learnedWords = learnedWords;
	}

	public void setProblemWords(Hashtable<Giongo, Integer> problemWords) {
		this.problemWords = problemWords;
	}

	/**
	 * Marks giongo as learned and updates it in db
	 * 
	 * @param g
	 *            target word
	 * @param cr
	 *            content resolver to database
	 */
	public void makeWordLearned(Giongo g, ContentResolver cr) {
		// update info in user table
		User u = App.userInfo;
		u.setLearnedGrammar(u.getLearnedGiongo() + 1);
		UserService us = new UserService();
		us.update(u, cr);
		// update current dictionary
		g.setLearnedPercentage(1);
		learnedWords.add(g);
		incrementNumberOfCorrectAnswers();
		App.giongoWordsDictionary.remove(g);
		// add entries to current dictionary to match target size
		App.giongoWordsDictionary
				.addEntriesToDictionaryAndGet(App.numberOfEntriesInCurrentDict);
		// update info in vocabulary table
		App.gs.update(g, cr);

	}

	/**
	 * If user many times in a row answered incorrectly, this word should be
	 * added to a list of problem words
	 * 
	 * @param g
	 *            target word
	 */
	public void addProblemWord(Giongo g) {
		if (problemWords.containsKey(g)) {
			problemWords.put(g, problemWords.get(g) + 1);
		} else
			problemWords.put(g, 1);
	}

	/*
	 * reset all values except for numberOfPassingsInARow for analyzing of how
	 * many times user passed tests in a row
	 */
	public void clearInfo() {
		// TODO: add number of passing in a row for all
		this.learnedWords = null;
		this.numberOfCorrectAnswers = 0;
		this.numberOfIncorrectAnswers = 0;
	}

}
