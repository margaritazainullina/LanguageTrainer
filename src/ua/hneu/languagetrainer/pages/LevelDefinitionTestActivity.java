package ua.hneu.languagetrainer.pages;

import ua.hneu.edu.languagetrainer.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class LevelDefinitionTestActivity extends Activity {
	/*TextView levelTV;
	TextView levelRatingBar;
	Button buttonTakeTest;
	Button buttonStart;
	int level = 5;*/

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_level_definition_test);

		/*levelTV = (TextView) findViewById(R.id.learnedWordsTextView);
		levelRatingBar = (TextView) findViewById(R.id.level1TextView);
		buttonTakeTest = (Button) findViewById(R.id.buttonTakeTest);
		buttonStart = (Button) findViewById(R.id.buttonStart);*/

	}

	public void buttonTakeTestOnClick(View v) {
		// go to introduction again
		/*Intent matchWordsIntent = new Intent(this,
				WordIntroductionActivity.class);
		startActivity(matchWordsIntent);*/
	}

	

}
