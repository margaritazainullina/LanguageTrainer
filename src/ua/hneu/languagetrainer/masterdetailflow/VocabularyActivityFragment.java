package ua.hneu.languagetrainer.masterdetailflow;

import android.annotation.TargetApi;
import android.content.Intent;
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
import ua.hneu.languagetrainer.App.Languages;
import ua.hneu.languagetrainer.pages.vocabulary.WordIntroductionActivity;

/**
 * A fragment representing a single Item detail screen. This fragment is either
 * contained in a {@link ItemListActivity} in two-pane mode (on tablets) or a
 * {@link ItemDetailActivity} on handsets.
 */
public class VocabularyActivityFragment extends Fragment {
	TextView infoTextView;
	ProgressBar progressBar;
	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_ITEM_ID = "item_id";

	/**
	 * The dummy content this fragment is presenting.
	 */
	private MainMenuValues.MenuItem mItem;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public VocabularyActivityFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("VocabularyActivityFragment",
				"VocabularyActivityFragment.onCreate()");
		if (getArguments().containsKey(ARG_ITEM_ID)) {
			// Load the dummy content specified by the fragment
			// arguments. In a real-world scenario, use a Loader
			// to load content from a content provider.
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

		// Show the dummy content as text in a TextView.
		if (mItem != null) {
			int learned = App.userInfo.getLearnedVocabulary();
			int all = App.userInfo.getNumberOfVocabularyInLevel();

			int learnedPersentage = (int) Math
					.round(((double) learned / (double) all) * 100);
			String info = this.getString(R.string.you_have_learned) + " "
					+ learned + " ";
			if (App.lang == Languages.RUS) {
				if (learned % 10 == 1)
					info += "слово из";
				else if (learned % 10 >= 1 && learned % 10 < 5)
					info += "слова из";
				else
					info += "слов из";
			} else {
				if (learned > 1)
					info += "words of";
				else
					info += "word of";
			}
			info += " " + all + "\n" + learnedPersentage + "%";

			infoTextView.setText(info);
			progressBar.setProgress(learnedPersentage);
		}
		return rootView;
	}	
}
