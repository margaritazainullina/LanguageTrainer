package ua.hneu.languagetrainer.pages.vocabulary;

import java.util.ArrayList;

import ua.hneu.edu.languagetrainer.R;
import ua.hneu.languagetrainer.AllVocabularyListViewAdapter;
import ua.hneu.languagetrainer.App;
import ua.hneu.languagetrainer.ListViewAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class AllVocabulary extends Activity {
	AllVocabularyListViewAdapter adapter1;
	ListView kanjiListView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_allvocabulary);
		
		// Initialize views
		kanjiListView = (ListView) findViewById(R.id.allInfoListView);
		
		ArrayList<String> kanji = App.allVocabularyDictionary.getAllKanji();
				
		adapter1 = new AllVocabularyListViewAdapter(this, App.allVocabularyDictionary);

		// bindings adapters to ListViews
		kanjiListView.setAdapter(adapter1);
		}
}
