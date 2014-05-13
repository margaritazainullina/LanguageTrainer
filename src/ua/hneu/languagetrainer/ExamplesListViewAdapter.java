package ua.hneu.languagetrainer;

import java.util.ArrayList;

import ua.hneu.edu.languagetrainer.R;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ExamplesListViewAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final ArrayList<String> text;
	private final ArrayList<String> romaji;
	private final ArrayList<String> translations;

	TextView textPart1;
	TextView textPart2;
	TextView textPart3;
	TextView romajiTv;
	TextView translationTv;

	int color;

	public ExamplesListViewAdapter(Context context, ArrayList<String> text,
			ArrayList<String> romaji, ArrayList<String> translations, int color) {
		super(context, R.layout.rowlayout_counter_words_list, text);
		this.context = context;
		this.text = text;
		this.romaji = romaji;
		this.translations = translations;
		this.color = color;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.rowlayout_examples,
				parent, false);
		textPart1 = (TextView) rowView.findViewById(R.id.textPart1);
		textPart2 = (TextView) rowView.findViewById(R.id.textPart2);
		textPart3 = (TextView) rowView.findViewById(R.id.textPart3);
		romajiTv = (TextView) rowView.findViewById(R.id.romaji);
		translationTv = (TextView) rowView.findViewById(R.id.translation);
		// split text to 3 parts to change color of item to whom belongs example
		String s = text.get(position);
		String[] parts = s.split("\\\\t");
		textPart1.setText(parts[0]);
		textPart2.setText(parts[1]);
		textPart3.setText(parts[2]);
		romajiTv.setText(romaji.get(position));
		translationTv.setText(translations.get(position));
		changeColor(rowView, color);
		return rowView;
	}

	public void changeColor(View rowView, int color) {		
		textPart2.setTextColor(color);
	}
}