package enju;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.StringTokenizer;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import toolinterface.EnjuInterface;

public class EnjuPathExtractor{
	EnjuInterface enjuInterface;
	public EnjuPathExtractor(String fileName){
		enjuInterface = new EnjuInterface(fileName);
	}
	
	public String extractPath(int sentenceNum, int tokenNum1, int tokenNum2){
		LinkedList<Node> upList= new LinkedList<Node>(); 
		LinkedList<Node> downList= new LinkedList<Node>(); 
		Node n1 = enjuInterface.getNodeFromTokenNumber(tokenNum1);
		Node n2 = enjuInterface.getNodeFromTokenNumber(tokenNum2);
		Node topNode = null;
		Node n = n1;
		String up = "";
		String down = "";
		int i = 0;
		while(n!=null){
			System.out.println(i++ + n.getTextContent());
			if(!getSiblingNode(n).isEmpty()){
				System.out.println("found sibling");
				for(Node node : getSiblingNode(n)){
					if(containsNode(node,n2)){
						System.out.println("contain");
						upList.add(node);
						topNode = node.getParentNode();
						break;
					}
				}
			}
			if(topNode != null) break;
			upList.add(n);
			n = n.getParentNode();
		}
		
		if(n==null) return "no_path";
		
		for(Node node: upList){
			up += enjuInterface.getAttrValue(node, "cat")+ "UP_" ;
		}
		
		n=n2;
		while(!topNode.isEqualNode(n)){
			downList.add(n);
			n = n.getParentNode();
		}
		for(Node node: downList){
			down = enjuInterface.getAttrValue(node, "cat")+ "DOWN_" + down ;
		}
		String path = up+enjuInterface.getAttrValue(topNode, "cat")+"_"+down;
		//System.out.println(path);
		return path;
	}
	
	
	public String extractPath(int sentenceNum,String token1, String token2){
		Node n1;
		Node n2; 
		
		if(!token1.equals(token2)){
			n1 = enjuInterface.getNodeFromText(sentenceNum, token1);
			n2 = enjuInterface.getNodeFromText(sentenceNum, token2);
			
		}else{
			n1 = enjuInterface.getNodeFromText(sentenceNum, token1);
			n2 = enjuInterface.getNodeFromTextN(sentenceNum, token2, 2);	
			if(n2 == null) return "sameword";
		}
		if(n1 == null || n2 == null) return "noword";
		Node topNode = null;
		Node n = n1;
		String path = "";
		while(n!=null){
			if(!getSiblingNode(n).isEmpty()){
				for(Node node : getSiblingNode(n)){
					if(containsNode(node,n2)){						
						if(!enjuInterface.getAttrValue(n, "cat").contains("X")) path += enjuInterface.getAttrValue(node, "cat")+ "UP_" ; 
						topNode = node.getParentNode();
						path += enjuInterface.getAttrValue(topNode, "cat") + "_"; 
						break;
					}
				}
			}
			if(topNode != null) break;
			if(!enjuInterface.getAttrValue(n, "cat").contains("X")) path += enjuInterface.getAttrValue(n, "cat")+ "UP_" ; 
			n = n.getParentNode();
		}
		
		if(n==null) return "no_path";		
		n=n2;
		String downPath = "";
		while(!topNode.isEqualNode(n)){
			if(!enjuInterface.getAttrValue(n, "cat").contains("X"))  downPath = enjuInterface.getAttrValue(n, "cat")+ "DOWN_" + downPath;
			n = n.getParentNode();
		}
	//	System.out.println(path+downPath);
		return path+downPath;
	}
	
	private LinkedList<Node> getSiblingNode(Node node){
		LinkedList<Node> siblingNodeList = new LinkedList<Node>();
		NodeList nl = null;
		if(node.getParentNode()!= null){
			nl = node.getParentNode().getChildNodes();
		}else{
			System.out.println("no parent node");
			return null;
		}
		//System.out.println(nl.getLength());
		for(int i = 0; i< nl.getLength(); i++){
			Node temp = nl.item(i);
			
			if((!temp.equals(node))&&(!temp.getTextContent().equals(" "))){
				siblingNodeList.add(temp);
				//System.out.println(temp.getTextContent());
			}		
		}
		return siblingNodeList;
	}
	
