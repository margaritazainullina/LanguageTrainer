package ua.hneu.languagetrainer;

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

	public static boolean isShowRomaji;

	@Override
	public void onCreate() {
		cr = getContentResolver();
		VocabularyService vs = new VocabularyService();
		vs.dropTable();
		vs.createTable();
		vs.bulkInsertFromCSV("N5.txt", getAssets(), 5, getContentResolver());
		us.dropTable();
		us.createTable(); 
		User u = new User(1, "ENG", 5, 0,
		637, 0, 0, 0, 0, 0, 0, 0, 0, 10, 10, 0, "");
		us.insert(u,cr);
		
		// fetch user data from db
		userInfo = us.selectUser(getContentResolver(), 1);

		// if level is 5 or 4 - show romaji in tests
		if (userInfo.getUserLevel() == 4 || userInfo.getUserLevel() == 5)
			isShowRomaji = true;
		else
			isShowRomaji = false;

		// load dictionary currentDictionary =
		currentDictionary = VocabularyService.createCurrentDictionary(
				userInfo.getUserLevel(),
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
