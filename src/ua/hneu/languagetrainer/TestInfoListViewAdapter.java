package ua.hneu.languagetrainer;

import java.util.ArrayList;

import ua.hneu.edu.languagetrainer.R;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class TestInfoListViewAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final ArrayList<String> name;
	private final ArrayList<String> values1;
	private final ArrayList<String> values2;
	private final ArrayList<String> values3;

	public TestInfoListViewAdapter(Context context,ArrayList<String> name,
			ArrayList<String> values1, ArrayList<String> values2,  ArrayList<String> values3) {
		super(context, R.layout.rowlayout_test_info_list, values1);
		this.context = context;
		this.name = name;
		this.values1 = values1;
		this.values2 = values2;
		this.values3 = values3;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.rowlayout_test_info_list, parent, false);
		TextView names = (TextView) rowView.findViewById(R.id.names);
		TextView part1 = (TextView) rowView.findViewById(R.id.part1);
		TextView part2 = (TextView) rowView.findViewById(R.id.part2);
		TextView part3 = (TextView) rowView.findViewById(R.id.part3);
		names.setText(name.get(position));
		part1.setText(values1.get(position));
		part2.setText(values2.get(position));
		part3.setText(values3.get(position));
		return rowView;
	}

	public void changeColor(View rowView, int color) {
		TextView names = (TextView) rowView.findViewById(R.id.names);
		TextView part1 = (TextView) rowView.findViewById(R.id.part1);
		TextView part2 = (TextView) rowView.findViewById(R.id.part2);
		TextView part3 = (TextView) rowView.findViewById(R.id.part3);
		names.setTextColor(color);
		part1.setTextColor(color);
		part2.setTextColor(color);
		part3.setTextColor(color);
	}

	public void setTextColorOfListViewRow(ListView l, int position, int color) {
		for (int i = 0; i < l.getChildCount(); i++) {
			View row = l.getChildAt(i);
			this.changeColor(row, Color.parseColor("#eaeaea"));
		}
		View currentRow = l.getChildAt(position);
		changeColor(currentRow, color);
	}
}