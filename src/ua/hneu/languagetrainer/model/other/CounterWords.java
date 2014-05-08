package ua.hneu.languagetrainer.model.other;

public class CounterWords {
	private String section;
	private String word;
	private String hiragana;
	private String romaji;
	private String translationEng;
	private String translationRus;
	private String color;

	public CounterWords(String section, String word, String hiragana,
			String romaji, String translationEng, String translationRus,
			String color) {
		super();
		this.section = section;
		this.word = word;
		this.hiragana = hiragana;
		this.romaji = romaji;
		this.translationEng = translationEng;
		this.translationRus = translationRus;
		this.color = color;
	}

	public String getSection() {
		return section;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getWord() {
		return word;
	}

	public String getHiragana() {
		return hiragana;
	}

	public String getRomaji() {
		return romaji;
	}

	public String getTranslationEng() {
		return translationEng;
	}

	public String getTranslationRus() {
		return translationRus;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public void setHiragana(String hiragana) {
		this.hiragana = hiragana;
	}

	public void setRomaji(String romaji) {
		this.romaji = romaji;
	}

	public void setTranslationEng(String translationEng) {
		this.translationEng = translationEng;
	}

	public void setTranslationRus(String translationRus) {
		this.translationRus = translationRus;
	}
}
