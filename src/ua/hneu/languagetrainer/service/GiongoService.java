package ua.hneu.languagetrainer.service;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ua.hneu.languagetrainer.db.dao.GiongoDAO;
import ua.hneu.languagetrainer.db.dao.GrammarDAO;
import ua.hneu.languagetrainer.db.dao.VocabularyDAO;
import ua.hneu.languagetrainer.model.grammar.GrammarExample;
import ua.hneu.languagetrainer.model.grammar.GrammarRule;
import ua.hneu.languagetrainer.model.other.Giongo;
import ua.hneu.languagetrainer.model.other.GiongoExample;
import ua.hneu.languagetrainer.model.tests.Question;
import ua.hneu.languagetrainer.model.vocabulary.VocabularyEntry;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class GiongoService {
	GiongoExampleService ges = new GiongoExampleService();
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
		values.put(GrammarDAO.PERCENTAGE, g.getLearnedPercentage());
		values.put(GrammarDAO.LASTVIEW, g.getLastview());
		values.put(GrammarDAO.SHOWNTIMES, g.getShownTimes());
		values.put(GiongoDAO.COLOR, g.getColor());
		cr.insert(GiongoDAO.CONTENT_URI, values);
	}

	public static void startCounting(ContentResolver contentResolver) {
		numberOfEnteries = getNumberOfGiongo(contentResolver) + 1;
	}

	private static int getNumberOfGiongo(ContentResolver cr) {
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
		db.execSQL("CREATE TABLE " + GiongoDAO.TABLE_NAME
				+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + GiongoDAO.WORD
				+ " TEXT, " + GiongoDAO.ROMAJI + " TEXT, "
				+ GiongoDAO.TRANSLATION_ENG + " TEXT, "
				+ GiongoDAO.TRANSLATION_RUS + " TEXT, " + GrammarDAO.PERCENTAGE
				+ " REAL, " + GrammarDAO.LASTVIEW + " DATETIME,"
				+ GrammarDAO.SHOWNTIMES + " INTEGER, " + GiongoDAO.COLOR
				+ " TEXT); ");
	}

	public void dropTable() {
		GiongoDAO.getDb().execSQL("DROP TABLE " + GiongoDAO.TABLE_NAME + ";");
	}

	public ArrayList<Giongo> getAllGiongo(ContentResolver cr) {
		String[] col = { GiongoDAO.ID, GiongoDAO.WORD, GiongoDAO.ROMAJI,
				GiongoDAO.TRANSLATION_ENG, GiongoDAO.TRANSLATION_RUS,
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
		ArrayList<Giongo> g = new ArrayList<Giongo>();
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
						g = new Giongo(s[0], s[1], s[2], s[3], 0, 0, "", color,
								new ArrayList<GiongoExample>());
						isFirst = false;
					} else {
						String[] s = mLine.split("\\t");
						GiongoExample ge = new GiongoExample(s[0] + "\\t"
								+ s[1] + "\\t" + s[2], s[3], s[4], s[5]);
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

}
