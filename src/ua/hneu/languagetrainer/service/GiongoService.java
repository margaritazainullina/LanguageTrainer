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
import ua.hneu.languagetrainer.model.grammar.GrammarExample;
import ua.hneu.languagetrainer.model.grammar.GrammarRule;
import ua.hneu.languagetrainer.model.other.Giongo;
import ua.hneu.languagetrainer.model.other.GiongoExample;
import ua.hneu.languagetrainer.model.vocabulary.DictionaryEntry;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class GiongoService {

	public void insert(Giongo g, ContentResolver cr) {
		ContentValues values = new ContentValues();
		values.put(GiongoDAO.WORD, g.getWord());
		values.put(GiongoDAO.ROMAJI, g.getRomaji());
		values.put(GiongoDAO.TRANSLATION_ENG, g.getTranslEng());
		values.put(GiongoDAO.TRANSLATION_RUS, g.getTranslRus());
		values.put(GiongoDAO.COLOR, g.getColor());
		cr.insert(GiongoDAO.CONTENT_URI, values);
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
				+ GiongoDAO.TRANSLATION_RUS + GiongoDAO.COLOR + " TEXT, "
				+ " TEXT); ");
	}

	public void dropTable() {
		GiongoDAO.getDb().execSQL("DROP TABLE " + GiongoDAO.TABLE_NAME + ";");
	}

	public ArrayList<Giongo> getAllGiongo(int level, ContentResolver cr) {
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
		String color;

		GiongoExampleService ges = new GiongoExampleService();
		ArrayList<Giongo> g = new ArrayList<Giongo>();
		while (!c.isAfterLast()) {
			id = c.getInt(0);
			word = c.getString(1);
			romaji = c.getString(2);
			translEng = c.getString(3);
			translRus = c.getString(4);
			color = c.getString(5);
			ArrayList<GiongoExample> ge = new ArrayList<GiongoExample>();
			ge = ges.getExamplesByGiongoId(id, cr);
			g.add(new Giongo(word, romaji, translEng, translRus, color, ge));
			c.moveToNext();
		}
		return g;
	}

	public String bulkInsertFromCSV(String filepath, AssetManager assetManager,
			ContentResolver cr) {

		ArrayList<String> entries = new ArrayList<String>();
		ArrayList<String> entries1 = new ArrayList<String>();
		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new InputStreamReader(
					assetManager.open("2.txt")));
			// do reading, usually loop until end of file reading
			String mLine;
			while ((mLine = reader.readLine()) != null) {
				entries.add(mLine);
			}
		} catch (IOException e) {
			Log.e("VocabularyService", e.getMessage() + " " + e.getCause());
		}
		try {
			reader = new BufferedReader(new InputStreamReader(
					assetManager.open("1.txt")));
			// do reading, usually loop until end of file reading
			String mLine;
			while ((mLine = reader.readLine()) != null) {
				entries1.add(mLine);
			}
		} catch (IOException e) {
			Log.e("VocabularyService", e.getMessage() + " " + e.getCause());
		}
		StringBuilder x = new StringBuilder();
		try {
			reader = new BufferedReader(new InputStreamReader(
					assetManager.open(filepath)));
			// do reading, usually loop until end of file reading
			String mLine;
			int i = 0;
			while ((mLine = reader.readLine()) != null) {
				if (mLine != null) {
					// String[] parts = mLine.split("\t");

					if (!mLine.isEmpty()) {
						// System.out.println(mLine + "\t" + entries.get(i));

						Pattern pattern = Pattern
								.compile("([\\p{Hiragana}\\p{Katakana}\\p{Han}\\t]*+)([a-zA-Z ]*)");
						Matcher matcher = pattern.matcher(mLine);
						String string = "";
						while (matcher.find()) {
							if (!matcher.group(0).isEmpty()) {
								string = matcher.group(1)+"\t" + entries.get(i)+ "\t"+ matcher.group(2)+"\t"+entries1.get(i);
								break;
							}
						}
						i++;
						x.append(string + "\r\n");						
					}
				}
			}
		} catch (IOException e) {
			Log.e("VocabularyService", e.getMessage() + " " + e.getCause());
		}
		return x.toString();
	}

}
