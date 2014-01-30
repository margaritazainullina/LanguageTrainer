package ua.hneu.languagetrainer.xmlparcing;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

public class DictUtil {

	public static WordDictionary readXml(String data) {		
		//String finalData = fXmlFile.getPath();
		WordDictionary dict = new WordDictionary();

		/*FileInputStream in;
		try {
			in = new FileInputStream(fXmlFile);

			StringBuffer data = new StringBuffer();
			InputStreamReader isr = new InputStreamReader(in);

			BufferedReader inRd = new BufferedReader(isr);

			StringBuilder inLine = new StringBuilder();
			String text;
			while ((text = inRd.readLine()) != null) {
				inLine.append(text);
				inLine.append("\n");
			}
			in.close();

			 finalData = data.toString();

		} catch (Exception e1) {
			e1.printStackTrace();
		}*/

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			//Document doc = dBuilder.parse(new InputSource(new ByteArrayInputStream(data)));
			Document doc = dBuilder.parse(new InputSource(new StringReader(data)));
			//Document doc = dBuilder.parse(data);

			doc.getDocumentElement().normalize();

			Element dictionary = doc.getDocumentElement();
			NodeList cardList = dictionary.getElementsByTagName("card");
			for (int i = 0; i < cardList.getLength(); i++) {

				// declaring variables for push into dictionary
				int id;
				String word1;
				List<String> translations = new ArrayList<String>();
				List<String> examples = new ArrayList<String>();
				String transcription;
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
						wordMeanings.add(new WordMeaning(transcription,
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