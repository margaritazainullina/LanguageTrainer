package ua.hneu.languagetrainer.service;

import ua.hneu.languagetrainer.db.dao.UserDAO;
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
		values.put(UserDAO.LEARNEDAUDIO, u.getLearnedAudio());
		values.put(UserDAO.ALLAUDIO, u.getNumberOfAudioInLevel());		
		values.put(UserDAO.LEARNEDGIONGO, u.getLearnedGiongo());
		values.put(UserDAO.ALLGIONGO, u.getNumberOfGiongoInLevel());
		values.put(UserDAO.LEARNEDCWORDS, u.getLearnedCounterWords());
		values.put(UserDAO.ALLCWORDS, u.getNumberOfCounterWordsInLevel());
		values.put(UserDAO.CURDICTSIZE, u.getNumberOfEntriesInCurrentDict());
		values.put(UserDAO.REPETATIONNUM,
				u.getNumberOfRepeatationsForLearning());
		values.put(UserDAO.TESTAVG, u.getTestAveragePercentage());
		values.put(UserDAO.ISLEVELLAUNCHEDFIRSTTIME, u.getIsLevelLaunchedFirstTime());
		cr.insert(UserDAO.CONTENT_URI, values);
	}

	public void update(User u, ContentResolver cr) {
		ContentValues values = new ContentValues();
		values.put(UserDAO.LEVEL, u.getLevel());
		values.put(UserDAO.LEARNEDVOC, u.getLearnedVocabulary());
		values.put(UserDAO.ALLVOC, u.getNumberOfVocabularyInLevel());
		values.put(UserDAO.LEARNEDGRAMMAR, u.getLearnedGrammar());
		values.put(UserDAO.ALLGRAMMAR, u.getNumberOfGrammarInLevel());
		values.put(UserDAO.LEARNEDAUDIO, u.getLearnedAudio());
		values.put(UserDAO.ALLAUDIO, u.getNumberOfAudioInLevel());		
		values.put(UserDAO.LEARNEDGIONGO, u.getLearnedGiongo());
		values.put(UserDAO.ALLGIONGO, u.getNumberOfGiongoInLevel());
		values.put(UserDAO.LEARNEDCWORDS, u.getLearnedCounterWords());
		values.put(UserDAO.ALLCWORDS, u.getNumberOfCounterWordsInLevel());
		values.put(UserDAO.CURDICTSIZE, u.getNumberOfEntriesInCurrentDict());
		values.put(UserDAO.REPETATIONNUM,
				u.getNumberOfRepeatationsForLearning());
		values.put(UserDAO.TESTAVG, u.getTestAveragePercentage());
		values.put(UserDAO.ISLEVELLAUNCHEDFIRSTTIME, u.getIsLevelLaunchedFirstTime());
		cr.update(UserDAO.CONTENT_URI, values, "_ID=" + u.getId(), null);
	}

	public void emptyTable() {
		UserDAO.getDb().execSQL("delete from " + UserDAO.TABLE_NAME);
	}

	public void createTable() {
		SQLiteDatabase db= UserDAO.getDb();
		db.execSQL(
				"CREATE TABLE " + UserDAO.TABLE_NAME
						+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
						+ UserDAO.LEVEL+ " INTEGER, " 
						+ UserDAO.LEARNEDVOC + " INTEGER, "
						+ UserDAO.ALLVOC + " INTEGER, "
						+ UserDAO.LEARNEDGRAMMAR + " INTEGER, "
						+ UserDAO.ALLGRAMMAR + " INTEGER, "
						+ UserDAO.LEARNEDAUDIO + " INTEGER, "
						+ UserDAO.ALLAUDIO + " INTEGER, "
						+ UserDAO.LEARNEDGIONGO + " INTEGER,"
						+ UserDAO.ALLGIONGO + " INTEGER, "
						+ UserDAO.LEARNEDCWORDS + " INTEGER,"
						+ UserDAO.ALLCWORDS + " INTEGER," 
						+ UserDAO.CURDICTSIZE + " INTEGER," 
						+ UserDAO.REPETATIONNUM + " INTEGER,"
						+ UserDAO.TESTAVG + " REAL,"
						+ UserDAO.ISLEVELLAUNCHEDFIRSTTIME+" INTEGER);");
	}

	public void dropTable() {
		UserDAO.getDb().execSQL("DROP TABLE " + UserDAO.TABLE_NAME + ";");
	}

	public User selectUser(ContentResolver cr,int id) {
		String[] selectionArgs = { UserDAO.ID, UserDAO.LEVEL,
				UserDAO.LEARNEDVOC, UserDAO.ALLVOC, UserDAO.LEARNEDGRAMMAR,
				UserDAO.ALLGRAMMAR, UserDAO.LEARNEDAUDIO,UserDAO.ALLAUDIO,
				UserDAO.LEARNEDGIONGO,UserDAO.ALLGIONGO, UserDAO.LEARNEDCWORDS,
				UserDAO.ALLCWORDS, UserDAO.CURDICTSIZE, UserDAO.REPETATIONNUM,
				UserDAO.TESTAVG, UserDAO.ISLEVELLAUNCHEDFIRSTTIME};
		
		Cursor c = cr.query(UserDAO.CONTENT_URI, selectionArgs, "_ID=" + id, null,
				null, null);

		int userLevel = -1;
		int learnedVocabulary = 0;
		int numberOfVocabularyInLevel = 0;
		int learnedGrammar = 0;
		int numberOfGrammarInLevel = 0;
		int learnedAudio = 0;
		int numberOfAudioInLevel = 0;
		int learnedGiongo = 0;
		int numberOfGiongoInLevel = 0;
		int learnedCounterWords = 0;
		int numberOfCounterWordsInLevel = 0;
		int numberOfEntriesInCurrentDict = 0;
		int numberOfRepeatationsForLearning = 0;
		double testAveragePercentage = 0;
		int isLevelLaunchedFirstTime = 1;
		
		c.moveToFirst();
		
		while (!c.isAfterLast()) {
			id = c.getInt(0);
			userLevel = c.getInt(1);
			learnedVocabulary = c.getInt(2);
			numberOfVocabularyInLevel = c.getInt(3);
			learnedGrammar = c.getInt(4);
			numberOfGrammarInLevel = c.getInt(5);
			learnedAudio = c.getInt(6);
			numberOfAudioInLevel = c.getInt(7);
			learnedGiongo = c.getInt(8);
			numberOfGiongoInLevel = c.getInt(9 );
			learnedCounterWords = c.getInt(10);
			numberOfCounterWordsInLevel = c.getInt(11);
			numberOfEntriesInCurrentDict = c.getInt(12);
			numberOfRepeatationsForLearning = c.getInt(13);
			testAveragePercentage = c.getDouble(14);
			isLevelLaunchedFirstTime = c.getInt(15);
			c.moveToNext();
		}
		c.close();
		User u = new User(id, userLevel, learnedVocabulary,
				numberOfVocabularyInLevel, learnedGrammar,
				numberOfGrammarInLevel, learnedAudio, numberOfAudioInLevel,
				learnedGiongo, numberOfGiongoInLevel, learnedCounterWords,
				numberOfCounterWordsInLevel, numberOfEntriesInCurrentDict,
				numberOfRepeatationsForLearning, testAveragePercentage, isLevelLaunchedFirstTime);
		return u;
	}
}