	private boolean containsNode(Node n1, Node n2){
		if(n1.isEqualNode(n2)) return true;
		if(n1.hasChildNodes()){
			NodeList nl = n1.getChildNodes();
			int length = nl.getLength();
			for(int i = 0; i< length; i++){
				//System.out.println(nl.item(i).getTextContent());
				if(containsNode(nl.item(i),n2)) return true;
			}
		}
		if(n1.isEqualNode(n2)) {
			//System.out.println("found " + n1.getTextContent() + " " + n2.getTextContent());
			return true;
		}
		return false;
	}
	
	public ArrayList<String> getPath3Grams(String path, int sentenceNum, String token1, String token2){
		ArrayList<String> path3Grams = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(path);
		String t1, t2=null, t3;
		t1=st.nextToken("_");
		if(st.hasMoreTokens()) t2=st.nextToken("_");
		while(st.hasMoreTokens()){
			t3 = st.nextToken("_");
			path3Grams.add(t1+"_"+t2+"_"+t3);
			t1=t2;
			t2=t3;
		}
		/*for(String s : path3Grams){
			System.out.println(s);
		}*/
		return path3Grams;
	}
	
	public ArrayList<String> getPath4Grams(String path, int sentenceNum, String token1, String token2){
		ArrayList<String> path4Grams = new ArrayList<String>();	
		StringTokenizer st = new StringTokenizer(path);
		String t1, t2=null, t3 = null, t4 = null;
		t1=st.nextToken("_");
		if(st.hasMoreTokens())t2=st.nextToken("_");
		if(st.hasMoreTokens())t3=st.nextToken("_");
		while(st.hasMoreTokens()){
			t4 = st.nextToken("_");
			path4Grams.add(t1+"_"+t2+"_"+t3+"_"+t4);
			t1=t2;
			t2=t3;
			t3=t4;
		}
	/*	for(String s : path3Grams){
			System.out.println(s);
		}*/
		return path4Grams;
	}
	
	public ArrayList<String> getPathNGrams(String path, int sentenceNum, String token1, String token2, int n){
		ArrayList<String> pathNGrams = new ArrayList<String>();	
		StringTokenizer st = new StringTokenizer(path);
		String t[] = new String[n]; 
		for(int i = 0; i<n-1; i++){
			if(st.hasMoreTokens()) t[i] = st.nextToken("_");
			else break;
		}
		while(st.hasMoreTokens()){
			t[n-1] = st.nextToken("_");
			String ngram="";
			for(int i =0;i<n-1;i++){
				ngram = ngram+t[i]+"_";
				t[i] = t[i+1];				
			}
			ngram = ngram+t[n-1];
			//System.out.println(ngram);
			pathNGrams.add(ngram);
		}
		return pathNGrams;
	}
	
	public int getPathLength(String path){
		StringTokenizer st = new StringTokenizer(path,"_");
		//st.nextToken();
		return st.countTokens();
	}
	
	public int getPathUpLength(String path){
		String tokens[] = path.split("UP");
		//st.nextToken();
		return tokens.length-1;
	}
	
	public int getPathDownLength(String path){
		String tokens[] = path.split("DOWN");
		//st.nextToken();
		return tokens.length-1;
	}
	
	public static void main(String args[]){
		String folderPath = "./data/xml/";
		//EnjuInterface.genBatchText(folderPath,"../../data/xml/");		
		EnjuPathExtractor enjuPathExtractor = new EnjuPathExtractor(folderPath+"XIE19980809.0010.tml");
		String path = enjuPathExtractor.extractPath(2, "expressed", "cooperation");
		int distance = enjuPathExtractor.getPathDownLength(path);
		enjuPathExtractor.getPathNGrams(path, 2, "expressed", "cooperation",5);
		System.out.println(distance);
		System.out.println(path);
	}
}
