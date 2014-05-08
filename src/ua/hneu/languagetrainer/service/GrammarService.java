package ua.hneu.languagetrainer.service;

import java.util.ArrayList;

import ua.hneu.languagetrainer.db.dao.GrammarDAO;
import ua.hneu.languagetrainer.db.dao.GrammarExamplesDAO;
import ua.hneu.languagetrainer.model.grammar.GrammarExample;
import ua.hneu.languagetrainer.model.grammar.GrammarRule;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class GrammarService {

	public void insert(GrammarRule g, ContentResolver cr) {
		ContentValues values = new ContentValues();
		values.put(GrammarDAO.RULE, g.getRule());
		values.put(GrammarDAO.DESC_ENG, g.getDescEng());
		values.put(GrammarDAO.DESC_RUS, g.getDescEng());
		values.put(GrammarDAO.COLOR, g.getColor());
		cr.insert(GrammarDAO.CONTENT_URI, values);
	}

	public void emptyTable() {
		GrammarDAO.getDb().execSQL("delete from " + GrammarDAO.TABLE_NAME);
	}

	public void createTable() {
		SQLiteDatabase db = GrammarDAO.getDb();
		db.execSQL("CREATE TABLE " + GrammarDAO.TABLE_NAME
				+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ GrammarDAO.LEVEL + " INTEGER, " + GrammarDAO.RULE + " TEXT, "
				+ GrammarDAO.DESC_ENG + " TEXT, " + GrammarDAO.DESC_RUS+ GrammarDAO.COLOR
				+ " TEXT); ");
	}

	public void dropTable() {
		GrammarDAO.getDb().execSQL("DROP TABLE " + GrammarDAO.TABLE_NAME + ";");
	}

	public ArrayList<GrammarRule> getRulesByLevel(int level,
			ContentResolver cr) {
		String[] col = { GrammarDAO.ID, 
				GrammarDAO.LEVEL, GrammarDAO.RULE,GrammarDAO.DESC_ENG,
				GrammarDAO.DESC_RUS,  GrammarDAO.COLOR };
		Cursor c = cr.query(GrammarDAO.CONTENT_URI, col, GrammarDAO.LEVEL + "="
				+ level, null, null, null);
		c.moveToFirst();
		int id=0;
		String rule = "";
		String descEng = "";
		String descRus = "";
		String color = "";
		GrammarExampleService ges = new GrammarExampleService();
		ArrayList<GrammarRule> gr = new ArrayList<GrammarRule>();
		while (!c.isAfterLast()) {
			id = c.getInt(0);
			rule = c.getString(2);
			descEng = c.getString(3);
			descRus = c.getString(4);
			color = c.getString(5);
			ArrayList<GrammarExample> ge = new ArrayList<GrammarExample>();
			ge = ges.getExamplesByRuleId(id, cr);
			gr.add(new GrammarRule(rule, level, descEng, descRus, ge,color));
			c.moveToNext();
		}
		return gr;
	}
}
