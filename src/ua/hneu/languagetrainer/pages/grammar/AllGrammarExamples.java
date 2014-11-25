package ua.hneu.languagetrainer.pages.grammar;

import ua.hneu.edu.languagetrainer.R;
import ua.hneu.languagetrainer.App;
import ua.hneu.languagetrainer.ExamplesListViewAdapter;
import ua.hneu.languagetrainer.TextToVoiceMediaPlayer;
import ua.hneu.languagetrainer.model.grammar.GrammarRule;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class AllGrammarExamples extends ListActivity {
	ExamplesListViewAdapter adapter;
	ListView kanjiListView;
	TextToVoiceMediaPlayer twmp;
	String phrase = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_allvocabulary);

		Bundle extras = getIntent().getExtras();
		GrammarRule gr = null;
		twmp = new TextToVoiceMediaPlayer();
		
		if (extras != null) {
			String rule = extras.get("rule").toString();
			gr = App.allGrammarDictionary.getByRule(rule);
		}

		adapter = new ExamplesListViewAdapter(this,
				gr.getAllExamplesText(), gr.getAllExamplesRomaji(),
				gr.getAllTranslations(), gr.getIntColor());
		this.setListAdapter(adapter);
	}

	@Override
	public boolean onSearchRequested() {
		return super.onSearchRequested();
	}
	public void onPlayClick1(View v) {
		// getting layout with text
		View v1 = (View) v.getParent();
		TextView textPart1 = (TextView) v1.findViewById(R.id.textPart1);
		TextView textPart2 = (TextView) v1.findViewById(R.id.textPart2);
		TextView textPart3 = (TextView) v1.findViewById(R.id.textPart3);
		phrase = (String) textPart1.getText() + textPart2.getText()
				+ textPart3.getText();
		twmp.play(phrase);
	}	
}
