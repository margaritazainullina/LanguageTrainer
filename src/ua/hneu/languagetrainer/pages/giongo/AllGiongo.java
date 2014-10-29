package ua.hneu.languagetrainer.pages.giongo;

import java.util.ArrayList;

import ua.hneu.edu.languagetrainer.R;
import ua.hneu.languagetrainer.AllGiongoListViewAdapter;
import ua.hneu.languagetrainer.AllGrammarListViewAdapter;
import ua.hneu.languagetrainer.AllVocabularyListViewAdapter;
import ua.hneu.languagetrainer.App;
import ua.hneu.languagetrainer.ListViewAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class AllGiongo extends Activity {
	AllGiongoListViewAdapter adapter1;
	ListView kanjiListView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_allgiongo);
		
		// Initialize views
		kanjiListView = (ListView) findViewById(R.id.allInfoListView);		
		adapter1 = new AllGiongoListViewAdapter(this, App.allGiongoDictionary);

		// bindings adapters to ListViews
		kanjiListView.setAdapter(adapter1);
		}
}
