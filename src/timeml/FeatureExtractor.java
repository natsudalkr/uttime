package timeml;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NavigableSet;
import java.util.TreeMap;

import base.Dictionary;

public class FeatureExtractor{
	BufferedWriter out;
	FileWriter fstream;
	BufferedWriter out2;
	FileWriter fstream2;
	
	public void addFeature(Dictionary dictionary, ArrayList<String> words, TreeMap<Integer,Double> featVec){
		for(String word:words){
			int key = dictionary.dict.get(word);
			if(featVec.containsKey(key)){
				//featVec.put(key, featVec.get(key)+1.0);		
				featVec.put(key,1.0);
			}else{
				featVec.put(key,1.0);
			}
		}
	}
	
	public void addFeatureS(Dictionary dictionary, ArrayList<String> words, TreeMap<Integer, String> featVec){
		for(String word:words){
			int key = dictionary.dict.get(word);
			if(featVec.containsKey(key)){
				//featVec.put(key, featVec.get(key)+1.0);		
				featVec.put(key,word);
			}else{
				featVec.put(key,word);
			}
		}
	}
	
	public void addFeature(Dictionary dictionary, String featureType, ArrayList<String> words, TreeMap<Integer,Double> featVec){
		for(String word:words){
			int key = dictionary.dict.get(featureType+ word);
			if(featVec.containsKey(key)){
				//featVec.put(key, featVec.get(key)+1.0);		
				featVec.put(key,1.0);
			}else{
				featVec.put(key,1.0);
			}
		}
	}
	
	public void addFeature(int offset, Dictionary dictionary , ArrayList<String> words, TreeMap<Integer,Double> featVec){
		for(String word:words){
			int key = dictionary.dict.get(word);
			if(featVec.containsKey(key)){
				//featVec.put(key+offset, featVec.get(key)+1.0);	
				featVec.put(key+offset,1.0);
			}else{
				featVec.put(key+offset,1.0);
			}
		}
	}
	
	public void addFeature(Dictionary dictionary, ArrayList<String> words, TreeMap<Integer,Double> featVec, double weight){
		for(String word:words){
			int key = dictionary.dict.get(word);
			if(featVec.containsKey(key)){
				//featVec.put(key, featVec.get(key)+1.0);		
				featVec.put(key,weight);
			}else{
				featVec.put(key,weight);
			}
		}
	}
	
	public void addFeature(Dictionary dictionary, String featureType, ArrayList<String> words, TreeMap<Integer,Double> featVec, double weight){
		for(String word:words){
			int key = dictionary.dict.get(featureType+ word);
			if(featVec.containsKey(key)){
				//featVec.put(key, featVec.get(key)+1.0);		
				featVec.put(key, weight);
			}else{
				featVec.put(key,weight);
			}
		}
	}
	
	public void addFeature(int offset, Dictionary dictionary , ArrayList<String> words, TreeMap<Integer,Double> featVec, double weight){
		for(String word:words){
			int key = dictionary.dict.get(word);
			if(featVec.containsKey(key)){
				//featVec.put(key+offset, featVec.get(key)+1.0);	
				featVec.put(key+offset, weight);
			}else{
				featVec.put(key+offset, weight);
			}
		}
	}
	
	public void addFeature(Dictionary dictionary, String word, TreeMap<Integer,Double> featVec){		
		int key = dictionary.dict.get(word);
		if(featVec.containsKey(key)){
			//featVec.put(key, featVec.get(key)+1.0);	
			featVec.put(key,1.0);
		}else{
			featVec.put(key,1.0);
		}		
	}
	
	public void addFeatureS(Dictionary dictionary, String word, TreeMap<Integer,String> featVec){		
		int key = dictionary.dict.get(word);
		if(featVec.containsKey(key)){
			//featVec.put(key, featVec.get(key)+1.0);	
			featVec.put(key,word);
		}else{
			featVec.put(key,word);
		}		
	}
	
	public void addFeature(int offset, Dictionary dictionary , String word, TreeMap<Integer,Double> featVec){
		int key = dictionary.dict.get(word);
		if(featVec.containsKey(key)){
			//featVec.put(key+offset, featVec.get(key)+1.0);	
			featVec.put(key+offset,1.0);
		}else{
			featVec.put(key+offset,1.0);
		}
	}
	
	public void addFeature(Dictionary dictionary, String word, TreeMap<Integer,Double> featVec, double weight){		
		int key = dictionary.dict.get(word);
		if(featVec.containsKey(key)){
			featVec.put(key, featVec.get(key)+weight);	
			//featVec.put(key,1.0);
		}else{
			featVec.put(key,weight);
		}	
	}
	
	public void addFeature(int offset, Dictionary dictionary , String word, TreeMap<Integer,Double> featVec, double weight){
		int key = dictionary.dict.get(word);
		if(featVec.containsKey(key)){
			featVec.put(key+offset, featVec.get(key)+weight);	
			//featVec.put(key,1.0);
		}else{
			featVec.put(key+offset,weight);
		}	
	}
	
	public void printFeature(TreeMap<Integer, Double> featVec){
		System.out.println(featVec.toString());
	}
	
	public void printFeatureToFile(TreeMap<Integer, Double> featVec, String label, String fileName){
		try {
			fstream = new FileWriter(fileName, true);
			out = new BufferedWriter(fstream);
			String feat = "";
			NavigableSet<Integer> keys = featVec.navigableKeySet();
			for(int key : keys){
				feat +=  key + ":" + featVec.get(key) + "\t";
			}
			out.write(label + "\t" + feat);
			out.newLine();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void printFeatureToFileS(TreeMap<Integer, String> featVec, String label, String fileName){
		try {
			fstream = new FileWriter(fileName, true);
			out = new BufferedWriter(fstream);
			fstream2 = new FileWriter(fileName+"lab", true);
			out2 = new BufferedWriter(fstream2);
			String feat = "";
			
			NavigableSet<Integer> keys = featVec.navigableKeySet();
			for(int key : keys){
				feat +=  featVec.get(key).replace(" ", "_").replace("\n", "_") + "\t";
			}
			out.write(feat);
			out.newLine();
			out.close();
			
			out2.write(label+"\t");
			out2.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
