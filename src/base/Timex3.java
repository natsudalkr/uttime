package base;

import java.util.ArrayList;

public class Timex3 {
	public String anchorTimeID = "";
	public String functionInDocument = "";
	public boolean temporalFunction;
	public String tid = "";
	public String type = "";
	public String value = "";
	public String text = "";
	public ArrayList<String> synset;
	public String fileName = "";
	public int sentenceNumber = -1;
	public String sentence;
	public String beginPoint = "";
	public String endPoint = "";
	public int tokenNumberF = -1;
	public int tokenNumberS = -1;
	public String timeToken = "";
	public boolean isDCT = false;
	public int enjuTokenId;
	
	public Timex3(){
		
	}
	
	public Timex3(String text){
		this.text = text;
	}
	
	public void normalize(){
		
	}
	
	public String getIsDCT(String prefix){
		return prefix+"dct_"+isDCT;	
	}
	
	public String getType(String prefix){
		return prefix+"type_"+type;	
	}
	
	
	public ArrayList<String> getBasicFeatureWords(String prefix){
		ArrayList<String> features = new ArrayList<String>();
		features.add(prefix + "TYPE_" + type);
		features.add(prefix + "VALUE_" + value);
		features.add(prefix + "NVALUE_" + value.replaceAll("[0-9]", "X"));
		//System.out.println(value.replaceAll("[0-9]", "X"));
		features.add(prefix + "TEXT_" + text);
		features.add(prefix + "TEMPFUNC_" +temporalFunction);
		features.add(prefix + "FUNCINDOC_" +functionInDocument);
		return features;
	}
	
	public ArrayList<String> getSynset(String prefix){
		ArrayList<String> synsetList = new ArrayList<String>();
		for(String word: synset){
			synsetList.add(prefix + word);
		}
		
		return synsetList;
	}
}
