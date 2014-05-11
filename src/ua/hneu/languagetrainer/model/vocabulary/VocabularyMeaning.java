package ua.hneu.languagetrainer.model.vocabulary;

import java.util.List;

public class VocabularyMeaning {

	String hiragana;
	String romaji;
	List<String> translations;

	VocabularyMeaning(String transcription, String romaji, List<String> translations) {
		this.hiragana = transcription;
		this.romaji = romaji;
		this.translations = translations;
	}

	VocabularyMeaning() {
	}
	
	public String translationsToString() {
		StringBuilder sb = new StringBuilder();
		for (String tr : translations) {
			sb.append(tr + ", ");
		}
		// trim ending ","
		String s = sb.toString();
		if (s.length() > 0)
			s = s.substring(0, s.length() - 2);
		return s;
	}

	
}
