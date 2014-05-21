package ua.hneu.languagetrainer.masterdetailflow;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import ua.hneu.edu.languagetrainer.R;
import ua.hneu.languagetrainer.App;

public class GrammarActivityFragment extends Fragment {
	TextView infoTextView;
	ProgressBar progressBar;
	public static final String ARG_ITEM_ID = "item_id";
	
	public GrammarActivityFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.grammar_fragment,
				container, false);

		infoTextView = (TextView) rootView.findViewById(R.id.grammarInfoTextView);
		progressBar = (ProgressBar) rootView.findViewById(R.id.grammarProgressBar);

		Log.i("GrammarActivityFragment",
				"GrammarActivityFragment.onCreateView()");

		// Show the dummy content as text in a TextView.
			int learned = App.userInfo.getLearnedGrammar();
			int all = App.userInfo.getNumberOfGrammarInLevel();

			int learnedPersentage = (int) Math
					.round(((double) learned / (double) all) * 100);
			String info = this.getString(R.string.grammar_learned) + ": "
					+ learned + " " + this.getString(R.string.out_of) + " "
					+ all + " - " + learnedPersentage + "%";

			infoTextView.setText(info);
			progressBar.setProgress(learnedPersentage);
		return rootView;
	}
}
