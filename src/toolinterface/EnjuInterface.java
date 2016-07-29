package toolinterface;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class EnjuInterface {
	XMLInterface xmlInterface; 
	Document doc;
	public EnjuInterface(String fileName){
		xmlInterface = new XMLInterface();
		//System.out.println(fileName);
		doc = xmlInterface.parseFile(fileName);
		
	}
	
	public TreeMap<String,String> getFeatureFromXml(int sentenceNum, String tok){
		Node t = getNodeFromText(sentenceNum,tok);
		TreeMap<String,String> enjuFeatures = new TreeMap<String,String>();
		if(t!=null){
			//System.out.println(getAttrValue(t,"pred"));
			if(!getAttrValue(t,"base").equals("none"))enjuFeatures.put("ENJU_BASE", Boolean.toString(getAttrValue(t,"base").equals("be")));
			if(!getAttrValue(t,"cat").equals("none"))enjuFeatures.put("ENJU_CAT", getAttrValue(t,"cat"));
			if(!getAttrValue(t,"pred").equals("none"))enjuFeatures.put("ENJU_PRED", getAttrValue(t,"pred"));
			if(!getAttrValue(t,"aux").equals("none")) enjuFeatures.put("ENJU_AUX", getAttrValue(t,"aux"));
			if(!getAttrValue(t,"voice").equals("none"))enjuFeatures.put("ENJU_VOICE", getAttrValue(t,"voice"));
			if(!getAttrValue(t,"lexentry").equals("none"))enjuFeatures.put("ENJU_LEXENTRY", getAttrValue(t,"lexentry"));
			//if(!getAttrValue(t,"tense").equals("none"))enjuFeatures.put("ENJU_TENSE", getAttrValue(t,"tense"));
			//if(!getAttrValue(t,"type").equals("none"))enjuFeatures.put("ENJU_TYPE", getAttrValue(t,"type"));
		}
		return enjuFeatures;
	}
	
	public String getAttrValue(Node node, String attr){
		Node temp = node.getAttributes().getNamedItem(attr);
		if(temp != null)return temp.getNodeValue();
		else return "none";
	}
	
	public Node getNodeFromText(int sentenceNum, String text){
		NodeList tokenList = getTokenList();
		int length = tokenList.getLength();
		for(int i = 0; i < length; i++){
			String tempID = tokenList.item(i).getTextContent();
			if(tempID.equals(text) && getSentenceNumber(tokenList.item(i)).equals("s"+sentenceNum)) {			
				return tokenList.item(i);
			}
		}
		return null;
	}
	
	public Node getNodeFromTextN(int sentenceNum, String text, int n){
		NodeList tokenList = getTokenList();
		int length = tokenList.getLength();
		int count =0;
		for(int i = 0; i < length; i++){
			String tempID = tokenList.item(i).getTextContent();
			if(tempID.equals(text) && getSentenceNumber(tokenList.item(i)).equals("s"+sentenceNum)) {			
				count++;
				//System.out.println(count);
				if(count == n){
					//System.out.println("RETURN");
					return tokenList.item(i);
				}
			}
		}
		return null;
	}

	
	public Node getNodeFromID(int sentenceNum, String id){
		List<Node> consList = getConsList(sentenceNum);
		int length = consList.size();
		for(int i = 0; i < length; i++){
			String tempID = getAttrValue(consList.get(i),"id");
			//System.out.println(tempID +  " " + id);
			if(tempID.equals(id)) {			
				return consList.get(i);
			}
		}
		List<Node> tokenList = getTokenList(sentenceNum);
		length = tokenList.size();
		for(int i = 0; i < length; i++){
			String tempID = getAttrValue(tokenList.get(i),"id");
			//System.out.println(tempID +  " " + id);
			if(tempID.equals(id)) {			
				return tokenList.get(i);
			}
		}
		return null;
	}
	
	public int getTokId(int sentenceNumber, String tok, int tokid){
		TreeMap<Integer, Node> tokMap = getTokenMap(sentenceNumber);
		int id = tokid;
		
	//	System.out.println("tok id" + tokid + " " + sentenceNumber + " " + tokMap.size());
		if(tokMap.get(id)!=null){
			String text = tokMap.get(id).getTextContent();
			int i = 0;
			int length = tokMap.size();
			//System.out.println("Map size : " + length);
			if(text.equals(tok)) return id;
			while(id-i>=getFirstTokIdInSentence(sentenceNumber)&&id+i<getFirstTokIdInSentence(sentenceNumber)+tokMap.size()){
				//System.out.println(id + " " + i + " " + text + " " + tok);
				text = tokMap.get(id+i).getTextContent();
				
				if(text.equals(tok)) {
					id = id+i;
					break;
				}
				text = tokMap.get(id-i).getTextContent();
				if(text.equals(tok)){
					id = id -i;
					break;
				}
				i++;
			}
		}else id = 2000;
		return id;
	}
	
	public List<Node> getTokenList(int sentenceNum){
		//System.out.println(doc.getFirstChild().getNodeName().toString());
		NodeList senList = doc.getElementsByTagName("sentence");
		List<Node> tokenList = new ArrayList<Node>();
		int length = senList.getLength();
		//System.out.println(length);
		for(int i = 0; i < length; i++){
			
			if(getAttrValue(senList.item(i), "id").equals("s" + sentenceNum)){
				//System.out.println(getAttrValue(senList.item(i), "id"));
				/*NodeList tempList = senList.item(i).getChildNodes();
				int tempLength = tempList.getLength();
				for(int j  = 0; j < tempLength; j++){
					System.out.println(j + " " +tempList.item(j).getNodeName());
					if(tempList.item(j).getNodeName().equals("tok")){
						tokenList.add(tempList.item(j));
					}
				}*/
				getToken(senList.item(i),tokenList);
			}
		}
		return tokenList;
	}
	
	public TreeMap<Integer, Node> getTokenMap(int sentenceNum){
		NodeList senList = doc.getElementsByTagName("sentence");
		TreeMap<Integer, Node> tokenMap = new TreeMap<Integer, Node>();
		int length = senList.getLength();
		//System.out.println("length " + length);
		for(int i = 0; i < length; i++){
			
			if(getAttrValue(senList.item(i), "id").equals("s" + (sentenceNum))){
				//System.out.println("id" + getAttrValue(senList.item(i), "id"));
				/*NodeList tempList = senList.item(i).getChildNodes();
				int tempLength = tempList.getLength();
				for(int j  = 0; j < tempLength; j++){
					System.out.println(j + " " +tempList.item(j).getNodeName());
					if(tempList.item(j).getNodeName().equals("tok")){
						tokenList.add(tempList.item(j));
					}
				}*/
				getToken(senList.item(i),tokenMap);
			}
		}
		return tokenMap;
	}
	
	private void getToken(Node node, List<Node> tokenList){
		if(node.getNodeName().equals("tok")){
			tokenList.add(node);
		}else{
			NodeList tempList = node.getChildNodes();
			int tempLength = tempList.getLength();
			for(int j  = 0; j < tempLength; j++){
				getToken(tempList.item(j),tokenList);
			}
		}
	}
	
	private void getToken(Node node, TreeMap<Integer, Node> tokenMap){		
		if(node.getNodeName().equals("tok")){
			int id = Integer.parseInt(node.getAttributes().getNamedItem("id").getNodeValue().replace("t", ""));
			tokenMap.put(id, node);
		}else{
			NodeList tempList = node.getChildNodes();
			int tempLength = tempList.getLength();
			for(int j  = 0; j < tempLength; j++){
				getToken(tempList.item(j),tokenMap);
			}
		}
	}
	
	public List<Node> getConsList(int sentenceNum){
		NodeList senList = doc.getElementsByTagName("sentence");
		List<Node> consList = new ArrayList<Node>();
		int length = senList.getLength();
		for(int i = 0; i < length; i++){
			if(getAttrValue(senList.item(i), "id").equals("s" + sentenceNum)){
				/*NodeList tempList = senList.item(i).getChildNodes();
				int tempLength = tempList.getLength();
				for(int j  = 0; j < tempLength; j++){
					if(tempList.item(j).getNodeName().equals("cons")){
						tokenList.add(tempList.item(j));
					}
				}*/
				getCons(senList.item(i), consList);
				//System.out.println("consList " + consList.size());
				break;
			}
		}
		return consList;
	}
	
	private void getCons(Node node, List<Node> consList){
		if(node.getNodeName().equals("sentence")){
			NodeList tempList = node.getChildNodes();
			int tempLength = tempList.getLength();
			for(int j  = 0; j < tempLength; j++){
				getCons(tempList.item(j),consList);
			}
		}else if(node.getNodeName().equals("cons")){
			consList.add(node);
			NodeList tempList = node.getChildNodes();
			int tempLength = tempList.getLength();
			for(int j  = 0; j < tempLength; j++){
				getCons(tempList.item(j),consList);
			}
		}else{
			return;
		}
	}
	
	private String getSentenceNumber(Node node){
		while(getAttrValue(node,"parse_status").equals("none")){
			node = node.getParentNode();;
		}
		return getAttrValue(node, "id");
	}
	
	public Node getNodeFromTokenNumber(int tokenNumber){
		String tokenID = "t" + tokenNumber;
		NodeList tokenList = doc.getElementsByTagName("tok");
		int length = tokenList.getLength();
		System.out.println("token list : " + length);
		for(int i = 0; i < length; i++){
			String tempID = tokenList.item(i).getAttributes().getNamedItem("id").getNodeValue();
			if(tempID.equals(tokenID)) return tokenList.item(i);
		}
		return null;
	}
	
	public static void genBatchText(String folderPath, String prefix2){
		FileInterface myFileInterface = new FileInterface();
		List<File> files = myFileInterface.genFileList(Arrays.asList(folderPath));
		for(File file: files) {
			System.out.println("./enju -xml < ." +folderPath+file.getName()+ " > " + prefix2 + file.getName());	
		}
	}
	
	public static void genBatchText(String[] folderPaths, String prefix, String prefix2){
		FileInterface myFileInterface = new FileInterface();		
		for(String folderPath: folderPaths){
			List<File> files = myFileInterface.genFileList(Arrays.asList(folderPath));
			for(File file: files) {
				System.out.println("./enju -xml < ." +folderPath+file.getName()+ " > ." +(folderPath+file.getName()).replace(prefix, prefix2).replace(".sgm", ".enju"));	
			}
		}
	}
	
	public NodeList getTokenList(){
		NodeList tokenList = doc.getElementsByTagName("tok");
		return tokenList;
	}
	
	public int getFirstTokIdInSentence(int sentenceNumber){
		List<Node> tokenList = getTokenList(sentenceNumber);
		int id = Integer.MAX_VALUE;
		int length = tokenList.size();
		//System.out.println("Size " + length);
		for(int i = 0; i < length; i++){
			String tempID = tokenList.get(i).getAttributes().getNamedItem("id").getNodeValue();
			if(Integer.parseInt(tempID.replace("t", "")) < id) {
				id = Integer.parseInt(tempID.replace("t", ""));
			}
		}
		return id;
	}

	public static void patchFile(String fileName, String fileName_out){
		
		String content = "";
		try {
			content = new Scanner(new File(fileName)).useDelimiter("\\Z").next();
			//System.out.println(content);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		
		try {
			FileWriter fstream = new FileWriter(fileName_out, false);
			BufferedWriter out = new BufferedWriter(fstream); 
			out.write("<enju>" + content + "</enju>");
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String args[]){
		if(args[0].equals("patchfile")){
			EnjuInterface.patchFile(args[1], args[2]);		
		}
		
		
	}
}
