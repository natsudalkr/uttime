package base;

import java.util.ArrayList;
import java.util.NavigableSet;
import java.util.TreeMap;

public class EventInstance {
	public Event event;
	public String eiid;
	public TreeMap<String,String> intermediateFeatures;
	
	public EventInstance(Event event, String eiid){
		this.event = event;
		intermediateFeatures = new TreeMap<String, String>();
		this.eiid = eiid;
	}
	
	public String getIntermediateFeatureWord(String featureName){
		return featureName+"_"+intermediateFeatures.get(featureName);
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
	
	public void addIntermediateFeature(TreeMap<String,String> intermediateFeatures){
		this.intermediateFeatures = intermediateFeatures;
	}
}
