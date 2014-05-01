package ua.hneu.languagetrainer.pages.vocabulary;

import ua.hneu.edu.languagetrainer.R;
import ua.hneu.languagetrainer.App;
import ua.hneu.languagetrainer.model.User;
import ua.hneu.languagetrainer.model.vocabulary.DictionaryEntry;
import ua.hneu.languagetrainer.model.vocabulary.WordDictionary;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class ResultActivity extends Activity {
	TextView learnedWordsTextView;
	TextView recommendationsTextView;
	TextView sessionPercentageTextView;
	TextView totalPercentageTextView;
	TextView cautionTextView;
	TextView mistakesTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vocabulary_result);

		learnedWordsTextView = (TextView) findViewById(R.id.learnedWordsTextView);
		totalPercentageTextView = (TextView) findViewById(R.id.totalPercentageTextView);
		recommendationsTextView = (TextView) findViewById(R.id.recommendationsTextView);
		sessionPercentageTextView = (TextView) findViewById(R.id.sessionPercentageTextView);
		cautionTextView = (TextView) findViewById(R.id.cautionTextView);
		mistakesTextView = (TextView) findViewById(R.id.mistakesTextView);

		// information about learned words
		int numberOfLearnedWords = 0;
		StringBuffer sb = new StringBuffer();
		for (DictionaryEntry entry : App.vp.getLearnedWords().getEntries()) {
			if (entry.getLearnedPercentage() >= 1) {
				numberOfLearnedWords++;
				if (entry.getKanji() != "")
					sb.append(entry.getKanji());
				else
					sb.append(entry.getTranscription());
				sb.append(", ");
			}
		}
		sb.delete(sb.length() - 3, sb.length() - 1);
		StringBuffer sb1 = new StringBuffer();
		if (numberOfLearnedWords == 0)
			sb1.append("You haven't learned any word :(");
		else
			sb1.append("You've learned ");
		sb1.append(numberOfLearnedWords);
		if (numberOfLearnedWords == 1)
			sb1.append(" word:\n");
		else
			sb1.append(" words:\n");
		sb1.append(sb);
		learnedWordsTextView.setText(sb1);

		// information about session result
		int correct = App.vp.getNumberOfCorrectAnswersInMatching()
				+ App.vp.getNumberOfCorrectAnswersInTranslation();
		int incorrect = App.vp.getNumberOfIncorrectAnswersInMatching()
				+ App.vp.getNumberOfIncorrectAnswersInTranslation();
		double success = (double) (correct - incorrect)
				/ (double) (correct + incorrect);
		double percentage = Math.round((double) correct
				/ (double) (correct + incorrect) * 100);

		if (success > 0.9)
			sessionPercentageTextView.setText("Great! ");
		else if (success > 0.6)
			sessionPercentageTextView.setText("Good! ");
		else
			sessionPercentageTextView.setText("You should be more attentive. ");
		sessionPercentageTextView.append("You answered correctly on "
				+ percentage + "% questions");

		// update information in userInfo and db
		User u = App.userInfo;
		u.setLearnedVocabulary(u.getLearnedVocabulary() + numberOfLearnedWords);
		App.updateUserData();

		// total
		int all = App.userInfo.getNumberOfVocabularyInLevel();
		int learned = App.userInfo.getLearnedVocabulary();
		double totalPercentage = Math
				.round(((double) learned / (double) all) * 100);
		totalPercentageTextView.setText("By now you have learned "
				+ totalPercentage + " % of vocabulary");

		// mistakes
		StringBuffer sb2 = new StringBuffer();
		sb2.append("Pay attention to words: ");
		int numberOfWordsWithWrongAnswers = 0;
		for (DictionaryEntry entry : App.vp.getProblemWords().keySet()) {
			if (App.vp.getProblemWords().get(entry) >= 2) {
				sb2.append(entry.getKanji());
				sb2.append(", ");
				numberOfWordsWithWrongAnswers++;
			}
		}
		sb2.delete(sb.length() - 2, sb.length() - 2);
		if (numberOfWordsWithWrongAnswers != 0)
			mistakesTextView.setText(sb2);

		// cautions
		int num = App.vp.getNumberOfPassingsInARow();
		if (num > 5)
			cautionTextView.setText("It's enough vocabulary for today.");

		// clear information about passing
		App.vp.clearInfo();
	}

	public void buttonRepeatTrainingOnClick(View v) {
		// go to introduction again
		Intent matchWordsIntent = new Intent(this,
				WordIntroductionActivity.class);
		startActivity(matchWordsIntent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.word_practice_result, menu);
		return true;
	}

}
