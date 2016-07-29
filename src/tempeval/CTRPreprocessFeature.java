package tempeval;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NavigableSet;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import timeml.EventExtractor;
import timeml.EventAnalyzer;
import timeml.RelationExtractor;
import timeml.Timex3Extractor;
import toolinterface.EnjuInterface;
import toolinterface.FileInterface;
import toolinterface.StanfordAnnotatorInterface;
import toolinterface.WordnetInterface;
import toolinterface.XMLInterface;
import base.Event;
import base.EventInstance;
import base.Link;
import base.TLink;
import base.Timex3;

import edu.stanford.nlp.ling.CoreAnnotations.CharacterOffsetBeginAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.CharacterOffsetEndAnnotation;
import edu.stanford.nlp.util.CoreMap;
import edu.uci.ics.jung.graph.Graph;
import enju.EnjuFeatureExtractor;

public class CTRPreprocessFeature {
	XMLInterface xmlInterface;
	EventExtractor eventExtractor;
	EventAnalyzer eventAnalyzer;
	StanfordAnnotatorInterface annotatorInterface;
	FileInterface fileInterface;
	Timex3Extractor timeExtractor;
	List<File> files;	
	String wordNetPath;
	WordnetInterface wordnetInterface;
	EnjuInterface enjuInterface;
	EnjuFeatureExtractor enjuFeatureExtractor;
	RelationExtractor relationExtractor;
	CTRRelationFeatureExtractor relationFeatureExtractor;
	
	public CTRPreprocessFeature(String dataPath){
		xmlInterface = new XMLInterface();
		eventExtractor = new EventExtractor();
		eventAnalyzer = new EventAnalyzer();
		annotatorInterface = new StanfordAnnotatorInterface("tokenize, cleanxml, ssplit,  pos, lemma");
		fileInterface = new FileInterface();
		timeExtractor = new Timex3Extractor();
		wordnetInterface = new WordnetInterface("WordNet-3.0/dict");
		files = fileInterface.genFileList(dataPath);
		relationExtractor = new RelationExtractor();
		relationFeatureExtractor = new CTRRelationFeatureExtractor();
	}

	public static void main(String args[]){
		String dataPath = args[0];
		String featurePath = args[1];
		String enjuFilePath = args[2];
		CTRPreprocessFeature myCTRPreprocessFeature = new CTRPreprocessFeature(dataPath);
		myCTRPreprocessFeature.genFeatureFiles(dataPath, featurePath, enjuFilePath);

	}

