package ua.hneu.languagetrainer.masterdetailflow;

import ua.hneu.edu.languagetrainer.R;
import ua.hneu.languagetrainer.App;
import ua.hneu.languagetrainer.pages.grammar.GrammarIntroductionActivity;
import ua.hneu.languagetrainer.service.GrammarService;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

public class GrammarActivity extends FragmentActivity {

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
					GrammarActivityFragment.ARG_ITEM_ID,
					getIntent().getStringExtra(
							GrammarActivityFragment.ARG_ITEM_ID));
			GrammarActivityFragment fragment = new GrammarActivityFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.add(R.id.item_detail_container, fragment).commit();
		}
	}

	public void onClickPracticeGrammar(View v) {
		// load grammar
		App.grammarDictionary = GrammarService.createCurrentDictionary(
				App.userInfo.getLevel(), App.numberOfEntriesInCurrentDict,
				App.cr);
		Intent intent = new Intent(this, GrammarIntroductionActivity.class);
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
