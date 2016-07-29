package timeml;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NavigableSet;
import java.util.TreeMap;



import base.Dictionary;
import base.Event;
import base.EventInstance;
import base.Link;
import base.TLink;
import base.Timex3;

public class RelationFeatureExtractor extends FeatureExtractor {
	
	public RelationFeatureExtractor(){
		
	}
	public void printRefToFile(String fileName, String relType, String lid, String sentence1, String relatedID, String sentence2, String refFileName){
		BufferedWriter out;
		FileWriter fstream;
		try {
			fstream = new FileWriter(refFileName, true);
			out = new BufferedWriter(fstream);
			
			out.write(fileName + "\t" + relType + "\t" + lid + "\t" + sentence1 + "\t" + relatedID + "\t"+ sentence2);
			out.newLine();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void extractLinkEEFeatures(Dictionary dictionary, TreeMap<String, Link> linkListEE, TreeMap<String, EventInstance> eventInstanceList, String fileName, String linkType, String refFileName){
		NavigableSet<String> keys = linkListEE.navigableKeySet();
        for(String key: keys){
        	Link tLink = linkListEE.get(key);	
        	//System.out.println(key);
        	EventInstance e1 = eventInstanceList.get(tLink.ID);
        	EventInstance e2 = eventInstanceList.get(tLink.relatedID);
        	extractRelationFeatureAndPrintToFile(fileName, dictionary, tLink.relType, e1, e2, tLink, linkType);
        	printRefToFile(e1.event.fileName, tLink.relType, tLink.lid, e1.event.eventID, e2.event.eventID, "", refFileName);
        }
	}
	public void extractLinkTEFeatures(Dictionary dictionary,TreeMap<String, Link> linkListTE, TreeMap<String, EventInstance> eventInstanceList, TreeMap<String,Timex3> timeList,String fileName, String linkType,String refFileName){
		NavigableSet<String> keys = linkListTE.navigableKeySet();
        for(String key: keys){
        	Link tLink = linkListTE.get(key);	        	
        	Timex3 e1 = timeList.get(tLink.ID);
        	EventInstance e2 = eventInstanceList.get(tLink.relatedID);	  
        	extractRelationFeatureAndPrintToFile(fileName, dictionary, tLink.relType, e1, e2, tLink, linkType);
        	printRefToFile(e2.event.fileName, tLink.relType, tLink.lid, tLink.ID, e2.event.eventID, e2.event.sentence, refFileName);
        }	        
	}
	public void extractLinkETFeatures(Dictionary dictionary,TreeMap<String, Link> linkListET, TreeMap<String, EventInstance> eventInstanceList, TreeMap<String,Timex3> timeList,String fileName ,String linkType, String refFileName){
		NavigableSet<String> keys = linkListET.navigableKeySet();
        for(String key: keys){
        	Link tLink = linkListET.get(key);	 
        	EventInstance e1 = eventInstanceList.get(tLink.ID);
        	Timex3 e2 = timeList.get(tLink.relatedID);
        	extractRelationFeatureAndPrintToFile(fileName, dictionary, tLink.relType, e1, e2,tLink , linkType);
        	printRefToFile(e1.event.fileName, tLink.relType, tLink.lid, tLink.ID, tLink.relatedID, e2.sentence, refFileName);
        }
	}
	public void extractLinkTTFeatures(Dictionary dictionary,TreeMap<String, Link> linkListTT, TreeMap<String,Timex3> timeList,String fileName , String linkType,String refFileName){
		NavigableSet<String> keys = linkListTT.navigableKeySet();
        for(String key: keys){
        	Link tLink = linkListTT.get(key);	 
        	Timex3 e1 = timeList.get(tLink.ID);
        	Timex3 e2 = timeList.get(tLink.relatedID);
        	extractRelationFeatureAndPrintToFile(fileName, dictionary, tLink.relType, e1, e2,tLink, linkType);
        	printRefToFile(e1.fileName, tLink.relType, tLink.lid, tLink.ID, tLink.relatedID, e2.sentence, refFileName);
        }
	}
	
	public void extractLinkEEFeaturesP(Dictionary dictionary, TreeMap<String, Link> linkListEE, TreeMap<String, EventInstance> eventInstanceList, String fileName, String linkType, String refFileName){
		NavigableSet<String> keys = linkListEE.navigableKeySet();
        for(String key: keys){
        	Link tLink = linkListEE.get(key);	
        	if(tLink.hasPath){
	        	EventInstance e1 = eventInstanceList.get(tLink.ID);
	        	EventInstance e2 = eventInstanceList.get(tLink.relatedID);
	        	extractRelationFeatureAndPrintToFile(fileName, dictionary, tLink.relType, e1, e2, tLink, linkType);
	        	printRefToFile(e1.event.fileName, tLink.relType, tLink.lid, e1.event.eventID, e2.event.eventID, "", refFileName);
	        }
        }
	}
	public void extractLinkTEFeaturesP(Dictionary dictionary,TreeMap<String, Link> linkListTE, TreeMap<String, EventInstance> eventInstanceList, TreeMap<String,Timex3> timeList,String fileName, String linkType,String refFileName){
		NavigableSet<String> keys = linkListTE.navigableKeySet();
        for(String key: keys){
        	Link tLink = linkListTE.get(key);	  
        	if(tLink.hasPath){
	        	Timex3 e1 = timeList.get(tLink.ID);
	        	EventInstance e2 = eventInstanceList.get(tLink.relatedID);	  
	        	extractRelationFeatureAndPrintToFile(fileName, dictionary, tLink.relType, e1, e2, tLink, linkType);
	        	printRefToFile(e2.event.fileName, tLink.relType, tLink.lid, tLink.ID, e2.event.eventID, e2.event.sentence, refFileName);
	        }	   
        }
	}
	public void extractLinkETFeaturesP(Dictionary dictionary,TreeMap<String, Link> linkListET, TreeMap<String, EventInstance> eventInstanceList, TreeMap<String,Timex3> timeList,String fileName ,String linkType, String refFileName){
		NavigableSet<String> keys = linkListET.navigableKeySet();
        for(String key: keys){
        	Link tLink = linkListET.get(key);
        	if(tLink.hasPath){
	        	EventInstance e1 = eventInstanceList.get(tLink.ID);
	        	Timex3 e2 = timeList.get(tLink.relatedID);
	        	extractRelationFeatureAndPrintToFile(fileName, dictionary, tLink.relType, e1, e2,tLink , linkType);
	        	printRefToFile(e1.event.fileName, tLink.relType, tLink.lid, tLink.ID, tLink.relatedID, e2.sentence, refFileName);
	        }
        }
	}
	public void extractLinkTTFeaturesP(Dictionary dictionary,TreeMap<String, Link> linkListTT, TreeMap<String,Timex3> timeList,String fileName , String linkType,String refFileName){
		NavigableSet<String> keys = linkListTT.navigableKeySet();
        for(String key: keys){
        	Link tLink = linkListTT.get(key);	
        	if(tLink.hasPath){
	        	Timex3 e1 = timeList.get(tLink.ID);
	        	Timex3 e2 = timeList.get(tLink.relatedID);
	        	extractRelationFeatureAndPrintToFile(fileName, dictionary, tLink.relType, e1, e2,tLink, linkType);
	        	printRefToFile(e1.fileName, tLink.relType, tLink.lid, tLink.ID, tLink.relatedID, e2.sentence, refFileName);
	        }
        }
	}
	
	public void extractRelationFeatureAndPrintToFile(String fileName, Dictionary dictionary, String label, EventInstance e1, EventInstance e2, Link tLink, String linkType ){
		dictionary.genDict(e1.event.getBasicFeatureWords("E_BASIC_"));
		dictionary.genDict(e1.getIntermediateFeatureWords("E_INTERMEDIATE_"));
		dictionary.genDict(e1.event.getSynset("E_SYNSET_"));
		dictionary.genDict("E_CLASS_"+e1.event.eventClass);
		dictionary.genDict(e2.event.getBasicFeatureWords("E_BASIC_"));
		dictionary.genDict(e2.getIntermediateFeatureWords("E_INTERMEDIATE_"));
		dictionary.genDict(e2.event.getSynset("E_SYNSET_"));
		dictionary.genDict("E_CLASS_"+e2.event.eventClass);
		dictionary.genDict("E_TENSE_BIGRAM_", e1.intermediateFeatures.get("TENSE_")+"_"+e2.intermediateFeatures.get("TENSE_"));
    	dictionary.genDict("E_ASPECT_BIGRAM_", e1.intermediateFeatures.get("ASPECT_")+"_"+e2.intermediateFeatures.get("ASPECT_"));
    	dictionary.genDict("E_CLASS_BIGRAM_", e1.event.eventClass+"_"+e2.event.eventClass);
    	if(e1.intermediateFeatures.get("TENSE_").equals(e2.intermediateFeatures.get("TENSE_"))){
    		dictionary.genDict("E_TENSE_MATCH_", "TRUE");
    	}else{
    		dictionary.genDict("E_TENSE_MATCH_", "FALSE");
    	}
    	if(e1.intermediateFeatures.get("ASPECT_").equals(e2.intermediateFeatures.get("ASPECT_"))){
    		dictionary.genDict("E_ASPECT_MATCH_", "TRUE");
    	}else{
    		dictionary.genDict("E_ASPECT_MATCH_", "FALSE");
    	}
    	if(e1.event.eventClass.equals(e2.event.eventClass)){
    		dictionary.genDict("E_CLASS_MATCH_", "TRUE");
    	}else{
    		dictionary.genDict("E_CLASS_MATCH_", "FALSE");
    	}
    	if(e1.event.sentenceNumber < e2.event.sentenceNumber) dictionary.genDict("S_BEFORE_", "TRUE");
    	if(e1.event.sentenceNumber > e2.event.sentenceNumber) dictionary.genDict("S_AFTER_", "TRUE");
    	if(e1.event.sentenceNumber == e2.event.sentenceNumber) {
    		dictionary.genDict("S_SAME_", "TRUE");
    		int distance = (e1.event.tokenNumberS-e2.event.tokenNumberS);
    		dictionary.genDict("TOKEN_DIS_", distance+"");
    		//System.out.print(distance + "\t");
    		if(distance == -1) dictionary.genDict("TOKEN_DIS_", "NEXT1");
    		if(distance == -2) dictionary.genDict("TOKEN_DIS_", "NEXT2");
    		if(distance < -1) dictionary.genDict("TOKEN_DIS_","NEXTB");
    		if(distance > 1) dictionary.genDict("TOKEN_DIS_","NEXTA");
    	}
    	dictionary.genDict("E_PATH_", tLink.path);
    	dictionary.genDict("E_PATH_3GRAMS_",tLink.path3Grams);
    	dictionary.genDict("E_PATH_4GRAMS_",tLink.path4Grams);
    	dictionary.genDict("E_PATH_5GRAMS_",tLink.path5Grams);
    	dictionary.genDict("E_PATH_6GRAMS_",tLink.path6Grams);
    	dictionary.genDict("E_PATH_LENGTH_",tLink.pathLength+"");
    	dictionary.genDict("E_PATH_UP_LENGTH_",tLink.pathUpLength+"");
    	dictionary.genDict("E_PATH_DOWN_LENGTH_",tLink.pathDownLength+"");
    	dictionary.genDict("E_PAPATH_",tLink.PAPath);
    	dictionary.genDict("E_PA_VWALKS_",tLink.vertexWalks);
    	dictionary.genDict("E_PA_EWALKS_",tLink.edgeWalks);
    	dictionary.genDict("E_PA_SUBVWALKS_",tLink.subVWalks);
    	dictionary.genDict("E_PA_SUBEWALKS_",tLink.subEWalks);
    	dictionary.genDict(tLink.neighborFeatures);
    	dictionary.genDict(tLink.probPath);
    	dictionary.genDict(tLink.probPath2);
		TreeMap<Integer,Double> featVec = new TreeMap<Integer,Double>();
		
		addFeature(dictionary, e1.event.getBasicFeatureWords("E_BASIC_"), featVec);
    	addFeature(dictionary, e1.getIntermediateFeatureWords("E_INTERMEDIATE_"), featVec);
    	addFeature(dictionary, e1.event.getSynset("E_SYNSET_"), featVec);
    	addFeature(dictionary, "E_CLASS_"+e1.event.eventClass, featVec);
    	addFeature(500000,dictionary, e2.event.getBasicFeatureWords("E_BASIC_"), featVec);
    	addFeature(500000,dictionary, e2.getIntermediateFeatureWords("E_INTERMEDIATE_"), featVec);
    	addFeature(500000,dictionary, e2.event.getSynset("E_SYNSET_"), featVec);
    	addFeature(500000,dictionary, "E_CLASS_"+e2.event.eventClass, featVec);
    	addFeature(dictionary,"E_TENSE_BIGRAM_"+ e1.intermediateFeatures.get("TENSE_")+"_"+e2.intermediateFeatures.get("TENSE_"), featVec);
    	addFeature(dictionary,"E_ASPECT_BIGRAM_"+ e1.intermediateFeatures.get("ASPECT_")+"_"+e2.intermediateFeatures.get("ASPECT_"), featVec);
    	addFeature(dictionary,"E_CLASS_BIGRAM_"+ e1.event.eventClass+"_"+e2.event.eventClass, featVec);
    	if(e1.intermediateFeatures.get("TENSE_").equals(e2.intermediateFeatures.get("TENSE_"))){
    		addFeature(dictionary,"E_TENSE_MATCH_TRUE", featVec);
    	}else{
    		addFeature(dictionary,"E_TENSE_MATCH_FALSE", featVec);
    	}
    	if(e1.intermediateFeatures.get("ASPECT_").equals(e2.intermediateFeatures.get("ASPECT_"))){
    		addFeature(dictionary,"E_ASPECT_MATCH_TRUE", featVec);
    	}else{
    		addFeature(dictionary,"E_ASPECT_MATCH_FALSE", featVec);
    	}
    	if(e1.event.eventClass.equals(e2.event.eventClass)){
    		addFeature(dictionary,"E_CLASS_MATCH_TRUE", featVec);
    	}else{
    		addFeature(dictionary,"E_CLASS_MATCH_FALSE", featVec);
    	}
    	if(e1.event.sentenceNumber < e2.event.sentenceNumber) addFeature(dictionary,"S_BEFORE_TRUE",featVec);
    	if(e1.event.sentenceNumber > e2.event.sentenceNumber) addFeature(dictionary,"S_AFTER_TRUE",featVec);
    	if(e1.event.sentenceNumber == e2.event.sentenceNumber){
    		addFeature(dictionary,"S_SAME_TRUE",featVec);
    		int distance = (e1.event.tokenNumberS-e2.event.tokenNumberS);
    		//addFeature(dictionary,"TOKEN_DIS_"+ distance, featVec);
    		//System.out.println(distance);
    		if(distance == -1) addFeature(dictionary,"TOKEN_DIS_NEXT1",featVec);
    		if(distance == -2) addFeature(dictionary,"TOKEN_DIS_NEXT2",featVec);
    		if(distance < -1) addFeature(dictionary,"TOKEN_DIS_NEXTB",featVec);
    		if(distance > 1) addFeature(dictionary,"TOKEN_DIS_NEXTA",featVec);
    	}    
    	addFeature(dictionary, "E_PATH_"+tLink.path, featVec);
    	addFeature(dictionary,  "E_PATH_3GRAMS_", tLink.path3Grams, featVec);
    	//addFeature(dictionary, tLink.path4Grams, featVec);
    	//addFeature(dictionary, tLink.path5Grams, featVec);
    	//addFeature(dictionary, tLink.path6Grams, featVec);
    	//addFeature(dictionary, "E_PATH_LENGTH" + tLink.pathLength, featVec);
    	addFeature(dictionary, "E_PATH_UP_LENGTH_" + tLink.pathUpLength, featVec);
    	addFeature(dictionary, "E_PATH_DOWN_LENGTH_" + tLink.pathDownLength, featVec);
    	addFeature(dictionary, "E_PA_VWALKS_",tLink.vertexWalks, featVec);
    	addFeature(dictionary, "E_PA_EWALKS_",tLink.edgeWalks, featVec);
    	addFeature(dictionary, "E_PA_SUBVWALKS_",tLink.subVWalks, featVec);
    	addFeature(dictionary, "E_PA_SUBEWALKS_",tLink.subEWalks, featVec);
    	if(!tLink.PAPath.equals("PAPath")) addFeature(dictionary, "E_PAPATH_"+tLink.PAPath, featVec);
    	addFeature(dictionary, tLink.neighborFeatures, featVec);
    /*	NavigableSet<String> keys = tLink.probPath.navigableKeySet();
    	for(String key: keys){
    		addFeature(dictionary, key, featVec, tLink.probPath.get(key));
    	}
    	keys = tLink.probPath2.navigableKeySet();
    	for(String key: keys){
    		addFeature(dictionary, key, featVec, tLink.probPath2.get(key));
    	}
    	*/
    	dictionary.genDict("LABEL_REL_"+ linkType + "_",label);
    	String labelNum = dictionary.dict.get("LABEL_REL_" + linkType + "_"+label).toString();
    	printFeatureToFile(featVec, labelNum, fileName);
	}
	
	public void extractRelationFeatureAndPrintToFile(String fileName, Dictionary dictionary, String label, Timex3 e1, EventInstance e2, Link tLink,String linkType ){	
    	dictionary.genDict(e1.getBasicFeatureWords("T_"));
    	dictionary.genDict("T_DCT_",e1.isDCT+"");
    	//dictionary.genDict(e1.getSynset("T_SYNSET_"));
		dictionary.genDict(e2.event.getBasicFeatureWords("E_BASIC_"));
		dictionary.genDict(e2.getIntermediateFeatureWords("E_INTERMEDIATE_"));
		dictionary.genDict(e2.event.getSynset("E_SYNSET_"));
		dictionary.genDict("E_CLASS_"+e2.event.eventClass);	
    	dictionary.genDict("TE_PATH_", tLink.path);
    	dictionary.genDict("TE_PATH_3GRAMS_",tLink.path3Grams);
    	dictionary.genDict("TE_PATH_4GRAMS_",tLink.path4Grams);
    	dictionary.genDict("TE_PATH_5GRAMS_",tLink.path5Grams);
    	dictionary.genDict("TE_PATH_6GRAMS_",tLink.path6Grams);
    	dictionary.genDict("TE_PATH_LENGTH_",tLink.pathLength+"");
    	dictionary.genDict("TE_PATH_UP_LENGTH_",tLink.pathUpLength+"");
    	dictionary.genDict("TE_PATH_DOWN_LENGTH_",tLink.pathDownLength+"");
    	dictionary.genDict("TE_PAPATH_",tLink.PAPath);
    	dictionary.genDict("TE_PA_VWALKS_",tLink.vertexWalks);
    	dictionary.genDict("TE_PA_EWALKS_",tLink.edgeWalks);
    	dictionary.genDict("TE_PA_SUBVWALKS_",tLink.subVWalks);
    	dictionary.genDict("TE_PA_SUBEWALKS_",tLink.subEWalks);
    	dictionary.genDict(tLink.neighborFeatures);
    	dictionary.genDict(tLink.probPath);
    	dictionary.genDict(tLink.probPath2);
		TreeMap<Integer,Double> featVec = new TreeMap<Integer,Double>();
		addFeature(dictionary, e1.getBasicFeatureWords("T_"), featVec);
		addFeature(dictionary, "T_DCT_"+e1.isDCT, featVec);
    	//addFeature(dictionary, e1.getSynset("T_SYNSET_"), featVec);
    	addFeature(500000,dictionary, e2.event.getBasicFeatureWords("E_BASIC_"), featVec);
    	addFeature(500000,dictionary, e2.getIntermediateFeatureWords("E_INTERMEDIATE_"), featVec);
    	addFeature(500000,dictionary, e2.event.getSynset("E_SYNSET_"), featVec);
    	addFeature(500000,dictionary, "E_CLASS_"+e2.event.eventClass, featVec);
    	addFeature(dictionary, "TE_PATH_"+tLink.path, featVec);
    	addFeature(dictionary,  "TE_PATH_3GRAMS_", tLink.path3Grams, featVec);
    	//addFeature(dictionary, tLink.path4Grams, featVec);
    	//addFeature(dictionary, tLink.path5Grams, featVec);
    	//addFeature(dictionary, tLink.path6Grams, featVec);
    	//addFeature(dictionary, "E_PATH_LENGTH" + tLink.pathLength, featVec);
    	addFeature(dictionary, "TE_PATH_UP_LENGTH_" + tLink.pathUpLength, featVec);
    	addFeature(dictionary, "TE_PATH_DOWN_LENGTH_" + tLink.pathDownLength, featVec);
    	addFeature(dictionary, "TE_PA_VWALKS_",tLink.vertexWalks, featVec);
    	addFeature(dictionary, "TE_PA_EWALKS_",tLink.edgeWalks, featVec);
    	addFeature(dictionary, "TE_PA_SUBVWALKS_",tLink.subVWalks, featVec);
    	addFeature(dictionary, "TE_PA_SUBEWALKS_",tLink.subEWalks, featVec);
    	if(!tLink.PAPath.equals("PAPath")) addFeature(dictionary, "TE_PAPATH_"+tLink.PAPath, featVec);
    	addFeature(dictionary, tLink.neighborFeatures, featVec);
    /*	NavigableSet<String> keys = tLink.probPath.navigableKeySet();
    	for(String key: keys){
    		addFeature(dictionary, key, featVec, tLink.probPath.get(key));
    	}
    	keys = tLink.probPath2.navigableKeySet();
    	for(String key: keys){
    		addFeature(dictionary, key, featVec, tLink.probPath2.get(key));
    	}*/
    	dictionary.genDict("LABEL_REL_"+ linkType + "_",label);
    	String labelNum = dictionary.dict.get("LABEL_REL_"+ linkType+ "_" +label).toString();
    	printFeatureToFile(featVec, labelNum, fileName);
	}
	
	public void extractRelationFeatureAndPrintToFile(String fileName, Dictionary dictionary, String label , EventInstance e1, Timex3 e2,Link tLink, String linkType){
		//System.out.println(tLink.fileName);		
		dictionary.genDict(e1.event.getBasicFeatureWords("E_BASIC_"));
		dictionary.genDict(e1.getIntermediateFeatureWords("E_INTERMEDIATE_"));
		dictionary.genDict(e1.event.getSynset("E_SYNSET_"));
		dictionary.genDict("E_CLASS_"+e1.event.eventClass);
    	dictionary.genDict(e2.getBasicFeatureWords("T_"));
    	//dictionary.genDict(e2.getSynset("T_SYNSET_"));
    	dictionary.genDict("T_DCT_",e2.isDCT+"");
    	dictionary.genDict("ET_PATH_", tLink.path);
    	dictionary.genDict("ET_PATH_3GRAMS_",tLink.path3Grams);
    	dictionary.genDict("ET_PATH_LENGTH_",tLink.pathLength+"");
    	dictionary.genDict("ET_PATH_UP_LENGTH_",tLink.pathUpLength+"");
    	dictionary.genDict("ET_PATH_DOWN_LENGTH_",tLink.pathDownLength+"");
    	dictionary.genDict("ET_PAPATH_",tLink.PAPath);
    	dictionary.genDict("ET_PA_VWALKS_",tLink.vertexWalks);
    	dictionary.genDict("ET_PA_EWALKS_",tLink.edgeWalks);
    	dictionary.genDict("ET_PA_SUBVWALKS_",tLink.subVWalks);
    	dictionary.genDict("ET_PA_SUBEWALKS_",tLink.subEWalks);
    	dictionary.genDict(tLink.neighborFeatures);
    	dictionary.genDict(tLink.probPath);
    	dictionary.genDict(tLink.probPath2);
		TreeMap<Integer,Double> featVec = new TreeMap<Integer,Double>();	
		
		addFeature(dictionary, e1.event.getBasicFeatureWords("E_BASIC_"), featVec);
    	addFeature(dictionary, e1.getIntermediateFeatureWords("E_INTERMEDIATE_"), featVec);
    	addFeature(dictionary, e1.event.getSynset("E_SYNSET_"), featVec);
    	addFeature(dictionary, "E_CLASS_"+e1.event.eventClass, featVec);
    	addFeature(500000,dictionary, e2.getBasicFeatureWords("T_"), featVec);
    	addFeature(500000,dictionary, "T_DCT_"+e2.isDCT, featVec);
    	addFeature(dictionary, "ET_PATH_"+tLink.path, featVec);
    	addFeature(dictionary, "ET_PATH_3GRAMS_", tLink.path3Grams, featVec);
    	addFeature(dictionary, "ET_PATH_UP_LENGTH_" + tLink.pathUpLength, featVec);
    	addFeature(dictionary, "ET_PATH_DOWN_LENGTH_" + tLink.pathDownLength, featVec);
    	addFeature(dictionary, "ET_PA_VWALKS_",tLink.vertexWalks, featVec);
    	addFeature(dictionary, "ET_PA_EWALKS_",tLink.edgeWalks, featVec);
    	addFeature(dictionary, "ET_PA_SUBVWALKS_",tLink.subVWalks, featVec);
    	addFeature(dictionary, "ET_PA_SUBEWALKS_",tLink.subEWalks, featVec);
    	if(!tLink.PAPath.equals("PAPath")) addFeature(dictionary, "ET_PAPATH_"+tLink.PAPath, featVec);
   // 	addFeature(50000,dictionary, e2.getSynset("T_SYNSET_"), featVec);
  	    addFeature(dictionary, tLink.neighborFeatures, featVec); 
   /* 	NavigableSet<String> keys = tLink.probPath.navigableKeySet();
    	for(String key: keys){
    		addFeature(dictionary, key, featVec, tLink.probPath.get(key));
    	}
    	keys = tLink.probPath2.navigableKeySet();
    	for(String key: keys){
    		addFeature(dictionary, key, featVec, tLink.probPath2.get(key));
    	}*/
    	
    	dictionary.genDict("LABEL_REL_"+ linkType + "_",label);
    	String labelNum = dictionary.dict.get("LABEL_REL_"  + linkType+ "_" + label).toString();
    	printFeatureToFile(featVec, labelNum, fileName);
	}
	
	public void extractRelationFeatureAndPrintToFile(String fileName, Dictionary dictionary, String label , Timex3 e1, Timex3 e2, Link tLink, String linkType){
    	dictionary.genDict(e1.getBasicFeatureWords("T_"));
    	//dictionary.genDict(e1.getSynset("T_SYNSET_"));
    	dictionary.genDict(e2.getBasicFeatureWords("T_"));
    	dictionary.genDict(tLink.neighborFeatures);
    	dictionary.genDict(tLink.probPath);
    	dictionary.genDict(tLink.probPath2);
    	//dictionary.genDict(e2.getSynset("T_SYNSET_"));	
		TreeMap<Integer,Double> featVec = new TreeMap<Integer,Double>();
		addFeature(dictionary, e1.getBasicFeatureWords("T_"), featVec);
		//addFeature(dictionary, e1.getSynset("T_SYNSET_"), featVec);
    	addFeature(500000, dictionary, e2.getBasicFeatureWords("T_"), featVec);
    	//addFeature(500000, dictionary, e2.getSynset("T_SYNSET_"), featVec);
    	addFeature(dictionary, tLink.neighborFeatures, featVec); 
    	
   /*
     	NavigableSet<String> keys = tLink.probPath.navigableKeySet();
    	for(String key: keys){
    		addFeature(dictionary, key, featVec, tLink.probPath.get(key));
    	}
    	keys = tLink.probPath2.navigableKeySet();
    	for(String key: keys){
    		addFeature(dictionary, key, featVec, tLink.probPath2.get(key));
    	}
    	*/
    	dictionary.genDict("LABEL_REL_"+ linkType + "_",label);
    	String labelNum = dictionary.dict.get("LABEL_REL_"  + linkType+ "_" + label).toString();
    	printFeatureToFile(featVec, labelNum, fileName);
	}
	
	public void extractRelationFeatureAndPrintToFile(String fileName, Dictionary dictionary, String label, Event e1, Event e2, String linkType){
		TreeMap<Integer,Double> featVec = new TreeMap<Integer,Double>();
		addFeature(dictionary, e1.getBasicFeatureWords("E_BASIC_"), featVec);
    	addFeature(dictionary, e1.getIntermediateFeatureWords("E_INTERMEDIATE_"), featVec);
    	addFeature(dictionary, e1.getSynset("E_SYNSET_"), featVec);   	
    	addFeature(dictionary.getSize(),dictionary, e2.getBasicFeatureWords("E_BASIC_"), featVec);
    	addFeature(dictionary.getSize(),dictionary, e2.getIntermediateFeatureWords("E_INTERMEDIATE_"), featVec);
    	addFeature(dictionary.getSize(),dictionary, e2.getSynset("E_SYNSET_"), featVec);
    	dictionary.genDict("LABEL_REL_"+ linkType + "_",label);
    	String labelNum = dictionary.dict.get("LABEL_REL_"  + linkType+ "_" + label).toString();
    	printFeatureToFile(featVec, labelNum, fileName);
	}
	
	public void extractRelationFeatureAndPrintToFile(String fileName, Dictionary dictionary, String label, Timex3 e1, Event e2, String linkType){
		TreeMap<Integer,Double> featVec = new TreeMap<Integer,Double>();
		addFeature(dictionary, e1.getBasicFeatureWords("T_"), featVec);
    	addFeature(dictionary, e1.getSynset("T_SYNSET_"), featVec);
    	addFeature(dictionary.getSize(),dictionary, e2.getBasicFeatureWords("E_BASIC_"), featVec);
    	addFeature(dictionary.getSize(),dictionary, e2.getIntermediateFeatureWords("E_INTERMEDIATE_"), featVec);
    	addFeature(dictionary.getSize(),dictionary, e2.getSynset("E_SYNSET_"), featVec);
    	dictionary.genDict("LABEL_REL_"+ linkType + "_",label);
    	String labelNum = dictionary.dict.get("LABEL_REL_"  + linkType+ "_" + label).toString();
    	printFeatureToFile(featVec, labelNum, fileName);
	}
	
	public void extractRelationFeatureAndPrintToFile(String fileName, Dictionary dictionary, String label,Event e2, String linkType){
		TreeMap<Integer,Double> featVec = new TreeMap<Integer,Double>();
    	addFeature(dictionary.getSize(),dictionary, e2.getBasicFeatureWords("E_BASIC_"), featVec);
    	addFeature(dictionary.getSize(),dictionary, e2.getIntermediateFeatureWords("E_INTERMEDIATE_"), featVec);
    	addFeature(dictionary.getSize(),dictionary, e2.getSynset("E_SYNSET_"), featVec);
    	dictionary.genDict("LABEL_REL_"+ linkType + "_",label);
    	String labelNum = dictionary.dict.get("LABEL_REL_"  + linkType+ "_" + label).toString();
    	printFeatureToFile(featVec, labelNum, fileName);
	}
}
