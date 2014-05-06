package ua.hneu.languagetrainer.service;

import ua.hneu.languagetrainer.db.dao.QuestionDAO;
import ua.hneu.languagetrainer.db.dao.TestDAO;
import ua.hneu.languagetrainer.db.dao.VocabularyDAO;
import ua.hneu.languagetrainer.model.tests.Answer;
import ua.hneu.languagetrainer.model.tests.Question;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class QuestionService {
	AnswerService as = new AnswerService();
	static int numberOfEnteries = 0;

	public void insert(Question q, int testId, ContentResolver cr) {
		for (Answer a : q.getAnswers()) {
			as.insert(a, numberOfEnteries, cr);
		}
		numberOfEnteries++;

		ContentValues values = new ContentValues();
		values.put(QuestionDAO.TITLE, q.getTitle());
		values.put(QuestionDAO.TEXT, q.getText());
		values.put(QuestionDAO.WEIGHT, q.getWeight());
		values.put(QuestionDAO.T_ID, testId);
		cr.insert(QuestionDAO.CONTENT_URI, values);
	}

	public void update(Question q, ContentResolver cr) {
		ContentValues values = new ContentValues();
		values.put(QuestionDAO.TITLE, q.getTitle());
		values.put(QuestionDAO.TEXT, q.getText());
		cr.update(QuestionDAO.CONTENT_URI, values, "_ID=" + q.getId(), null);
	}

	public void emptyTable() {
		QuestionDAO.getDb().execSQL("delete from " + QuestionDAO.TABLE_NAME);
	}

	public void createTable() {
		SQLiteDatabase db = QuestionDAO.getDb();
		db.execSQL("CREATE TABLE " + QuestionDAO.TABLE_NAME
				+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ QuestionDAO.TITLE + " TEXT, " + QuestionDAO.TEXT + " TEXT, "
				+ QuestionDAO.WEIGHT + " DOUBLE," + QuestionDAO.T_ID
				+ " INTEGER, " + "FOREIGN KEY(" + QuestionDAO.T_ID
				+ ") REFERENCES " + TestDAO.TABLE_NAME + "(" + TestDAO.ID
				+ "));");
	}

	public void dropTable() {
		QuestionDAO.getDb().execSQL(
				"DROP TABLE " + QuestionDAO.TABLE_NAME + ";");
	}

	public static int getNumberOfQuestions(ContentResolver contentResolver) {
		Cursor countCursor = contentResolver.query(QuestionDAO.CONTENT_URI,
				new String[] { "count(*) AS count" }, null + "", null, null);
		countCursor.moveToFirst();
		int count = countCursor.getInt(0);
		countCursor.close();
		return count;
	}

	public static void startCounting(ContentResolver contentResolver) {
		numberOfEnteries = getNumberOfQuestions(contentResolver)+1;
	}
}