package ua.hneu.languagetrainer.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;

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

	public void insert(Test t, ContentResolver cr) {
		int n = getNumberOfTests(cr) + 1;
		for (Question q : t.getQuestions()) {
			qs.insert(q, n, cr);
		}

		ContentValues values = new ContentValues();
		values.put(TestDAO.NAME, t.getTestName().toString());
		values.put(TestDAO.LEVEL, t.getLevel());
		cr.insert(TestDAO.CONTENT_URI, values);
	}

	public void update(Test t, ContentResolver cr) {
		ContentValues values = new ContentValues();
		values.put(TestDAO.NAME, t.getTestName().toString());
		values.put(TestDAO.LEVEL, t.getLevel());
		cr.update(TestDAO.CONTENT_URI, values, "_ID=" + t.getId(), null);
	}

	public void emptyTable() {
		TestDAO.getDb().execSQL("delete from " + TestDAO.TABLE_NAME);
	}

	public void createTable() {
		SQLiteDatabase db = TestDAO.getDb();
		db.execSQL("CREATE TABLE if not exists " + TestDAO.TABLE_NAME
				+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + TestDAO.LEVEL
				+ " INTEGER, " + TestDAO.NAME + " TEXT);");
	}

	public void dropTable() {
		TestDAO.getDb().execSQL(
				"DROP TABLE if exists " + TestDAO.TABLE_NAME + ";");
	}

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
					NodeList questions = test.getElementsByTagName("question");
					for (int i = 0; i < questions.getLength(); i++) {
						Element question = (Element) questions.item(i);
						String title = question.getAttribute("title");
						String questionText = question.getAttribute("text");
						double weight = Double.parseDouble(question
								.getAttribute("weight"));
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
								answersList));

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

	public int getNumberOfTests(ContentResolver contentResolver) {
		Cursor countCursor = contentResolver.query(TestDAO.CONTENT_URI,
				new String[] { "count(*) AS count" }, null + "", null, null);
		countCursor.moveToFirst();
		int count = countCursor.getInt(0);
		countCursor.close();
		return count;
	}

	@SuppressLint("NewApi")
	public Test getTestByName(String name, ContentResolver cr) {
		String[] col = { TestDAO.ID, TestDAO.LEVEL, TestDAO.NAME };
		Cursor c = cr.query(TestDAO.CONTENT_URI, col, TestDAO.NAME + "=\""
				+ name + "\"", null, null, null);
		c.moveToFirst();
		int id = 0;
		int level = 0;
		ArrayList<Question> q;
		Test t = null;
		while (!c.isAfterLast()) {
			id = c.getInt(0);
			level = c.getInt(1);
			q = qs.getQuestionsByTestId(id, cr);
			t = new Test(q, level, name);
			c.moveToNext();
		}
		return t;
	}
}
