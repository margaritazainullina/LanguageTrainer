package ua.hneu.languagetrainer;

import java.util.Locale;

import ua.hneu.edu.languagetrainer.R;
import ua.hneu.languagetrainer.masterdetailflow.MainMenuValues;
import ua.hneu.languagetrainer.model.User;
import ua.hneu.languagetrainer.model.vocabulary.WordDictionary;
import ua.hneu.languagetrainer.passing.VocabularyPassing;
import ua.hneu.languagetrainer.service.AnswerService;
import ua.hneu.languagetrainer.service.GiongoService;
import ua.hneu.languagetrainer.service.QuestionService;
import ua.hneu.languagetrainer.service.TestService;
import ua.hneu.languagetrainer.service.UserService;
import ua.hneu.languagetrainer.service.VocabularyService;
import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;

public class App extends Application {

	// dictionary for session
	public static WordDictionary currentDictionary;
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
		MainMenuValues.addItem(new MainMenuValues.MenuItem("vocabulary", this
				.getString(R.string.grammar)));
		MainMenuValues.addItem(new MainMenuValues.MenuItem("vocabulary", this
				.getString(R.string.listening)));
		MainMenuValues.addItem(new MainMenuValues.MenuItem("vocabulary", this
				.getString(R.string.mock_tests)));
		MainMenuValues.addItem(new MainMenuValues.MenuItem("vocabulary", this
				.getString(R.string.other)));
		MainMenuValues.addItem(new MainMenuValues.MenuItem("vocabulary", this
				.getString(R.string.settings)));

		cr = getContentResolver();
		VocabularyService vs = new VocabularyService();
		//us.dropTable();
		//us.createTable();
		 //vocabulary
		/*vs.dropTable();
		vs.createTable();
		vs.bulkInsertFromCSV("N5.txt", getAssets(), 5, getContentResolver());
		vs.bulkInsertFromCSV("N4.txt", getAssets(), 4, getContentResolver());
		vs.bulkInsertFromCSV("N3.txt", getAssets(), 3, getContentResolver());
		vs.bulkInsertFromCSV("N3.txt", getAssets(), 2, getContentResolver());
		vs.bulkInsertFromCSV("N1.txt", getAssets(), 1, getContentResolver());
		//user
		us.dropTable();
		us.createTable();
		//test	
		TestService ts= new TestService();
		QuestionService qs= new QuestionService();
		QuestionService.startCounting(getContentResolver());
		AnswerService as= new AnswerService();
		ts.dropTable();
		qs.dropTable();
		as.dropTable();
		ts.createTable();
		qs.createTable();
		as.createTable();		
		ts.insertFromXml("level_def_test.xml", getAssets(), getContentResolver());*/
		// if it isn't first time when launching app - user exists in db
		
		
		/*User currentUser = us.getUserWithCurrentLevel(cr);
		if (currentUser != null) {
			// fetch user data from db
			userInfo = currentUser;
			// if level is 5 or 4 - show romaji in tests
			if (userInfo.getLevel() == 4 || userInfo.getLevel() == 5)
				isShowRomaji = true;
			else
				isShowRomaji = false;

			// load dictionary
			currentDictionary = VocabularyService.createCurrentDictionary(
					userInfo.getLevel(),
					userInfo.getNumberOfEntriesInCurrentDict(), cr);

		}
		App.context = getApplicationContext();
		super.onCreate();*/
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
					vs.getNumberOfWordsInLevel(5, cr), 0, 0, 0, 0, 0, 0, 0, 0,
					10, 10, 0, 1, 1);
			us.insert(userInfo, cr);
			// load dictionary
			currentDictionary = VocabularyService.createCurrentDictionary(
					userInfo.getLevel(),
					userInfo.getNumberOfEntriesInCurrentDict(), cr);
		} else {
			userInfo = currentUser;
			us.update(userInfo, cr);
		}

	}

}
