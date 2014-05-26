package ua.hneu.languagetrainer;

import java.util.Locale;

import ua.hneu.edu.languagetrainer.R;
import ua.hneu.languagetrainer.masterdetailflow.MenuElements;
import ua.hneu.languagetrainer.model.User;
import ua.hneu.languagetrainer.model.grammar.GrammarDictionary;
import ua.hneu.languagetrainer.model.other.CounterWordsDictionary;
import ua.hneu.languagetrainer.model.other.GiongoDictionary;
import ua.hneu.languagetrainer.model.vocabulary.VocabularyDictionary;
import ua.hneu.languagetrainer.passing.CounterWordsPassing;
import ua.hneu.languagetrainer.passing.GiongoPassing;
import ua.hneu.languagetrainer.passing.GrammarPassing;
import ua.hneu.languagetrainer.passing.TestPassing;
import ua.hneu.languagetrainer.passing.VocabularyPassing;
import ua.hneu.languagetrainer.service.AnswerService;
import ua.hneu.languagetrainer.service.CounterWordsService;
import ua.hneu.languagetrainer.service.GiongoExampleService;
import ua.hneu.languagetrainer.service.GiongoService;
import ua.hneu.languagetrainer.service.GrammarExampleService;
import ua.hneu.languagetrainer.service.GrammarService;
import ua.hneu.languagetrainer.service.QuestionService;
import ua.hneu.languagetrainer.service.TestService;
import ua.hneu.languagetrainer.service.UserService;
import ua.hneu.languagetrainer.service.VocabularyService;
import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class App extends Application {

	// dictionary for session
	public static VocabularyDictionary vocabularyDictionary;
	// grammar for session
	public static GrammarDictionary grammarDictionary;
	// giongo for session
	public static GiongoDictionary giongoWordsDictionary;
	// counter words for session
	public static CounterWordsDictionary counterWordsDictionary;

	// user info
	public static User userInfo;
	// service for access to db
	public static UserService us = new UserService();
	// Object for saving information about current vocabulary passing
	public static VocabularyPassing vp = new VocabularyPassing();
	// Object for saving information about current grammar passing;
	public static GrammarPassing grp = new GrammarPassing();
	// Object for saving information about current giongo passing;
	public static GiongoPassing gp = new GiongoPassing();
	// Object for saving information about current counter words passing;
	public static CounterWordsPassing cwp = new CounterWordsPassing();
	// Object for saving information about test passing;
	public static TestPassing tp = new TestPassing();
	// contentResolver for database
	public static ContentResolver cr;
	public static VocabularyService vs = new VocabularyService();
	public static TestService ts = new TestService();
	public static QuestionService qs = new QuestionService();
	public static AnswerService as = new AnswerService();
	public static GiongoService gs = new GiongoService();
	public static GiongoExampleService ges = new GiongoExampleService();
	public static GrammarService grs = new GrammarService();
	public static GrammarExampleService gres = new GrammarExampleService();
	public static CounterWordsService cws = new CounterWordsService();
	public static Context context;
	public static Languages lang;
	public static boolean isShowRomaji;
	public static int numberOfEntriesInCurrentDict = 0;
	public static int numberOfRepeatationsForLearning = 0;
	static SharedPreferences settings;
	public static Editor editor;

	public enum Languages {
		ENG, RUS
	};

	@Override
	public void onCreate() {
		// get current location
		if (Locale.getDefault().getDisplayLanguage().equals("русский"))
			lang = Languages.RUS;
		else
			lang = Languages.ENG;
		// preferences from storage
		settings = getSharedPreferences("appSettings", MODE_PRIVATE);
		editor = settings.edit();

		// set localized menu elements
		MenuElements.addItem(new MenuElements.MenuItem("vocabulary", this
				.getString(R.string.vocabulary)));
		MenuElements.addItem(new MenuElements.MenuItem("grammar", this
				.getString(R.string.grammar)));
		MenuElements.addItem(new MenuElements.MenuItem("mock_tests", this
				.getString(R.string.mock_tests)));
		MenuElements.addItem(new MenuElements.MenuItem("giongo", this
				.getString(R.string.giongo)));
		MenuElements.addItem(new MenuElements.MenuItem("counter_words", this
				.getString(R.string.counter_words)));
		MenuElements.addItem(new MenuElements.MenuItem("settings", this
				.getString(R.string.settings)));

		cr = getContentResolver();

		// creating and inserting into whole database
		// vocabulary

		/*vs.dropTable();
		vs.createTable();
		vs.bulkInsertFromCSV("N5.txt", getAssets(), 5, getContentResolver());
		vs.bulkInsertFromCSV("N4.txt", getAssets(), 4, getContentResolver());
		vs.bulkInsertFromCSV("N3.txt", getAssets(), 3, getContentResolver());
		vs.bulkInsertFromCSV("N3.txt", getAssets(), 2, getContentResolver());
		vs.bulkInsertFromCSV("N1.txt", getAssets(), 1, getContentResolver());

		// test
		ts.dropTable();
		qs.dropTable();
		as.dropTable();
		ts.createTable();
		TestService.startCounting(getContentResolver());
		qs.createTable();
		QuestionService.startCounting(getContentResolver());
		as.createTable();
		ts.insertFromXml("level_def_test.xml", getAssets(),
				getContentResolver());
		ts.insertFromXml("mock_test_n5.xml", getAssets(), getContentResolver());

		/*GiongoService gs = new GiongoService();
		gs.dropTable();
		gs.createTable();
		ges.dropTable();
		GiongoService.startCounting(getContentResolver());
		ges.createTable();
		gs.bulkInsertFromCSV("giongo.txt", getAssets(), getContentResolver());

		cws.dropTable();
		cws.createTable();
		cws.bulkInsertFromCSV("numbers.txt", getAssets(), getContentResolver());
		cws.bulkInsertFromCSV("people_and_things.txt", getAssets(),
				getContentResolver());
		cws.bulkInsertFromCSV("time_calendar.txt", getAssets(),
				getContentResolver());
		cws.bulkInsertFromCSV("time_calendar.txt", getAssets(),
				getContentResolver());
		cws.bulkInsertFromCSV("extent_freq.txt", getAssets(),
				getContentResolver());

		grs.dropTable();
		grs.createTable();
		GrammarService.startCounting(getContentResolver());
		gres.dropTable();
		gres.createTable();
		grs.bulkInsertFromCSV("grammar_n5.txt", 5, getAssets(),
				getContentResolver());
*/
		us.dropTable();
		us.createTable();

		// if it isn't first time when launching app - user exists in db
		User currentUser = us.getUserWithCurrentLevel(App.cr);
		if (currentUser != null) {
			// fetch user data from db
			userInfo = currentUser;
		}
		App.context = getApplicationContext();
		getSettings();
		super.onCreate();
	}

	public static void updateUserData() {
		us.update(userInfo, cr);
	}

	public static void goToLevel(int level) {
		// if user with this level doesn't exist - create new user with new
		// level
		User currentUser = us.selectUser(level, cr);
		int numOfVoc = vs.getNumberOfWordsInLevel(level, cr);
		int numOfGrammar = grs.getNumberOfGrammarInLevel(level, cr);
		int numOfGiongo = gs.getNumberOfGiongo(cr);
		int numOfCounterWords = cws.getNumberOfCounterWords(cr);
		if (currentUser == null) {
			int id = us.getNumberOfUsers(cr) + 1;
			userInfo = new User(id, level, 0, numOfVoc, 0, numOfGrammar, 0,
					numOfGiongo, 0, numOfCounterWords, 1, 1);
			// set all other users as not current
			us.insert(userInfo, cr);
			// load dictionary
			vocabularyDictionary = VocabularyService.createCurrentDictionary(
					userInfo.getLevel(), numberOfEntriesInCurrentDict, cr);
			App.editor.putString("showRomaji", "only_4_5");
			// default settings when launched first
			if (level == 4 || level == 5)
				App.isShowRomaji = true;
			else
				App.isShowRomaji = false;
			editor.putInt("numOfEntries", 7);
			numberOfEntriesInCurrentDict = 7;
			editor.putInt("numOfRepetations", 7);
			numberOfRepeatationsForLearning = 7;
		} else {
			userInfo = currentUser;
			us.update(userInfo, cr);
			editor.putInt("numOfEntries", 7);

			us.setAsInactiveOtherLevels(level, cr);
			editor.putInt("level", level);
			editor.apply();
		}
		getSettings();
	}

	public static void getSettings() {
		numberOfEntriesInCurrentDict = settings.getInt("numOfEntries", 7);
		numberOfRepeatationsForLearning = settings
				.getInt("numOfRepetations", 7);
		String value = settings.getString("showRomaji", "only_4_5");
		if (value.equals("always")) {
			App.isShowRomaji = true;
			editor.putString("showRomaji", value);
		} else if (value.equals("only_4_5")) {
			if (App.userInfo == null)
				App.isShowRomaji = true;
			else if (App.userInfo.getLevel() == 4
					|| App.userInfo.getLevel() == 5)
				App.isShowRomaji = true;
			else
				App.isShowRomaji = false;
		} else {
			App.isShowRomaji = false;
		}
	}

	public static long[] getTimeTestLimits() {
		long timeLimit1 = 0;
		long timeLimit2 = 0;
		long timeLimit3 = 0;
		switch (userInfo.getIsCurrentLevel()) {
		// in milliseconds
		case 1:
			timeLimit1 = 110 * 60 * 1000;
			timeLimit2 = 0;
			timeLimit3 = 60 * 60 * 1000;
		case 2:
			timeLimit1 = 105 * 60 * 1000;
			timeLimit2 = 0;
			timeLimit3 = 50 * 60 * 1000;
		case 3:
			timeLimit1 = 30 * 60 * 1000;
			timeLimit2 = 70 * 60 * 1000;
			timeLimit3 = 40 * 60 * 1000;
		case 4:
			timeLimit1 = 30 * 60 * 1000;
			timeLimit2 = 60 * 60 * 1000;
			timeLimit3 = 35 * 60 * 1000;
		case 5:
			//timeLimit1 = 20 * 1000;
			timeLimit1 = 25 * 60 * 1000;
			timeLimit2 = 50 * 60 * 1000;
			timeLimit3 = 30 * 60 * 1000;
		}
		return new long[] { timeLimit1, timeLimit2, timeLimit3 };
	}

	// increment for percentage of learned element when responding correctly
	public static double getPercentageIncrement() {
		return 1.0 / numberOfRepeatationsForLearning;
	}

	public static boolean isVocabularyLearned() {
		return (userInfo.getNumberOfVocabularyInLevel() == userInfo
				.getLearnedVocabulary());
	}

	public static boolean isGrammarLearned() {
		return (userInfo.getNumberOfGrammarInLevel() == userInfo
				.getLearnedGrammar());
	}

	public static boolean isGiongoLearned() {
		return (userInfo.getNumberOfGiongoInLevel() == userInfo
				.getLearnedGiongo());
	}

	public static boolean isCounterWordsLearned() {
		return (userInfo.getNumberOfCounterWordsInLevel() == userInfo
				.getLearnedCounterWords());
	}

	public static boolean isAllTestsPassed() {
		return ts.isAllTestsPassed(cr, userInfo.getLevel());
	}

	public static boolean isAllLearned() {
		if (isVocabularyLearned() && isGrammarLearned() && isGiongoLearned()
				&& isCounterWordsLearned() && isAllTestsPassed()) {
			goToLevel(userInfo.getLevel() + 1);
			return true;
		}
		return false;
	}
}
