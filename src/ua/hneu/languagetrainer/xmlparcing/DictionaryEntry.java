package ua.hneu.languagetrainer.xmlparcing;

import java.util.Collections;
import java.util.List;

public class DictionaryEntry implements Comparable{

	private int id;
	private String word;
	private WordMeaning meaning;

	public DictionaryEntry(int id, String word, WordMeaning meaning) {
		this.id = id;
		this.word = word;
		this.meaning = meaning;
	}

	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		sb.append(meaning.transcription);
		sb.append(", ");
		sb.append(meaning.romaji);
		sb.append("]");
		sb.append("]");
		sb.append("\n");
		sb.append(meaning.translationsToString());
		sb.append("\n");	
		return sb.toString();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public WordMeaning getMeanings() {
		return meaning;
	}

	public List<String> getTranslations() {
		return this.meaning.translations;
	}

	public String getTranscription() {
		return this.meaning.transcription;
	}

	public String getRomaji() {
		return this.meaning.romaji;
	}

	public void setMeanings(WordMeaning meaning) {
		this.meaning = meaning;
	}

	public void setTranslations(List<String> translations) {
		this.meaning.translations = translations;
	}

	public void setTranscription(String transcription) {
		this.meaning.transcription = transcription;
	}

	public void setRomaji(String romaji) {
		this.meaning.romaji = romaji;
	}

	public String translationsToString() {
		return this.meaning.translationsToString();
	}

	@Override
	public int compareTo(Object e) {
		DictionaryEntry e1 = (DictionaryEntry) e;
		return this.word.compareTo(e1.word);
	}
}
