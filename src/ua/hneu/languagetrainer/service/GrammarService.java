package ua.hneu.languagetrainer.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import ua.hneu.languagetrainer.App;
import ua.hneu.languagetrainer.db.dao.GiongoDAO;
import ua.hneu.languagetrainer.db.dao.GrammarDAO;
import ua.hneu.languagetrainer.model.grammar.GrammarDictionary;
import ua.hneu.languagetrainer.model.grammar.GrammarExample;
import ua.hneu.languagetrainer.model.grammar.GrammarRule;
import ua.hneu.languagetrainer.model.other.Giongo;
import ua.hneu.languagetrainer.model.other.GiongoDictionary;
import ua.hneu.languagetrainer.model.other.GiongoExample;
import ua.hneu.languagetrainer.model.vocabulary.VocabularyDictionary;
import ua.hneu.languagetrainer.model.vocabulary.VocabularyEntry;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * @author Margarita Zainullina <margarita.zainullina@gmail.com>
 * @version 1.0
 */
public class GrammarService {
	boolean isFirstTimeCreated;
	static GrammarExampleService ges = new GrammarExampleService();
	static int numberOfEnteries = 0;
	private ArrayList<GrammarRule> entries;

	/**
	 * Inserts a Grammar rule to database
	 * 
	 * @param g
	 *            - GrammarRule instance to insert
	 * @param cr
	 *            - content resolver to database
	 */
	public void insert(GrammarRule g, ContentResolver cr) {
		for (GrammarExample ge : g.getExamples()) {
			ges.insert(ge, numberOfEnteries, cr);
		}
		numberOfEnteries++;
		ContentValues values = new ContentValues();
		values.put(GrammarDAO.RULE, g.getRule());
		values.put(GrammarDAO.LEVEL, g.getLevel());
		values.put(GrammarDAO.DESC_ENG, g.getDescEng());
		values.put(GrammarDAO.DESC_RUS, g.getDescRus());
		values.put(GrammarDAO.PERCENTAGE, g.getLearnedPercentage());
		values.put(GrammarDAO.LASTVIEW, g.getLastview());
		values.put(GrammarDAO.SHOWNTIMES, g.getShownTimes());
		values.put(GrammarDAO.COLOR, g.getColor());
		cr.insert(GrammarDAO.CONTENT_URI, values);
	}

	/**
	 * Updates a GrammarRule in database
	 * 
	 * @param g
	 *            - GrammarRule instance to upate
	 * @param cr
	 *            - content resolver to database
	 */
	public void update(GrammarRule g, ContentResolver cr) {
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
		String s = GrammarDAO.RULE + " =\"" + g.getRule() + "\" ";
		cr.update(GrammarDAO.CONTENT_URI, values, s, null);
	}

	/**
	 * Gets GrammarDictionary with all GrammarRule from db with defined level
	 * 
	 * @param level
	 *            - level of GrammarRule to select
	 * @param cr
	 *            - content resolver to database
	 * @return giongoList list of all giongo in the table
	 */
	public static GrammarDictionary selectAllEntriesOflevel(int level,
			ContentResolver contentResolver) {
		GrammarDictionary gd = new GrammarDictionary();

		String[] selectionArgs = { GrammarDAO.ID, GrammarDAO.RULE,
				GrammarDAO.LEVEL, GrammarDAO.DESC_ENG, GrammarDAO.DESC_RUS,
				GrammarDAO.PERCENTAGE, GrammarDAO.LASTVIEW,
				GrammarDAO.SHOWNTIMES, GrammarDAO.COLOR };
		Cursor c = contentResolver.query(GrammarDAO.CONTENT_URI, selectionArgs,
				"level=" + level, null, null);
		c.moveToFirst();
		int id = 0;
		String rule = "";
		String eng = "";
		String rus = "";
		double percentage = 0;
		String lastview = "";
		int shownTimes = 0;
		String color = "";
		while (!c.isAfterLast()) {
			id = c.getInt(0);
			rule = c.getString(1);
			eng = c.getString(3);
			rus = c.getString(4);
			percentage = c.getDouble(5);
			lastview = c.getString(6);
			shownTimes = c.getInt(7);
			color = c.getString(8);
			c.moveToNext();

			GrammarRule gr = new GrammarRule(rule, level, eng, rus, percentage,
					lastview, shownTimes, color, ges.getExamplesByRuleId(id,
							contentResolver));
			gd.add(gr);
		}
		c.close();
		return gd;
	}

