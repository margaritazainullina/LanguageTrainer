package ua.hneu.languagetrainer.model;

import ua.hneu.languagetrainer.App;
import ua.hneu.languagetrainer.service.UserService;
import android.content.ContentResolver;

public class User {
	private int id;
	// From 1 to 5 (N1-N5)
	// "-1" means that user haven't selected level yet
	private int userLevel = -1;
	// Vocabulary
	private int learnedVocabulary = 0;
	private int numberOfVocabularyInLevel = 0;
	// Grammar
	private int learnedGrammar = 0;
	private int numberOfGrammarInLevel = 0;
	// Other (depending on the level)
	private int learnedGiongo = 0;
	private int numberOfGiongoInLevel = 0;
	private int learnedCounterWords = 0;
	private int numberOfCounterWordsInLevel = 0;
	// if user created - VocabularyService.all hasn't been created
	// 1-true, 0-false for storing in db
	public int isLevelLaunchedFirstTime = 1;
	public int isCurrentLevel = 1;

	public User(int id, int userLevel, int learnedVocabulary,
			int numberOfVocabularyInLevel, int learnedGrammar,
			int numberOfGrammarInLevel, int learnedGiongo,
			int numberOfGiongoInLevel, int learnedCounterWords,
			int numberOfCounterWordsInLevel,  int isLevelLaunchedFirstTime,
			int isCurrentLevel) {
		super();
		this.id = id;
		this.userLevel = userLevel;
		this.learnedVocabulary = learnedVocabulary;
		this.numberOfVocabularyInLevel = numberOfVocabularyInLevel;
		this.learnedGrammar = learnedGrammar;
		this.numberOfGrammarInLevel = numberOfGrammarInLevel;
		this.learnedGiongo = learnedGiongo;
		this.numberOfGiongoInLevel = numberOfGiongoInLevel;
		this.learnedCounterWords = learnedCounterWords;
		this.numberOfCounterWordsInLevel = numberOfCounterWordsInLevel;
		this.isLevelLaunchedFirstTime = isLevelLaunchedFirstTime;
		this.isCurrentLevel = isCurrentLevel;
	}

	public int getIsCurrentLevel() {
		return isCurrentLevel;
	}

	public void setIsCurrentLevel(int isCurrentLevel) {
		this.isCurrentLevel = isCurrentLevel;
	}

	public int getId() {
		return id;
	}

	public int getLevel() {
		return userLevel;
	}

	public int getLearnedVocabulary() {
		return learnedVocabulary;
	}

	public int getNumberOfVocabularyInLevel() {
		return numberOfVocabularyInLevel;
	}

	public int getLearnedGrammar() {
		return learnedGrammar;
	}

	public int getNumberOfGrammarInLevel() {
		return numberOfGrammarInLevel;
	}

	public int getLearnedGiongo() {
		return learnedGiongo;
	}

	public int getNumberOfGiongoInLevel() {
		return numberOfGiongoInLevel;
	}

	public int getLearnedCounterWords() {
		return learnedCounterWords;
	}

	public int getNumberOfCounterWordsInLevel() {
		return numberOfCounterWordsInLevel;
	}

	public int getIsLevelLaunchedFirstTime() {
		return isLevelLaunchedFirstTime;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setUserLevel(int userLevel) {
		this.userLevel = userLevel;
		// if user passed to a new level - vocabulary VocabularyService.all
		// hasn't been created
		App.userInfo.isLevelLaunchedFirstTime = 1;
	}

	public void setLearnedVocabulary(int learnedVocabulary) {
		this.learnedVocabulary = learnedVocabulary;
	}

	public void setNumberOfVocabularyInLevel(int numberOfVocabularyInLevel) {
		this.numberOfVocabularyInLevel = numberOfVocabularyInLevel;
	}

	public void setLearnedGrammar(int learnedGrammar) {
		this.learnedGrammar = learnedGrammar;
	}

	public void setNumberOfGrammarInLevel(int numberOfGrammarInLevel) {
		this.numberOfGrammarInLevel = numberOfGrammarInLevel;
	}

	public void setLearnedGiongo(int learnedGiongo) {
		this.learnedGiongo = learnedGiongo;
	}

	public void setNumberOfGiongoInLevel(int numberOfGiongoInLevel) {
		this.numberOfGiongoInLevel = numberOfGiongoInLevel;
	}

	public void setLearnedCounterWords(int learnedCounterWords) {
		this.learnedCounterWords = learnedCounterWords;
	}

	public void setNumberOfCounterWordsInLevel(int numberOfCounterWordsInLevel) {
		this.numberOfCounterWordsInLevel = numberOfCounterWordsInLevel;
	}


	public void setIsLevelLaunchedFirstTime(int isLevelLaunchedFirstTime) {
		this.isLevelLaunchedFirstTime = isLevelLaunchedFirstTime;
	}

	public void updateUserData(ContentResolver cr) {
		UserService us = new UserService();
		us.update(this, cr);
	}

}
