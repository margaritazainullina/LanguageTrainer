package ua.hneu.languagetrainer;

import ua.edu.hneu.test.R;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MySimpleArrayAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final String[] values;

	public MySimpleArrayAdapter(Context context, String[] values) {
		super(context, R.layout.rowlayout, values);
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.rowlayout, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.label);
		textView.setText(values[position]);
		return rowView;
	}

	public void changeColor(View rowView, int color) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		TextView textView = (TextView) rowView.findViewById(R.id.label);
		textView.setTextColor(color);
	}

	public void setColorToListViewRow(ListView l, int position, int color) {
		for (int i = 0; i < l.getChildCount(); i++) {
			View row = l.getChildAt(i);
			this.changeColor(row, Color.WHITE);
		}
		View currentRow = l.getChildAt(position);
		TextView textView = (TextView) currentRow.findViewById(R.id.label);
		textView.setTextColor(color);
	}

	public void hideListViewRow(View listRow, Animation anim) {
		//settings for fading of listView row
		anim.setDuration(350);
		anim.setFillAfter(true);
		listRow.startAnimation(anim);
		//disable the row
		listRow.setActivated(false);
		listRow.setEnabled(false);
		//make it not visible
		listRow.setVisibility(View.INVISIBLE);
	}

}