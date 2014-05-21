package ua.hneu.languagetrainer.masterdetailflow;

import ua.hneu.edu.languagetrainer.R;
import ua.hneu.languagetrainer.App;
import ua.hneu.languagetrainer.pages.vocabulary.WordIntroductionActivity;
import ua.hneu.languagetrainer.service.VocabularyService;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

public class VocabularyActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_detail);
		Log.i("ItemDetailActivity", "ItemDetailActivity.onCreate()");
		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);

		if (savedInstanceState == null) {
			Bundle arguments = new Bundle();
			arguments.putString(
					VocabularyActivityFragment.ARG_ITEM_ID,
					getIntent().getStringExtra(
							VocabularyActivityFragment.ARG_ITEM_ID));
			VocabularyActivityFragment fragment = new VocabularyActivityFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.add(R.id.item_detail_container, fragment).commit();
		}

	}

	public void onClickPracticeVocabulary(View v) {
		// load vocabulary
		App.vocabularyDictionary = VocabularyService.createCurrentDictionary(
				App.userInfo.getLevel(), App.numberOfEntriesInCurrentDict,
				App.cr);
		Intent intent = new Intent(this, WordIntroductionActivity.class);
		startActivity(intent);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpTo(this,
					new Intent(this, MenuListActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
