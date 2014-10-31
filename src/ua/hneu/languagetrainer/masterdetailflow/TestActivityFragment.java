package ua.hneu.languagetrainer.masterdetailflow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import ua.hneu.edu.languagetrainer.R;
import ua.hneu.languagetrainer.App;
import ua.hneu.languagetrainer.TestInfoListViewAdapter;
import ua.hneu.languagetrainer.service.TestService;
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

public class TestActivityFragment extends Fragment {
	TextView infoTextView;
	ListView infoListView;
	ArrayList<String> testNames = new ArrayList<String>();
	ArrayList<String> testNumb = new ArrayList<String>();
	ArrayList<String> resultsPart1 = new ArrayList<String>();
	ArrayList<String> resultsPart2 = new ArrayList<String>();
	ArrayList<String> resultsPart3 = new ArrayList<String>();
	TestInfoListViewAdapter adapter;
	public static final String ARG_ITEM_ID = "item_id";
	TestService ts = new TestService();
	public static String testName;
private boolean wasLoded=false;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.mock_test_fragment,
				container, false);

		HashMap<String, int[]> info = ts.getTestNamesAndPoints(App.cr,
				App.userInfo.getLevel());
		infoListView = (ListView) rootView.findViewById(R.id.infoListView);
		
		if(!wasLoded){
		testNames.add(this.getString(R.string.test));
		if (App.userInfo.getLevel() == 4 || App.userInfo.getLevel() == 5) {
			resultsPart1.add(this.getString(R.string.voc_gr) + ", "
					+ this.getString(R.string.reading));
			resultsPart2.add("");
		} else {
			resultsPart1.add(this.getString(R.string.voc_gr));
			resultsPart2.add(this.getString(R.string.reading));
		}
		resultsPart3.add(this.getString(R.string.listening));

		int i = 1;
		Set<Entry<String, int[]>> set = info.entrySet();
		Iterator<Entry<String, int[]>> it = set.iterator();
		
		while (it.hasNext()) {
			Map.Entry<String, int[]> entry = (Map.Entry<String, int[]>) it
					.next();
			testNames.add(entry.getKey() + "");
			testNumb.add(this.getString(R.string.test_name) + " " + i);
			if (App.userInfo.getLevel() == 4 || App.userInfo.getLevel() == 5) {
				// if 4 or 5 level - Language Knowledge+Reading 120 points,
				// listening 60
				resultsPart1.add(entry.getValue()[0] + entry.getValue()[1]
						+ "/" + 120);
				resultsPart2.add("");
				resultsPart3.add(entry.getValue()[2] + "/" + 60);
			} else {
				// if 1,2,3 level - Language Knowledge 60 points, Reading 60
				// points, listening 60
				resultsPart1.add(entry.getValue()[0] + "/" + 60);
				resultsPart2.add(entry.getValue()[1] + "/" + 60);
				resultsPart3.add(entry.getValue()[2] + "/" + 60);
			}
			i++;
		}}

		adapter = new TestInfoListViewAdapter(getActivity(), testNames,
				resultsPart1, resultsPart2, resultsPart3);
		infoListView.setAdapter(adapter);
		infoListView.setOnItemClickListener(sectionsListViewClickListener);
wasLoded = true;
		return rootView;
	}

	final private transient OnItemClickListener sectionsListViewClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(final AdapterView<?> parent, final View view,
				final int position, final long itemID) {

			// TODO: replace this
			adapter.setTextColorOfListViewRow((ListView) parent, position,
					Color.parseColor("#ffbb33"));
			testName = testNames.get(position);
		}
	};

}
