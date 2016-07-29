package base;

import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.NavigableSet;
import java.util.TreeMap;

public class Event {
	public String eventID;
	public String eventClass;
	
	public String fileName;
	public int sentenceNumber;
	public int tokenNumberF = -1;
	public int tokenNumberS = -1;
	public ArrayList<String> synset;
	public TreeMap<String,String> intermediateFeatures;
	public String sentence;
	public String text;
	public int enjuTokenId;
	
	public TreeMap<String,String> basicFeatures;
	
	
	public Event(){
		basicFeatures = new TreeMap<String, String>();
		intermediateFeatures = new TreeMap<String, String>();
	}
	
	public Event(TreeMap<String,String> basicFeatures){
		this.basicFeatures = basicFeatures; 
		intermediateFeatures = new TreeMap<String, String>();
	}
		
	public ArrayList<String> getBasicFeatureWords(String prefix){
		ArrayList<String> featureWords = new ArrayList<String>();
		NavigableSet<String> keys = basicFeatures.navigableKeySet();
		for(String key: keys){
			featureWords.add(prefix+key+"_"+basicFeatures.get(key));
		}
		return featureWords;
	}
	
	public ArrayList<String> getBasicFeatureWords(String featureType, String prefix){
		ArrayList<String> featureWords = new ArrayList<String>();
		NavigableSet<String> keys = basicFeatures.navigableKeySet();
		for(String key: keys){
			if(key.startsWith(featureType)) featureWords.add(prefix+key+"_"+basicFeatures.get(key));
		}
		return featureWords;
	}
	
	public void addBasicFeatureWords(TreeMap<String,String> features){
		 basicFeatures.putAll(features);
	}
	
	public String getBasicFeatureWord(String featureName){
		return featureName+"_"+basicFeatures.get(featureName);
	}
	
	public ArrayList<String> getIntermediateFeatureWords(String prefix){
		ArrayList<String> featureWords = new ArrayList<String>();
		NavigableSet<String> keys = intermediateFeatures.navigableKeySet();
		for(String key: keys){
			featureWords.add(prefix+key+"_"+intermediateFeatures.get(key));
		}
		return featureWords;
	}
	
	public ArrayList<String> getIntermediateFeatureWords(String featureType, String prefix){
		ArrayList<String> featureWords = new ArrayList<String>();
		NavigableSet<String> keys = intermediateFeatures.navigableKeySet();
		for(String key: keys){
			if(key.startsWith(featureType)) featureWords.add(prefix+key+"_"+intermediateFeatures.get(key));
		}
		return featureWords;
	}
	
	public String getEventID(){
		return eventID;
	}
	
	public void setEventID(String eventID){
		this.eventID = eventID;
	}
	
	public ArrayList<String> getSynset(String prefix){
		ArrayList<String> synsetList = new ArrayList<String>();
		for(String word: synset){
			synsetList.add(prefix + word);
		}
		
		return synsetList;
	}

}
