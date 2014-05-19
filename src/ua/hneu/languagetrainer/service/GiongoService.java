package ua.hneu.languagetrainer.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import ua.hneu.languagetrainer.App;
import ua.hneu.languagetrainer.db.dao.CounterWordsDAO;
import ua.hneu.languagetrainer.db.dao.GiongoDAO;
import ua.hneu.languagetrainer.model.other.Giongo;
import ua.hneu.languagetrainer.model.other.GiongoDictionary;
import ua.hneu.languagetrainer.model.other.GiongoExample;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class GiongoService {
	GiongoExampleService ges = new GiongoExampleService();
	public static GiongoDictionary all;
	static int numberOfEnteries = 0;

	public void insert(Giongo g, ContentResolver cr) {
		for (GiongoExample ge : g.getExamples()) {
			ges.insert(ge, numberOfEnteries, cr);
		}
		numberOfEnteries++;
		ContentValues values = new ContentValues();
		values.put(GiongoDAO.WORD, g.getWord());
		values.put(GiongoDAO.ROMAJI, g.getRomaji());
		values.put(GiongoDAO.TRANSLATION_ENG, g.getTranslEng());
		values.put(GiongoDAO.TRANSLATION_RUS, g.getTranslRus());
		values.put(GiongoDAO.PERCENTAGE, g.getLearnedPercentage());
		values.put(GiongoDAO.LASTVIEW, g.getLastview());
		values.put(GiongoDAO.SHOWNTIMES, g.getShownTimes());
		values.put(GiongoDAO.COLOR, g.getColor());
		cr.insert(GiongoDAO.CONTENT_URI, values);
	}

	public void update(Giongo g, ContentResolver contentResolver) {
		ContentValues values = new ContentValues();
		values.put(GiongoDAO.WORD, g.getWord());
		values.put(GiongoDAO.ROMAJI, g.getRomaji());
		values.put(GiongoDAO.TRANSLATION_ENG, g.getTranslEng());
		values.put(GiongoDAO.TRANSLATION_RUS, g.getTranslRus());
		values.put(GiongoDAO.PERCENTAGE, g.getLearnedPercentage());
		values.put(GiongoDAO.LASTVIEW, g.getLastview());
		values.put(GiongoDAO.SHOWNTIMES, g.getShownTimes());
		values.put(GiongoDAO.COLOR, g.getColor());
		contentResolver.insert(GiongoDAO.CONTENT_URI, values);
		String s = CounterWordsDAO.WORD + " =\"" + g.getWord() + "\" ";
		contentResolver.update(GiongoDAO.CONTENT_URI, values, s, null);

	}

	public static void startCounting(ContentResolver contentResolver) {
		numberOfEnteries = getNumberOfGiongo(contentResolver) + 1;
	}

	public static int getNumberOfGiongo(ContentResolver cr) {
		Cursor countCursor = cr.query(GiongoDAO.CONTENT_URI,
				new String[] { "count(*) AS count" }, null + "", null, null);
		countCursor.moveToFirst();
		int count = countCursor.getInt(0);
		countCursor.close();
		return count;
	}

	public void emptyTable() {
		GiongoDAO.getDb().execSQL("delete from " + GiongoDAO.TABLE_NAME);
	}

	public void createTable() {
		SQLiteDatabase db = GiongoDAO.getDb();
		db.execSQL("CREATE TABLE if not exists " + GiongoDAO.TABLE_NAME
				+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + GiongoDAO.WORD
				+ " TEXT, " + GiongoDAO.ROMAJI + " TEXT, "
				+ GiongoDAO.TRANSLATION_ENG + " TEXT, "
				+ GiongoDAO.TRANSLATION_RUS + " TEXT, " + GiongoDAO.PERCENTAGE
				+ " REAL, " + GiongoDAO.LASTVIEW + " DATETIME,"
				+ GiongoDAO.SHOWNTIMES + " INTEGER, " + GiongoDAO.COLOR
				+ " TEXT); ");
	}

	public void dropTable() {
		GiongoDAO.getDb().execSQL(
				"DROP TABLE if exists " + GiongoDAO.TABLE_NAME + ";");
	}

	@SuppressLint("NewApi")
	public GiongoDictionary getAllGiongo(ContentResolver cr) {
		String[] col = { GiongoDAO.ID, GiongoDAO.WORD, GiongoDAO.ROMAJI,
				GiongoDAO.TRANSLATION_ENG, GiongoDAO.TRANSLATION_RUS,
				GiongoDAO.PERCENTAGE, GiongoDAO.LASTVIEW, GiongoDAO.SHOWNTIMES,
				GiongoDAO.COLOR };
		Cursor c = cr.query(GiongoDAO.CONTENT_URI, col, null, null, null, null);
		c.moveToFirst();
		int id = 0;
		String word;
		String romaji;
		String translEng;
		String translRus;
		double percentage = 0;
		String lastview = "";
		int showntimes = 0;
		String color;

		GiongoExampleService ges = new GiongoExampleService();
		GiongoDictionary g = new GiongoDictionary();
		while (!c.isAfterLast()) {
			id = c.getInt(0);
			word = c.getString(1);
			romaji = c.getString(2);
			translEng = c.getString(3);
			translRus = c.getString(4);
			percentage = c.getDouble(5);
			lastview = c.getString(6);
			showntimes = c.getInt(7);
			color = c.getString(8);
			ArrayList<GiongoExample> ge = new ArrayList<GiongoExample>();
			ge = ges.getExamplesByGiongoId(id, cr);
			g.add(new Giongo(word, romaji, translEng, translRus, percentage,
					showntimes, lastview, color, ge));
			c.moveToNext();
		}
		c.close();
		return g;
	}

	public void bulkInsertFromCSV(String filepath, AssetManager assetManager,
			ContentResolver cr) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(
					assetManager.open(filepath)));
			// do reading, usually loop until end of file reading
			String mLine;
			boolean isFirst = true;
			Giongo g = new Giongo();
			// setting random colors
			Random r = new Random();
			int r1 = r.nextInt(5);
			String color = "";

			while ((mLine = reader.readLine()) != null) {
				if (!mLine.isEmpty()) {
					if (isFirst) {
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
						g = new Giongo(s[0].trim(), s[1].trim(), s[2].trim(), s[3].trim(), 0, 0, "", color.trim(),
								new ArrayList<GiongoExample>());
						isFirst = false;
					} else {
						String[] s = mLine.split("\\t");
						GiongoExample ge = new GiongoExample(s[0].trim() + "\\t"
								+ s[1].trim() + "\\t" + s[2].trim(), s[3].trim(), s[4].trim(), s[5].trim());
						g.examples.add(ge);
					}
				} else {
					this.insert(g, cr);
					g = new Giongo();
					g.setExamples(new ArrayList<GiongoExample>());
					isFirst = true;

				}
			}
		} catch (IOException e) {
			Log.e("VocabularyService", e.getMessage() + " " + e.getCause());

		}
	}

	public GiongoDictionary createCurrentDictionary(
			int numberOfEntriesInCurrentDict, ContentResolver cr) {
		all = new GiongoDictionary();
		all = getAllGiongo(cr);
		GiongoDictionary current = new GiongoDictionary();
		// if words have never been showed - set entries randomly
		if (App.userInfo.isLevelLaunchedFirstTime == 1) {
			all.sortRandomly();
			for (int i = 0; i < App.numberOfEntriesInCurrentDict; i++) {
				Giongo e = all.get(i);
				if (e.getLearnedPercentage() != 1)
					current.add(e);
			}
		} else {
			// sorting descending
			// get last elements
			all.sortByLastViewedTime();
			int i = all.size() - 1;
			while (current.size() < App.numberOfEntriesInCurrentDict) {
				Giongo e = all.get(i);
				if (e.getLearnedPercentage() != 1)
					current.add(e);
				i--;
				Log.i("createCurrentDictionary", all.get(i).toString());
			}
		}
		return current;
	}
	

}