	/**
	 * Returns GrammarDictionary to store it in the App class
	 * 
	 * @param level
	 *            level of GrammarRule to select
	 * @param numberEntriesInCurrentDict
	 *            number of entries to select for learning
	 * @param cr
	 *            content resolver to database
	 * @return currentDict GiongoDictionary with Giongo entries
	 */
	public static GrammarDictionary createCurrentDictionary(int level,
			int numberWordsInCurrentDict, ContentResolver contentResolver) {
		if(App.allGrammarDictionary==null){
			App.allGrammarDictionary = new GrammarDictionary();
			App.allGrammarDictionary = selectAllEntriesOflevel(level, contentResolver);
		}
		GrammarDictionary currentDict = new GrammarDictionary();
		// if words have never been showed - set entries randomly
		if (App.userInfo.isLevelLaunchedFirstTime == 1) {
			currentDict.getEntries().addAll(App.allGrammarDictionary.getRandomEntries(App.numberOfEntriesInCurrentDict));
		} else {
			currentDict= GrammarService.getNLastViewedEntries(App.numberOfEntriesInCurrentDict, contentResolver);
		}
		return currentDict;
	}

	
	
		
	public static GrammarDictionary getNLastViewedEntries(int size,
			ContentResolver cr) {
		GrammarDictionary gd = new GrammarDictionary();

		String[] selectionArgs = { GrammarDAO.ID, GrammarDAO.RULE,
				GrammarDAO.LEVEL, GrammarDAO.DESC_ENG, GrammarDAO.DESC_RUS,
				GrammarDAO.PERCENTAGE, GrammarDAO.LASTVIEW,
				GrammarDAO.SHOWNTIMES, GrammarDAO.COLOR };
		Cursor c = cr.query(GrammarDAO.CONTENT_URI, selectionArgs, GrammarDAO.PERCENTAGE
				+ "<1", null, GrammarDAO.LASTVIEW + " ASC limit " + size, null);
		c.moveToFirst();
		int id = 0;
		String rule = "";
		int level = 0;
		String eng = "";
		String rus = "";
		double percentage = 0;
		String lastview = "";
		int shownTimes = 0;
		String color = "";
		while (!c.isAfterLast()) {
			id = c.getInt(0);
			rule = c.getString(1);
			level = c.getInt(2);
			eng = c.getString(3);
			rus = c.getString(4);
			percentage = c.getDouble(5);
			lastview = c.getString(6);
			shownTimes = c.getInt(7);
			color = c.getString(8);
			c.moveToNext();

			GrammarRule gr = new GrammarRule(rule, level, eng, rus, percentage,
					lastview, shownTimes, color, ges.getExamplesByRuleId(id,
							cr));
			gd.add(gr);
		}
		c.close();
		return gd;
	}

	/**
	 * Deletes all entries from Grammar table
	 */
	public void emptyTable() {
		GrammarDAO.getDb().execSQL("delete from " + GrammarDAO.TABLE_NAME);
	}

	/**
	 * A stub to find out last id of Grammar rules to insert an example to it
	 * 
	 * @param cr
	 *            content resolver to database
	 */
	public static void startCounting(ContentResolver contentResolver) {
		numberOfEnteries = getNumberOfGrammar(contentResolver) + 1;
	}

	/**
	 * Returns number of entries in Grammar table
	 * 
	 * @param cr
	 *            content resolver to database
	 * @return number of entries
	 */
	private static int getNumberOfGrammar(ContentResolver cr) {
		Cursor countCursor = cr.query(GrammarDAO.CONTENT_URI,
				new String[] { "count(*) AS count" }, null + "", null, null);
		countCursor.moveToFirst();
		int count = countCursor.getInt(0);
		countCursor.close();
		return count;
	}

