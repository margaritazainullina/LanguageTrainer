package ua.hneu.languagetrainer.pages;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import ua.hneu.edu.languagetrainer.R;
import ua.hneu.languagetrainer.App;
import ua.hneu.languagetrainer.masterdetailflow.ItemListActivity;
import ua.hneu.languagetrainer.model.other.Giongo;
import ua.hneu.languagetrainer.pages.test.LevelDefinitionTestActivity;
import ua.hneu.languagetrainer.service.AnswerService;
import ua.hneu.languagetrainer.service.GiongoExampleService;
import ua.hneu.languagetrainer.service.GiongoService;
import ua.hneu.languagetrainer.service.QuestionService;
import ua.hneu.languagetrainer.service.TestService;
import android.app.Activity;
import android.content.Context;
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

		GiongoService gs = new GiongoService();
		GiongoExampleService ges = new GiongoExampleService();
		gs.dropTable();
		gs.createTable();
		ges.dropTable();
		GiongoService.startCounting(getContentResolver());
		ges.createTable();
		gs.bulkInsertFromCSV("giongo.txt", getAssets(),
				getContentResolver());
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