	public void genFeatureFiles(String dataPath, String featurePath, String enjuFilePath){
		DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
	    DocumentBuilder docBuilder = null;
	    Document doc;
	    Element root;
		for(File file: files) {
			String fileName = file.getAbsolutePath();
			System.out.println("Processing " + fileName);
			Document timeDoc = xmlInterface.parseFile(fileName);
			TreeMap<String, Link> TLinkListEE = new TreeMap<String, Link>();	  
		    TreeMap<String, Link> TLinkListTE = new TreeMap<String, Link>();	
		    TreeMap<String, Link> TLinkListET = new TreeMap<String, Link>();	 
		    TreeMap<String, Link> TLinkListTT = new TreeMap<String, Link>();

		    /* All TE links are inverted to ET links*/
			relationExtractor.extractRelation4(fileName,timeDoc,TLinkListEE,TLinkListTE,TLinkListET,TLinkListTT,null,null);
			String fileName_enju = fileName.replace(dataPath, enjuFilePath).replace(".tml", ".enju");
			enjuInterface = new EnjuInterface(fileName_enju);
		    enjuFeatureExtractor = new EnjuFeatureExtractor(fileName_enju);
		    
			TreeMap<String,Timex3> timeList = new TreeMap<String,Timex3>();  
	        Timex3 t0 = extractDCT(timeDoc);
	        fileName = file.getParent()+"/"+file.getName();
	        t0.fileName = fileName;	        
	        timeList.put("t0", t0); 
			
	        TreeMap<String, EventInstance> eventInstanceList = new TreeMap<String,EventInstance>();
	        extractTimesAndEvents(fileName, fileName_enju,timeDoc,eventInstanceList,timeList);	  	         
	        
	        enjuFeatureExtractor.extractPathFeature(eventInstanceList, timeList, TLinkListEE, TLinkListTE, TLinkListET, TLinkListTT);    
	        fileName = fileName.replace(dataPath, featurePath).replace(".tml",".feature");
			try {
				docBuilder = dbfac.newDocumentBuilder();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			}
		    doc = docBuilder.newDocument();	
		    root = doc.createElement("feature");
		    doc.appendChild(root);
		   
		    Element linkType = doc.createElement("EE"); 
		    root.appendChild(linkType);

		    NavigableSet<String> keys = TLinkListEE.navigableKeySet();
		    for(String key : keys){
		    	Element link = doc.createElement("TLINK");
		    	linkType.appendChild(link);	
		    	CTRFeatVecSet featVecSet = new CTRFeatVecSet();
		    	
		    	Link tLink = TLinkListEE.get(key);
		    	EventInstance e1 = eventInstanceList.get(tLink.ID);
	        	EventInstance e2 = eventInstanceList.get(tLink.relatedID);
	        	
	        	relationFeatureExtractor.extractRelationFeature(e1, e2, tLink, featVecSet);    
	        	
	        	link.setAttribute("lid", key);
	        	link.setAttribute("type", "EE");
	        	String baseline = relationFeatureExtractor.getFeatVecString(featVecSet.baseline);	        	       	
	        	link.setAttribute("baseline", baseline);
	        	String deepsyn = relationFeatureExtractor.getFeatVecString(featVecSet.deepsyn);	        	       	
	        	link.setAttribute("deepsyn", deepsyn);
	        	
	        	link.setAttribute("relType", tLink.relType);
	        	link.setAttribute("ID", TLinkListEE.get(key).ID);
	        	link.setAttribute("relatedID", TLinkListEE.get(key).relatedID);
	        	link.setAttribute("reverse", tLink.reverse);
		    }

		    /* All TE links were inverted to ET links, so TE size = 0. This part of code does nothing. */
		    linkType = doc.createElement("TE");
		    root.appendChild(linkType);
		    keys = TLinkListTE.navigableKeySet();
		    for(String key : keys){
		    	Element link = doc.createElement("TLINK");
		    	linkType.appendChild(link);
		    	CTRFeatVecSet featVecSet = new CTRFeatVecSet();	    	
		    	Link tLink = TLinkListTE.get(key);	        	
	        	Timex3 t1 = timeList.get(tLink.ID);
	        	EventInstance e2 = eventInstanceList.get(tLink.relatedID);	 
	        	relationFeatureExtractor.extractRelationFeature(t1, e2, tLink, featVecSet);    
		    	
	        	link.setAttribute("lid", key);
	        	link.setAttribute("type", "TE");
	        	String baseline = relationFeatureExtractor.getFeatVecString(featVecSet.baseline);	        	       	
	        	link.setAttribute("baseline", baseline);
	        	String deepsyn = relationFeatureExtractor.getFeatVecString(featVecSet.deepsyn);	        	       	
	        	link.setAttribute("deepsyn", deepsyn);
	        
	        	link.setAttribute("relType", tLink.relType);
	        	link.setAttribute("ID", TLinkListTE.get(key).ID);
	        	link.setAttribute("relatedID", TLinkListTE.get(key).relatedID);
	        	link.setAttribute("reverse", tLink.reverse);
		    } 

		    linkType = doc.createElement("ET");
		    root.appendChild(linkType);
		    keys = TLinkListET.navigableKeySet();
		    for(String key : keys){
		    	Element link = doc.createElement("TLINK");
		    	linkType.appendChild(link);
		    	CTRFeatVecSet featVecSet = new CTRFeatVecSet();
		    	Link tLink = TLinkListET.get(key);	 
	        	EventInstance e1 = eventInstanceList.get(tLink.ID);
	        	Timex3 t2 = timeList.get(tLink.relatedID);
		    	relationFeatureExtractor.extractRelationFeature(e1, t2, tLink, featVecSet);   
		    	
		    	link.setAttribute("lid", key);
		    	link.setAttribute("type", "ET");
		    	String baseline = relationFeatureExtractor.getFeatVecString(featVecSet.baseline);	        	       	
	        	link.setAttribute("baseline", baseline);
	        	String deepsyn = relationFeatureExtractor.getFeatVecString(featVecSet.deepsyn);	        	       	
	        	link.setAttribute("deepsyn", deepsyn);
	        	
	        	link.setAttribute("relType", tLink.relType);
	        	link.setAttribute("ID", TLinkListET.get(key).ID);
	        	link.setAttribute("relatedID", TLinkListET.get(key).relatedID);
	        	link.setAttribute("reverse", tLink.reverse);
		    }

		    linkType = doc.createElement("TT");
		    root.appendChild(linkType);
		    keys = TLinkListTT.navigableKeySet();
		    for(String key : keys){
		    	Element link = doc.createElement("TLINK");
		    	linkType.appendChild(link);
		    	CTRFeatVecSet featVecSet = new CTRFeatVecSet();
		    	Link tLink = TLinkListTT.get(key);	 
	        	Timex3 t1 = timeList.get(tLink.ID);
	        	Timex3 t2 = timeList.get(tLink.relatedID);
		    	relationFeatureExtractor.extractRelationFeature(t1, t2, tLink, featVecSet);   
		    	
		    	link.setAttribute("lid", key);
		    	link.setAttribute("type", "TT");
		    	String baseline = relationFeatureExtractor.getFeatVecString(featVecSet.baseline);	        	       	
	        	link.setAttribute("baseline", baseline);
	        	String deepsyn = relationFeatureExtractor.getFeatVecString(featVecSet.deepsyn);	        	       	
	        	link.setAttribute("deepsyn", deepsyn);
	        	  	       	     	
	        	link.setAttribute("relType", tLink.relType);
	        	link.setAttribute("ID", TLinkListTT.get(key).ID);
	        	link.setAttribute("relatedID", TLinkListTT.get(key).relatedID);
	        	link.setAttribute("reverse", tLink.reverse);
		    }
		    writeToFile(fileName, doc);
		}
	}
	

