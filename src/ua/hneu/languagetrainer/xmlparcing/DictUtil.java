package ua.hneu.languagetrainer.xmlparcing;

import java.io.*;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import android.content.Context;

public class DictUtil {

	// context of activity and path to the local xml file with vocabulary
	public static String readXml(Context ctx,String path) {
		// string with xml file
		String finalData = null;
		
		FileInputStream in;
		try {
			// read file from the activity's context
			in = ctx.openFileInput(path);
			StringBuffer data = new StringBuffer();
			InputStreamReader isr = new InputStreamReader(in);
			BufferedReader inRd = new BufferedReader(isr);
			String text;
			while ((text = inRd.readLine()) != null) {
				data.append(text);
				data.append("\n");
			}
			in.close();
			// set xml content to the data string
			finalData = data.toString();

		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return finalData;
	}
		public static WordDictionary ParseVocabularyXml(String data) {
			WordDictionary dict = new WordDictionary();
		try {
			//make a document from string
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new InputSource(new StringReader(
					data)));

			doc.getDocumentElement().normalize();

			Element dictionary = doc.getDocumentElement();
			//get word cards
			NodeList cardList = dictionary.getElementsByTagName("card");
			for (int i = 0; i < cardList.getLength(); i++) {

				// declaring variables for pushing into dictionary
				int id;
				String word1;
				List<String> translations = new ArrayList<String>();
				List<String> examples = new ArrayList<String>();
				String transcription;
				String romaji;
				List<WordMeaning> wordMeanings = new ArrayList<WordMeaning>();

				// parcing xml
				Element card = (Element) cardList.item(i);
				Element word = (Element) card.getChildNodes().item(0);

				word1 = word.getTextContent();
				id = Integer.parseInt(word.getAttribute("wordId"));

				NodeList meaningsList = card.getElementsByTagName("meanings");
				for (int j = 0; j < meaningsList.getLength(); j++) {
					Element meanings = (Element) meaningsList.item(j);
					NodeList meaningList = meanings
							.getElementsByTagName("meaning");

					for (int k = 0; k < meaningsList.getLength(); k++) {
						Element meaning = (Element) meaningList.item(k);

						transcription = meaning.getAttribute("transcription");
						romaji = meaning.getAttribute("romaji");

						NodeList translationsList = meaning
								.getElementsByTagName("translations");
						for (int l = 0; l < translationsList.getLength(); l++) {

							NodeList translationList = meaning
									.getElementsByTagName("word");
							for (int m = 0; m < translationList.getLength(); m++) {
								Element translation = (Element) translationList
										.item(m);
								translations.add(translation.getTextContent());
							}
						}

						NodeList examplesList = meaning
								.getElementsByTagName("examples");
						for (int l = 0; l < examplesList.getLength(); l++) {

							NodeList exampleList = meaning
									.getElementsByTagName("example");
							for (int m = 0; m < exampleList.getLength(); m++) {
								Element example = (Element) exampleList.item(m);
								examples.add(example.getTextContent());
							}
						}
						wordMeanings.add(new WordMeaning(transcription, romaji,
								translations, examples));
					}
				}
				dict.add(new DictionaryEntry(id, word1, wordMeanings));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dict;
	}

}