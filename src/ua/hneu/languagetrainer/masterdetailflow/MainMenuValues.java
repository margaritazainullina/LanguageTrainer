package ua.hneu.languagetrainer.masterdetailflow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.res.Resources;

import ua.hneu.edu.languagetrainer.R;

public class MainMenuValues {
	public static List<MenuItem> ITEMS = new ArrayList<MenuItem>();

	public static Map<String, MenuItem> ITEM_MAP = new HashMap<String, MenuItem>();

	public MainMenuValues(){
		/*addItem(new MenuItem("vocabulary", "vocabulary"));
		addItem(new MenuItem("grammar", "grammar"));
		addItem(new MenuItem("listening", "listening"));
		addItem(new MenuItem("mock_tests", "mock_tests"));
		addItem(new MenuItem("other", "other"));
		addItem(new MenuItem("settings", "settings"));*/
		
		/*addItem(new DummyItem("vocabulary", Resources.getSystem().getString(R.string.vocabulary)));
		addItem(new DummyItem("grammar", Resources.getSystem().getString(R.string.grammar)));
		addItem(new DummyItem("listening", Resources.getSystem().getString(R.string.listening)));
		addItem(new DummyItem("mock_tests", Resources.getSystem().getString(R.string.mock_tests)));
		addItem(new DummyItem("other", Resources.getSystem().getString(R.string.other)));
		addItem(new DummyItem("settings", Resources.getSystem().getString(R.string.settings)));*/
	}
	

	public static void addItem(MenuItem item) {
		ITEMS.add(item);
		ITEM_MAP.put(item.id, item);
	}

	public static class MenuItem {
		public String id;
		public String content;

		public MenuItem(String id, String content) {
			this.id = id;
			this.content = content;
		}
		@Override
		public String toString() {
			return content;
		}
	}
}


