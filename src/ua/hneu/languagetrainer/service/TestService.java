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
import ua.hneu.languagetrainer.db.dao.VocabularyDAO;
import ua.hneu.languagetrainer.model.tests.Answer;
import ua.hneu.languagetrainer.model.tests.Question;
import ua.hneu.languagetrainer.model.tests.Test;
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
		values.put(TestDAO.TYPE, t.getTestType().toString());
		values.put(TestDAO.LEVEL, t.getLevel());
		cr.insert(TestDAO.CONTENT_URI, values);
	}

	public void update(Test t, ContentResolver cr) {
		ContentValues values = new ContentValues();
		values.put(TestDAO.TYPE, t.getTestType().toString());
		values.put(TestDAO.LEVEL, t.getLevel());
		cr.update(TestDAO.CONTENT_URI, values, "_ID=" + t.getId(), null);
	}

	public void emptyTable() {
		TestDAO.getDb().execSQL("delete from " + TestDAO.TABLE_NAME);
	}

	public void createTable() {
		SQLiteDatabase db = TestDAO.getDb();
		db.execSQL("CREATE TABLE " + TestDAO.TABLE_NAME
				+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + TestDAO.LEVEL
				+ " INTEGER, " + TestDAO.TYPE + " TEXT);");
	}

	public void dropTable() {
		TestDAO.getDb().execSQL("DROP TABLE " + TestDAO.TABLE_NAME + ";");
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
				}
			}
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
			String type = test.getAttribute("type");
			int level = Integer.parseInt(test.getAttribute("level"));
			Test t = new Test();
			if (type == Test.Type.LEVEL_DEF.toString())
				t.setTestType(Test.Type.LEVEL_DEF);
			else
				t.setTestType(Test.Type.MOCK_TEST);
			t.setLevel(level);

			NodeList questions = test.getElementsByTagName("question");
			for (int i = 0; i < questions.getLength(); i++) {
				Element question = (Element) questions.item(i);
				String title = question.getAttribute("title");
				String questionText = question.getAttribute("text");
				double weight = Double.parseDouble(question
						.getAttribute("weight"));
				NodeList answers = question.getChildNodes();
				ArrayList<Answer> answersList = new ArrayList<Answer>();
				for (int j = 0; j < answers.getLength(); j++) {
					Element answer = (Element) answers.item(j);
					String answerText = answer.getTextContent();
					boolean isCorrect = answer.getAttribute("isCorrect")
							.equals("1");
					answersList.add(new Answer(answerText, isCorrect));
				}
				t.addQuestion(new Question(qs.getNumberOfQuestions(cr), title,
						questionText, weight, answersList));
			}
			// Inserting in db
			insert(t, cr);

		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.i("BulkInsertFromCSV", "insetred: " + "");
	}

	public int getNumberOfTests(ContentResolver contentResolver) {
		Cursor countCursor = contentResolver.query(VocabularyDAO.CONTENT_URI,
				new String[] { "count(*) AS count" }, null + "", null, null);
		countCursor.moveToFirst();
		int count = countCursor.getInt(0);
		countCursor.close();
		return count;
	}
}