package ua.hneu.languagetrainer;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import ua.edu.hneu.test.R;
import ua.hneu.languagetrainer.data.CurrentUserData;
import ua.hneu.languagetrainer.firstpage.DummyContent;
import ua.hneu.languagetrainer.pages.WordPracticeActivity;

/**
 * A fragment representing a single Item detail screen. This fragment is either
 * contained in a {@link ItemListActivity} in two-pane mode (on tablets) or a
 * {@link ItemDetailActivity} on handsets.
 */
public class VocabularyActivityFragment extends Fragment {
	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_ITEM_ID = "item_id";
	CurrentUserData ud = CurrentUserData.getInstance();

	/**
	 * The dummy content this fragment is presenting.
	 */
	private DummyContent.DummyItem mItem;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public VocabularyActivityFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments().containsKey(ARG_ITEM_ID)) {
			// Load the dummy content specified by the fragment
			// arguments. In a real-world scenario, use a Loader
			// to load content from a content provider.
			mItem = DummyContent.ITEM_MAP.get(getArguments().getString(
					ARG_ITEM_ID));

		}
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.vocabulary_fragment,
				container, false);

		// Show the dummy content as text in a TextView.
		if (mItem != null) {
			double learned = ud.getLearnedWords() ;
			double all = ud.getAllWords();
			int learnedPersentage = (int) Math.round((learned / all) * 100);
			String info = "You have learned " + ud.getLearnedWords()
					+ " words out of " + ud.getAllWords() + "\n"
					+ learnedPersentage+ "%";
			((TextView) rootView.findViewById(R.id.item_detail)).setText(info);
			
			((ProgressBar) rootView.findViewById(R.id.progressBar)).setProgress(learnedPersentage);
		}

		return rootView;
	}
	
	
}
