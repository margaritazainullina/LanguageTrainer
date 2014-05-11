package ua.hneu.languagetrainer.db.dao;
import java.util.HashMap;

import ua.hneu.languagetrainer.db.DictionaryDbHelper;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;

public class VocabularyDAO extends ContentProvider {

	public static String TABLE_NAME = "vocabulary";
	public static final String ID = "_id";
	public static final String KANJI = "kanji";
	public static final String LEVEL = "level";
	public static final String TRANSCRIPTION = "transcription";
	public static final String ROMAJI = "romaji";
	public static final String TRANSLATIONS = "translations";
	public static final String TRANSLATIONS_RUS = "translations_rus";
	public static final String PERCENTAGE = "percentage";
	public static final String SHOWNTIMES = "showntimes";
	public static final String LASTVIEW = "lastview";
	public static final String COLOR = "color";

	public static final Uri CONTENT_URI = Uri
			.parse("content://ua.edu.hneu.languagetrainer.db.vocabularyprovider/dictionary");
	public static final int URI_CODE = 1;
	public static final int URI_CODE_ID = 2;

	private static final UriMatcher mUriMatcher;

	private static HashMap<String, String> mContactMap;

	static SQLiteDatabase db;

	static {
		mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		mUriMatcher.addURI("ua.hneu.languagetrainer.db", TABLE_NAME, URI_CODE);
		mUriMatcher.addURI("ua.hneu.languagetrainer.db", TABLE_NAME + "/#",
				URI_CODE_ID);

		mContactMap = new HashMap<String, String>();
		mContactMap.put(DictionaryDbHelper._ID, DictionaryDbHelper._ID);
		mContactMap.put(KANJI, KANJI);
		mContactMap.put(LEVEL, LEVEL);
		mContactMap.put(TRANSCRIPTION, TRANSCRIPTION);
		mContactMap.put(ROMAJI, ROMAJI);
		mContactMap.put(TRANSLATIONS, TRANSLATIONS);
		mContactMap.put(TRANSLATIONS_RUS, TRANSLATIONS_RUS);
		mContactMap.put(PERCENTAGE, PERCENTAGE);
		mContactMap.put(LASTVIEW, LASTVIEW);
		mContactMap.put(SHOWNTIMES, SHOWNTIMES);
		mContactMap.put(COLOR, COLOR);
	}

	public String getDbName() {
		return (DictionaryDbHelper.DB_NAME);
	}

	public static SQLiteDatabase getDb() {
		return db;
	}

	@Override
	public boolean onCreate() {
		db = (new DictionaryDbHelper(getContext())).getWritableDatabase();
		return (db == null) ? false : true;
	}
	
	@Override
	public Cursor query(Uri url, String[] projection, String selection,
			String[] selectionArgs, String sort) {

		String orderBy;
		if (TextUtils.isEmpty(sort)) {
			orderBy = ID;
		} else {
			orderBy = sort;
		}

		Cursor c = db.query(TABLE_NAME, projection, selection, selectionArgs,
				null, null, orderBy);
		c.setNotificationUri(getContext().getContentResolver(), url);
		return c;
	}

	@Override
	public Uri insert(Uri url, ContentValues inValues) {

		ContentValues values = new ContentValues(inValues);

		long rowId = db.insert(TABLE_NAME, KANJI, values);
		if (rowId > 0) {
			Uri uri = ContentUris.withAppendedId(CONTENT_URI, rowId);
			getContext().getContentResolver().notifyChange(uri, null);
			return uri;
		} else {
			throw new SQLException("Failed to insert row into " + url);
		}
	}

	@Override
	public int delete(Uri url, String where, String[] whereArgs) {
		int retVal = db.delete(TABLE_NAME, where, whereArgs);
		getContext().getContentResolver().notifyChange(url, null);
		return retVal;
	}

	@Override
	public int update(Uri url, ContentValues values, String where,
			String[] whereArgs) {
		int retVal = db.update(TABLE_NAME, values, where, whereArgs);
		getContext().getContentResolver().notifyChange(url, null);
		return retVal;
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

}
