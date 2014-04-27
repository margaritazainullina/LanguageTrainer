package ua.hneu.languagetrainer.model;

import java.util.List;

public class WordMeaning {

	String transcription;
	String romaji;
	List<String> translations;

	WordMeaning(String transcription, String romaji, List<String> translations) {
		this.transcription = transcription;
		this.romaji = romaji;
		this.translations = translations;
	}

	WordMeaning() {
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
