package ua.hneu.languagetrainer.pages;

import ua.hneu.edu.languagetrainer.R;
import ua.hneu.languagetrainer.App;
import ua.hneu.languagetrainer.masterdetailflow.ItemListActivity;
import ua.hneu.languagetrainer.pages.test.LevelDefinitionTestActivity;
import ua.hneu.languagetrainer.service.CounterWordsService;
import ua.hneu.languagetrainer.service.GrammarExampleService;
import ua.hneu.languagetrainer.service.GrammarService;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GreetingActivity extends Activity {
	Button buttonTakeTest;
	Button buttonStart;
	int level = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_greeting);
		buttonTakeTest = (Button) findViewById(R.id.buttonTakeTest);
		buttonStart = (Button) findViewById(R.id.buttonStart);

		/*GiongoService gs = new GiongoService();
		GiongoExampleService ges = new GiongoExampleService();
		gs.dropTable();
		gs.createTable();
		ges.dropTable();
		GiongoService.startCounting(getContentResolver());
		ges.createTable();
		gs.bulkInsertFromCSV("giongo.txt", getAssets(),
				getContentResolver());*/
		
		/*CounterWordsService cws = new CounterWordsService();
		cws.dropTable();
		cws.createTable();
		cws.bulkInsertFromCSV("numbers.txt", getAssets(),getContentResolver());
		cws.bulkInsertFromCSV("people_and_things.txt", getAssets(),getContentResolver());
		cws.bulkInsertFromCSV("time_calendar.txt", getAssets(),getContentResolver());
		cws.bulkInsertFromCSV("time_calendar.txt", getAssets(),getContentResolver());
		cws.bulkInsertFromCSV("extent_freq.txt", getAssets(),getContentResolver());*/
		
		GrammarService grs = new GrammarService();
		GrammarExampleService gres = new GrammarExampleService();
		grs.dropTable();
		grs.createTable();
		GrammarService.startCounting(getContentResolver());
		gres.dropTable();
		gres.createTable();		
		grs.bulkInsertFromCSV("grammar_n5.txt", 5, getAssets(), getContentResolver());
		
	}

	public void buttonTakeTestOnClick(View v) {
		Intent intent = new Intent(this, LevelDefinitionTestActivity.class);
		startActivity(intent);
	}

	public void buttonStartOnClick(View v) {
		if (level != -1) {
			App.goToLevel(level);
			Intent intent = new Intent(this, ItemListActivity.class);
			startActivity(intent);
		}
	}

	public void radioButtonN5OnClick(View v) {
		level = 5;
	}

	public void radioButtonN4OnClick(View v) {
		level = 4;
	}

	public void radioButtonN3OnClick(View v) {
		level = 3;
	}

	public void radioButtonN2OnClick(View v) {
		level = 2;
	}

	public void radioButtonN1OnClick(View v) {
		level = 1;
	}

}
