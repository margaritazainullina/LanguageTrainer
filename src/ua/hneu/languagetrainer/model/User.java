package ua.hneu.languagetrainer.model;

import java.util.Date;

import android.content.ContentResolver;

import ua.hneu.languagetrainer.service.UserService;

public class User {
	private int id;
	private String language;
	// From 1 to 5 (N1-N5)
	// "-1" means that user haven't selected level yet
	private int userLevel = -1;
	// Vocabulary
	private int learnedVocabulary = 0;
	private int numberOfVocabularyInLevel = 0;
	// Grammar
	private int learnedGrammar = 0;
	private int numberOfGrammarInLevel = 0;
	// Audio
	private int learnedAudio = 0;
	private int numberOfAudioInLevel = 0;
	// Other (depending on the level)
	private int learnedGiongo = 0;
	private int numberOfGiongoInLevel = 0;
	private int learnedCounterWords = 0;
	private int numberOfCounterWordsInLevel = 0;
	private int numberOfEntriesInCurrentDict = 0;
	private int numberOfRepeatationsForLearning = 0;
	// Information about tests is in table Tests
	// just statistics for quick access
	private double testAveragePercentage = 0;
	private String lastPassing;

	public User(int id, String language, int userLevel, int learnedVocabulary,
			int numberOfVocabularyInLevel, int learnedGrammar,
			int numberOfGrammarInLevel, int learnedAudio,
			int numberOfAudioInLevel, int learnedGiongo,
			int numberOfGiongoInLevel, int learnedCounterWords,
			int numberOfCounterWordsInLevel, int numberOfEntriesInCurrentDict,
			int numberOfRepeatationsForLearning, double testAveragePercentage, String lastPassing) {
		super();
		this.id = id;
		this.language = language;
		this.userLevel = userLevel;
		this.learnedVocabulary = learnedVocabulary;
		this.numberOfVocabularyInLevel = numberOfVocabularyInLevel;
		this.learnedGrammar = learnedGrammar;
		this.numberOfGrammarInLevel = numberOfGrammarInLevel;
		this.learnedAudio = learnedAudio;
		this.numberOfAudioInLevel = numberOfAudioInLevel;
		this.learnedGiongo = learnedGiongo;
		this.numberOfGiongoInLevel = numberOfGiongoInLevel;
		this.learnedCounterWords = learnedCounterWords;
		this.numberOfCounterWordsInLevel = numberOfCounterWordsInLevel;
		this.numberOfEntriesInCurrentDict = numberOfEntriesInCurrentDict;
		this.numberOfRepeatationsForLearning = numberOfRepeatationsForLearning;
		this.testAveragePercentage = testAveragePercentage;
		this.lastPassing = lastPassing;
	}

	public int getId() {
		return id;
	}

	public String getLanguage() {
		return language;
	}

	public int getUserLevel() {
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

	public int getLearnedAudio() {
		return learnedAudio;
	}

	public int getNumberOfAudioInLevel() {
		return numberOfAudioInLevel;
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

	public int getNumberOfEntriesInCurrentDict() {
		return numberOfEntriesInCurrentDict;
	}

	public int getNumberOfRepeatationsForLearning() {
		return numberOfRepeatationsForLearning;
	}

	public double getTestAveragePercentage() {
		return testAveragePercentage;
	}
	
	public String getLastPassing() {
		return lastPassing;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public void setUserLevel(int userLevel) {
		this.userLevel = userLevel;
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

	public void setLearnedAudio(int learnedAudio) {
		this.learnedAudio = learnedAudio;
	}

	public void setNumberOfAudioInLevel(int numberOfAudioInLevel) {
		this.numberOfAudioInLevel = numberOfAudioInLevel;
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

	public void setNumberOfEntriesInCurrentDict(int numberOfEntriesInCurrentDict) {
		this.numberOfEntriesInCurrentDict = numberOfEntriesInCurrentDict;
	}

	public void setNumberOfRepeatationsForLearning(
			int numberOfRepeatationsForLearning) {
		this.numberOfRepeatationsForLearning = numberOfRepeatationsForLearning;
	}

	public void setTestAveragePercentage(double testAveragePercentage) {
		this.testAveragePercentage = testAveragePercentage;
	}
	public void setLastPassing(String lastPassing) {
		this.lastPassing = lastPassing;
		//updateUserData();
	}

	// increment for percentage of learned element when responding correctly
	public double getPercentageIncrement() {
		return 1.0 / numberOfRepeatationsForLearning;
	}

	public void updateUserData(ContentResolver cr) {
		UserService us = new UserService();
		us.update(this, cr);
	}

}
