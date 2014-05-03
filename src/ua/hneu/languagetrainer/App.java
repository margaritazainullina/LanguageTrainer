package ua.hneu.languagetrainer;

import java.util.Locale;

import ua.hneu.edu.languagetrainer.R;
import ua.hneu.languagetrainer.masterdetailflow.MainMenuValues;
import ua.hneu.languagetrainer.model.User;
import ua.hneu.languagetrainer.model.vocabulary.DictionaryEntry;
import ua.hneu.languagetrainer.model.vocabulary.WordDictionary;
import ua.hneu.languagetrainer.passing.VocabularyPassing;
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
		
		//set localized menu elements
		MainMenuValues.addItem(new MainMenuValues.MenuItem("vocabulary", this.getString(R.string.vocabulary)));
		MainMenuValues.addItem(new MainMenuValues.MenuItem("vocabulary", this.getString(R.string.grammar)));
		MainMenuValues.addItem(new MainMenuValues.MenuItem("vocabulary", this.getString(R.string.listening)));
		MainMenuValues.addItem(new MainMenuValues.MenuItem("vocabulary", this.getString(R.string.mock_tests)));
		MainMenuValues.addItem(new MainMenuValues.MenuItem("vocabulary", this.getString(R.string.other)));
		MainMenuValues.addItem(new MainMenuValues.MenuItem("vocabulary", this.getString(R.string.settings)));
		
		cr = getContentResolver();
		VocabularyService vs = new VocabularyService();
		/*
		 * vs.dropTable(); vs.createTable(); vs.bulkInsertFromCSV("N5.txt",
		 * getAssets(), 5, getContentResolver()); vs.bulkInsertFromCSV("N4.txt",
		 * getAssets(), 4, getContentResolver()); vs.bulkInsertFromCSV("N3.txt",
		 * getAssets(), 3, getContentResolver()); vs.bulkInsertFromCSV("N3.txt",
		 * getAssets(), 2, getContentResolver()); vs.bulkInsertFromCSV("N1.txt",
		 * getAssets(), 1, getContentResolver()); us.dropTable();
		 * us.createTable();
		 */
		/*us.dropTable();
		us.createTable();
		User u = new User(1, 3, 0, vs.getNumberOfWordsInLevel(3, cr), 0, 0, 0,
				0, 0, 0, 0, 0, 10, 10, 0, "");
		us.insert(u, cr);*/

		// fetch user data from db
		userInfo = us.selectUser(getContentResolver(), 1);

		// if level is 5 or 4 - show romaji in tests
		if (userInfo.getLevel() == 4 || userInfo.getLevel() == 5)
			isShowRomaji = true;
		else
			isShowRomaji = false;

		// load dictionary currentDictionary =
		currentDictionary = VocabularyService.createCurrentDictionary(
				userInfo.getLevel(),
				userInfo.getNumberOfEntriesInCurrentDict(), cr);

		DictionaryEntry de = currentDictionary.get(0);
		de.incrementShowntimes();
		vs.update(de, cr);

		App.context = getApplicationContext();
		super.onCreate();
	}

	public static void updateUserData() {
		us.update(userInfo, cr);
	}

}
