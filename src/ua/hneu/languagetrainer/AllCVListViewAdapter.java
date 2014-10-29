package ua.hneu.languagetrainer;

import java.util.ArrayList;
import java.util.Dictionary;

import ua.hneu.edu.languagetrainer.R;
import ua.hneu.languagetrainer.model.other.CounterWordsDictionary;
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

public class AllCVListViewAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final ArrayList<String> word;
	private final ArrayList<String> reading;
	private final ArrayList<String> translation;

	TextView wordTv;
	TextView readingTv;
	TextView translationTv;
	
	public AllCVListViewAdapter(Context context, CounterWordsDictionary d ) {
		super(context, R.layout.all_counterwords_rowlayout, d.getAllWords());
		this.context = context;
		this.word=d.getAllWords();
		this.reading=d.getAllTranscriptions();
		this.translation = d.getAllTranslations();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.all_counterwords_rowlayout, parent, false);
		TextView wordTv = (TextView) rowView.findViewById(R.id.acr_word);
		TextView readingTv = (TextView) rowView.findViewById(R.id.acr_reading);
		TextView translationTv = (TextView) rowView.findViewById(R.id.acr_translation);
		wordTv.setText(word.get(position));
		readingTv.setText(reading.get(position));
		translationTv.setText(translation.get(position));
		return rowView;
	}

}