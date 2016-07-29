package enju;

import java.util.NavigableSet;
import java.util.TreeMap;

import base.Event;
import base.EventInstance;
import base.Link;
import base.Timex3;

public class EnjuFeatureExtractor {
	EnjuPathExtractor enjuPathExtractor;
	EnjuPAPathExtractor enjuPAPathExtractor;
	
	public EnjuFeatureExtractor(){
		
	}
	
	public EnjuFeatureExtractor(String fileName){
		enjuPathExtractor = new EnjuPathExtractor(fileName);
	    enjuPAPathExtractor = new EnjuPAPathExtractor(fileName);
	}
	
	public void extractPathFeature(TreeMap<String, EventInstance> eventInstanceList, TreeMap<String,Timex3> timeList, TreeMap<String, Link> TLinkListEE, TreeMap<String, Link> TLinkListTE, TreeMap<String, Link> TLinkListET, TreeMap<String, Link> TLinkListTT){
		 NavigableSet<String> keys = TLinkListEE.navigableKeySet();
	     for(String key: keys){
         	Link tLink = TLinkListEE.get(key);
         	Event e1 = eventInstanceList.get(tLink.ID).event;
         	Event e2 = eventInstanceList.get(tLink.relatedID).event;
         	if(tLink.relType.equals("IDENTITY")) tLink.relType = "SIMULTANEOUS";
         	if(e1.sentenceNumber == e2.sentenceNumber && !e1.text.equals(e2.text)){
         		//System.out.println(e1.sentenceNumber + " " + e1.text + " "+ e2.text);
         		//System.out.println(e1.sentence);
         		tLink.path = enjuPathExtractor.extractPath(e1.sentenceNumber,e1.text,e2.text);
         		tLink.path3Grams = enjuPathExtractor.getPath3Grams(tLink.path,e1.sentenceNumber,e1.text,e2.text);
         		tLink.path4Grams = enjuPathExtractor.getPath4Grams(tLink.path,e1.sentenceNumber,e1.text,e2.text);
         		tLink.path5Grams = enjuPathExtractor.getPathNGrams(tLink.path,e1.sentenceNumber,e1.text,e2.text,5);
         		tLink.path6Grams = enjuPathExtractor.getPathNGrams(tLink.path,e1.sentenceNumber,e1.text,e2.text,6);
         		tLink.pathLength = enjuPathExtractor.getPathLength(tLink.path);
         		tLink.pathUpLength = enjuPathExtractor.getPathUpLength(tLink.path);
         		tLink.pathDownLength = enjuPathExtractor.getPathDownLength(tLink.path);
         		//sameSent++;
         		//System.out.println(key+ " " +e1.tokenNumberF + " " + e2.tokenNumberF);
         		if(e1.tokenNumberF < 10000 && e2.tokenNumberF < 10000 && e1.tokenNumberF > 0 && e2.tokenNumberF > 0){
	            		tLink.PAPath = enjuPAPathExtractor.getPAPath(e1.sentenceNumber,e1.text,e2.text,e1.tokenNumberF,e2.tokenNumberF);
	            		tLink.vertexWalks = enjuPAPathExtractor.getVertexWalks(e1.sentenceNumber,e1.text,e2.text,e1.tokenNumberF,e2.tokenNumberF);
	            		tLink.edgeWalks = enjuPAPathExtractor.getEdgeWalks(e1.sentenceNumber,e1.text,e2.text,e1.tokenNumberF,e2.tokenNumberF);
	            		tLink.subVWalks = enjuPAPathExtractor.getSubVWalks(tLink.vertexWalks);
	            		tLink.subEWalks = enjuPAPathExtractor.getSubEWalks(tLink.edgeWalks);
	            		//System.out.println("pa path " + tLink.PAPath);
         		}
         	//	System.out.println(e1.eventID + " " + e2.eventID);
         	//	System.out.println(tLink.path);
         	}
		}   
        keys = TLinkListTE.navigableKeySet();
        for(String key: keys){
        	Link tLink = TLinkListTE.get(key);
        	//System.out.println(key);
        	//System.out.println(tLink.ID);
        	Timex3 e1 = timeList.get(tLink.ID);
        	Event e2 = eventInstanceList.get(tLink.relatedID).event;
        	if(tLink.relType.equals("IDENTITY")) tLink.relType = "SIMULTANEOUS";
        	if(e1.sentenceNumber == e2.sentenceNumber && !e1.text.equals(e2.text)){
        		//System.out.println(e1.sentenceNumber + " " + e1.text + " "+ e2.text);
        		//System.out.println(e1.sentence);
        		tLink.path = enjuPathExtractor.extractPath(e1.sentenceNumber,e1.timeToken,e2.text);
        		tLink.path3Grams = enjuPathExtractor.getPath3Grams(tLink.path,e1.sentenceNumber,e1.timeToken,e2.text);
        		tLink.path4Grams = enjuPathExtractor.getPath4Grams(tLink.path,e1.sentenceNumber,e1.timeToken,e2.text);
        		tLink.path5Grams = enjuPathExtractor.getPathNGrams(tLink.path,e1.sentenceNumber,e1.timeToken,e2.text,5);
        		tLink.path6Grams = enjuPathExtractor.getPathNGrams(tLink.path,e1.sentenceNumber,e1.timeToken,e2.text,6);
        		tLink.pathLength = enjuPathExtractor.getPathLength(tLink.path);
        		tLink.pathUpLength = enjuPathExtractor.getPathUpLength(tLink.path);
        		tLink.pathDownLength = enjuPathExtractor.getPathDownLength(tLink.path);
        		//sameSent++;
        		//System.out.println(key+ " " +e1.tokenNumberF + " " + e2.tokenNumberF);
        		if(e1.tokenNumberF < 10000 && e2.tokenNumberF < 10000 && e1.tokenNumberF > 0 && e2.tokenNumberF > 0){
            		tLink.PAPath = enjuPAPathExtractor.getPAPath(e1.sentenceNumber,e1.timeToken,e2.text,e1.tokenNumberF,e2.tokenNumberF);
            		tLink.vertexWalks = enjuPAPathExtractor.getVertexWalks(e1.sentenceNumber,e1.timeToken,e2.text,e1.tokenNumberF,e2.tokenNumberF);
            		tLink.edgeWalks = enjuPAPathExtractor.getEdgeWalks(e1.sentenceNumber,e1.timeToken,e2.text,e1.tokenNumberF,e2.tokenNumberF);
            		tLink.subVWalks = enjuPAPathExtractor.getSubVWalks(tLink.vertexWalks);
            		tLink.subEWalks = enjuPAPathExtractor.getSubEWalks(tLink.edgeWalks);
            		//System.out.println("pa path" + tLink.PAPath);
        		}
        	}
    	}
     
        keys = TLinkListET.navigableKeySet();
        for(String key: keys){
        	Link tLink = TLinkListET.get(key);
        	Timex3 e2 = timeList.get(tLink.relatedID);
        	Event e1 = eventInstanceList.get(tLink.ID).event;
        	if(tLink.relType.equals("IDENTITY")) tLink.relType = "SIMULTANEOUS";
        	//System.out.println(key);
        	if(e1.sentenceNumber == e2.sentenceNumber && !e1.text.equals(e2.text)){
        		tLink.path = enjuPathExtractor.extractPath(e1.sentenceNumber,e1.text,e2.timeToken);
        		tLink.path3Grams = enjuPathExtractor.getPath3Grams(tLink.path,e1.sentenceNumber,e1.text,e2.timeToken);
        		tLink.pathLength = enjuPathExtractor.getPathLength(tLink.path);
        		tLink.pathUpLength = enjuPathExtractor.getPathUpLength(tLink.path);
        		tLink.pathDownLength = enjuPathExtractor.getPathDownLength(tLink.path);
        		//sameSent++;
        		if(e1.tokenNumberF < 10000 && e2.tokenNumberF < 10000 && e1.tokenNumberF > 0 && e2.tokenNumberF > 0){
            		tLink.PAPath = enjuPAPathExtractor.getPAPath(e1.sentenceNumber,e1.text,e2.timeToken,e1.tokenNumberF,e2.tokenNumberF);
            		tLink.vertexWalks = enjuPAPathExtractor.getVertexWalks(e1.sentenceNumber,e1.text,e2.timeToken,e1.tokenNumberF,e2.tokenNumberF);
            		tLink.edgeWalks = enjuPAPathExtractor.getEdgeWalks(e1.sentenceNumber,e1.text,e2.timeToken,e1.tokenNumberF,e2.tokenNumberF);
            		tLink.subVWalks = enjuPAPathExtractor.getSubVWalks(tLink.vertexWalks);
            		tLink.subEWalks = enjuPAPathExtractor.getSubEWalks(tLink.edgeWalks);
        		}       	
        	}          
    	}
   /*     
        keys = TLinkListTT.navigableKeySet();
        for(String key: keys){
        	Link tLink = TLinkListTT.get(key);
        	//System.out.println(key);
        	//System.out.println(tLink.ID);
        	Timex3 e1 = timeList.get(tLink.ID);
        	Timex3 e2 = timeList.get(tLink.relatedID);
        	if(tLink.relType.equals("IDENTITY")) tLink.relType = "SIMULTANEOUS"; 
    	}*/
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
