package ua.hneu.languagetrainer;

import android.app.Application;
import android.util.Log;

public class App extends Application {

	private static String userName = "";
	// From 1 to 5 (N1-N5)
	private static int userLevel = -1;
	// Vocabulary
	private static int learnedWords = -1;
	private static int allWords = -1;
	// Grammar
	private static int learnedGrammar = -1;
	private static int allGrammar = -1;
	// Audio
	private static int learnedAudio = -1;
	private static int allAudio = -1;
	// Mock tests
	private static int learnedTests = -1;
	private static int allTests = -1;
	// Other (depending on the level)
	private static int learnedGiongo = -1;
	private static int allGiongo = -1;
	private static int learnedCountWords = -1;
	private static int allCountWords = -1;

	public static String getUserName() {
		return userName;
	}

	public static int getUserLevel() {
		return userLevel;
	}

	public static int getLearnedWords() {
		return learnedWords;
	}

	public static int getAllWords() {
		return allWords;
	}

	public static int getLearnedGrammar() {
		return learnedGrammar;
	}

	public static int getAllGrammar() {
		return allGrammar;
	}

	public static int getLearnedAudio() {
		return learnedAudio;
	}

	public static int getAllAudio() {
		return allAudio;
	}

	public static int getLearnedTests() {
		return learnedTests;
	}

	public static int getAllTests() {
		return allTests;
	}

	public static int getLearnedGiongo() {
		return learnedGiongo;
	}

	public static int getAllGiongo() {
		return allGiongo;
	}

	public static int getLearnedCountWords() {
		return learnedCountWords;
	}

	public static int getAllCountWords() {
		return allCountWords;
	}

	public static void setUserName(String userName) {
		App.userName = userName;
	}

	public static void setUserLevel(int userLevel) {
		App.userLevel = userLevel;
	}

	public static void setLearnedWords(int learnedWords) {
		App.learnedWords = learnedWords;
	}

	public static void setAllWords(int allWords) {
		App.allWords = allWords;
	}

	public static void setLearnedGrammar(int learnedGrammar) {
		App.learnedGrammar = learnedGrammar;
	}

	public static void setAllGrammar(int allGrammar) {
		App.allGrammar = allGrammar;
	}

	public static void setLearnedAudio(int learnedAudio) {
		App.learnedAudio = learnedAudio;
	}

	public static void setAllAudio(int allAudio) {
		App.allAudio = allAudio;
	}

	public static void setLearnedTests(int learnedTests) {
		App.learnedTests = learnedTests;
	}

	public static void setAllTests(int allTests) {
		App.allTests = allTests;
	}

	public static void setLearnedGiongo(int learnedGiongo) {
		App.learnedGiongo = learnedGiongo;
	}

	public static void setAllGiongo(int allGiongo) {
		App.allGiongo = allGiongo;
	}

	public static void setLearnedCountWords(int learnedCountWords) {
		App.learnedCountWords = learnedCountWords;
	}

	public static void setAllCountWords(int allCountWords) {
		App.allCountWords = allCountWords;
	}

	@Override
	public void onCreate() {
		userName = "Margarita";
		userLevel = 2;
		learnedWords = 988;
		allWords = 1810;
		learnedGrammar = 15;
		allGrammar = 260;
		learnedAudio = 11;
		allAudio = 48;
		learnedGiongo = 114;
		allGiongo = 230;
		learnedCountWords = 45;
		allCountWords = 80;
		super.onCreate();
	}

}
