package ua.hneu.languagetrainer.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import ua.hneu.languagetrainer.App;
import ua.hneu.languagetrainer.db.dao.GiongoDAO;
import ua.hneu.languagetrainer.db.dao.GrammarDAO;
import ua.hneu.languagetrainer.db.dao.GrammarExamplesDAO;
import ua.hneu.languagetrainer.db.dao.VocabularyDAO;
import ua.hneu.languagetrainer.model.grammar.GrammarExample;
import ua.hneu.languagetrainer.model.grammar.GrammarRule;
import ua.hneu.languagetrainer.model.other.Giongo;
import ua.hneu.languagetrainer.model.other.GiongoExample;
import ua.hneu.languagetrainer.model.vocabulary.DictionaryEntry;
import ua.hneu.languagetrainer.model.vocabulary.WordDictionary;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class GrammarService {
	public static WordDictionary all;
	boolean isFirstTimeCreated;
	GrammarExampleService ges = new GrammarExampleService();
	static int numberOfEnteries = 0;

	public void insert(GrammarRule g, ContentResolver cr) {
		for (GrammarExample ge : g.getExamples()) {
			ges.insert(ge, numberOfEnteries, cr);
		}
		numberOfEnteries++;
		ContentValues values = new ContentValues();
		values.put(GrammarDAO.RULE, g.getRule());
		values.put(GrammarDAO.LEVEL, g.getLevel());
		values.put(GrammarDAO.DESC_ENG, g.getDescEng());
		values.put(GrammarDAO.DESC_RUS, g.getDescEng());
		values.put(GrammarDAO.PERCENTAGE, g.getLearnedPercentage());
		values.put(GrammarDAO.LASTVIEW, g.getLastview());
		values.put(GrammarDAO.SHOWNTIMES, g.getShownTimes());
		values.put(GrammarDAO.COLOR, g.getColor());
		cr.insert(GrammarDAO.CONTENT_URI, values);
	}

	public void emptyTable() {
		GrammarDAO.getDb().execSQL("delete from " + GrammarDAO.TABLE_NAME);
	}

	public static void startCounting(ContentResolver contentResolver) {
		numberOfEnteries = getNumberOfGrammar(contentResolver) + 1;
	}

	private static int getNumberOfGrammar(ContentResolver cr) {
		Cursor countCursor = cr.query(GrammarDAO.CONTENT_URI,
				new String[] { "count(*) AS count" }, null + "", null, null);
		countCursor.moveToFirst();
		int count = countCursor.getInt(0);
		countCursor.close();
		return count;
	}

	public void createTable() {
		SQLiteDatabase db = GrammarDAO.getDb();
		db.execSQL("CREATE TABLE " + GrammarDAO.TABLE_NAME
				+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ GrammarDAO.LEVEL + " INTEGER, " + GrammarDAO.RULE + " TEXT, "
				+ GrammarDAO.DESC_ENG + " TEXT, " + GrammarDAO.DESC_RUS
				+ " TEXT, " + GrammarDAO.PERCENTAGE + " REAL, "
				+ GrammarDAO.LASTVIEW + " DATETIME," + GrammarDAO.SHOWNTIMES
				+ " INTEGER, " + GrammarDAO.COLOR + " TEXT); ");
	}

	public void dropTable() {
		GrammarDAO.getDb().execSQL("DROP TABLE " + GrammarDAO.TABLE_NAME + ";");
	}

	/*
	 * public static WordDictionary createCurrentDictionary(int level, int
	 * countWordsInCurrentDict, ContentResolver contentResolver) { all = new
	 * WordDictionary(); all = selectAllEntriesOflevel(level, contentResolver);
	 * WordDictionary current = new WordDictionary(); // if words have never
	 * been showed - set entries randomly if
	 * (App.userInfo.isLevelLaunchedFirstTime == 1) { all.sortRandomly(); for
	 * (int i = 0; i < App.userInfo.getNumberOfVocabularyInCurrentDict(); i++) {
	 * DictionaryEntry e = all.get(i); if (e.getLearnedPercentage() != 1)
	 * current.add(e); } } else { // sorting descending // get last elements
	 * all.sortByLastViewedTime(); int i = all.size() - 1; while (current.size()
	 * < App.userInfo .getNumberOfVocabularyInCurrentDict()) { DictionaryEntry e
	 * = all.get(i); if (e.getLearnedPercentage() != 1) current.add(e); i--;
	 * Log.i("createCurrentDictionary", all.get(i).toString()); } } return
	 * current; }
	 */

	public ArrayList<GrammarRule> getRulesByLevel(int level, ContentResolver cr) {
		String[] col = { GrammarDAO.ID, GrammarDAO.LEVEL, GrammarDAO.RULE,
				GrammarDAO.DESC_ENG, GrammarDAO.DESC_RUS, GrammarDAO.COLOR };
		Cursor c = cr.query(GrammarDAO.CONTENT_URI, col, GrammarDAO.LEVEL + "="
				+ level, null, null, null);
		c.moveToFirst();
		int id = 0;
		String rule = "";
		String descEng = "";
		String descRus = "";
		double percentage = 0;
		String lastview = "";
		int showntimes = 0;
		String color = "";
		GrammarExampleService ges = new GrammarExampleService();
		ArrayList<GrammarRule> gr = new ArrayList<GrammarRule>();
		while (!c.isAfterLast()) {
			id = c.getInt(0);
			rule = c.getString(2);
			descEng = c.getString(3);
			descRus = c.getString(4);
			percentage = c.getDouble(5);
			lastview = c.getString(6);
			showntimes = c.getInt(7);
			color = c.getString(8);
			ArrayList<GrammarExample> ge = new ArrayList<GrammarExample>();
			ge = ges.getExamplesByRuleId(id, cr);

			gr.add(new GrammarRule(rule, level, descEng, descRus, percentage, lastview, showntimes,
					color, ge));

			c.moveToNext();
		}
		return gr;
	}

	public void bulkInsertFromCSV(String filepath, int level,
			AssetManager assetManager, ContentResolver cr) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(
					assetManager.open(filepath)));
			// do reading, usually loop until end of file reading
			String mLine;
			boolean isFirst = true;
			GrammarRule g = new GrammarRule();
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
						g = new GrammarRule(s[0], level, s[1], s[2], 0, "", 0,
								color, new ArrayList<GrammarExample>());
						isFirst = false;
					} else {
						String[] s = mLine.split("\\t");
						GrammarExample ge = new GrammarExample(s[0] + "\\t"
								+ s[1] + "\\t" + s[2], s[3], s[4], s[5]);
						g.examples.add(ge);
					}
				} else {
					this.insert(g, cr);
					g = new GrammarRule();
					isFirst = true;
				}
			}
		} catch (IOException e) {
			Log.e("VocabularyService", e.getMessage() + " " + e.getCause());

		}
	}
}
