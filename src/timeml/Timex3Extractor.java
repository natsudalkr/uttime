package timeml;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import base.Event;
import base.Timex3;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.util.CoreMap;

import toolinterface.FileInterface;
import toolinterface.XMLInterface;

public class Timex3Extractor {
	public Timex3Extractor(){		
	}
	
	public ArrayList<String> getTimex3Strings(org.w3c.dom.Document doc){
		NodeList timex3List =  doc.getElementsByTagName("TIMEX3");
		ArrayList<String> eventString = new ArrayList<String>();
		int length = timex3List.getLength();
		for(int i = 0; i < length; i++){
			eventString.add(timex3List.item(i).getTextContent());
		}
		return eventString;	
	}
	
	public String getAttributeValue(NamedNodeMap attributes,String attributeName){
		if(attributes.getNamedItem(attributeName)!=null){
			return attributes.getNamedItem(attributeName).getNodeValue();
		}
		
		return "none";
	}
	
	public ArrayList<Timex3> getTimex3Object(org.w3c.dom.Document doc, String sentence){
		NodeList timex3NodeList =  doc.getElementsByTagName("TIMEX3");
		ArrayList<Timex3> timex3List = new ArrayList<Timex3>();
		int length = timex3NodeList.getLength();
		for(int i = 0; i < length; i++){
			Timex3 t = new Timex3();
			NamedNodeMap attributes = timex3NodeList.item(i).getAttributes(); 
			t.tid = attributes.getNamedItem("tid").getNodeValue();
			t.type = attributes.getNamedItem("type").getNodeValue();
			t.value = attributes.getNamedItem("value").getNodeValue();
			t.text = timex3NodeList.item(i).getTextContent();
			t.anchorTimeID = getAttributeValue(attributes, "anchorTimeID");
			t.functionInDocument = getAttributeValue(attributes, "functionInDocument");
			t.beginPoint = getAttributeValue(attributes,"beginPoint");
			t.endPoint = getAttributeValue(attributes,"endPoint");
			t.temporalFunction = Boolean.getBoolean(getAttributeValue(attributes, "temporalFunction"));			
			t.sentence = sentence;
			timex3List.add(t);
		}
		return timex3List;	
	}
	
	public ArrayList<Timex3> getTimex3Object(org.w3c.dom.Document doc){
		NodeList timex3NodeList =  doc.getElementsByTagName("TIMEX3");
		ArrayList<Timex3> timex3List = new ArrayList<Timex3>();
		int length = timex3NodeList.getLength();
		for(int i = 0; i < length; i++){
			Timex3 t = new Timex3();
			NamedNodeMap attributes = timex3NodeList.item(i).getAttributes(); 
			t.tid = attributes.getNamedItem("tid").getNodeValue();
			t.type = attributes.getNamedItem("type").getNodeValue();
			t.value = attributes.getNamedItem("value").getNodeValue();
			t.text = timex3NodeList.item(i).getTextContent();
			t.anchorTimeID = getAttributeValue(attributes, "anchorTimeID");
			t.functionInDocument = getAttributeValue(attributes, "functionInDocument");
			t.temporalFunction = Boolean.getBoolean(getAttributeValue(attributes, "temporalFunction"));
			t.sentence = t.text;
			timex3List.add(t);
		}
		return timex3List;	
	}
	
	
	public void printTimex3List(ArrayList<Timex3> timex3List){
		for(Timex3 t : timex3List){
			System.out.println(t.tid + " : " +t.text + " " + t.type + " " + t.value + " " + t.anchorTimeID + " " + t.functionInDocument + " " + t.temporalFunction);
		}
	}
	
	public int getTimeTokenNumber(String timeText, CoreMap sentence){
		List<CoreLabel> tokens = sentence.get(TokensAnnotation.class);
		int i = 0;
		String word = "";
		for(CoreLabel token: tokens) {	
			word += " " + token.get(TextAnnotation.class);
			if(word.contains(timeText)) {
				//System.out.println(word + " : " + timeText + " : " + i);
				return i;
			}
			i++;
		}
		
		return 10000;
	}
	
	public String getTimeToken(String timeText, CoreMap sentence){
		List<CoreLabel> tokens = sentence.get(TokensAnnotation.class);
		String word = "";
		for(CoreLabel token: tokens) {	
			word += " " + token.get(TextAnnotation.class);
			if(word.contains(timeText) || word.replace(" ,",",").contains(timeText) || word.replace(" '","'").contains(timeText)) {
				//System.out.println(word + " : " + timeText + " : " + i);
				return token.get(TextAnnotation.class);
			}
		}
		
		return "";
	}
	

	
	public static void main(String args[]){
		Timex3Extractor myTimeExtractor = new Timex3Extractor();
		
		FileInterface myFileInterface = new FileInterface();
		List<String> folderPath = Arrays.asList("TimeBank","AQUAINT");
		XMLInterface myXmlInterface = new XMLInterface();
		List<File> files = myFileInterface.genFileList(folderPath);	
		for(File file: files) {
	        String fileName = file.getAbsolutePath();
	        System.out.println(fileName);
			Document myDoc = myXmlInterface.parseFile(fileName);	       	        
	        
			ArrayList<Timex3> timex3List =  myTimeExtractor.getTimex3Object(myDoc);
			myTimeExtractor.printTimex3List(timex3List);
			//ArrayList<String> timeStrings = myTimeExtractor.getTimex3Strings(myDoc);
	        
	        //for(String timeString : timeStrings){
	        //	System.out.println(timeString);
	        //}
		}
	}
	
	
}
