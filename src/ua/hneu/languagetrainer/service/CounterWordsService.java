package ua.hneu.languagetrainer.service;

import java.util.ArrayList;

import ua.hneu.languagetrainer.db.dao.CounterWordsDAO;
import ua.hneu.languagetrainer.model.other.CounterWords;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
				+ CounterWordsDAO.TRANSLATION_ENG + " TEXT, "
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
		String color = "";
		GrammarExampleService ges = new GrammarExampleService();
		ArrayList<CounterWords> cw = new ArrayList<CounterWords>();
		while (!c.isAfterLast()) {
			word = c.getString(1);
			hiragana = c.getString(2);
			translationEng = c.getString(3);
			translationRus = c.getString(4);
			color = c.getString(5);
			cw.add(new CounterWords(section, word, hiragana, romaji,
					translationEng, translationRus, color));
			c.moveToNext();
		}
		return cw;
	}
}
