package ua.hneu.languagetrainer.service;

import java.util.ArrayList;

import ua.hneu.languagetrainer.db.dao.GiongoExamplesDAO;
import ua.hneu.languagetrainer.model.other.GiongoExample;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author Margarita Zainullina <margarita.zainullina@gmail.com>
 * @version 1.0
 */
public class GiongoExampleService {
	/**
	 * Inserts a GiongoExample to database
	 * 
	 * @param ge
	 *            - GiongoExample instance to insert
	 * @param giongoId
	 *            - id of a Giongo example belongs to
	 * @param cr
	 *            - content resolver to database
	 */
	public void insert(GiongoExample ge, int giongoId, ContentResolver cr) {
		ContentValues values = new ContentValues();
		values.put(GiongoExamplesDAO.GIONGO_ID, giongoId);
		values.put(GiongoExamplesDAO.TEXT, ge.getText());
		values.put(GiongoExamplesDAO.ROMAJI, ge.getRomaji());
		values.put(GiongoExamplesDAO.TRANSLATION_ENG, ge.getTranslationEng());
		values.put(GiongoExamplesDAO.TRANSLATION_RUS, ge.getTranslationRus());
		cr.insert(GiongoExamplesDAO.CONTENT_URI, values);
	}

	/**
	 * Deletes all entries from GiongoExamples table
	 */
	public void emptyTable() {
		GiongoExamplesDAO.getDb().execSQL(
				"delete from " + GiongoExamplesDAO.TABLE_NAME);
	}

	/**
	 * Creates GiongoExamples table
	 */
	public void createTable() {
		SQLiteDatabase db = GiongoExamplesDAO.getDb();
		db.execSQL("CREATE TABLE if not exists " + GiongoExamplesDAO.TABLE_NAME
				+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ GiongoExamplesDAO.GIONGO_ID + " TEXT, "
				+ GiongoExamplesDAO.TEXT + " TEXT, " + GiongoExamplesDAO.ROMAJI
				+ " TEXT, " + GiongoExamplesDAO.TRANSLATION_ENG + " TEXT, "
				+ GiongoExamplesDAO.TRANSLATION_RUS + " TEXT); ");
	}

	/**
	 * Drops GiongoExamples table
	 */
	public void dropTable() {
		GiongoExamplesDAO.getDb().execSQL(
				"DROP TABLE if exists " + GiongoExamplesDAO.TABLE_NAME + ";");
	}

	/**
	 * Gets list of examples by Giongo entry id
	 * 
	 * @param giongoId
	 *            id of a giongo to which giongo example belongs
	 * @param cr
	 *            - content resolver to database
	 * @return examples list of answers
	 */
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
		ArrayList<GiongoExample> examples = new ArrayList<GiongoExample>();
		while (!c.isAfterLast()) {
			text = c.getString(1);
			romaji = c.getString(2);
			eng = c.getString(3);
			rus = c.getString(4);
			examples.add(new GiongoExample(text, romaji, eng, rus));
			c.moveToNext();
		}
		c.close();
		return examples;
	}
}
