package ua.hneu.languagetrainer.masterdetailflow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import ua.hneu.edu.languagetrainer.R;
import ua.hneu.languagetrainer.App;
import ua.hneu.languagetrainer.CounterWordsListViewAdapter;
import ua.hneu.languagetrainer.service.CounterWordsService;
import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class CounterWordsFragment extends Fragment {
	TextView infoTextView;
	ListView sectionsListView;
	ArrayList<String> sectionNames = new ArrayList<String>();
	ArrayList<String> infoList = new ArrayList<String>();
	CounterWordsListViewAdapter adapter;
	public static final String ARG_ITEM_ID = "item_id";
	CounterWordsService cvs = new CounterWordsService();
	public static String selectedSection;

	public CounterWordsFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.counterwords_fragment,
				container, false);

		HashMap<String, int[]> info = cvs.getAllSectionsWithProgress(App.cr);
		sectionNames = new ArrayList<String>();
		infoList = new ArrayList<String>();
		sectionsListView = (ListView) rootView
				.findViewById(R.id.sectionsListView);
		Set set = info.entrySet();
		Iterator it = set.iterator();
		while (it.hasNext()) {
			Map.Entry<String, int[]> entry = (Map.Entry<String, int[]>) it
					.next();
			sectionNames.add(entry.getKey() + "");
			infoList.add(entry.getValue()[1] + "/" + entry.getValue()[0]);
		}

		adapter = new CounterWordsListViewAdapter(getActivity(), sectionNames,
				infoList);
		sectionsListView.setAdapter(adapter);
		sectionsListView.setOnItemClickListener(sectionsListViewClickListener);
		return rootView;
	}

	final private transient OnItemClickListener sectionsListViewClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(final AdapterView<?> parent, final View view,
				final int position, final long itemID) {
			// TODO: replace this
			String[] r = infoList.get(position).split("/");
			if (Integer.parseInt(r[0]) != Integer.parseInt(r[1]))
				selectedSection = sectionNames.get(position);
			adapter.setTextColorOfListViewRow((ListView) parent, position,
					Color.parseColor("#ffbb33"));
		}
	};

}
