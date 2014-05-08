package ua.hneu.languagetrainer.model.other;

public class GiongoExample {
	private String text;
	private String romaji;
	private String translationEng;
	private String translationRus;

	public GiongoExample(String text, String romaji, String translationEng,
			String translationRus) {
		super();
		this.text = text;
		this.romaji = romaji;
		this.translationEng = translationEng;
		this.translationRus = translationRus;
	}

	public String getText() {
		return text;
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

	public void setText(String text) {
		this.text = text;
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
