package ua.hneu.languagetrainer.service;

import ua.hneu.languagetrainer.db.dao.UserDAO;
import ua.hneu.languagetrainer.model.User;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UserService {

	public void insert(User u, ContentResolver cr) {
		ContentValues values = new ContentValues();
		values.put(UserDAO.LEVEL, u.getLevel());
		values.put(UserDAO.LEARNEDVOC, u.getLearnedVocabulary());
		values.put(UserDAO.ALLVOC, u.getNumberOfVocabularyInLevel());
		values.put(UserDAO.LEARNEDGRAMMAR, u.getLearnedGrammar());
		values.put(UserDAO.ALLGRAMMAR, u.getNumberOfGrammarInLevel());
		values.put(UserDAO.LEARNEDGIONGO, u.getLearnedGiongo());
		values.put(UserDAO.ALLGIONGO, u.getNumberOfGiongoInLevel());
		values.put(UserDAO.LEARNEDCWORDS, u.getLearnedCounterWords());
		values.put(UserDAO.ALLCWORDS, u.getNumberOfCounterWordsInLevel());
		values.put(UserDAO.ISLEVELLAUNCHEDFIRSTTIME,
				u.getIsLevelLaunchedFirstTime());
		values.put(UserDAO.ISCURRENTLEVEL, u.getIsCurrentLevel());
		cr.insert(UserDAO.CONTENT_URI, values);
	}

	public void update(User u, ContentResolver cr) {
		ContentValues values = new ContentValues();
		values.put(UserDAO.LEVEL, u.getLevel());
		values.put(UserDAO.LEARNEDVOC, u.getLearnedVocabulary());
		values.put(UserDAO.ALLVOC, u.getNumberOfVocabularyInLevel());
		values.put(UserDAO.LEARNEDGRAMMAR, u.getLearnedGrammar());
		values.put(UserDAO.ALLGRAMMAR, u.getNumberOfGrammarInLevel());
		values.put(UserDAO.LEARNEDGIONGO, u.getLearnedGiongo());
		values.put(UserDAO.ALLGIONGO, u.getNumberOfGiongoInLevel());
		values.put(UserDAO.LEARNEDCWORDS, u.getLearnedCounterWords());
		values.put(UserDAO.ALLCWORDS, u.getNumberOfCounterWordsInLevel());
		values.put(UserDAO.ISLEVELLAUNCHEDFIRSTTIME,
				u.getIsLevelLaunchedFirstTime());
		values.put(UserDAO.ISCURRENTLEVEL, u.getIsCurrentLevel());
		cr.update(UserDAO.CONTENT_URI, values, "_ID=" + u.getId(), null);
	}

	public void emptyTable() {
		UserDAO.getDb().execSQL("delete from " + UserDAO.TABLE_NAME);
	}

	public void createTable() {
		SQLiteDatabase db = UserDAO.getDb();
		db.execSQL("CREATE TABLE if not exists " + UserDAO.TABLE_NAME
				+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + UserDAO.LEVEL
				+ " INTEGER, " + UserDAO.LEARNEDVOC + " INTEGER, "
				+ UserDAO.ALLVOC + " INTEGER, " + UserDAO.LEARNEDGRAMMAR
				+ " INTEGER, " + UserDAO.ALLGRAMMAR + " INTEGER, "
				+ UserDAO.LEARNEDGIONGO + " INTEGER," + UserDAO.ALLGIONGO
				+ " INTEGER, " + UserDAO.LEARNEDCWORDS + " INTEGER,"
				+ UserDAO.ALLCWORDS + " INTEGER,"
				+ UserDAO.ISLEVELLAUNCHEDFIRSTTIME + " INTEGER,"
				+ UserDAO.ISCURRENTLEVEL + " INTEGER);");
	}

	public void dropTable() {
		UserDAO.getDb().execSQL(
				"DROP TABLE if exists " + UserDAO.TABLE_NAME + ";");
	}

	@SuppressLint("NewApi")
	public User selectUser(int level, ContentResolver cr) {
		String[] selectionArgs = { UserDAO.ID, UserDAO.LEVEL,
				UserDAO.LEARNEDVOC, UserDAO.ALLVOC, UserDAO.LEARNEDGRAMMAR,
				UserDAO.ALLGRAMMAR, UserDAO.LEARNEDGIONGO, UserDAO.ALLGIONGO,
				UserDAO.LEARNEDCWORDS, UserDAO.ALLCWORDS,
				UserDAO.ISLEVELLAUNCHEDFIRSTTIME, UserDAO.ISCURRENTLEVEL };

		Cursor c = cr.query(UserDAO.CONTENT_URI, selectionArgs, UserDAO.LEVEL
				+ "=" + level, null, null, null);
		int id = 0;
		int learnedVocabulary = 0;
		int numberOfVocabularyInLevel = 0;
		int learnedGrammar = 0;
		int numberOfGrammarInLevel = 0;
		int learnedGiongo = 0;
		int numberOfGiongoInLevel = 0;
		int learnedCounterWords = 0;
		int numberOfCounterWordsInLevel = 0;
		int isLevelLaunchedFirstTime = 1;
		int isCurrentLevel = 1;
		c.moveToFirst();
		boolean isNotNull = false;
		while (!c.isAfterLast()) {
			isNotNull = true;
			id = c.getInt(0);
			learnedVocabulary = c.getInt(2);
			numberOfVocabularyInLevel = c.getInt(3);
			learnedGrammar = c.getInt(4);
			numberOfGrammarInLevel = c.getInt(5);
			learnedGiongo = c.getInt(6);
			numberOfGiongoInLevel = c.getInt(7);
			learnedCounterWords = c.getInt(8);
			numberOfCounterWordsInLevel = c.getInt(9);
			isLevelLaunchedFirstTime = c.getInt(10);
			isCurrentLevel = c.getInt(11);
			c.moveToNext();
		}
		c.close();
		if (isNotNull) {
			User u = new User(id, level, learnedVocabulary,
					numberOfVocabularyInLevel, learnedGrammar,
					numberOfGrammarInLevel, learnedGiongo,
					numberOfGiongoInLevel, learnedCounterWords,
					numberOfCounterWordsInLevel, isLevelLaunchedFirstTime,
					isCurrentLevel);
			return u;
		} else
			return null;
	}

	public int getNumberOfUsers(ContentResolver cr) {
		Cursor countCursor = cr.query(UserDAO.CONTENT_URI,
				new String[] { "count(*) AS count" }, null, null, null);

		countCursor.moveToFirst();
		int count = countCursor.getInt(0);
		countCursor.close();
		return count;
	}

	@SuppressLint("NewApi")
	public User getUserWithCurrentLevel(ContentResolver cr) {
		String[] selectionArgs = { UserDAO.ID, UserDAO.LEVEL,
				UserDAO.LEARNEDVOC, UserDAO.ALLVOC, UserDAO.LEARNEDGRAMMAR,
				UserDAO.ALLGRAMMAR, UserDAO.LEARNEDGIONGO, UserDAO.ALLGIONGO,
				UserDAO.LEARNEDCWORDS, UserDAO.ALLCWORDS,
				UserDAO.ISLEVELLAUNCHEDFIRSTTIME, UserDAO.ISCURRENTLEVEL };

		Cursor c = cr.query(UserDAO.CONTENT_URI, selectionArgs,
				UserDAO.ISCURRENTLEVEL + "=" + 1, null, null, null);
		int id = 0;
		int level = 0;
		int learnedVocabulary = 0;
		int numberOfVocabularyInLevel = 0;
		int learnedGrammar = 0;
		int numberOfGrammarInLevel = 0;
		int learnedAudio = 0;
		int numberOfGiongoInLevel = 0;
		int learnedCounterWords = 0;
		int numberOfCounterWordsInLevel = 0;
		int isLevelLaunchedFirstTime = 1;
		int isCurrentLevel = 1;
		c.moveToFirst();
		boolean isUserExists = false;
		while (!c.isAfterLast()) {
			isUserExists = true;
			id = c.getInt(0);
			level = c.getInt(1);
			learnedVocabulary = c.getInt(2);
			numberOfVocabularyInLevel = c.getInt(3);
			learnedGrammar = c.getInt(4);
			numberOfGrammarInLevel = c.getInt(5);
			learnedAudio = c.getInt(6);
			numberOfGiongoInLevel = c.getInt(7);
			learnedCounterWords = c.getInt(8);
			numberOfCounterWordsInLevel = c.getInt(9);
			isLevelLaunchedFirstTime = c.getInt(10);
			isCurrentLevel = c.getInt(11);
			c.moveToNext();
		}
		c.close();
		if (isUserExists) {
			User u = new User(id, level, learnedVocabulary,
					numberOfVocabularyInLevel, learnedGrammar,
					numberOfGrammarInLevel, learnedAudio,
					numberOfGiongoInLevel, learnedCounterWords,
					numberOfCounterWordsInLevel, isLevelLaunchedFirstTime,
					isCurrentLevel);
			return u;
		} else
			return null;
	}

	public void setAsInactiveOtherLevels(int level, ContentResolver cr) {
		for (int i = 1; i < 6; i++) {
			User u = selectUser(i, cr);
			if (u != null && i != level) {
				u.setIsCurrentLevel(0);
				update(u, cr);
			}
		}
		User u = selectUser(level, cr);
		u.setIsCurrentLevel(1);
		update(u, cr);
	}
}