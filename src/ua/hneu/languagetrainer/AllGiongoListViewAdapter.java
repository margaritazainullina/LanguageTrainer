package ua.hneu.languagetrainer;

import java.util.ArrayList;
import java.util.Dictionary;

import ua.hneu.edu.languagetrainer.R;
import ua.hneu.languagetrainer.model.grammar.GrammarDictionary;
import ua.hneu.languagetrainer.model.other.GiongoDictionary;
import ua.hneu.languagetrainer.model.vocabulary.VocabularyDictionary;
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

public class AllGiongoListViewAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final ArrayList<String> giongo;
	private final ArrayList<String> translations;

	TextView giongoTv;
	TextView translationTv;

	public AllGiongoListViewAdapter(Context context, GiongoDictionary d) {
		super(context, R.layout.all_giongo_rowlayout, d.getAllGiongo());
		this.context = context;
		this.giongo = d.getAllGiongo();
		this.translations = d.getAllTranslation();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.all_giongo_rowlayout, parent,
				false);
		giongoTv = (TextView) rowView.findViewById(R.id.agr_giongo);
		translationTv = (TextView) rowView.findViewById(R.id.agr_translation);
		giongoTv.setText(giongo.get(position));
		translationTv.setText(translations.get(position));

		giongoTv.setTypeface(App.kanjiFont, Typeface.NORMAL);
		return rowView;
	}

}