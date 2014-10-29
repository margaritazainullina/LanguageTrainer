package ua.hneu.languagetrainer.pages.counterwords;

import ua.hneu.edu.languagetrainer.R;
import ua.hneu.languagetrainer.AllCVListViewAdapter;
import ua.hneu.languagetrainer.App;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class AllCounterWords extends Activity {
	AllCVListViewAdapter adapter1;
	ListView kanjiListView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_all_counter_words);
		
		// Initialize views
		kanjiListView = (ListView) findViewById(R.id.allInfoListView);		
		adapter1 = new AllCVListViewAdapter(this, App.allCounterWordsDictionary);

		// bindings adapters to ListViews
		kanjiListView.setAdapter(adapter1);
		}
}
