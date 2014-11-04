package ua.hneu.languagetrainer.pages.vocabulary;

import java.util.ArrayList;

import ua.hneu.edu.languagetrainer.R;
import ua.hneu.languagetrainer.AllVocabularyListViewAdapter;
import ua.hneu.languagetrainer.App;
import ua.hneu.languagetrainer.ListViewAdapter;
import ua.hneu.languagetrainer.masterdetailflow.VocabularyActivity;
import ua.hneu.languagetrainer.model.vocabulary.VocabularyDictionary;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnActionExpandListener;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnCloseListener;

public class AllVocabulary extends ListActivity {
	AllVocabularyListViewAdapter adapter1;
	ListView kanjiListView;
	VocabularyDictionary kanji;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_allvocabulary);

		handleIntent(getIntent());
		showAll();

		// TODO all like this
		/*
		 * ActionBar actionBar = getActionBar();
		 * actionBar.setDisplayHomeAsUpEnabled(true); actionBar.
		 */
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
							doMySearch(change);
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
		// TODO: call detail activity for clicked entry
	}

	private void doMySearch(String query) {
		kanji = kanji.search(query);
		adapter1 = new AllVocabularyListViewAdapter(this, kanji);
		this.setListAdapter(adapter1);
	}

	private void showAll() {
		kanji = App.allVocabularyDictionary;
		adapter1 = new AllVocabularyListViewAdapter(this, kanji);
		this.setListAdapter(adapter1);
	}

	private void handleIntent(Intent intent) {
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);
			doMySearch(query);
		}
	}
}