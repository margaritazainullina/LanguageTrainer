package ua.hneu.languagetrainer.data;

public class CurrentUserData {
	private static volatile CurrentUserData instance = null;
	private String userName = "";
	// -1 - Ð½Ðµ Ð¾Ð¿Ñ€ÐµÐ´ÐµÐ»ÐµÐ½Ð¾, 0-Ð½Ð°Ñ‡Ð°Ð»ÑŒÐ½Ñ‹Ð¹ 5-1 Ñ�Ð¾Ð¾Ñ‚Ð²ÐµÑ‚Ñ�Ñ‚Ð²ÑƒÐµÑ‚ N5-N1
	private int userLevel = -1;
	// Ð»ÐµÐºÑ�Ð¸ÐºÐ°
	private int learnedWords = -1;
	private int allWords = -1;
	// Ð³Ñ€Ð°Ð¼Ð¼Ð°Ñ‚Ð¸ÐºÐ°
	private int learnedGrammar = -1;
	private int allGrammar = -1;
	// Ð°ÑƒÐ´Ð¸Ð¾Ð²Ð°Ð½Ð¸Ðµ
	private int learnedAudio = -1;
	private int allAudio = -1;
	// Ñ‚ÐµÑ�Ñ‚Ñ‹
	private int learnedTests = -1;
	private int allTests = -1;
	// Ð´Ñ€ÑƒÐ³Ð¾Ðµ
	private int learnedGiongo = -1;
	private int allGiongo = -1;
	private int learnedCountWords = -1;
	private int allCountWords = -1;

	private CurrentUserData() {
		//TODO: fetch these valuse from bd or xml file, currently - stub
		userName = "Margarita";
		userLevel = 2;
		learnedWords = 563;
		allWords = 1810;
		learnedGrammar = 15;
		allGrammar = 260;
		learnedAudio = 11;
		allAudio = 48;
		learnedGiongo = 114;
		allGiongo = 230;
		learnedCountWords = 45;
		allCountWords = 80;
	}

	public static synchronized CurrentUserData getInstance() {
		if (instance == null) {
			instance = new CurrentUserData();
		}
		return instance;
	}

	public String getUserName() {
		return userName;
	}

	public int getUserLevel() {
		return userLevel;
	}

	public int getLearnedWords() {
		return learnedWords;
	}

	public int getAllWords() {
		return allWords;
	}

	public int getLearnedGrammar() {
		return learnedGrammar;
	}

	public int getAllGrammar() {
		return allGrammar;
	}

	public int getLearnedAudio() {
		return learnedAudio;
	}

	public int getAllAudio() {
		return allAudio;
	}

	public int getLearnedTests() {
		return learnedTests;
	}

	public int getAllTests() {
		return allTests;
	}

	public int getLearnedGiongo() {
		return learnedGiongo;
	}

	public int getAllGiongo() {
		return allGiongo;
	}

	public int getLearnedCountWords() {
		return learnedCountWords;
	}

	public int getAllCountWords() {
		return allCountWords;
	}
	
}
