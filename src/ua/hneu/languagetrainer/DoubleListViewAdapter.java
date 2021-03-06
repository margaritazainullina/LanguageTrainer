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

public class DoubleListViewAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final ArrayList<String> values;
	private boolean isJapanese;

	public DoubleListViewAdapter(Context context, ArrayList<String> values,
			boolean isJapanese) {
		super(context, R.layout.double_rowlayout, values);
		this.context = context;
		this.values = values;
		this.isJapanese = isJapanese;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.double_rowlayout, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.label);
		TextView readingTV = (TextView) rowView.findViewById(R.id.reading);
		if (isJapanese) {
			String[] s = values.get(position).split(" - ");
			if (s.length > 1) {
				readingTV.setText(" - " + s[1]);				
			}
			textView.setText(s[0]);
			textView.setTypeface(App.kanjiFont, Typeface.NORMAL);
		}
		return rowView;
	}

	public void changeColor(View rowView, int color) {
		TextView textView = (TextView) rowView.findViewById(R.id.label);
		TextView textView1 = (TextView) rowView.findViewById(R.id.reading);
		textView.setTextColor(color);
		textView1.setTextColor(color);
	}

	public void setTextColorOfListViewRow(ListView l, int position, int color) {
		for (int i = 0; i < l.getChildCount(); i++) {
			View row = l.getChildAt(i);
			this.changeColor(row, Color.parseColor("#eaeaea"));
		}
		View currentRow = l.getChildAt(position);
		TextView textView = (TextView) currentRow.findViewById(R.id.label);
		textView.setTextColor(color);
		textView = (TextView) currentRow.findViewById(R.id.reading);
		textView.setTextColor(color);
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