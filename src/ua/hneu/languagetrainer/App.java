package ua.hneu.languagetrainer;

import java.util.Locale;

import ua.hneu.edu.languagetrainer.R;
import ua.hneu.languagetrainer.masterdetailflow.MainMenuValues;
import ua.hneu.languagetrainer.model.User;
import ua.hneu.languagetrainer.model.grammar.GrammarDictionary;
import ua.hneu.languagetrainer.model.other.CounterWordsDictionary;
import ua.hneu.languagetrainer.model.other.GiongoDictionary;
import ua.hneu.languagetrainer.model.vocabulary.VocabularyDictionary;
import ua.hneu.languagetrainer.passing.GrammarPassing;
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
	public static GrammarPassing gp = new GrammarPassing();
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

		// set localized menu elements
		MainMenuValues.addItem(new MainMenuValues.MenuItem("vocabulary", this
				.getString(R.string.vocabulary)));
		MainMenuValues.addItem(new MainMenuValues.MenuItem("grammar", this
				.getString(R.string.grammar)));
		MainMenuValues.addItem(new MainMenuValues.MenuItem("mock_tests", this
				.getString(R.string.mock_tests)));
		MainMenuValues.addItem(new MainMenuValues.MenuItem("giongo", this
				.getString(R.string.giongo)));
		MainMenuValues.addItem(new MainMenuValues.MenuItem("counter_words",
				this.getString(R.string.counter_words)));
		MainMenuValues.addItem(new MainMenuValues.MenuItem("settings", this
				.getString(R.string.settings)));

		cr = getContentResolver();
		 
		// creating and inserting into whole database
		// vocabulary
//
//		vs.dropTable();
//		vs.createTable();
//		vs.bulkInsertFromCSV("N5.txt", getAssets(), 5, getContentResolver());
//		vs.bulkInsertFromCSV("N4.txt", getAssets(), 4, getContentResolver());
//		vs.bulkInsertFromCSV("N3.txt", getAssets(), 3, getContentResolver());
//		vs.bulkInsertFromCSV("N3.txt", getAssets(), 2, getContentResolver());
//		vs.bulkInsertFromCSV("N1.txt", getAssets(), 1, getContentResolver());
//
//		// user us.dropTable(); us.createTable(); // test
//
//		// ts.dropTable(); //qs.dropTable(); //as.dropTable();
//		ts.createTable();
//		qs.createTable();
//		QuestionService.startCounting(getContentResolver());
//		as.createTable();
//		ts.insertFromXml("level_def_test.xml", getAssets(),
//				getContentResolver());
//
//		GiongoService gs = new GiongoService();
//		gs.dropTable();
//		gs.createTable();
//		ges.dropTable();
//		GiongoService.startCounting(getContentResolver());
//		ges.createTable();
//		gs.bulkInsertFromCSV("giongo.txt", getAssets(), getContentResolver());
//
//		cws.dropTable();
//		cws.createTable();
//		cws.bulkInsertFromCSV("numbers.txt", getAssets(), getContentResolver());
//		cws.bulkInsertFromCSV("people_and_things.txt", getAssets(),
//				getContentResolver());
//		cws.bulkInsertFromCSV("time_calendar.txt", getAssets(),
//				getContentResolver());
//		cws.bulkInsertFromCSV("time_calendar.txt", getAssets(),
//				getContentResolver());
//		cws.bulkInsertFromCSV("extent_freq.txt", getAssets(),
//				getContentResolver());
//
//		grs.dropTable();
//		grs.createTable();
//		GrammarService.startCounting(getContentResolver());
//		gres.dropTable();
//		gres.createTable();
//		grs.bulkInsertFromCSV("grammar_n5.txt", 5, getAssets(),
//				getContentResolver());
//		us.createTable();
		// if it isn't first time when launching app - user exists in db
		User currentUser = us.getUserWithCurrentLevel(App.cr);
		if (currentUser != null) {
			// fetch user data from db
			userInfo = currentUser;
			// if level is 5 or 4 - show romaji in tests
			if (userInfo.getLevel() == 4 || userInfo.getLevel() == 5)
				isShowRomaji = true;
			else
				isShowRomaji = false;

		}
		App.context = getApplicationContext();
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
		int numOfGiongo = gs.getNumberOfGiongo(level, cr);
		int numOfCounterWords = cws.getNumberOfCounterWords(cr);
		if (currentUser == null) {
			int id = us.getNumberOfUsers(cr) + 1;
			userInfo = new User(id, level, 0, numOfVoc, 0, numOfGrammar, 0,
					numOfGiongo, 0, numOfCounterWords, 10, 10, 0, 1, 1);
			us.insert(userInfo, cr);
			// load dictionary
			vocabularyDictionary = VocabularyService.createCurrentDictionary(
					userInfo.getLevel(),
					userInfo.getNumberOfEntriesInCurrentDict(), cr);
		} else {
			userInfo = currentUser;
			us.update(userInfo, cr);
		}

	}

}
