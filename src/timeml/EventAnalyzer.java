package timeml;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.w3c.dom.Document;

import base.Event;


import edu.stanford.nlp.ling.CoreAnnotations.CharacterOffsetBeginAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.CharacterOffsetEndAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TrueCaseAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.XmlContextAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.util.CoreMap;

public class EventAnalyzer {
	static List<String> beList = new ArrayList<String>(Arrays.asList("be","'m","'re","'s","is","am","are","was","were","been"));
	static List<String> haveList = new ArrayList<String>(Arrays.asList("has","have","had"));
	static List<String> modalList = new ArrayList<String>(Arrays.asList("can","could","may","might","must","shall","should","will","would","'ll","'d"));
	static List<String> futureList = new ArrayList<String>(Arrays.asList("will","would","'ll"));
	/*static List<String> advOfTimeRelationList = new ArrayList<String>(Arrays.asList("now","then","today","tomorrow","tonight","annually",
			"daily","fortnightly","hourly","monthly","nightly","quarterly","weekly","yearly","later","next","soon"
			));*/
	//static List<String> excludeList = new ArrayList<String>(Arrays.asList("JJ","JJR","JJS","DT","PRP$","RB","RBR","RBS","POS",",",":",".","$","#","-LCB-","-LRB-","-RRB-","``","''"));//,"$",":"));
	static List<String> excludeList = new ArrayList<String>(Arrays.asList(""));
	static List<String> advOfTimeRelationList = new ArrayList<String>(Arrays.asList(""));
	//static List<String> excludeList = new ArrayList<String>(Arrays.asList(""));//,"$",":"));
	static List<String> generalList = new ArrayList<String>(Arrays.asList(""));
	EventExtractor myEventExtractor;
	
