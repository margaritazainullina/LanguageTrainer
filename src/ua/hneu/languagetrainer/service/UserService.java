package ua.hneu.languagetrainer.service;

import ua.hneu.languagetrainer.db.dao.UserDAO;
import ua.hneu.languagetrainer.db.dao.VocabularyDAO;
import ua.hneu.languagetrainer.model.User;
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
		values.put(UserDAO.CURDICTSIZE, u.getNumberOfEntriesInCurrentDict());
		values.put(UserDAO.REPETATIONNUM,
				u.getNumberOfRepeatationsForLearning());
		values.put(UserDAO.TESTAVG, u.getTestAveragePercentage());
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
		values.put(UserDAO.CURDICTSIZE, u.getNumberOfEntriesInCurrentDict());
		values.put(UserDAO.REPETATIONNUM,
				u.getNumberOfRepeatationsForLearning());
		values.put(UserDAO.TESTAVG, u.getTestAveragePercentage());
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
		db.execSQL("CREATE TABLE " + UserDAO.TABLE_NAME
				+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + UserDAO.LEVEL
				+ " INTEGER, " + UserDAO.LEARNEDVOC + " INTEGER, "
				+ UserDAO.ALLVOC + " INTEGER, " + UserDAO.LEARNEDGRAMMAR
				+ " INTEGER, " + UserDAO.ALLGRAMMAR + " INTEGER, "
				+ UserDAO.LEARNEDGIONGO + " INTEGER," + UserDAO.ALLGIONGO
				+ " INTEGER, " + UserDAO.LEARNEDCWORDS + " INTEGER,"
				+ UserDAO.ALLCWORDS + " INTEGER," + UserDAO.CURDICTSIZE
				+ " INTEGER," + UserDAO.REPETATIONNUM + " INTEGER,"
				+ UserDAO.TESTAVG + " REAL," + UserDAO.ISLEVELLAUNCHEDFIRSTTIME
				+ " INTEGER," + UserDAO.ISCURRENTLEVEL + " INTEGER);");
	}

	public void dropTable() {
		UserDAO.getDb().execSQL("DROP TABLE " + UserDAO.TABLE_NAME + ";");
	}

	public User selectUser(ContentResolver cr, int level) {
		String[] selectionArgs = { UserDAO.ID, UserDAO.LEVEL,
				UserDAO.LEARNEDVOC, UserDAO.ALLVOC, UserDAO.LEARNEDGRAMMAR,
				UserDAO.ALLGRAMMAR, UserDAO.LEARNEDGIONGO, UserDAO.ALLGIONGO,
				UserDAO.LEARNEDCWORDS, UserDAO.ALLCWORDS, UserDAO.CURDICTSIZE,
				UserDAO.REPETATIONNUM, UserDAO.TESTAVG,
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
		int numberOfEntriesInCurrentDict = 0;
		int numberOfRepeatationsForLearning = 0;
		double testAveragePercentage = 0;
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
			numberOfEntriesInCurrentDict = c.getInt(10);
			numberOfRepeatationsForLearning = c.getInt(11);
			testAveragePercentage = c.getDouble(12);
			isLevelLaunchedFirstTime = c.getInt(13);
			isCurrentLevel = c.getInt(14);
			c.moveToNext();
		}
		c.close();
		if (isNotNull) {
			User u = new User(id, level, learnedVocabulary,
					numberOfVocabularyInLevel, learnedGrammar,
					numberOfGrammarInLevel, learnedGiongo,
					numberOfGiongoInLevel, learnedCounterWords,
					numberOfCounterWordsInLevel, numberOfEntriesInCurrentDict,
					numberOfRepeatationsForLearning, testAveragePercentage,
					isLevelLaunchedFirstTime, isCurrentLevel);
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

	public User getUserWithCurrentLevel(ContentResolver cr) {
		String[] selectionArgs = { UserDAO.ID, UserDAO.LEVEL,
				UserDAO.LEARNEDVOC, UserDAO.ALLVOC, UserDAO.LEARNEDGRAMMAR,
				UserDAO.ALLGRAMMAR, UserDAO.LEARNEDGIONGO, UserDAO.ALLGIONGO,
				UserDAO.LEARNEDCWORDS, UserDAO.ALLCWORDS, UserDAO.CURDICTSIZE,
				UserDAO.REPETATIONNUM, UserDAO.TESTAVG,
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
		int numberOfEntriesInCurrentDict = 0;
		int numberOfRepeatationsForLearning = 0;
		double testAveragePercentage = 0;
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
			numberOfEntriesInCurrentDict = c.getInt(10);
			numberOfRepeatationsForLearning = c.getInt(11);
			testAveragePercentage = c.getDouble(12);
			isLevelLaunchedFirstTime = c.getInt(13);
			isCurrentLevel = c.getInt(14);
			c.moveToNext();
		}
		c.close();
		if (isUserExists) {
			User u = new User(id, level, learnedVocabulary,
					numberOfVocabularyInLevel, learnedGrammar,
					numberOfGrammarInLevel, learnedAudio,
					numberOfGiongoInLevel, learnedCounterWords,
					numberOfCounterWordsInLevel, numberOfEntriesInCurrentDict,
					numberOfRepeatationsForLearning, testAveragePercentage,
					isLevelLaunchedFirstTime, isCurrentLevel);
			return u;
		} else
			return null;
	}
}