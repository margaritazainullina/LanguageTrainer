package ua.hneu.languagetrainer.pages.grammar;

import java.util.ArrayList;

import ua.hneu.edu.languagetrainer.R;
import ua.hneu.languagetrainer.AllCVListViewAdapter;
import ua.hneu.languagetrainer.AllGrammarListViewAdapter;
import ua.hneu.languagetrainer.AllVocabularyListViewAdapter;
import ua.hneu.languagetrainer.App;
import ua.hneu.languagetrainer.ExamplesListViewAdapter;
import ua.hneu.languagetrainer.ListViewAdapter;
import ua.hneu.languagetrainer.model.grammar.GrammarDictionary;
import ua.hneu.languagetrainer.model.grammar.GrammarRule;
import ua.hneu.languagetrainer.model.other.GiongoDictionary;
import android.app.Activity;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnCloseListener;

public class AllGrammarExamples extends ListActivity {
	ExamplesListViewAdapter adapter;
	ListView kanjiListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_allvocabulary);

		Bundle extras = getIntent().getExtras();
		GrammarRule gr = null;

		if (extras != null) {
			String rule = extras.get("rule").toString();
			gr = App.allGrammarDictionary.getByRule(rule);
		}

		//TODO: custom adapter!
		adapter = new ExamplesListViewAdapter(this,
				gr.getAllExamplesText(), gr.getAllExamplesRomaji(),
				gr.getAllTranslations(), gr.getIntColor());
		this.setListAdapter(adapter);
	}

	@Override
	public boolean onSearchRequested() {
		return super.onSearchRequested();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.searchview_in_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

}
