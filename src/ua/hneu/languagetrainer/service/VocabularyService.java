package ua.hneu.languagetrainer.service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import ua.hneu.languagetrainer.App;
import ua.hneu.languagetrainer.db.dao.VocabularyDAO;
import ua.hneu.languagetrainer.model.vocabulary.DictionaryEntry;
import ua.hneu.languagetrainer.model.vocabulary.WordDictionary;
import ua.hneu.languagetrainer.model.vocabulary.WordMeaning;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class VocabularyService {

	public static DictionaryEntry getEntryById(int id, ContentResolver cr) {
		String[] col = { "KANJI", "LEVEL", "TRANSCRIPTION", "ROMAJI",
				"TRANSLATIONS", "EXAMPLES", "PERCENTAGE", "LASTVIEW",
				"SHOWNTIMES" };
		Cursor c = cr.query(VocabularyDAO.CONTENT_URI, col, "_ID=" + id, null,
				null, null);
		c.moveToFirst();

		DictionaryEntry de;

		String kanji = "";
		int level = 0;
		String transcription = "";
		String romaji = "";
		String translations = "";
		String examples = "";
		int percentage = 0;
		String lastview = "";
		int showntimes = 0;

		while (!c.isAfterLast()) {
			kanji = c.getString(0);
			id = c.getInt(1);
			transcription = c.getString(2);
			romaji = c.getString(3);
			translations = c.getString(4);
			examples = c.getString(5);
			percentage = c.getInt(6);
			lastview = c.getString(7);
			showntimes = c.getInt(8);
			c.moveToNext();
		}

		List<String> translations1 = new ArrayList<String>(
				Arrays.asList(translations.split(";")));

		de = new DictionaryEntry(id, kanji, level, transcription, romaji,
				translations1, examples, percentage, lastview, showntimes);
		return de;
	}

	public void insert(DictionaryEntry de, ContentResolver cr) {
		ContentValues values = new ContentValues();
		values.put(VocabularyDAO.KANJI, de.getKanji());
		values.put(VocabularyDAO.LEVEL, de.getLevel());
		values.put(VocabularyDAO.TRANSCRIPTION, de.getTranscription());
		values.put(VocabularyDAO.ROMAJI, de.getRomaji());
		values.put(VocabularyDAO.TRANSLATIONS, de.getTranslationsToString());
		values.put(VocabularyDAO.EXAMPLES, de.getExamples());
		values.put(VocabularyDAO.PERCENTAGE, de.getLearnedPercentage());
		values.put(VocabularyDAO.LASTVIEW, de.getLastview());
		values.put(VocabularyDAO.SHOWNTIMES, de.getShowntimes());
		cr.insert(VocabularyDAO.CONTENT_URI, values);
	}

	public void edit(int id, String kanji, int level, String transcription,
			String romaji, String translations, String examples,
			double percentage, String lastview, int showntimes,
			ContentResolver cr) {
		ContentValues values = new ContentValues();
		values.put(VocabularyDAO.KANJI, kanji);
		values.put(VocabularyDAO.LEVEL, level);
		values.put(VocabularyDAO.TRANSCRIPTION, transcription);
		values.put(VocabularyDAO.ROMAJI, romaji);
		values.put(VocabularyDAO.TRANSLATIONS, translations);
		values.put(VocabularyDAO.EXAMPLES, examples);
		values.put(VocabularyDAO.PERCENTAGE, percentage);
		values.put(VocabularyDAO.LASTVIEW, lastview);
		values.put(VocabularyDAO.SHOWNTIMES, showntimes);
		cr.update(VocabularyDAO.CONTENT_URI, values, "_ID=" + id, null);
	}

	public void setPercentage(int id, double percentage, ContentResolver cr) {

		String[] col = { "KANJI", "LEVEL", "TRANSCRIPTION", "ROMAJI",
				"TRANSLATIONS", "EXAMPLES", "PERCENTAGE", "LASTVIEW",
				"SHOWNTIMES" };
		Cursor c = cr.query(VocabularyDAO.CONTENT_URI, col, "_ID=" + id, null,
				null, null);
		c.moveToFirst();

		String kanji = "";
		int level = 0;
		String transcription = "";
		String romaji = "";
		String translations = "";
		String examples = "";
		String lastview = "";
		String showntimes = "";

		while (!c.isAfterLast()) {
			kanji = c.getString(0);
			level = c.getInt(1);
			transcription = c.getString(2);
			romaji = c.getString(3);
			translations = c.getString(4);
			examples = c.getString(5);
			lastview = c.getString(7);
			showntimes = c.getString(8);
			c.moveToNext();
		}

		ContentValues values = new ContentValues();
		values.put(VocabularyDAO.KANJI, kanji);
		values.put(VocabularyDAO.LEVEL, level);
		values.put(VocabularyDAO.TRANSCRIPTION, transcription);
		values.put(VocabularyDAO.ROMAJI, romaji);
		values.put(VocabularyDAO.TRANSLATIONS, translations);
		values.put(VocabularyDAO.EXAMPLES, examples);
		values.put(VocabularyDAO.PERCENTAGE, percentage);
		values.put(VocabularyDAO.LASTVIEW, lastview);
		values.put(VocabularyDAO.SHOWNTIMES, showntimes);
		cr.update(VocabularyDAO.CONTENT_URI, values, "_ID=" + id, null);
	}

	public void emptyTable() {
		VocabularyDAO.getDb()
				.execSQL("delete from " + VocabularyDAO.TABLE_NAME);
	}

	public void createTable() {
		SQLiteDatabase db = VocabularyDAO.getDb();
		db.execSQL("CREATE TABLE " + VocabularyDAO.TABLE_NAME
				+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ VocabularyDAO.KANJI + " TEXT, " + VocabularyDAO.LEVEL
				+ " TEXT, " + VocabularyDAO.TRANSCRIPTION + " TEXT, "
				+ VocabularyDAO.ROMAJI + " TEXT, " + VocabularyDAO.TRANSLATIONS
				+ " TEXT, " + VocabularyDAO.EXAMPLES + " TEXT, "
				+ VocabularyDAO.PERCENTAGE + " REAL, " + VocabularyDAO.LASTVIEW
				+ " DATETIME," + VocabularyDAO.SHOWNTIMES + " INTEGER);");
	}

	public void dropTable() {
		VocabularyDAO.getDb().execSQL(
				"DROP TABLE " + VocabularyDAO.TABLE_NAME + ";");
	}

	public void bulkInsertFromCSV(String filepath, AssetManager assetManager,
			int level, ContentResolver cr) {

		ArrayList<String[]> entries1 = new ArrayList<String[]>();

		BufferedReader reader = null;
		int i = 0;
		int q = 0;
		try {
			reader = new BufferedReader(new InputStreamReader(
					assetManager.open(filepath)));
			// do reading, usually loop until end of file reading
			String mLine;
			while ((mLine = reader.readLine()) != null) {
				// 3 columns separated by tabulation characters
				if (mLine != null) {
					String[] parts = mLine.split("\t");
					entries1.add(parts);
				}
			}

			System.out.println(i + "   " + q);
		} catch (IOException e) {
			Log.i("VocabularyService", e.getMessage() + " " + e.getCause());
		}

		for (final String[] entry : entries1) {
			String kanji = entry[0];
			String romaji = entry[2];
			String transcription = entry[1];
			String[] meanings = entry[3].split(";");
			List<String> translations = new ArrayList<String>(
					Arrays.asList(meanings));
			DictionaryEntry de = new DictionaryEntry(0, kanji, level,
					transcription, romaji, translations, "", 0, "", 0);
			this.insert(de, cr);
		}
	}

	public static WordDictionary createCurrentDictionary(int level,
			int countWordsInCurrentDict, ContentResolver contentResolver) {
		WordDictionary all = new WordDictionary();
		//TODO: include entries of levels below
		
		all = selectAllEntriesOflevel(level, contentResolver);

		WordDictionary current = new WordDictionary();
		// TODO: replace random with SRS methodology
		Random rn = new Random();
		while (current.size() < App.getUserInfo()
				.getNumberOfEntriesInCurrentDict()) {
			int i = rn.nextInt(all.size());
			DictionaryEntry entry = all.get(i);
			// if (entry.getLevel() == level)
			current.add(entry);
		}
		return current;
	}

	public static WordDictionary selectAllEntriesOflevel(int level,
			ContentResolver contentResolver) {
		WordDictionary wd = new WordDictionary();

		String[] selectionArgs = { VocabularyDAO.ID, VocabularyDAO.KANJI,
				VocabularyDAO.LEVEL, VocabularyDAO.TRANSCRIPTION,
				VocabularyDAO.ROMAJI, VocabularyDAO.TRANSLATIONS,
				VocabularyDAO.EXAMPLES, VocabularyDAO.PERCENTAGE,
				VocabularyDAO.SHOWNTIMES, VocabularyDAO.LASTVIEW };
		Cursor c = contentResolver.query(VocabularyDAO.CONTENT_URI,
				selectionArgs, "level=" + level, null, null);
		c.moveToFirst();
		int id = 0;
		String kanji = "";
		String transcription = "";
		String romaji = "";
		String translations = "";
		String examples = "";
		int percentage = 0;
		String lastview = "";
		int showntimes = 0;

		while (!c.isAfterLast()) {
			id = c.getInt(0);
			kanji = c.getString(1);
			transcription = c.getString(3);
			romaji = c.getString(4);
			translations = c.getString(5);
			examples = c.getString(6);
			percentage = c.getInt(7);
			lastview = c.getString(8);
			showntimes = c.getInt(9);
			c.moveToNext();

			List<String> translations1 = new ArrayList<String>(
					Arrays.asList(translations.split(";")));

			DictionaryEntry de = new DictionaryEntry(id, kanji, level,
					transcription, romaji, translations1, examples, percentage,
					lastview, showntimes);
			wd.add(de);
		}
		return wd;
	}

	public static int getNumberOfWordsInLevel(int level,
			ContentResolver contentResolver) {
		Cursor countCursor = contentResolver.query(VocabularyDAO.CONTENT_URI,
				new String[] { "count(*) AS count" }, null, null, null);

		countCursor.moveToFirst();
		int count = countCursor.getInt(0);
		return count;
	}
}
