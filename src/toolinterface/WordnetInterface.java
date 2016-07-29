package toolinterface;

import java.util.ArrayList;
import java.util.TreeMap;

import edu.smu.tspell.wordnet.NounSynset;
import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.SynsetType;
import edu.smu.tspell.wordnet.WordNetDatabase;

public class WordnetInterface{
	WordNetDatabase database;

	public WordnetInterface(String dictPath){
		System.setProperty("wordnet.database.dir",dictPath);
		database = WordNetDatabase.getFileInstance();
		
	}
	
	public ArrayList<String> getSynset(String word){
		ArrayList<String> wordList = new ArrayList<String>();
		Synset[] synsets = database.getSynsets(word);
		
		for (int j = 0; j < synsets.length; j++)
		{
			String[] wordForms = synsets[j].getWordForms();
			
			if(wordForms.length == 0) System.out.println("no word in synset");
			for (int k = 0; k < wordForms.length; k++)
			{
				//System.out.println(j + " " + k + " "+  wordForms[k]);
				if(!wordList.contains(wordForms[k])){
					wordList.add(wordForms[k]);
				}
			}			
		}
		return wordList;
	}
	public ArrayList<String> getSynset(String word, SynsetType type){
		ArrayList<String> wordList = new ArrayList<String>();
		Synset[] synsets = database.getSynsets(word, type);
		
		for (int j = 0; j < synsets.length; j++)
		{
			String[] wordForms = synsets[j].getWordForms();
			
			if(wordForms.length == 0) System.out.println("no word in synset");
			for (int k = 0; k < wordForms.length; k++)
			{
				//System.out.println(j + " " + k + " "+  wordForms[k]);
				if(!wordList.contains(wordForms[k])){
					wordList.add(wordForms[k]);
				}
			}			
		}
		return wordList;
	}
	
	public ArrayList<String> getSynset(String[] words){
		ArrayList<String> wordList = new ArrayList<String>();
		for(String word: words){
			Synset[] synsets = database.getSynsets(word);
			for (int j = 0; j < synsets.length; j++)
			{
				String[] wordForms = synsets[j].getWordForms();
				if(wordForms.length == 0) System.out.println("no word in synset");
				for (int k = 0; k < wordForms.length; k++)
				{
					if(!wordList.contains(wordForms[k])){
						wordList.add(wordForms[k]);
					}
				}			
			}
		}	
		return wordList;
	}
	
	public ArrayList<String> getSynset(String prefix, String word){
		ArrayList<String> wordList = new ArrayList<String>();
		Synset[] synsets = database.getSynsets(word);
		
		for (int j = 0; j < synsets.length; j++)
		{
			String[] wordForms = synsets[j].getWordForms();
			if(wordForms.length == 0) System.out.println("no word in synset");
			for (int k = 0; k < wordForms.length; k++)
			{
				//System.out.println(j + " " + k + " "+  wordForms[k]);
				if(!wordList.contains(wordForms[k])){
					wordList.add(prefix+wordForms[k]);
				}
			}			
		}
		return wordList;
	}
	
	public ArrayList<String> getSynset(String prefix, String[] words){
		ArrayList<String> wordList = new ArrayList<String>();
		for(String word: words){
		//	database.
			Synset[] synsets = database.getSynsets(word);
		//	Synset[] synsets = database.g
			for (int j = 0; j < synsets.length; j++)
			{
				String[] wordForms = synsets[j].getWordForms();
				if(wordForms.length == 0) System.out.println("no word in synset");
				for (int k = 0; k < wordForms.length; k++)
				{
					if(!wordList.contains(wordForms[k])){
						wordList.add(prefix+wordForms[k]);
					}
				}			
			}
		}	
		return wordList;
	}
	
	
      
	
	public static void main(String[] args){
		String dictPath = "/Applications/WordNet-3.0/dict";
		WordnetInterface wi = new WordnetInterface(dictPath);
		wi.getSynset("test");
		ArrayList<String> wl = wi.getSynset("green");
		System.out.println(wl.toString());
	}
}
