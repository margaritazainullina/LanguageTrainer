package ua.hneu.languagetrainer.passing;

import java.util.Hashtable;

import android.content.ContentResolver;

import ua.hneu.languagetrainer.App;
import ua.hneu.languagetrainer.model.User;
import ua.hneu.languagetrainer.model.grammar.GrammarDictionary;
import ua.hneu.languagetrainer.model.grammar.GrammarRule;
import ua.hneu.languagetrainer.service.UserService;

public class GrammarPassing {
	private int numberOfCorrectAnswers = 0;
	private int numberOfIncorrectAnswers = 0;
	private int numberOfPassingsInARow = 0;

	private GrammarDictionary learnedRules = new GrammarDictionary();
	private Hashtable<GrammarRule, Integer> problemRules = new Hashtable<GrammarRule, Integer>();

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

	public GrammarDictionary getLearnedRules() {
		return learnedRules;
	}

	public Hashtable<GrammarRule, Integer> getProblemRules() {
		return problemRules;
	}

	public void setLearnedRules(GrammarDictionary learnedRules) {
		this.learnedRules = learnedRules;
	}

	public void setProblemRules(Hashtable<GrammarRule, Integer> problemRules) {
		this.problemRules = problemRules;
	}

	public void makeWordLearned(GrammarRule gr, ContentResolver cr) {
		// update info in user table
		User u = App.userInfo;
		u.setLearnedGrammar(u.getLearnedGrammar() + 1);
		UserService us = new UserService();
		us.update(u, cr);
		// update current dictionary
		gr.setLearnedPercentage(1);
		learnedRules.add(gr);
		incrementNumberOfCorrectAnswers();
		App.grammarDictionary.remove(gr);
		// add entries to current dictionary to match target size
		App.grammarDictionary.addEntriesToDictionaryAndGet(App.numberOfEntriesInCurrentDict);
		// update info in vocabulary table
		App.grs.update(gr, cr);

	}

	public void addProblemRule(GrammarRule rule) {
		if (problemRules.containsKey(rule)) {
			problemRules.put(rule, problemRules.get(rule) + 1);
		} else
			problemRules.put(rule, 1);
	}

	public void clearInfo() {
		// reset all values except for numberOfPassingsInARow for analyzing of
		// how many times user passed tests in a row
		this.learnedRules = null;
		this.numberOfCorrectAnswers = 0;
		this.numberOfIncorrectAnswers = 0;
	}

	public void incrementNumberOfPassingsInARow() {
		numberOfPassingsInARow++;
	}
}