	private void writeToFile(String fileName, Document doc){
        //set up a transformer
        TransformerFactory transfac = TransformerFactory.newInstance();
        Transformer trans;
		try {
			trans = transfac.newTransformer();
			trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
	        trans.setOutputProperty(OutputKeys.INDENT, "yes");
       
	        FileWriter fw = new FileWriter(fileName);
	        StreamResult result = new StreamResult(fw);
	        DOMSource source = new DOMSource(doc);
	        trans.transform(source, result);    
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}   
	}

	public void extractTimesAndEvents(String fileName, String fileName_enju, Document doc, TreeMap<String, EventInstance> eventInstanceList,TreeMap<String,Timex3> timeList){
		String text = xmlInterface.serializeNode(doc.getElementsByTagName("TEXT").item(0));
		List<CoreMap> sentences = annotatorInterface.annotate(text);
		int offset1 = text.indexOf("<TEXT>")+6, offset2;
		int sentenceNumber = 0;
		int tokenCount = 0;
		int offset =0;
		enjuInterface = new EnjuInterface(fileName_enju);
		for(CoreMap sentence:sentences)
    	{       		
        	offset2 = sentence.get(CharacterOffsetBeginAnnotation.class);	        	
        	String sentenceString = text.substring(offset1, offset2).trim() + sentence.toString();

        	Document sentenceDoc = xmlInterface.parseString(sentenceString);
        	ArrayList<java.util.Map.Entry<String,String>> eventStrings =  eventExtractor.getEventStrings(sentenceDoc);
        	List<Event> events = eventAnalyzer.getEvents(sentenceDoc,eventStrings, sentence);
        	List<Timex3> times = timeExtractor.getTimex3Object(sentenceDoc);
        	tokenCount = enjuInterface.getFirstTokIdInSentence(sentenceNumber) ;
        	for(Event event: events){
        		
        		ArrayList<String> eiidList = eventExtractor.findEventInstance(doc, event.eventID);
        		event.synset = wordnetInterface.getSynset(event.basicFeatures.get("WORD_E"));
        		for(String eiid : eiidList)
        		{

        			EventInstance eventInstance = new EventInstance(event, eiid);
        			eventInstanceList.put(eventInstance.eiid,eventInstance);
        		}	        		
            	//event.addBasicFeatureWords(enjuInterface.getFeatureFromXml(sentenceNumber, event.text));
        		event.fileName = fileName;
        		event.sentenceNumber = sentenceNumber;
        		event.tokenNumberF = event.tokenNumberS + tokenCount;
        		event.enjuTokenId = enjuInterface.getTokId(event.sentenceNumber, event.text, event.tokenNumberF);
        	}	
        	
        	for(Timex3 time :times){
        		timeList.put(time.tid, time);
        		time.synset = wordnetInterface.getSynset(time.text);
        		time.fileName = fileName;
        		time.sentenceNumber = sentenceNumber;
        		time.tokenNumberF = timeExtractor.getTimeTokenNumber(time.text,sentence) + tokenCount ;
        		time.timeToken = timeExtractor.getTimeToken(time.text,sentence);
        		time.enjuTokenId = enjuInterface.getTokId(time.sentenceNumber, time.text, time.tokenNumberF);
        	}
        	offset1 = sentence.get(CharacterOffsetEndAnnotation.class);
        	sentenceNumber++;
        	if(sentenceString.contains("--")) sentenceNumber--;
    	}	
        eventExtractor.getIntermediateFeatures(doc, eventInstanceList);
	}
	
	public Timex3 extractDCT(Document doc){
		NodeList DCTs = (doc.getElementsByTagName("DCT").item(0).getChildNodes());
        int length = DCTs.getLength();
        Timex3 t0 = new Timex3();
        for(int i = 0; i<length; i++){
        	Node DCT = DCTs.item(i);	               	
	        t0.tid = DCT.getAttributes().getNamedItem("tid").getNodeValue();
			t0.type = DCT.getAttributes().getNamedItem("type").getNodeValue();
			t0.isDCT = true;
			t0.value = DCT.getAttributes().getNamedItem("value").getNodeValue();
        }
        return t0;
	}
}
