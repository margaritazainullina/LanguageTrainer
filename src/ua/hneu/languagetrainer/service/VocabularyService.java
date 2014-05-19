package ua.hneu.languagetrainer.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import ua.hneu.languagetrainer.App;
import ua.hneu.languagetrainer.db.dao.VocabularyDAO;
import ua.hneu.languagetrainer.model.vocabulary.VocabularyDictionary;
import ua.hneu.languagetrainer.model.vocabulary.VocabularyEntry;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class VocabularyService {
	public static VocabularyDictionary all;
	boolean isFirstTimeCreated;

	@SuppressLint("NewApi")
	public static VocabularyEntry getEntryById(int id, ContentResolver cr) {
		String[] col = { "KANJI", "LEVEL", "TRANSCRIPTION", "ROMAJI",
				"TRANSLATIONS", "TRANSLATIONS_RUS", "PERCENTAGE",
				"LASTVIEW", "SHOWNTIMES", "COLOR" };
		Cursor c = cr.query(VocabularyDAO.CONTENT_URI, col, "_ID=" + id, null,
				null, null);
		c.moveToFirst();

		VocabularyEntry de;

		String kanji = "";
		int level = 0;
		String transcription = "";
		String romaji = "";
		String translations = "";
		String translationsRus = "";
		int percentage = 0;
		String lastview = "";
		int showntimes = 0;
		String color = "";

		while (!c.isAfterLast()) {
			id = c.getInt(0);
			kanji = c.getString(1).trim();
			level = c.getInt(2);
			transcription = c.getString(3).trim();
			romaji = c.getString(4).trim();
			translations = c.getString(5).trim();
			translationsRus = c.getString(6).trim();
			percentage = c.getInt(7);
			lastview = c.getString(8).trim();
			showntimes = c.getInt(9);
			color = c.getString(10).trim();
			c.moveToNext();
		}

		List<String> translations1 = new ArrayList<String>(
				Arrays.asList(translations.split(";")));
		List<String> translations2 = new ArrayList<String>(
				Arrays.asList(translationsRus.split(";")));

		de = new VocabularyEntry(id, kanji, level, transcription, romaji,
				translations1, translations2, percentage, lastview,
				showntimes, color);
		c.close();
		return de;
	}

	public void insert(VocabularyEntry de, ContentResolver cr) {
		ContentValues values = new ContentValues();
		values.put(VocabularyDAO.KANJI, de.getKanji());
		values.put(VocabularyDAO.LEVEL, de.getLevel());
		values.put(VocabularyDAO.TRANSCRIPTION, de.getTranscription());
		values.put(VocabularyDAO.ROMAJI, de.getRomaji());
		values.put(VocabularyDAO.TRANSLATIONS, de.translationsToString());
		values.put(VocabularyDAO.TRANSLATIONS_RUS, de.translationsRusToString());
		values.put(VocabularyDAO.PERCENTAGE, de.getLearnedPercentage());
		values.put(VocabularyDAO.LASTVIEW, de.getLastview());
		values.put(VocabularyDAO.SHOWNTIMES, de.getShownTimes());
		values.put(VocabularyDAO.COLOR, de.getColor());
		cr.insert(VocabularyDAO.CONTENT_URI, values);
	}

	public void update(VocabularyEntry de, ContentResolver cr) {
		ContentValues values = new ContentValues();
		values.put(VocabularyDAO.ID, de.getId());
		values.put(VocabularyDAO.KANJI, de.getKanji());
		values.put(VocabularyDAO.LEVEL, de.getLevel());
		values.put(VocabularyDAO.TRANSCRIPTION, de.getTranscription());
		values.put(VocabularyDAO.ROMAJI, de.getRomaji());
		values.put(VocabularyDAO.TRANSLATIONS, de.translationsToString());
		values.put(VocabularyDAO.TRANSLATIONS_RUS, de.translationsRusToString());
		values.put(VocabularyDAO.PERCENTAGE, de.getLearnedPercentage());
		values.put(VocabularyDAO.LASTVIEW, de.getLastview());
		values.put(VocabularyDAO.SHOWNTIMES, de.getShownTimes());
		values.put(VocabularyDAO.COLOR, de.getColor());
		cr.update(VocabularyDAO.CONTENT_URI, values, "_ID=" + de.getId(), null);
	}

	public void emptyTable() {
		VocabularyDAO.getDb()
				.execSQL("delete from " + VocabularyDAO.TABLE_NAME);
	}

	public void createTable() {
		SQLiteDatabase db = VocabularyDAO.getDb();
		db.execSQL("CREATE TABLE  if not exists " + VocabularyDAO.TABLE_NAME
				+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ VocabularyDAO.KANJI + " TEXT, " + VocabularyDAO.LEVEL
				+ " TEXT, " + VocabularyDAO.TRANSCRIPTION + " TEXT, "
				+ VocabularyDAO.ROMAJI + " TEXT, " + VocabularyDAO.TRANSLATIONS
				+ " TEXT, " + VocabularyDAO.TRANSLATIONS_RUS + " TEXT, "
				+ VocabularyDAO.PERCENTAGE
				+ " REAL, " + VocabularyDAO.LASTVIEW + " DATETIME,"
				+ VocabularyDAO.SHOWNTIMES + " INTEGER, " + VocabularyDAO.COLOR
				+ " TEXT);");
	}

	public void dropTable() {
		VocabularyDAO.getDb().execSQL(
				"DROP TABLE if exists " + VocabularyDAO.TABLE_NAME + ";");
	}

	public void bulkInsertFromCSV(String filepath, AssetManager assetManager,
			int level, ContentResolver cr) {

		ArrayList<String[]> entries1 = new ArrayList<String[]>();

		BufferedReader reader = null;
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
		} catch (IOException e) {
			Log.e("VocabularyService", e.getMessage() + " " + e.getCause());
		}

		for (final String[] entry : entries1) {
			String kanji = entry[0];
			String romaji = entry[2];
			String transcription = entry[1];
			String[] meanings = entry[3].split(";");
			String[] meaningsRus = entry[4].split(";");
			List<String> translations = new ArrayList<String>(
					Arrays.asList(meanings));

			List<String> translationsRus = new ArrayList<String>(
					Arrays.asList(meaningsRus));

			// setting random colors
			Random r = new Random();
			int r1 = r.nextInt(5);
			String color = "";
			switch (r1) {
			case 0: {
				color = "138,213,240";
				break;
			}
			case 1: {
				color = "214,173,235";
				break;
			}
			case 2: {
				color = "197,226,109";
				break;
			}

			case 3: {
				color = "255,217,128";
				break;
			}
			case 4: {
				color = "255,148,148";
				break;
			}
			}

			VocabularyEntry de = new VocabularyEntry(0, kanji, level,
					transcription, romaji, translations, translationsRus,
					0, "", 0, color);
			this.insert(de, cr);
		}
	}

	public static VocabularyDictionary createCurrentDictionary(int level,
			int countWordsInCurrentDict, ContentResolver contentResolver) {
		all = new VocabularyDictionary();
		all = selectAllEntriesOflevel(level, contentResolver);
		VocabularyDictionary current = new VocabularyDictionary();
		// if words have never been showed - set entries randomly
		if (App.userInfo.isLevelLaunchedFirstTime == 1) {
			all.sortRandomly();
			for (int i = 0; i < App.numberOfEntriesInCurrentDict; i++) {
				VocabularyEntry e = all.get(i);
				if (e.getLearnedPercentage() != 1)
					current.add(e);
			}
		} else {
			// sorting descending
			// get last elements
			all.sortByLastViewedTime();
			int i = all.size() - 1;
			while (current.size() < App.numberOfEntriesInCurrentDict) {
				VocabularyEntry e = all.get(i);
				if (e.getLearnedPercentage() != 1)
					current.add(e);
				i--;
				Log.i("createCurrentDictionary", all.get(i).toString());
			}
		}
		return current;
	}

	public static VocabularyDictionary selectAllEntriesOflevel(int level,
			ContentResolver contentResolver) {
		VocabularyDictionary wd = new VocabularyDictionary();

		String[] selectionArgs = { VocabularyDAO.ID, VocabularyDAO.KANJI,
				VocabularyDAO.LEVEL, VocabularyDAO.TRANSCRIPTION,
				VocabularyDAO.ROMAJI, VocabularyDAO.TRANSLATIONS,
				VocabularyDAO.TRANSLATIONS_RUS,
				VocabularyDAO.PERCENTAGE, VocabularyDAO.LASTVIEW,
				VocabularyDAO.SHOWNTIMES, VocabularyDAO.COLOR };
		Cursor c = contentResolver.query(VocabularyDAO.CONTENT_URI,
				selectionArgs, "level=" + level, null, null);
		c.moveToFirst();
		int id = 0;
		String kanji = "";
		String transcription = "";
		String romaji = "";
		String translations = "";
		String translationsRus = "";
		double percentage = 0;
		String lastview = "";
		int showntimes = 0;
		String color = "";

		while (!c.isAfterLast()) {
			id = c.getInt(0);
			kanji = c.getString(1);
			transcription = c.getString(3);
			romaji = c.getString(4);
			translations = c.getString(5);
			translationsRus = c.getString(6);
			percentage = c.getDouble(7);
			lastview = c.getString(8);
			showntimes = c.getInt(9);
			color = c.getString(10);
			c.moveToNext();

			List<String> translations1 = new ArrayList<String>(
					Arrays.asList(translations.split(";")));

			List<String> translations2 = new ArrayList<String>(
					Arrays.asList(translationsRus.split(";")));

			VocabularyEntry de = new VocabularyEntry(id, kanji, level,
					transcription, romaji, translations1, translations2,
					percentage, lastview, showntimes, color);
			wd.add(de);
		}
		c.close();
		return wd;
	}

	public int getNumberOfWordsInLevel(int level,
			ContentResolver contentResolver) {
		Cursor countCursor = contentResolver.query(VocabularyDAO.CONTENT_URI,
				new String[] { "count(*) AS count" }, VocabularyDAO.LEVEL + "="
						+ level + "", null, null);

		countCursor.moveToFirst();
		int count = countCursor.getInt(0);
		countCursor.close();
		return count;
	}
}
