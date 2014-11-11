package ua.hneu.languagetrainer;

import java.util.ArrayList;

import ua.hneu.edu.languagetrainer.R;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class CounterWordsListViewAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final ArrayList<String> values1;
	private final ArrayList<String> values2;

	public CounterWordsListViewAdapter(Context context,
			ArrayList<String> values1, ArrayList<String> values2) {
		super(context, R.layout.rowlayout_counter_words_list, values1);
		this.context = context;
		this.values1 = values1;
		this.values2 = values2;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.rowlayout_counter_words_list, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.section);
		TextView textView1 = (TextView) rowView.findViewById(R.id.ListElementInfo);
		textView.setText(values1.get(position));
		textView1.setText(values2.get(position));
		
		//rowView.setTypeface(App.kanjiFont, Typeface.NORMAL);
		return rowView;
	}

	public void changeColor(View rowView, int color) {
		TextView textView = (TextView) rowView.findViewById(R.id.section);
		TextView textView1 = (TextView) rowView.findViewById(R.id.ListElementInfo);
		textView.setTextColor(color);
		textView1.setTextColor(color);
	}

	public void setTextColorOfListViewRow(ListView l, int position, int color) {
		for (int i = 0; i < l.getChildCount(); i++) {
			View row = l.getChildAt(i);
			this.changeColor(row, Color.parseColor("#eaeaea"));
		}
		View currentRow = l.getChildAt(position);
		changeColor(currentRow, color);
	}

	public void hideElement(View listRow, Animation anim, long duration) {
		// settings for fading of listView row
		anim.setDuration(duration);
		anim.setFillAfter(true);
		listRow.startAnimation(anim);
		// disable the row
		listRow.setActivated(false);
		listRow.setEnabled(false);
		// make it not visible
		listRow.setVisibility(View.INVISIBLE);
	}

}