	public EventAnalyzer(){
		myEventExtractor = new EventExtractor();
	}
	

	
	public List<Event> getEvents(Document doc, ArrayList<java.util.Map.Entry<String,String>> eventTexts, CoreMap sentence){
		ArrayList<Event> events = new ArrayList<Event>();
		int idx = 0;
		String word = "", pos = "", lemma = "";
		String word_b1 = "", pos_b1 = "", lemma_b1 = "";
		String word_b2 = "", pos_b2 = "", lemma_b2 = "";
		String word_a1 = "", pos_a1 = "", lemma_a1 = "";
		String word_a2 = "", pos_a2 = "", lemma_a2 = "";
		boolean have_word = false;
		boolean be_word = false;
		boolean modal_word = false;
		boolean be = false;
		String adv = "none";
		boolean hasAdv = false;
		boolean future_word = false;
		int tokenNumber = 0;
		List<CoreLabel> tokens = sentence.get(TokensAnnotation.class);
		ArrayList<String> added = new ArrayList<String>();
		
		for(CoreLabel token: tokens) {			 
			 word = token.get(TextAnnotation.class).replaceAll("[0-9]", "X"); 
			 pos = token.get(PartOfSpeechAnnotation.class);
			 lemma = token.get(LemmaAnnotation.class).replaceAll("[0-9]", "X");
			 //List<String> xml = token.get(XmlContextAnnotation.class);
			 int tokenSize = tokens.size();
			 //System.out.println(word + " " +xml.toString());
			 if(advOfTimeRelationList.contains(word)) {
				 hasAdv = true;
				 adv = word;
			 }
			 for(Entry<String,String> eventText: eventTexts)
			 {
				// System.out.println(eventText.getValue()+" "+(word));
				 if(eventText.getValue().contains(" ")) {
					 StringTokenizer tk = new StringTokenizer(eventText.getValue());
					 String lastTok = "";
					 while(tk.hasMoreTokens()){
						 lastTok = tk.nextToken();
					 }
					 eventText.setValue(lastTok);
				 }
				 eventText.setValue(eventText.getValue().replaceAll("[0-9]", "X"));
				 if(eventText.getValue().contains("$")) eventText.setValue("$");
				 if(eventText.getValue().contains("%")) eventText.setValue("%");
				 
				 if(eventText.getValue().equals(word) && !added.contains(eventText.getKey())){	    
					 int i = 1;
					 if(idx+i<tokenSize){
						 do{
							 word_a1 = tokens.get(idx+i).get(TextAnnotation.class).replaceAll("[0-9]", "X"); 						 
							 pos_a1 =  tokens.get(idx+i).get(PartOfSpeechAnnotation.class);						
							 lemma_a1 = tokens.get(idx+i).get(LemmaAnnotation.class).replaceAll("[0-9]", "X"); 
							 i++;
						 }while(excludeList.contains(pos_a1) && idx+i<tokenSize);
						 if(idx+i<tokenSize){
							 do{
								 word_a2 = tokens.get(idx+i).get(TextAnnotation.class).replaceAll("[0-9]", "X"); 				 
								 pos_a2 = tokens.get(idx+i).get(PartOfSpeechAnnotation.class);						 
								 lemma_a2 = tokens.get(idx+i).get(LemmaAnnotation.class).replaceAll("[0-9]", "X");
								 i++;
							 }while(excludeList.contains(pos_a2) && idx+i<tokenSize);
						 }
					 }
					
					 if(haveList.contains(word_b1) || haveList.contains(word_b2)) have_word = true;
					 if(beList.contains(word_b1) || beList.contains(word_b2)) be_word = true;					 
					 if(modalList.contains(word_b1) || modalList.contains(word_b2)) modal_word = true;
					 if(futureList.contains(word_b1) || futureList.contains(word_b2)) future_word = true;
					 TreeMap<String,String> basicFeatures = new TreeMap<String,String>();
					 basicFeatures.put("WORD_E", word);
					 if(generalList.contains(pos_b1)) basicFeatures.put("WORD_B1", pos_b1); else basicFeatures.put("WORD_B1", word_b1);
					 if(generalList.contains(pos_b2)) basicFeatures.put("WORD_B2", pos_b2); else basicFeatures.put("WORD_B2", word_b2);
					 if(generalList.contains(pos_a1)) basicFeatures.put("WORD_A1", pos_a1); else basicFeatures.put("WORD_A1", word_a1);
					 if(generalList.contains(pos_a2)) basicFeatures.put("WORD_A2", pos_a2); else basicFeatures.put("WORD_A2", word_a2);
					 basicFeatures.put("POS_E", pos);
					 basicFeatures.put("POS_B1", pos_b1);
					 basicFeatures.put("POS_B2", pos_b2);
					 basicFeatures.put("POS_A1", pos_a1);
					 basicFeatures.put("POS_A2", pos_a2);
					 basicFeatures.put("LEMMA_E", lemma);
					 if(generalList.contains(pos_b1)) basicFeatures.put("LEMMA_B1", pos_b1); else basicFeatures.put("LEMMA_B1", lemma_b1);
					 if(generalList.contains(pos_b2)) basicFeatures.put("LEMMA_B2", pos_b2); else basicFeatures.put("LEMMA_B2", lemma_b2);
					 if(generalList.contains(pos_a1)) basicFeatures.put("LEMMA_A1", pos_a1); else basicFeatures.put("LEMMA_A1", lemma_a1);
					 if(generalList.contains(pos_a2)) basicFeatures.put("LEMMA_A2", pos_a2); else basicFeatures.put("LEMMA_A2", lemma_a2);
					 //basicFeatures.put("HAS_ADV", Boolean.toString(hasAdv));
					 if(!adv.equals("none"))basicFeatures.put("ADV", adv);
					 //basicFeatures.put("HAVE_WORD", Boolean.toString(have_word));
					 //basicFeatures.put("BE_WORD", Boolean.toString(be_word));
					 //basicFeatures.put("MODAL_WORD", Boolean.toString(modal_word));
					 //if(future_word)basicFeatures.put("FUTURE","TRUE");
					 Event event = new Event(basicFeatures);
					 event.eventID = eventText.getKey();
					 event.sentence = sentence.toString().replace("\n", " ").replace("\r", " ");
					 //System.out.println(event.sentence);
					 event.eventClass = myEventExtractor.getAttrValue(myEventExtractor.findEventNode(doc, "eid", event.eventID),"class");
					 //System.out.println(eventClass);
					 //event.intermediateFeatures.put("CLASS", eventClass);
					 event.text = word;		
					 event.tokenNumberS = idx ;
					// System.out.println(event.tokenNumberS);
					 events.add(event);
					 added.add(event.eventID);
					 have_word = false;
					 be_word = false;
					 modal_word = false;
					 future_word = false;
					 be = false;
					 break;
		         }
				 
			 }
			 
			 
			 if(!excludeList.contains(pos)){
				 word_b2 = word_b1;
				 word_b1 = word;
				 
				 pos_b2 = pos_b1;
				 pos_b1 = pos;
				 
				 lemma_b2 = lemma_b1;
				 lemma_b1 = lemma;
			 }
			 idx++;
		}
		return events;
	}
	
	

}
