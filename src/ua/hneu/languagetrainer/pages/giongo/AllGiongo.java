package ua.hneu.languagetrainer.pages.giongo;

import java.util.ArrayList;

import ua.hneu.edu.languagetrainer.R;
import ua.hneu.languagetrainer.AllGiongoListViewAdapter;
import ua.hneu.languagetrainer.AllGrammarListViewAdapter;
import ua.hneu.languagetrainer.AllVocabularyListViewAdapter;
import ua.hneu.languagetrainer.App;
import ua.hneu.languagetrainer.ListViewAdapter;
import ua.hneu.languagetrainer.model.other.GiongoDictionary;
import ua.hneu.languagetrainer.model.vocabulary.VocabularyDictionary;
import ua.hneu.languagetrainer.pages.grammar.AllGrammarExamples;
import android.app.Activity;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnCloseListener;

public class AllGiongo extends ListActivity {
	AllGiongoListViewAdapter adapter1;
	ListView kanjiListView;
	GiongoDictionary entries;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_allvocabulary);
		handleIntent(getIntent());
		showAll();
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

		SearchView searchView = (SearchView) menu.findItem(R.id.menu_search)
				.getActionView();
		if (searchView != null) {
			searchView
					.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

						public boolean onQueryTextSubmit(String submit) {
							return false;
						}

						public boolean onQueryTextChange(String change) {
							if (change.isEmpty())
								showAll();
							search(change);
							return false;
						}

					});
			searchView.setOnCloseListener(new OnCloseListener() {

				@Override
				public boolean onClose() {
					showAll();
					return false;
				}
			});
		}

		return super.onCreateOptionsMenu(menu);
	}

	public void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent(this, AllGiongoExamples.class);
		String desc = entries.get(position).getWord();
		intent.putExtra("giongo", desc);
		startActivity(intent);
	}
	
	private void handleIntent(Intent intent) {
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);
			search(query);
		}
	}
	
	private void search(String query) {
		entries = entries.search(query);
		adapter1 = new AllGiongoListViewAdapter(this, entries);
		this.setListAdapter(adapter1);
	}

	private void showAll() {
		entries = App.allGiongoDictionary;
		adapter1 = new AllGiongoListViewAdapter(this, entries);
		this.setListAdapter(adapter1);
	}
}
