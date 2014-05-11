package ua.hneu.languagetrainer;

import java.util.ArrayList;
import java.util.Locale;

import ua.hneu.edu.languagetrainer.R;
import ua.hneu.languagetrainer.masterdetailflow.MainMenuValues;
import ua.hneu.languagetrainer.model.User;
import ua.hneu.languagetrainer.model.grammar.GrammarRule;
import ua.hneu.languagetrainer.model.vocabulary.WordDictionary;
import ua.hneu.languagetrainer.service.GiongoExampleService;
import ua.hneu.languagetrainer.passing.VocabularyPassing;
import ua.hneu.languagetrainer.service.AnswerService;
import ua.hneu.languagetrainer.service.CounterWordsService;
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
	public static WordDictionary vocabularyDictionary;
	// grammar for session
	public static ArrayList<GrammarRule> grammarDictionary;
	// user info
	public static User userInfo;
	// service for access to db
	public static UserService us = new UserService();
	// Object for saving information about current passing;
	public static VocabularyPassing vp = new VocabularyPassing();
	// contentResolver for database
	public static ContentResolver cr;
	public static VocabularyService vs = new VocabularyService();
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
		MainMenuValues.addItem(new MainMenuValues.MenuItem("listening", this
				.getString(R.string.listening)));
		MainMenuValues.addItem(new MainMenuValues.MenuItem("mock_tests", this
				.getString(R.string.mock_tests)));
		MainMenuValues.addItem(new MainMenuValues.MenuItem("other", this
				.getString(R.string.other)));
		MainMenuValues.addItem(new MainMenuValues.MenuItem("settings", this
				.getString(R.string.settings)));

		cr = getContentResolver();
		// creating and inserting into whole database

		VocabularyService vs = new VocabularyService();
		// vocabulary
		/*vs.dropTable();
		vs.createTable();
		vs.bulkInsertFromCSV("N5.txt", getAssets(), 5, getContentResolver());
		vs.bulkInsertFromCSV("N4.txt", getAssets(), 4, getContentResolver());
		vs.bulkInsertFromCSV("N3.txt", getAssets(), 3, getContentResolver());
		vs.bulkInsertFromCSV("N3.txt", getAssets(), 2, getContentResolver());
		vs.bulkInsertFromCSV("N1.txt", getAssets(), 1, getContentResolver());*/
		// user //us.dropTable();
		//us.createTable();
		// test
		/*
		 * TestService ts= new TestService(); QuestionService qs = new
		 * QuestionService(); AnswerService as = new AnswerService();
		 * ts.dropTable(); qs.dropTable(); as.dropTable(); ts.createTable();
		 * qs.createTable();
		 * QuestionService.startCounting(getContentResolver());
		 * as.createTable(); ts.insertFromXml("level_def_test.xml", getAssets(),
		 * getContentResolver());
		 */

		GiongoService gs = new GiongoService();
		GiongoExampleService ges = new GiongoExampleService();
		gs.dropTable();
		gs.createTable();
		ges.dropTable();
		GiongoService.startCounting(getContentResolver());
		ges.createTable();
		gs.bulkInsertFromCSV("giongo.txt", getAssets(), getContentResolver());

		CounterWordsService cws = new CounterWordsService();
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

		GrammarService grs = new GrammarService();
		GrammarExampleService gres = new GrammarExampleService();
		grs.dropTable();
		grs.createTable();
		GrammarService.startCounting(getContentResolver());
		gres.dropTable();
		gres.createTable();
		grs.bulkInsertFromCSV("grammar_n5.txt", 5, getAssets(),
				getContentResolver());

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
		User currentUser = us.selectUser(cr, level);
		if (currentUser == null) {
			int id = us.getNumberOfUsers(cr) + 1;
			userInfo = new User(id, level, 0,
					vs.getNumberOfWordsInLevel(5, cr), 0, 0, 0, 0, 0, 0, 10,
					10, 0, 1, 1);
			us.insert(userInfo, cr);
			// load dictionary
			vocabularyDictionary = VocabularyService.createCurrentDictionary(
					userInfo.getLevel(),
					userInfo.getNumberOfVocabularyInCurrentDict(), cr);
		} else {
			userInfo = currentUser;
			us.update(userInfo, cr);
		}

	}

}
