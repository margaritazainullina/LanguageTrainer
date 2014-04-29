package ua.hneu.languagetrainer;

import java.util.Date;
import java.util.Locale;

import ua.hneu.languagetrainer.db.DictionaryDbHelper;
import ua.hneu.languagetrainer.db.dao.VocabularyDAO;
import ua.hneu.languagetrainer.model.User;
import ua.hneu.languagetrainer.model.vocabulary.DictionaryEntry;
import ua.hneu.languagetrainer.model.vocabulary.WordDictionary;
import ua.hneu.languagetrainer.passing.VocabularyPassing;
import ua.hneu.languagetrainer.service.UserService;
import ua.hneu.languagetrainer.service.VocabularyService;
import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;

public class App extends Application {

	// dictionary for session
	private static WordDictionary currentDictionary;
	// user info
	private static User userInfo;
	// service for access to db
	private static UserService us = new UserService();
	// Object for saving information about current passing;
	private static VocabularyPassing vp = new VocabularyPassing();
	// contentResolver for database
	private static ContentResolver cr;

	private static Context context;

	public static WordDictionary getCurrentDictionary() {
		return currentDictionary;
	}

	public static User getUserInfo() {
		return userInfo;
	}

	public static void setCurrentDictionary(WordDictionary currentDictionary) {
		App.currentDictionary = currentDictionary;
	}

	public void setUserInfo(User userInfo) {
		App.userInfo = userInfo;
	}

	@Override
	public void onCreate() {
		cr = getContentResolver();
		
		  us.dropTable();
		  us.createTable();		  
		  User u = new User(1, "ENG", 4, 0, 637, 0, 0, 0, 0, 0, 0, 0, 0, 10,
		  10, 0, "2013-10-07 08:23:19"); // 2013-10-07 08:23:19 
		  us.insert(u,cr);
		 

		// fetch user data from db
		userInfo = us.selectUser(getContentResolver(), 1);

		// load dictionary currentDictionary =
		currentDictionary = VocabularyService.createCurrentDictionary(
				userInfo.getUserLevel(),
				userInfo.getNumberOfEntriesInCurrentDict(), cr);

		App.context = getApplicationContext();
		super.onCreate();
	}

	public static void updateUserData() {
		us.edit(userInfo, cr);
	}

}