// public String aaa(String filepath, AssetManager assetManager,
// ContentResolver cr) { ArrayList<String> entries = new
// ArrayList<String>(); ArrayList<String> entries1 = new
// ArrayList<String>(); BufferedReader reader = null;
//
// try { reader = new BufferedReader(new InputStreamReader(
// assetManager.open("2.txt"))); String mLine; while ((mLine =
// reader.readLine()) != null) { entries.add(mLine); } } catch (IOException
// e) { Log.e("VocabularyService", e.getMessage() + " " + e.getCause()); }
// try { reader = new BufferedReader(new InputStreamReader(
// assetManager.open("1.txt"))); String mLine; while ((mLine =
// reader.readLine()) != null) { entries1.add(mLine); } } catch (IOException
// e) { Log.e("VocabularyService", e.getMessage() + " " + e.getCause()); }
// StringBuilder x = new StringBuilder(); try { reader = new
// BufferedReader(new InputStreamReader( assetManager.open(filepath)));
//
// String mLine; int i = 0; while ((mLine = reader.readLine()) != null) { if
// (mLine != null) { // String[] parts = mLine.split("\t");
//
// if (!mLine.isEmpty()) { // System.out.println(mLine + "\t" + //
// entries.get(i));
//
// Pattern pattern = Pattern
// .compile("([\\p{Hiragana}\\p{Katakana}\\p{Han}\\t]*+)([a-zA-Z ]*)");
// Matcher matcher = pattern.matcher(mLine); String string = ""; while
// (matcher.find()) { if (!matcher.group(0).isEmpty()) { string =
// matcher.group(1) + "\t" + entries.get(i) + "\t" + matcher.group(2) + "\t"
// + entries1.get(i); break; } } i++; x.append(string + "\r\n"); } } } }
// catch (IOException e) { Log.e("VocabularyService", e.getMessage() + " " +
// e.getCause()); } return x.toString(); }

