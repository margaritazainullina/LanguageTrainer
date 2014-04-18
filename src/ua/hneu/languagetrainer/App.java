package ua.hneu.languagetrainer;

import ua.hneu.languagetrainer.xmlparcing.DictUtil;
import ua.hneu.languagetrainer.xmlparcing.WordDictionary;
import android.app.Application;
import android.content.Context;

public class App extends Application {
	 private static Context context;
	 
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
	
	//dictionary
	private static WordDictionary currentDictionary;	
	
	public static WordDictionary getCurrentDictionary() {
		return currentDictionary;
	}

	public static void setCurrentDictionary(WordDictionary currentDictionary1) {
		currentDictionary = currentDictionary1;
	}

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

	// if data was updated by set method - also write it to the file
	public void setUserName(String userName) {
		App.userName = userName;		
		updateUserData();
	}

	public static void setUserLevel(int userLevel) {
		App.userLevel = userLevel;
		updateUserData();
	}

	public static void setLearnedWords(int learnedWords) {
		App.learnedWords = learnedWords;
		updateUserData();
	}

	public static void setAllWords(int allWords) {
		App.allWords = allWords;
		updateUserData();
	}

	public static void setLearnedGrammar(int learnedGrammar) {
		App.learnedGrammar = learnedGrammar;
		updateUserData();
	}

	public static void setAllGrammar(int allGrammar) {
		App.allGrammar = allGrammar;
		updateUserData();
	}

	public static void setLearnedAudio(int learnedAudio) {
		App.learnedAudio = learnedAudio;
		updateUserData();
	}

	public static void setAllAudio(int allAudio) {
		App.allAudio = allAudio;
		updateUserData();
	}

	public static void setLearnedTests(int learnedTests) {
		App.learnedTests = learnedTests;
		updateUserData();
	}

	public static void setAllTests(int allTests) {
		App.allTests = allTests;
		updateUserData();
	}

	public static void setLearnedGiongo(int learnedGiongo) {
		App.learnedGiongo = learnedGiongo;
		updateUserData();
	}

	public static void setAllGiongo(int allGiongo) {
		App.allGiongo = allGiongo;
		updateUserData();
	}

	public static void setLearnedCountWords(int learnedCountWords) {
		App.learnedCountWords = learnedCountWords;
		updateUserData();
	}

	public static void setAllCountWords(int allCountWords) {
		App.allCountWords = allCountWords;
		updateUserData();
	}

	@Override
	public void onCreate() {		
		// reading from csv file user.txt
		// all values are separated with commas and match the class fields
		String data = DictUtil.readFile(this, "user.txt");
		String[] elements = data.split("(,)(\\s)+");
		userName = elements[0];
		userLevel = Integer.parseInt(elements[1]);
		learnedWords = Integer.parseInt(elements[2]);
		allWords = Integer.parseInt(elements[3]);
		learnedGrammar = Integer.parseInt(elements[4]);
		allGrammar = Integer.parseInt(elements[5]);
		learnedAudio = Integer.parseInt(elements[6]);
		allAudio = Integer.parseInt(elements[7]);
		learnedGiongo = Integer.parseInt(elements[8]);
		allGiongo = Integer.parseInt(elements[9]);
		learnedCountWords = Integer.parseInt(elements[10]);
		allCountWords = Integer.parseInt(elements[11]);

		App.context = getApplicationContext();
		super.onCreate();
	}
		
	public  static void updateUserData() {
		// writing to csv file current user values
		StringBuilder data = new StringBuilder();
		data.append(userName + ", ");
		data.append(learnedWords + ", ");
		data.append(allWords + ", ");
		data.append(learnedGrammar + ", ");
		data.append(allGrammar + ", ");
		data.append(learnedAudio + ", ");
		data.append(allAudio + ", ");
		data.append(learnedGiongo + ", ");
		data.append(allGiongo + ", ");
		data.append(learnedCountWords + ", ");
		data.append(allCountWords + ", ");

		DictUtil.writeFile(context, "user.txt", data.toString());
	}

}
