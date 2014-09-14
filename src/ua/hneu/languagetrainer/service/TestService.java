package ua.hneu.languagetrainer.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import ua.hneu.languagetrainer.db.dao.TestDAO;
import ua.hneu.languagetrainer.model.tests.Answer;
import ua.hneu.languagetrainer.model.tests.Question;
import ua.hneu.languagetrainer.model.tests.Test;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TestService {
	QuestionService qs = new QuestionService();
	static int numberOfEnteries = 0;

	/**
	 * Inserts an Test to database
	 * 
	 * @param t
	 *            Test instance with answers to insert
	 * @param cr
	 *            content resolver to database
	 */
	public void insert(Test t, ContentResolver cr) {
		for (Question q : t.getQuestions()) {
			qs.insert(q, numberOfEnteries, cr);
		}
		numberOfEnteries++;
		ContentValues values = new ContentValues();
		values.put(TestDAO.NAME, t.getTestName().toString());
		values.put(TestDAO.LEVEL, t.getLevel());
		values.put(TestDAO.POINTS1, 0);
		values.put(TestDAO.POINTS2, 0);
		values.put(TestDAO.POINTS3, 0);
		cr.insert(TestDAO.CONTENT_URI, values);
	}

	/**
	 * Updates an Test to database
	 * 
	 * @param t
	 *            Test instance with answers to insert
	 * @param cr
	 *            content resolver to database
	 */
	public void update(Test t, ContentResolver cr) {
		ContentValues values = new ContentValues();
		values.put(TestDAO.NAME, t.getTestName().toString());
		values.put(TestDAO.LEVEL, t.getLevel());
		values.put(TestDAO.POINTS1, t.getPointsPart1());
		values.put(TestDAO.POINTS2, t.getPointsPart2());
		values.put(TestDAO.POINTS3, t.getPointsPart3());
		cr.update(TestDAO.CONTENT_URI, values, "NAME=" + "\"" + t.getName()
				+ "\"", null);
	}

	/**
	 * Deletes all entries from Test table
	 */
	public void emptyTable() {
		TestDAO.getDb().execSQL("delete from " + TestDAO.TABLE_NAME);
	}

	/**
	 * Returns number of entries in Test table
	 * 
	 * @param cr
	 *            content resolver to database
	 * @return num number of entries
	 */
	public static int getNumberOfQuestions(ContentResolver contentResolver) {
		Cursor countCursor = contentResolver.query(TestDAO.CONTENT_URI,
				new String[] { "count(*) AS count" }, null + "", null, null);
		countCursor.moveToFirst();
		int num = countCursor.getInt(0);
		countCursor.close();
		return num;
	}

	/**
	 * A stub to find out last id in Test table
	 * 
	 * @param cr
	 *            content resolver to database
	 */
	public static void startCounting(ContentResolver contentResolver) {
		numberOfEnteries = getNumberOfQuestions(contentResolver) + 1;
	}

	/**
	 * Creates Test table
	 */
	public void createTable() {
		SQLiteDatabase db = TestDAO.getDb();
		db.execSQL("CREATE TABLE if not exists " + TestDAO.TABLE_NAME
				+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + TestDAO.LEVEL
				+ " INTEGER, " + TestDAO.NAME + " TEXT, " + TestDAO.POINTS1
				+ " INTEGER, " + TestDAO.POINTS2 + " INTEGER, "
				+ TestDAO.POINTS3 + " INTEGER);");
	}

	/**
	 * Drops Test table
	 */
	public void dropTable() {
		TestDAO.getDb().execSQL(
				"DROP TABLE if exists " + TestDAO.TABLE_NAME + ";");
	}

	/**
	 * Inserts all test, question and answer entries from assets xml file
	 * 
	 * @param filepath
	 *            path to assets file
	 * @param assetManager
	 *            assetManager from activity context
	 * @param cr
	 *            content resolver to database
	 */
	public void insertFromXml(String filepath, AssetManager assetManager,
			ContentResolver cr) {
		StringBuilder xml = new StringBuilder();
		// reading from assets xml file
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(
					assetManager.open(filepath)));
			// do reading, usually loop until end of file reading

			String mLine;
			while ((mLine = reader.readLine()) != null) {
				if (mLine != null) {
					xml.append(mLine);
					// System.out.println(mLine);
				}
			}
			// System.out.println(xml.toString());
		} catch (IOException e) {
			Log.e("TestService", e.getMessage() + " " + e.getCause());
		}
		// parsing xml
		try {
			// make a document from string
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new InputSource(new StringReader(xml
					.toString())));

			doc.getDocumentElement().normalize();

			Element test = doc.getDocumentElement();
			String testName = test.getAttribute("name");
			int level = Integer.parseInt(test.getAttribute("level"));
			Test t = new Test();
			t.setName(testName);
			t.setLevel(level);
			// parse sections element
			NodeList sections = test.getElementsByTagName("section");
			for (int k = 0; k < sections.getLength(); k++) {
				Element section = (Element) sections.item(k);
				String sectionName = section.getAttribute("name");
				// parse tasks element
				NodeList tasks = section.getElementsByTagName("task");
				for (int w = 0; w < tasks.getLength(); w++) {
					Element task = (Element) tasks.item(w);
					String taskCaption = task.getAttribute("caption");
					// parse question element
					NodeList questions = task.getElementsByTagName("question");
					for (int i = 0; i < questions.getLength(); i++) {
						Element question = (Element) questions.item(i);
						String title = question.getAttribute("title");
						String questionText = question.getAttribute("text");
						double weight = Double.parseDouble(question
								.getAttribute("weight"));
						String imgRef = question.getAttribute("imgRef");
						String audioRef = question.getAttribute("audioRef");
						// parse answers element
						NodeList answers = question.getChildNodes();
						ArrayList<Answer> answersList = new ArrayList<Answer>();
						for (int j = 0; j < answers.getLength(); j++) {
							Element answer = (Element) answers.item(j);
							String answerText = answer.getTextContent();
							boolean isCorrect = answer
									.getAttribute("isCorrect").equals("1");
							answersList.add(new Answer(answerText, isCorrect));
						}
						// creating Test instance with all questions and answers
						t.addQuestion(new Question(QuestionService
								.getNumberOfQuestions(cr), sectionName,
								taskCaption, title, questionText, weight,
								answersList, imgRef, audioRef));
					}
				}
			}
			// Inserting in db
			insert(t, cr);

		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.i("BulkInsertFromCSV", "insetred: " + "");
	}

	/**
	 * Returns number of all tests to get id of last tests for inserting in
	 * examples table
	 * 
	 * @param cr
	 *            content resolver to database
	 * @return num number of all tests in table
	 */
	public int getNumberOfTests(ContentResolver contentResolver) {
		Cursor countCursor = contentResolver.query(TestDAO.CONTENT_URI,
				new String[] { "count(*) AS count" }, null + "", null, null);
		countCursor.moveToFirst();
		int num = countCursor.getInt(0);
		countCursor.close();
		return num;
	}

	/**
	 * Gets list of all tests, containing questions and answers
	 * 
	 * @param section
	 *            name of section of counter words in English or Russian
	 *            (depends on locale)
	 * @param cr
	 *            content resolver to database
	 * @return testList
	 */
	@SuppressLint("NewApi")
	public Test getTestByName(String name, ContentResolver cr) {
		String[] col = { TestDAO.ID, TestDAO.LEVEL, TestDAO.NAME };
		Cursor c = cr.query(TestDAO.CONTENT_URI, col, TestDAO.NAME + "=\""
				+ name + "\"", null, null, null);
		c.moveToFirst();
		int id = 0;
		int level = 0;
		ArrayList<Question> q;
		Test testList = null;
		while (!c.isAfterLast()) {
			id = c.getInt(0);
			level = c.getInt(1);
			q = qs.getQuestionsByTestId(id, cr);
			testList = new Test(q, level, name);
			c.moveToNext();
		}
		c.close();
		return testList;
	}

	/**
	 * Returns true if all tests of current level are passed
	 * 
	 * @param level
	 *            target level
	 * @param cr
	 *            content resolver to database
	 */
	public boolean isAllTestsPassed(ContentResolver cr, int level) {
		ArrayList<Test> tests = getTestsByLevel(level, cr);
		for (Test test : tests) {
			if (!test.isPassed())
				return false;
		}
		return true;
	}

	/**
	 * Gets list of test of a level
	 * 
	 * @param level
	 *            target level
	 * @param cr
	 *            content resolver to database
	 * @return testsList
	 */
	@SuppressLint("NewApi")
	private ArrayList<Test> getTestsByLevel(int level, ContentResolver cr) {
		ArrayList<Test> testsList = new ArrayList<Test>();
		String[] col = { TestDAO.ID, TestDAO.LEVEL, TestDAO.NAME };
		Cursor c = cr.query(TestDAO.CONTENT_URI, col, TestDAO.LEVEL + "="
				+ level, null, null, null);
		c.moveToFirst();
		int id = 0;
		String name = "";
		ArrayList<Question> q;
		while (!c.isAfterLast()) {
			id = c.getInt(0);
			name = c.getString(3);
			q = qs.getQuestionsByTestId(id, cr);
			testsList.add(new Test(q, level, name));
			c.moveToNext();
		}
		c.close();
		return testsList;
	}

	/**
	 * Gets HashMap with key - test name, value - array with points of sections
	 * 
	 * @param level
	 *            target level
	 * @param cr
	 *            content resolver to database
	 * @return testsList
	 */
	@SuppressLint("NewApi")
	public HashMap<String, int[]> getTestNamesAndPoints(ContentResolver cr,
			int level) {
		HashMap<String, int[]> hm = new HashMap<String, int[]>();
		String[] col = { TestDAO.NAME, TestDAO.LEVEL, TestDAO.POINTS1,
				TestDAO.POINTS2, TestDAO.POINTS3 };
		Cursor c = cr.query(TestDAO.CONTENT_URI, col, TestDAO.LEVEL + "="
				+ level, null, null, null);
		c.moveToFirst();
		String name;
		int p1;
		int p2;
		int p3;
		while (!c.isAfterLast()) {
			name = c.getString(0);
			p1 = c.getInt(2);
			p2 = c.getInt(3);
			p3 = c.getInt(4);
			hm.put(name, new int[] { p1, p2, p3 });
			c.moveToNext();
		}
		c.close();
		return hm;
	}
}
