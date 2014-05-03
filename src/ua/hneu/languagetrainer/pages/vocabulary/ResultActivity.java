package ua.hneu.languagetrainer.pages.vocabulary;

import ua.hneu.edu.languagetrainer.R;
import ua.hneu.languagetrainer.App;
import ua.hneu.languagetrainer.model.User;
import ua.hneu.languagetrainer.model.vocabulary.DictionaryEntry;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
				if (!entry.getKanji().isEmpty())
					sb.append(entry.getKanji());
				else
					sb.append(entry.getTranscription());
				sb.append(", ");
			}
		}
		if (sb.length() != 0)
			sb.delete(sb.length() - 3, sb.length() - 1);
		StringBuffer sb1 = new StringBuffer();
		if (numberOfLearnedWords == 0)
			sb1.append(this.getString(R.string.you_havent_learned_any_word));
		else {
			sb1.append(this.getString(R.string.youve_learned)+" ");
			sb1.append(numberOfLearnedWords+": ");
		}
		sb1.append(sb);
		learnedWordsTextView.setText(sb1);

		// update information in userInfo and db
		User u = App.userInfo;
		u.setLearnedVocabulary(u.getLearnedVocabulary() + numberOfLearnedWords);
		App.updateUserData();

		// total
		int all = App.userInfo.getNumberOfVocabularyInLevel();
		int learned = App.userInfo.getLearnedVocabulary();
		double totalPercentage = Math
				.round(((double) learned / (double) all) * 100);
		totalPercentageTextView.setText(this
				.getString(R.string.by_now_youve_learned)
				+ " "
				+ totalPercentage
				+ " "
				+ this.getString(R.string.percentage_of_voc));

		// mistakes
		StringBuffer sb2 = new StringBuffer();
		sb2.append(this.getString(R.string.pay_attention_to_words) + " ");
		int numberOfProblemWords = 0;
		for (DictionaryEntry entry : App.vp.getProblemWords().keySet()) {
			if (App.vp.getProblemWords().get(entry) >= 2) {
				if (!entry.getKanji().isEmpty())
					sb2.append(entry.getKanji());
				else
					sb2.append(entry.getTranscription());
				sb2.append(", ");
				numberOfProblemWords++;
			}
		}
		sb2.delete(sb2.length() - 3, sb2.length() - 1);
		if (numberOfProblemWords != 0)
			mistakesTextView.setText(sb2);

		// information about session result
		int correct = App.vp.getNumberOfCorrectAnswersInMatching()
				+ App.vp.getNumberOfCorrectAnswersInTranslation()
				+ App.vp.getNumberOfCorrectAnswersInTranscription();
		int incorrect = App.vp.getNumberOfIncorrectAnswersInMatching()
				+ App.vp.getNumberOfIncorrectAnswersInTranslation()
				+ App.vp.getNumberOfIncorrectAnswersInTranscription();
		int success = (int) Math.round(((double) (correct - incorrect)
				/ (correct + incorrect) * 100));
		if (success < 0)
			success = 0;

		if (success > 80)
			sessionPercentageTextView.setText(this.getString(R.string.great)+" ");
		else if (success > 60)
			sessionPercentageTextView.setText(this.getString(R.string.good)+" ");
		else
			sessionPercentageTextView.setText(this
					.getString(R.string.more_atentive) + " ");
		sessionPercentageTextView.append(this
				.getString(R.string.correct_answer_rate) + " " + success);

		// cautions
		int num = App.vp.getNumberOfPassingsInARow();
		if (num > 5)
			cautionTextView.setText(this.getString(R.string.enough));

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
