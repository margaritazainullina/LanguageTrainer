package ua.hneu.languagetrainer.service;

import java.util.ArrayList;

import ua.hneu.languagetrainer.db.dao.GiongoExamplesDAO;
import ua.hneu.languagetrainer.model.other.GiongoExample;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class GiongoExampleService {

	public void insert(GiongoExample g, int giongoId, ContentResolver cr) {
		ContentValues values = new ContentValues();
		values.put(GiongoExamplesDAO.GIONGO_ID, giongoId);
		values.put(GiongoExamplesDAO.TEXT, g.getText());
		values.put(GiongoExamplesDAO.ROMAJI, g.getRomaji());
		values.put(GiongoExamplesDAO.TRANSLATION_ENG, g.getTranslationEng());
		values.put(GiongoExamplesDAO.TRANSLATION_RUS, g.getTranslationRus());
		cr.insert(GiongoExamplesDAO.CONTENT_URI, values);
	}

	public void emptyTable() {
		GiongoExamplesDAO.getDb().execSQL(
				"delete from " + GiongoExamplesDAO.TABLE_NAME);
	}

	public void createTable() {
		SQLiteDatabase db = GiongoExamplesDAO.getDb();
		db.execSQL("CREATE TABLE " + GiongoExamplesDAO.TABLE_NAME
				+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ GiongoExamplesDAO.GIONGO_ID + " TEXT, "
				+ GiongoExamplesDAO.TEXT + " TEXT, " + GiongoExamplesDAO.ROMAJI
				+ " TEXT, " + GiongoExamplesDAO.TRANSLATION_ENG + " TEXT, "
				+ GiongoExamplesDAO.TRANSLATION_RUS + " TEXT); ");
	}

	public void dropTable() {
		GiongoExamplesDAO.getDb().execSQL(
				"DROP TABLE " + GiongoExamplesDAO.TABLE_NAME + ";");
	}

	public ArrayList<GiongoExample> getExamplesByGiongoId(int giongoId,
			ContentResolver cr) {
		String[] col = { GiongoExamplesDAO.GIONGO_ID, GiongoExamplesDAO.TEXT,
				GiongoExamplesDAO.ROMAJI, GiongoExamplesDAO.TRANSLATION_ENG,
				GiongoExamplesDAO.TRANSLATION_RUS };
		Cursor c = cr.query(GiongoExamplesDAO.CONTENT_URI, col,
				GiongoExamplesDAO.GIONGO_ID + "=" + giongoId, null, null, null);
		c.moveToFirst();
		String text = "";
		String romaji = "";
		String eng = "";
		String rus = "";
		ArrayList<GiongoExample> ge = new ArrayList<GiongoExample>();
		while (!c.isAfterLast()) {
			text = c.getString(1);
			romaji = c.getString(2);
			eng = c.getString(3);
			rus = c.getString(4);
			ge.add(new GiongoExample(text, romaji, eng, rus));
			c.moveToNext();
		}
		return ge;
	}
}
