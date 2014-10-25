package ua.hneu.languagetrainer.pages.grammar;

import java.util.ArrayList;

import ua.hneu.edu.languagetrainer.R;
import ua.hneu.languagetrainer.AllGrammarListViewAdapter;
import ua.hneu.languagetrainer.AllVocabularyListViewAdapter;
import ua.hneu.languagetrainer.App;
import ua.hneu.languagetrainer.ListViewAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class AllGrammar extends Activity {
	AllGrammarListViewAdapter adapter1;
	ListView kanjiListView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_allgrammar);
		
		// Initialize views
		kanjiListView = (ListView) findViewById(R.id.allInfoListView);		
		adapter1 = new AllGrammarListViewAdapter(this, App.allGrammarDictionary);

		// bindings adapters to ListViews
		kanjiListView.setAdapter(adapter1);
		}
}
