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
import ua.hneu.languagetrainer.model.User;
import ua.hneu.languagetrainer.service.VocabularyService;

public class VocabularyActivityFragment extends Fragment {
	TextView infoTextView;
	ProgressBar progressBar;
	public static final String ARG_ITEM_ID = "item_id";

	private MainMenuValues.MenuItem mItem;

	public VocabularyActivityFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments().containsKey(ARG_ITEM_ID)) {
			mItem = MainMenuValues.ITEM_MAP.get(getArguments().getString(
					ARG_ITEM_ID));
		}		

	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.vocabulary_fragment,
				container, false);

		infoTextView = (TextView) rootView.findViewById(R.id.infoTextView);
		progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);

		Log.i("VocabularyActivityFragment",
				"VocabularyActivityFragment.onCreateView()");

		if (mItem != null) {
			int learned = App.userInfo.getLearnedVocabulary();
			int all = App.userInfo.getNumberOfVocabularyInLevel();

			int learnedPersentage = (int) Math
					.round(((double) learned / (double) all) * 100);
			String info = this.getString(R.string.youve_learned) + ": "
					+ learned + " " + this.getString(R.string.out_of) + " "
					+ all + " - " + learnedPersentage + "%";

			infoTextView.setText(info);
			progressBar.setProgress(learnedPersentage);
		}
		return rootView;
	}
}
