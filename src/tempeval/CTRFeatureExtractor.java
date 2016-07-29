package tempeval;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.NavigableSet;
import java.util.TreeMap;

public class CTRFeatureExtractor {
	public CTRFeatureExtractor(){
		
	}
	
	public void addFeature(ArrayList<String> words, LinkedList<String> featVec){
		for(String word:words){
			featVec.add(word.replace(" ", "_"));
		}
	}
	
	public void addFeature(String featureType, ArrayList<String> words, LinkedList<String> featVec){
		for(String word:words){
			featVec.add(featureType+ word.replace(" ", "_"));
		}
	}
	
	public void addFeature(String word, LinkedList<String> featVec){
		featVec.add(word.replace(" ", "_"));	
	}
	
	public String getFeatVecString(LinkedList<String> featVec){
		String s = "";
		for(String word: featVec){
			s += word +"}1.0  ";
		}
		return s.toLowerCase();
	}
	
	public String getFeatVecStringWithWeight(TreeMap<String, Double> featVec){
		String s = "";
		NavigableSet<String> keys = featVec.navigableKeySet();
		for(String word: keys){
			s += word +"}" + featVec.get(word) +  " ";
		}
		return s.toLowerCase();
	}
	
	
	public String getFeatVecString(LinkedList<String> featVec, String prefix){
		String s = "";
		for(String word: featVec){
			if(word.contains(prefix))
				s += word + " ";
		}
		return s;
	}
	
	/*
	public void addFeature(TreeMap<String,Integer> words, LinkedList<String> featVec){
		NavigableSet<String> keys = words.navigableKeySet();
		for(String word:keys){
			featVec.add(word.replace(" ", "_"));
		}
	}*/
	
	public void addFeature(TreeMap<String,Double> words, LinkedList<String> featVec){
		NavigableSet<String> keys = words.navigableKeySet();
		for(String word:keys){
			featVec.add(word.replace(" ", "_"));
		}
	}
	
	
	public void addFeature(TreeMap<String,Double> words, TreeMap<String, Double> featVec){
		NavigableSet<String> keys = words.navigableKeySet();
		for(String word:keys){
			featVec.put(word.replace(" ", "_"), words.get(word));
		}
	}
	
	public void addFeature(String word, TreeMap<String, Double> featVec){
		featVec.put(word.replace(" ", "_"), 1.0);
	}
	
}
