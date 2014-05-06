package ua.hneu.languagetrainer.service;

import ua.hneu.languagetrainer.db.dao.AnswerDAO;
import ua.hneu.languagetrainer.db.dao.QuestionDAO;
import ua.hneu.languagetrainer.db.dao.VocabularyDAO;
import ua.hneu.languagetrainer.model.tests.Answer;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class AnswerService {

	public void insert(Answer a, int questionId, ContentResolver cr) {
		ContentValues values = new ContentValues();
		values.put(AnswerDAO.ISCORRECT, a.isCorrect());
		values.put(AnswerDAO.TEXT, a.getText());
		values.put(AnswerDAO.Q_ID, questionId);
		cr.insert(AnswerDAO.CONTENT_URI, values);
	}

	public void emptyTable() {
		AnswerDAO.getDb().execSQL("delete from " + AnswerDAO.TABLE_NAME);
	}

	public void createTable() {
		SQLiteDatabase db = AnswerDAO.getDb();
		db.execSQL("CREATE TABLE " + AnswerDAO.TABLE_NAME
				+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ AnswerDAO.ISCORRECT + " INTEGER, " + AnswerDAO.Q_ID
				+ " INTEGER, " + AnswerDAO.TEXT + " TEXT, " + "FOREIGN KEY("
				+ AnswerDAO.Q_ID + ") REFERENCES " + QuestionDAO.TABLE_NAME
				+ "(" + QuestionDAO.ID + "));");
	}

	public void dropTable() {
		AnswerDAO.getDb().execSQL("DROP TABLE " + AnswerDAO.TABLE_NAME + ";");
	}
}