	/**
	 * Creates Grammar table
	 */
	public void createTable() {
		SQLiteDatabase db = GrammarDAO.getDb();
		db.execSQL("CREATE TABLE if not exists " + GrammarDAO.TABLE_NAME
				+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ GrammarDAO.LEVEL + " INTEGER, " + GrammarDAO.RULE + " TEXT, "
				+ GrammarDAO.DESC_ENG + " TEXT, " + GrammarDAO.DESC_RUS
				+ " TEXT, " + GrammarDAO.PERCENTAGE + " REAL, "
				+ GrammarDAO.LASTVIEW + " DATETIME," + GrammarDAO.SHOWNTIMES
				+ " INTEGER, " + GrammarDAO.COLOR + " TEXT); ");
	}

	/**
	 * Drops Grammar table
	 */
	public void dropTable() {
		GrammarDAO.getDb().execSQL(
				"DROP TABLE if exists " + GrammarDAO.TABLE_NAME + ";");
	}

	/**
	 * Gets list of all Grammar in db of target level
	 * 
	 * @param cr
	 *            - content resolver to database
	 * @return grammarRulesList list of all grammar rules in the table
	 */
	@SuppressLint("NewApi")
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
		ArrayList<GrammarRule> grammarRulesList = new ArrayList<GrammarRule>();
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

			grammarRulesList.add(new GrammarRule(rule, level, descEng, descRus,
					percentage, lastview, showntimes, color, ge));

			c.moveToNext();
		}
		c.close();
		return grammarRulesList;
	}

	/**
	 * Inserts all entries divided by tabs from assets file
	 * 
	 * @param level
	 *            level of target entries
	 * @param filepath
	 *            path to assets file
	 * @param assetManager
	 *            assetManager from activity context
	 * @param cr
	 *            content resolver to database
	 */
	public void bulkInsertFromCSV(String filepath, int level,
			AssetManager assetManager, ContentResolver cr) {
		BufferedReader reader = null;
		GrammarRule g = new GrammarRule();
		try {
			reader = new BufferedReader(new InputStreamReader(
					assetManager.open(filepath)));
			// do reading, usually loop until end of file reading
			String mLine;
			boolean isFirst = true;
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
						g = new GrammarRule(s[0].trim(), level, s[1].trim(),
								s[2].trim(), 0, "", 0, color.trim(),
								new ArrayList<GrammarExample>());
						isFirst = false;
					} else {
						String[] s = mLine.split("\\t");
						GrammarExample ge = new GrammarExample(s[0].trim()
								+ "\\t" + s[1].trim() + "\\t" + s[2].trim(),
								s[3].trim(), s[4].trim(), s[5].trim());
						g.examples.add(ge);
							Log.i("GrammarService bulkInsertFromCSV: ",
									g.getRule() + " incerted. ");
					}
				} else {
					this.insert(g, cr);
					Log.i("GrammarService bulkInsertFromCSV: ", g.getRule()
							+ " incerted. ");
					g = new GrammarRule();
					isFirst = true;
				}
			}
		} catch (IOException e) {
			Log.e("GrammarService", e.getMessage() + " " + e.getCause());
		}
		this.insert(g, cr);
	}

	// returns Set with stated size of unique random entries from current
	// dictionary
		public Set<GrammarRule> getRandomEntries(int size) {
			Set<GrammarRule> random = new HashSet<GrammarRule>();
			Random rn = new Random();

			while (random.size() <= size) {
				int i = rn.nextInt(entries.size());
				if(entries.get(i).getLearnedPercentage()<1){
					random.add(entries.get(i));
				}
			}
			return random;
		}
	
	/**
	 * Returns number of all grammar rules to get id of last rule for inserting
	 * in GrammarRulesExamples table
	 * 
	 * @param cr
	 *            content resolver to database
	 * @return num number of all counter words in table
	 */
	public int getNumberOfGrammarInLevel(int level,
			ContentResolver contentResolver) {
		Cursor countCursor = contentResolver.query(GrammarDAO.CONTENT_URI,
				new String[] { "count(*) AS count" }, GrammarDAO.LEVEL + "="
						+ level + "", null, null);
		countCursor.moveToFirst();
		int num = countCursor.getInt(0);
		countCursor.close();
		return num;
	}
}
