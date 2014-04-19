package ua.hneu.languagetrainer.xmlparcing;

import java.io.*;
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
	public static String readFile(Context ctx, String path) {
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
			}
			in.close();
			// set xml content to the data string
			finalData = data.toString();

		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return finalData;
	}

	public static void writeFile(Context ctx, String path, String data) {
		FileOutputStream os;
		try {
			os = new FileOutputStream(path);
			OutputStreamWriter osw = new OutputStreamWriter(os);
			osw.write(data);
			osw.flush();
			osw.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static WordDictionary ParseVocabularyXml(String data) {
		WordDictionary dict = new WordDictionary();
		try {
			// make a document from string
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new InputSource(
					new StringReader(data)));

			doc.getDocumentElement().normalize();

			Element dictionary = doc.getDocumentElement();
			// get word cards
			NodeList cardList = dictionary.getElementsByTagName("card");
			for (int i = 0; i < cardList.getLength(); i++) {

				// declaring variables for pushing into dictionary
				int id;
				String word1;
				List<String> translations = new ArrayList<String>();
				String transcription;
				String romaji;
				WordMeaning wordMeaning = new WordMeaning();

				// Parsing xml
				Element card = (Element) cardList.item(i);
				Element word = (Element) card.getChildNodes().item(0);

				word1 = word.getTextContent();
				id = Integer.parseInt(word.getAttribute("wordId"));

				Element meanings = (Element) card.getElementsByTagName(
						"meanings").item(0);
				Element meaning = (Element) meanings.getElementsByTagName(
						"meaning").item(0);
				//get statistics (percentage) of how well the user knows the word with attribute "status"
				Element statistics = (Element) meanings.getElementsByTagName(
						"statistics").item(0);
				String status = statistics.getAttribute("status");
				double learnedPercentage;
				try{
					learnedPercentage = Double.parseDouble(status) ;
				}
				catch (Exception e){
					//if something bad occurs
					//set percentage to 0
					//TODO: set it also in file
					learnedPercentage=0;
				}
				
				transcription = meaning.getAttribute("transcription");
				romaji = meaning.getAttribute("romaji");

				NodeList translationsList = meaning
						.getElementsByTagName("translations");
				for (int l = 0; l < translationsList.getLength(); l++) {

					NodeList translationList = meaning
							.getElementsByTagName("word");
					for (int m = 0; m < translationList.getLength(); m++) {
						Element translation = (Element) translationList.item(m);
						translations.add(translation.getTextContent());
					}
					wordMeaning = new WordMeaning(transcription, romaji,
							translations);
				}
				dict.add(new DictionaryEntry(id, word1, wordMeaning, learnedPercentage));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dict;
	}

	public static void updateXmlWithResults(WordDictionary w){
		//TODO: update <statistics status=""> in the xml
		for (DictionaryEntry e : w.getEntries()) {
			double p = e.getLearnedPercentage();
			
		}
	}
}