package ua.hneu.languagetrainer.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Random;

import ua.hneu.languagetrainer.App;
import ua.hneu.languagetrainer.App.Languages;
import ua.hneu.languagetrainer.db.dao.CounterWordsDAO;
import ua.hneu.languagetrainer.model.other.CounterWord;
import ua.hneu.languagetrainer.model.other.CounterWordsDictionary;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

@SuppressLint("NewApi")
public class CounterWordsService {
	public static CounterWordsDictionary all;

	public void insert(CounterWord cw, ContentResolver cr) {
		ContentValues values = new ContentValues();
		values.put(CounterWordsDAO.SECTION_ENG, cw.getSectionEng());
		values.put(CounterWordsDAO.SECTION_RUS, cw.getSectionRus());
		values.put(CounterWordsDAO.WORD, cw.getWord());
		values.put(CounterWordsDAO.HIRAGANA, cw.getHiragana());
		values.put(CounterWordsDAO.ROMAJI, cw.getRomaji());
		values.put(CounterWordsDAO.TRANSLATION_ENG, cw.getTranslationEng());
		values.put(CounterWordsDAO.TRANSLATION_RUS, cw.getTranslationRus());
		values.put(CounterWordsDAO.PERCENTAGE, cw.getLearnedPercentage());
		values.put(CounterWordsDAO.LASTVIEW, cw.getLastview());
		values.put(CounterWordsDAO.SHOWNTIMES, cw.getShownTimes());
		values.put(CounterWordsDAO.COLOR, cw.getColor());
		cr.insert(CounterWordsDAO.CONTENT_URI, values);
	}

	public void update(CounterWord cw, ContentResolver contentResolver) {
		ContentValues values = new ContentValues();
		values.put(CounterWordsDAO.SECTION_ENG, cw.getSectionEng());
		values.put(CounterWordsDAO.SECTION_RUS, cw.getSectionRus());
		values.put(CounterWordsDAO.WORD, cw.getWord());
		values.put(CounterWordsDAO.HIRAGANA, cw.getHiragana());
		values.put(CounterWordsDAO.ROMAJI, cw.getRomaji());
		values.put(CounterWordsDAO.TRANSLATION_ENG, cw.getTranslationEng());
		values.put(CounterWordsDAO.TRANSLATION_RUS, cw.getTranslationRus());
		values.put(CounterWordsDAO.PERCENTAGE, cw.getLearnedPercentage());
		values.put(CounterWordsDAO.LASTVIEW, cw.getLastview());
		values.put(CounterWordsDAO.SHOWNTIMES, cw.getShownTimes());
		values.put(CounterWordsDAO.COLOR, cw.getColor());
		String s = CounterWordsDAO.WORD + " =\"" + cw.getWord() + "\" ";
		contentResolver.update(CounterWordsDAO.CONTENT_URI, values, s, null);		
	}
	public void emptyTable() {
		CounterWordsDAO.getDb().execSQL(
				"delete from " + CounterWordsDAO.TABLE_NAME);
	}

	public void createTable() {
		SQLiteDatabase db = CounterWordsDAO.getDb();
		db.execSQL("CREATE TABLE if not exists " + CounterWordsDAO.TABLE_NAME
				+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ CounterWordsDAO.SECTION_ENG + " TEXT, "
				+ CounterWordsDAO.SECTION_RUS + " TEXT, "
				+ CounterWordsDAO.WORD + " TEXT, " + CounterWordsDAO.HIRAGANA
				+ " TEXT, " + CounterWordsDAO.ROMAJI + " TEXT, "
				+ CounterWordsDAO.TRANSLATION_ENG + " TEXT, "
				+ CounterWordsDAO.TRANSLATION_RUS + " TEXT, "
				+ CounterWordsDAO.PERCENTAGE + " REAL, "
				+ CounterWordsDAO.LASTVIEW + " DATETIME,"
				+ CounterWordsDAO.SHOWNTIMES + " INTEGER, "
				+ CounterWordsDAO.COLOR + " TEXT); ");
	}

	public void dropTable() {
		CounterWordsDAO.getDb().execSQL(
				"DROP TABLE if exists " + CounterWordsDAO.TABLE_NAME + ";");
	}

	public CounterWordsDictionary getCounterwordsBySection(String section,
			ContentResolver cr) {
		String[] col = { CounterWordsDAO.SECTION_ENG,
				CounterWordsDAO.SECTION_RUS, CounterWordsDAO.WORD,
				CounterWordsDAO.HIRAGANA, CounterWordsDAO.ROMAJI,
				CounterWordsDAO.TRANSLATION_ENG,
				CounterWordsDAO.TRANSLATION_RUS, CounterWordsDAO.PERCENTAGE,
				CounterWordsDAO.LASTVIEW, CounterWordsDAO.SHOWNTIMES,
				CounterWordsDAO.COLOR };
		Cursor c;
		if (App.lang == Languages.RUS)
			c = cr.query(CounterWordsDAO.CONTENT_URI, col,
					CounterWordsDAO.SECTION_RUS + "=\"" + section + "\"", null,
					null, null);
		else
			c = cr.query(CounterWordsDAO.CONTENT_URI, col,
					CounterWordsDAO.SECTION_ENG + "=\"" + section + "\"", null,
					null, null);

		c.moveToFirst();
		String sectionEng = "";
		String sectionRus = "";
		String word = "";
		String hiragana = "";
		String romaji = "";
		String translationEng = "";
		String translationRus = "";
		double percentage = 0;
		String lastview = "";
		int showntimes = 0;
		String color = "";
		CounterWordsDictionary cw = new CounterWordsDictionary();
		while (!c.isAfterLast()) {
			sectionEng = c.getString(0);
			sectionRus = c.getString(1);
			word = c.getString(2);
			hiragana = c.getString(3);
			romaji = c.getString(4);
			translationEng = c.getString(5);
			translationRus = c.getString(6);
			percentage = c.getDouble(7);
			lastview = c.getString(8);
			showntimes = c.getInt(9);
			color = c.getString(10);

			cw.add(new CounterWord(sectionEng, sectionRus, word, hiragana,
					romaji, translationEng, translationRus, percentage,
					showntimes, lastview, color));
			c.moveToNext();
		}
		c.close();
		return cw;
	}

