package ua.hneu.languagetrainer;

import java.util.ArrayList;
import java.util.Dictionary;

import ua.hneu.edu.languagetrainer.R;
import ua.hneu.languagetrainer.model.vocabulary.VocabularyDictionary;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class AllVocabularyListViewAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final ArrayList<String> kanji;
	private final ArrayList<String> reading;
	private final ArrayList<String> translation;
	private final ArrayList<String> stat;

	TextView kanjiTv;
	TextView readingTv;
	TextView translationTv;
	TextView statTv;
	TextView isLearnedTv;
	
	public AllVocabularyListViewAdapter(Context context, VocabularyDictionary d ) {
		super(context, R.layout.all_vocabulary_rowlayout, d.getAllKanji());
		this.context = context;
		this.kanji=d.getAllKanji();
		this.reading=d.getAllReadings();
		this.translation = d.getAllTranslations();
		//TODO: replace
		this.stat=d.getAllReadings();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.all_vocabulary_rowlayout, parent, false);
		TextView kanjiTv = (TextView) rowView.findViewById(R.id.avr_kanji);
		TextView readingTv = (TextView) rowView.findViewById(R.id.avr_reading);
		TextView translationTv = (TextView) rowView.findViewById(R.id.avr_translation);
		TextView statTv = (TextView) rowView.findViewById(R.id.avr_stat);
		TextView isLearnedTv = (TextView) rowView.findViewById(R.id.avr_isLearned);
		kanjiTv.setText(kanji.get(position));
		readingTv.setText(reading.get(position));
		translationTv.setText(translation.get(position));
		statTv.setText(stat.get(position));
		isLearnedTv.setText("Learned");
		return rowView;
	}

}