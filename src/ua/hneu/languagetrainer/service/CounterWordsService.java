package ua.hneu.languagetrainer.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import ua.hneu.languagetrainer.db.dao.CounterWordsDAO;
import ua.hneu.languagetrainer.db.dao.GrammarDAO;
import ua.hneu.languagetrainer.model.other.CounterWords;
import ua.hneu.languagetrainer.model.other.Giongo;
import ua.hneu.languagetrainer.model.other.GiongoExample;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class CounterWordsService {

	public void insert(CounterWords cw, ContentResolver cr) {
		ContentValues values = new ContentValues();
		values.put(CounterWordsDAO.SECTION, cw.getSection());
		values.put(CounterWordsDAO.WORD, cw.getWord());
		values.put(CounterWordsDAO.HIRAGANA, cw.getHiragana());
		values.put(CounterWordsDAO.ROMAJI, cw.getRomaji());
		values.put(CounterWordsDAO.TRANSLATION_ENG, cw.getTranslationEng());
		values.put(CounterWordsDAO.TRANSLATION_RUS, cw.getTranslationRus());
		values.put(CounterWordsDAO.COLOR, cw.getColor());
		cr.insert(CounterWordsDAO.CONTENT_URI, values);
	}

	public void emptyTable() {
		CounterWordsDAO.getDb().execSQL(
				"delete from " + CounterWordsDAO.TABLE_NAME);
	}

	public void createTable() {
		SQLiteDatabase db = CounterWordsDAO.getDb();
		db.execSQL("CREATE TABLE " + CounterWordsDAO.TABLE_NAME
				+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ CounterWordsDAO.SECTION + " TEXT, " + CounterWordsDAO.WORD
				+ " TEXT, " + CounterWordsDAO.HIRAGANA + " TEXT, "
				+ CounterWordsDAO.ROMAJI + " TEXT, "
				+ CounterWordsDAO.TRANSLATION_ENG + " TEXT, "
				+ CounterWordsDAO.TRANSLATION_RUS + " TEXT, "
				+ GrammarDAO.PERCENTAGE + " REAL, " + GrammarDAO.LASTVIEW
				+ " DATETIME," + GrammarDAO.SHOWNTIMES + " INTEGER, "
				+ CounterWordsDAO.COLOR + " TEXT); ");
	}

	public void dropTable() {
		CounterWordsDAO.getDb().execSQL(
				"DROP TABLE " + CounterWordsDAO.TABLE_NAME + ";");
	}

	public ArrayList<CounterWords> getCounterwordsBySection(String section,
			ContentResolver cr) {
		String[] col = { CounterWordsDAO.SECTION, CounterWordsDAO.WORD,
				CounterWordsDAO.HIRAGANA, CounterWordsDAO.ROMAJI,
				CounterWordsDAO.TRANSLATION_ENG,
				CounterWordsDAO.TRANSLATION_RUS, CounterWordsDAO.COLOR };
		Cursor c = cr.query(CounterWordsDAO.CONTENT_URI, col,
				CounterWordsDAO.SECTION + "=\"" + section + "\"", null, null,
				null);
		c.moveToFirst();
		String word = "";
		String hiragana = "";
		String romaji = "";
		String translationEng = "";
		String translationRus = "";
		double percentage = 0;
		String lastview = "";
		int showntimes = 0;
		String color = "";
		GrammarExampleService ges = new GrammarExampleService();
		ArrayList<CounterWords> cw = new ArrayList<CounterWords>();
		while (!c.isAfterLast()) {
			word = c.getString(1);
			hiragana = c.getString(2);
			translationEng = c.getString(3);
			translationRus = c.getString(4);
			percentage = c.getDouble(5);
			lastview = c.getString(6);
			showntimes = c.getInt(7);
			color = c.getString(8);

			cw.add(new CounterWords(section, word, hiragana, romaji,
					translationEng, translationRus, percentage, showntimes,
					lastview, color));
			c.moveToNext();
		}
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
			String section = "";
			String color = "";
			int i = 0;
			while ((mLine = reader.readLine()) != null) {
				// first line contains only caption
				// others - values separated by tabs
				if (i == 0) {
					section = mLine;
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
					if (!section.equals("Numbers"))
						insert(new CounterWords(section, s[0], s[1], s[2],
								s[3], s[4], 0, 0, "", color), cr);
					else
						insert(new CounterWords(section, s[0], s[1], s[2],
								s[3], s[3], 0, 0, "", color), cr);

				}
			}
		} catch (IOException e) {
			Log.e("VocabularyService", e.getMessage() + " " + e.getCause());

		}
	}
}
