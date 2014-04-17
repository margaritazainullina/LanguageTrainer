package ua.hneu.languagetrainer;

import android.R;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ListView;

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
		
		Animation animFadeIn = AnimationUtils.loadAnimation(this,
				R.anim.fade_out);
		adapter.hideListViewRow(v,animFadeIn);
	}

}