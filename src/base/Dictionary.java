package base;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NavigableSet;
import java.util.StringTokenizer;
import java.util.TreeMap;


public class Dictionary {
	public TreeMap<String,Integer> dict;
	public TreeMap<Integer,Integer> dictCount;
	
	public Dictionary(){
		dict = new TreeMap<String,Integer>();
		dictCount = new TreeMap<Integer,Integer>();
	}
	
	public void genDict(String featureType, ArrayList<String> words){
		for(String word: words){
			String feature = featureType + word;		
			if(!dict.containsKey(feature)){
				dict.put(feature, dict.size()+1);
				dictCount.put(dict.get(feature), 1);
			}else{
				int index = dict.get(feature);
				//dictCount.put(index, dictCount.get(index)+1);
			}
		}	
	}

	
	public void genDict(ArrayList<String> featureWords){
		for(String featureWord: featureWords){
			if(!dict.containsKey(featureWord)){
				dict.put(featureWord, dict.size()+1);
				dictCount.put(dict.get(featureWord), 1);
			}else{
				int index = dict.get(featureWord);
				//System.out.println("index : " +  index);
				//dictCount.put(index, dictCount.get(index)+1);
			}			
		}
	}
	
	
	public void genDict(TreeMap<String,Double> featureWordsTM){
		if(featureWordsTM != null){
			NavigableSet<String> featureWords = featureWordsTM.navigableKeySet();
			for(String featureWord: featureWords){
				if(!dict.containsKey(featureWord)){
					dict.put(featureWord, dict.size()+1);
					dictCount.put(dict.get(featureWord), 1);
				}else{
					int index = dict.get(featureWord);
					//System.out.println("index : " +  index);
					//dictCount.put(index, dictCount.get(index)+1);
				}			
			}
		}
	}
	public void genDict(String featureType,String word){	
		String feature = featureType + word;
		if(!dict.containsKey(feature)){
			dict.put(feature, dict.size()+1);
			dictCount.put(dict.get(feature), 1);
		}else{
			int index = dict.get(feature);
			//dictCount.put(index, dictCount.get(index)+1);
		}
	}
	
	public int genDict(String featureWord){	
		int index = 0;
		if(!dict.containsKey(featureWord)){
			int size = dict.size()+1;
			dict.put(featureWord, size);
			dictCount.put(dict.get(featureWord), 1);
		}
		index = dict.get(featureWord);
		return index;
	}
	
	public void printDict(){
		NavigableSet<String> keys = dict.navigableKeySet();
		for(String key : keys){
			System.out.println(key + ":" + dict.get(key));
		}
		System.out.println("Dictionary size : " + dict.size());
	}
	
	public void printDict(String featureType){
		NavigableSet<String> keys = dict.navigableKeySet();
		int size = 0;
		for(String key : keys){
			if(key.startsWith(featureType)){
				System.out.println(key + ":" + dict.get(key) + " " + dictCount.get(dict.get(key)));
				size++;
			}
		}
		System.out.println("Dictionary size : " + size);
	}
	
	public void printDictToFile(String fileName){
		try {
			FileWriter fileWriter = new FileWriter(new File(fileName),false);
			BufferedWriter out = new BufferedWriter(fileWriter);
			NavigableSet<String> keys = dict.navigableKeySet();
			for(String key : keys){
				out.write(key + "~" + dict.get(key).toString());
				out.newLine();
			}
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int getSize(){
		return dict.size();
	}
	
	public TreeMap<Integer,String> getConvertedDict(){
		TreeMap<Integer,String> cDict = new TreeMap<Integer,String>();
		NavigableSet<String> keys  = dict.navigableKeySet();
		for(String key: keys){
			cDict.put(dict.get(key), key);
		}
		return cDict;
	}
	
	public void loadDictionary(String fileName){
		dict.clear();
		try {
			FileReader fileReader;
			BufferedReader bufferedReader;
			fileReader = new FileReader(new File(fileName));
			bufferedReader = new BufferedReader(fileReader);
			String line;
			while((line = bufferedReader.readLine()) != null){
				StringTokenizer tokenizer = new StringTokenizer(line);
				//System.out.println(line);
				String label = tokenizer.nextToken("~");
				int weight = Integer.parseInt(tokenizer.nextToken().trim());
				dict.put(label, weight);
			}
			bufferedReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public TreeMap<Integer, String> findLabel(String prefix){
		TreeMap<Integer, String> labelMap = new TreeMap<Integer, String>();
		NavigableSet<String> keys = dict.navigableKeySet();
		for(String key: keys){
			if(key.contains(prefix)){
				labelMap.put(dict.get(key), key);
			}
		}			
		/*NavigableSet<Integer> intKeys = labelMap.navigableKeySet();
		for(int key: intKeys){
			System.out.println(key + " " + labelMap.get(key));
		}*/
		return labelMap;
	}
	
}
