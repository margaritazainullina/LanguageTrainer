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
	
	@Override
	public void onCreate() {
		cr = getContentResolver();		
		  /*us.dropTable();
		  us.createTable();		  
		  User u = new User(1, "ENG", 5, 0, 637, 0, 0, 0, 0, 0, 0, 0, 0, 10,
		  10, 0, "2013-10-07 08:23:19"); 
		  us.insert(u,cr);		*/ 
		//User u1 = new User(1, "ENG", 5, 0, 637, 0, 0, 0, 0, 0, 0, 0, 0, 10,10, 0, "2013-10-07 08:23:19");
		//us.update(u1, cr);
		
		
		// fetch user data from db
		userInfo = us.selectUser(getContentResolver(), 1);

		// load dictionary currentDictionary =
		currentDictionary = VocabularyService.createCurrentDictionary(
				userInfo.getUserLevel(),
				userInfo.getNumberOfEntriesInCurrentDict(), cr);
		
		DictionaryEntry de = currentDictionary.get(0);
		de.setKanji("1");
		vs.update(de, cr);
		
		App.context = getApplicationContext();
		super.onCreate();
	}

	public static void updateUserData() {
		us.update(userInfo, cr);
	}

}
