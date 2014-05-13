package ua.hneu.languagetrainer.service;

import java.util.ArrayList;

import ua.hneu.languagetrainer.db.dao.GrammarDAO;
import ua.hneu.languagetrainer.db.dao.GrammarExamplesDAO;
import ua.hneu.languagetrainer.db.dao.VocabularyDAO;
import ua.hneu.languagetrainer.model.grammar.GrammarDictionary;
import ua.hneu.languagetrainer.model.grammar.GrammarExample;
import ua.hneu.languagetrainer.model.grammar.GrammarRule;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class GrammarExampleService {

	public void insert(GrammarExample g, int ruleId, ContentResolver cr) {
		ContentValues values = new ContentValues();
		values.put(GrammarExamplesDAO.RULE_ID, ruleId);
		values.put(GrammarExamplesDAO.TEXT, g.getText());
		values.put(GrammarExamplesDAO.ROMAJI, g.getRomaji());
		values.put(GrammarExamplesDAO.TRANSLATION_ENG, g.getTranslationEng());
		values.put(GrammarExamplesDAO.TRANSLATION_RUS, g.getTranslationRus());
		//TODO: resolve
		//values.put(GrammarExamplesDAO.RULE_ID, g.getRule());
		cr.insert(GrammarExamplesDAO.CONTENT_URI, values);
	}

	public void emptyTable() {
		GrammarExamplesDAO.getDb().execSQL("delete from " + GrammarExamplesDAO.TABLE_NAME);
	}

	public void createTable() {
		SQLiteDatabase db = GrammarDAO.getDb();
		db.execSQL("CREATE TABLE if not exists " + GrammarExamplesDAO.TABLE_NAME
				+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "+ GrammarExamplesDAO.RULE_ID + " INTEGER, "
				+ GrammarExamplesDAO.TEXT + " TEXT, " + GrammarExamplesDAO.ROMAJI + " TEXT, "
				+ GrammarExamplesDAO.TRANSLATION_ENG + " TEXT, " + GrammarExamplesDAO.TRANSLATION_RUS
				+ " TEXT); ");
	}

	public void dropTable() {
		GrammarDAO.getDb().execSQL("DROP TABLE if exists " + GrammarExamplesDAO.TABLE_NAME + ";");
	}

	public ArrayList<GrammarExample> getExamplesByRuleId(int ruleId,
			ContentResolver cr) {
		String[] col = { GrammarExamplesDAO.RULE_ID, 
				GrammarExamplesDAO.TEXT, GrammarExamplesDAO.ROMAJI,GrammarExamplesDAO.TRANSLATION_ENG,
				GrammarExamplesDAO.TRANSLATION_RUS };
		Cursor c = cr.query(GrammarExamplesDAO.CONTENT_URI, col, GrammarExamplesDAO.RULE_ID + "="
				+ ruleId, null, null, null);
		c.moveToFirst();
		String text = "";
		String romaji = "";
		String eng = "";
		String rus = "";
		ArrayList<GrammarExample> ge = new ArrayList<GrammarExample>();
		while (!c.isAfterLast()) {
			text = c.getString(1);
			romaji = c.getString(2);
			eng = c.getString(3);
			rus = c.getString(4);
			ge.add(new GrammarExample(text, romaji, eng, rus));
			c.moveToNext();
		}
		return ge;
	}

}