	public void bulkInsertFromCSV(String filepath, AssetManager assetManager,
			ContentResolver cr) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(
					assetManager.open(filepath)));
			// do reading, usually loop until end of file reading
			String mLine;
			// setting random colors
			Random r = new Random();
			int r1 = r.nextInt(5);
			String sectionEng = "";
			String sectionRus = "";
			String color = "";
			int i = 0;
			while ((mLine = reader.readLine()) != null) {
				// first line contains only caption
				// others - values separated by tabs
				if (i == 0) {
					String[] section = mLine.split("\\t");
					sectionEng = section[0];
					sectionRus = section[1];
					i++;
				} else {
					String[] s = mLine.split("\\t");
					r = new Random();
					r1 = r.nextInt(5);
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
					if (!sectionEng.equals("Numbers"))
						insert(new CounterWord(sectionEng, sectionRus, s[0],
								s[1], s[2], s[3], s[4], 0, 0, "", color), cr);
					else
						insert(new CounterWord(sectionEng, sectionRus, s[0],
								s[1], s[2], s[3], s[3], 0, 0, "", color), cr);

				}
			}
		} catch (IOException e) {
			Log.e("VocabularyService", e.getMessage() + " " + e.getCause());

		}
	}

	// returns section name, number of all words and number of learned words
	public HashMap<String, int[]> getAllSectionsWithProgress(ContentResolver cr) {
		HashMap<String, int[]> info = new HashMap<String, int[]>();
		Cursor c;
		if (App.lang == Languages.ENG)
			c = CounterWordsDAO.db
					.rawQuery(
							"SELECT Section_eng, count(_id) FROM counter_words group by Section_eng",
							null);
		else
			c = CounterWordsDAO.db
					.rawQuery(
							"SELECT Section_rus, count(_id) FROM counter_words group by Section_rus",
							null);
		c.moveToFirst();
		String section = "";
		int number = 0;

		while (!c.isAfterLast()) {
			section = c.getString(0);
			number = c.getInt(1);
			info.put(section, new int[] { number, 0 });
			c.moveToNext();
		}
		Cursor c1;
		if (App.lang == Languages.ENG)
			c1 = CounterWordsDAO.db
					.rawQuery(
							"SELECT Section_eng, count(_id) FROM counter_words where percentage=1 group by Section_eng",
							null);
		else
			c1 = CounterWordsDAO.db
					.rawQuery(
							"SELECT Section_rus, count(_id) FROM counter_words where percentage=1 group by Section_rus",
							null);
		c1.moveToFirst();
		while (!c.isAfterLast()) {
			section = c1.getString(0);
			number = c1.getInt(1);
			int a = info.get(section)[0];
			info.remove(section);
			info.put(section, new int[] { a, number });
			c.moveToNext();
		}
		c.close();
		return info;
	}

	public CounterWordsDictionary createCurrentDictionary(String section,
			int countWordsInCurrentDict, ContentResolver contentResolver) {
		all = new CounterWordsDictionary();
		all = getCounterwordsBySection(section, contentResolver);
		CounterWordsDictionary current = new CounterWordsDictionary();
		// if words have never been showed - set entries randomly
		if (App.userInfo.isLevelLaunchedFirstTime == 1) {
			all.sortRandomly();
			for (int i = 0; i < App.userInfo.getNumberOfEntriesInCurrentDict(); i++) {
				CounterWord e = all.get(i);
				if (e.getLearnedPercentage() != 1)
					current.add(e);
			}
		} else {
			// sorting descending
			// get last elements
			all.sortByLastViewedTime();
			int i = all.size() - 1;
			while (current.size() < App.userInfo
					.getNumberOfEntriesInCurrentDict()) {
				CounterWord e = all.get(i);
				if (e.getLearnedPercentage() != 1)
					current.add(e);
				i--;
				Log.i("createCurrentDictionary", all.get(i).toString());
			}
		}
		return current;
	}

	public int getNumberOfCounterWords(ContentResolver contentResolver) {
		Cursor countCursor = contentResolver.query(CounterWordsDAO.CONTENT_URI,
				new String[] { "count(*) AS count" }, null, null, null);
		countCursor.moveToFirst();
		int count = countCursor.getInt(0);
		countCursor.close();
		return count;
	}

}
