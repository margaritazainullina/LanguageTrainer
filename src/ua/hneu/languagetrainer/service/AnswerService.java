package ua.hneu.languagetrainer.service;

import java.util.ArrayList;

import ua.hneu.languagetrainer.db.dao.AnswerDAO;
import ua.hneu.languagetrainer.db.dao.QuestionDAO;
import ua.hneu.languagetrainer.model.tests.Answer;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

@SuppressLint("NewApi")
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
		db.execSQL("CREATE TABLE if not exists " + AnswerDAO.TABLE_NAME
				+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ AnswerDAO.ISCORRECT + " INTEGER, " + AnswerDAO.Q_ID
				+ " INTEGER, " + AnswerDAO.TEXT + " TEXT, " + "FOREIGN KEY("
				+ AnswerDAO.Q_ID + ") REFERENCES " + QuestionDAO.TABLE_NAME
				+ "(" + QuestionDAO.ID + "));");
	}

	public void dropTable() {
		AnswerDAO.getDb().execSQL("DROP TABLE if exists " + AnswerDAO.TABLE_NAME + ";");
	}

	public ArrayList<Answer> getAswersByQuestionId(int questionId,
			ContentResolver cr) {
		String[] col = { AnswerDAO.Q_ID, AnswerDAO.TEXT, AnswerDAO.ISCORRECT };
		Cursor c = cr.query(AnswerDAO.CONTENT_URI, col, AnswerDAO.Q_ID + "="
				+ questionId, null, null, null);
		c.moveToFirst();
		String text = "";
		boolean isCorrect = false;
		ArrayList<Answer> a = new ArrayList<Answer>();
		while (!c.isAfterLast()) {
			text = c.getString(1);
			isCorrect = c.getInt(2) == 1;
			a.add(new Answer(text, isCorrect));
			c.moveToNext();
		}
		c.close();
		return a;
	}
}
