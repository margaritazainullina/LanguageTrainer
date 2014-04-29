package ua.hneu.languagetrainer.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

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
		values.put(UserDAO.LANGUAGE, u.getLanguage());
		values.put(UserDAO.LEVEL, u.getUserLevel());
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
		values.put(UserDAO.LASTPASSING, u.getLastPassing());
		cr.insert(UserDAO.CONTENT_URI, values);
	}

	public void edit(User u, ContentResolver cr) {
		ContentValues values = new ContentValues();
		values.put(UserDAO.LANGUAGE, u.getLanguage());
		values.put(UserDAO.LEVEL, u.getUserLevel());
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

		values.put(UserDAO.LASTPASSING, u.getLastPassing());
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
						+ UserDAO.LANGUAGE + " TEXT, " 
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
						+ UserDAO.LASTPASSING+" DATETIME);");
	}

	public void dropTable() {
		UserDAO.getDb().execSQL("DROP TABLE " + UserDAO.TABLE_NAME + ";");
	}

	public User selectUser(ContentResolver cr,int id) {
		String[] selectionArgs = { UserDAO.ID, UserDAO.LANGUAGE, UserDAO.LEVEL,
				UserDAO.LEARNEDVOC, UserDAO.ALLVOC, UserDAO.LEARNEDGRAMMAR,
				UserDAO.ALLGRAMMAR, UserDAO.LEARNEDAUDIO,UserDAO.ALLAUDIO,
				UserDAO.LEARNEDGIONGO,UserDAO.ALLGIONGO, UserDAO.LEARNEDCWORDS,
				UserDAO.ALLCWORDS, UserDAO.CURDICTSIZE, UserDAO.REPETATIONNUM,
				UserDAO.TESTAVG, UserDAO.LASTPASSING};
		
		Cursor c = cr.query(UserDAO.CONTENT_URI, selectionArgs, "_ID=" + id, null,
				null, null);

		String language = "";
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
		String lastPassing = null;
		
		c.moveToFirst();
		
		while (!c.isAfterLast()) {
			id = c.getInt(0);
			language = c.getString(1);
			userLevel = c.getInt(2);
			learnedVocabulary = c.getInt(3);
			numberOfVocabularyInLevel = c.getInt(4);
			learnedGrammar = c.getInt(5);
			numberOfGrammarInLevel = c.getInt(6);
			learnedAudio = c.getInt(7);
			numberOfAudioInLevel = c.getInt(8);
			learnedGiongo = c.getInt(9);
			numberOfGiongoInLevel = c.getInt(10);
			learnedCounterWords = c.getInt(11);
			numberOfCounterWordsInLevel = c.getInt(12);
			numberOfEntriesInCurrentDict = c.getInt(13);
			numberOfRepeatationsForLearning = c.getInt(14);
			testAveragePercentage = c.getDouble(15);
			lastPassing = c.getString(16);
			c.moveToNext();
		}
		User u = new User(id, language, userLevel, learnedVocabulary,
				numberOfVocabularyInLevel, learnedGrammar,
				numberOfGrammarInLevel, learnedAudio, numberOfAudioInLevel,
				learnedGiongo, numberOfGiongoInLevel, learnedCounterWords,
				numberOfCounterWordsInLevel, numberOfEntriesInCurrentDict,
				numberOfRepeatationsForLearning, testAveragePercentage, lastPassing);
		return u;
	}
}