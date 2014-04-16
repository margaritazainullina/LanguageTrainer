package ua.hneu.languagetrainer;

import android.app.ListActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MyListActivity extends ListActivity {
	MySimpleArrayAdapter adapter;

	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
				"Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
				"Linux", "OS/2" };
		adapter = new MySimpleArrayAdapter(this, values);
		setListAdapter(adapter);

	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// String item = (String) getListAdapter().getItem(position);

		// changes color of selected item
		for (int i = 0; i < l.getChildCount(); i++) {
			View v3 = l.getChildAt(i);
			adapter.changeColor(v3, Color.WHITE);
		}
		adapter.changeColor(v, Color.YELLOW);

	}

}