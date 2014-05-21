package ua.hneu.languagetrainer.passing;

import java.util.Hashtable;

import android.content.ContentResolver;

import ua.hneu.languagetrainer.App;
import ua.hneu.languagetrainer.model.User;
import ua.hneu.languagetrainer.model.other.CounterWord;
import ua.hneu.languagetrainer.model.other.CounterWordsDictionary;
import ua.hneu.languagetrainer.service.UserService;

public class CounterWordsPassing {
	private int numberOfCorrectAnswers = 0;
	private int numberOfIncorrectAnswers = 0;
	private int numberOfPassingsInARow = 0;

	private CounterWordsDictionary learnedWords = new CounterWordsDictionary();
	private Hashtable<CounterWord, Integer> problemWords = new Hashtable<CounterWord, Integer>();

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

	public void makeWordLearned(CounterWord g, ContentResolver cr) {
		// update info in user table
		User u = App.userInfo;
		u.setLearnedCounterWords(u.getLearnedCounterWords() + 1);
		UserService us = new UserService();
		us.update(u, cr);
		// update current dictionary
		g.setLearnedPercentage(1);
		learnedWords.add(g);
		incrementNumberOfCorrectAnswers();
		App.counterWordsDictionary.remove(g);
		// add entries to current dictionary to match target size
		App.counterWordsDictionary.addEntriesToDictionaryAndGet(App.numberOfEntriesInCurrentDict);
		// update info in vocabulary table
		App.cws.update(g, cr);

	}

	public void addProblemWord(CounterWord g) {
		if (problemWords.containsKey(g)) {
			problemWords.put(g, problemWords.get(g) + 1);
		} else
			problemWords.put(g, 1);
	}

	public void clearInfo() {
		// reset all values except for numberOfPassingsInARow for analyzing of
		// how many times user passed tests in a row
		// TODO: add number of passing in a row for all
		this.learnedWords = null;
		this.numberOfCorrectAnswers = 0;
		this.numberOfIncorrectAnswers = 0;
	}
}
