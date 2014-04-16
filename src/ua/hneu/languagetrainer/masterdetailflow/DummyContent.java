package ua.hneu.languagetrainer.masterdetailflow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.res.Resources;

import ua.edu.hneu.test.R;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

	/**
	 * An array of sample (dummy) items.
	 */
	public static List<DummyItem> ITEMS = new ArrayList<DummyItem>();

	/**
	 * A map of sample (dummy) items, by ID.
	 */
	public static Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

	//TODO: Fetch this values from localised strings
	static {	
		addItem(new DummyItem("1", "Vocabulary"));
		addItem(new DummyItem("2", "Grammar"));
		addItem(new DummyItem("3", "Listening"));
		addItem(new DummyItem("4", "Mock tests"));
		addItem(new DummyItem("5", "Other"));
		addItem(new DummyItem("6", "Settings"));
	}

	private static void addItem(DummyItem item) {
		ITEMS.add(item);
		ITEM_MAP.put(item.id, item);
	}

	/**
	 * A dummy item representing a piece of content.
	 */
	public static class DummyItem {
		public String id;
		public String content;

		public DummyItem(String id, String content) {
			this.id = id;
			this.content = content;
		}

		@Override
		public String toString() {
			return content;
		}
	}
}
