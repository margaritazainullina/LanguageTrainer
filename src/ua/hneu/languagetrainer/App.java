package ua.hneu.languagetrainer;

import ua.hneu.languagetrainer.db.DictionaryDbHelper;
import ua.hneu.languagetrainer.db.dao.VocabularyDAO;
import ua.hneu.languagetrainer.model.User;
import ua.hneu.languagetrainer.model.vocabulary.DictionaryEntry;
import ua.hneu.languagetrainer.model.vocabulary.WordDictionary;
import ua.hneu.languagetrainer.service.UserService;
import ua.hneu.languagetrainer.service.VocabularyService;
import android.app.Application;
import android.content.Context;
import android.database.Cursor;

public class App extends Application {

	// dictionary for session
	private static WordDictionary currentDictionary;
	// user info
	static User userInfo;
	// cursor for fetching from db
	private Cursor mCursor;
	// service for access to db
	UserService us = new UserService();

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
		this.userInfo = userInfo;
	}

	@Override
	public void onCreate() {

		VocabularyService vs = new VocabularyService();
		// vs.createTable();
	/*	us.dropTable();
		us.createTable();

		User u = new User(1, "ENG", 4, 0, 637, 0, 0, 0, 0, 0, 0, 0, 0, 10, 10,
				0);
		us.insert(u, getContentResolver());*/

		// fetch user data from db
		// DictionaryEntry de = VocabularyService.getEntryById(1,
		// getContentResolver());
		userInfo = us.selectUser(getContentResolver(), 1);

		// load dictionary currentDictionary =
		currentDictionary = VocabularyService.createCurrentDictionary(userInfo.getUserLevel(),
				userInfo.getNumberOfEntriesInCurrentDict(),
				getContentResolver());

		App.context = getApplicationContext();
		super.onCreate();
	}

	public static void updateUserData() {
		// writing to csv file current user values
		// TODO: write to db!!
		/*
		 * StringBuilder data = new StringBuilder(); data.append(userName +
		 * ", "); data.append(learnedWords + ", "); data.append(allWords +
		 * ", "); data.append(learnedGrammar + ", "); data.append(allGrammar +
		 * ", "); data.append(learnedAudio + ", "); data.append(allAudio +
		 * ", "); data.append(learnedGiongo + ", "); data.append(allGiongo +
		 * ", "); data.append(learnedCountWords + ", ");
		 * data.append(allCountWords + ", ");
		 * 
		 * DictUtil.writeFile(context, "user.txt", data.toString());
		 */
	}

}
