package ua.hneu.languagetrainer;

import java.util.ArrayList;
import java.util.Dictionary;

import ua.hneu.edu.languagetrainer.R;
import ua.hneu.languagetrainer.model.grammar.GrammarDictionary;
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

public class AllGrammarListViewAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final ArrayList<String> rules;
	private final ArrayList<String> descriptions;

	TextView ruleTv;
	TextView descriptionTv;

	public AllGrammarListViewAdapter(Context context, GrammarDictionary d) {
		super(context, R.layout.all_grammar_rowlayout, d.getAllRules());
		this.context = context;
		this.rules = d.getAllRules();
		this.descriptions = d.getAllDescriptions();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.all_grammar_rowlayout, parent,
				false);
		ruleTv = (TextView) rowView.findViewById(R.id.agr_rule);
		descriptionTv = (TextView) rowView.findViewById(R.id.agr_description);
		ruleTv.setText(rules.get(position));
		descriptionTv.setText(descriptions.get(position));
		
		ruleTv.setTypeface(App.kanjiFont, Typeface.NORMAL);
		return rowView;
	}

}