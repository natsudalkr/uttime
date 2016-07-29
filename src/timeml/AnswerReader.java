package timeml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TreeMap;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import base.Dictionary;



import toolinterface.FileInterface;
import toolinterface.XMLInterface;

public class AnswerReader {
	
	String systemPath = "";
	String goldPath = "";
	String systemPath2 = "";
	XMLInterface xmlInterface;
	FileInterface fileInterface;
	RelationExtractor relationExtractor;
	Dictionary dict;
	
	public AnswerReader(String systemPath, String systemPath2, String goldPath){
		this.systemPath = systemPath;
		this.systemPath2 = systemPath2;
		this.goldPath = goldPath;
		xmlInterface = new XMLInterface();
		fileInterface= new FileInterface();
		relationExtractor = new RelationExtractor();
		dict = new Dictionary();
	}
	
	public AnswerReader(String systemPath, String goldPath){
		this.systemPath = systemPath;
		this.goldPath = goldPath;
		xmlInterface = new XMLInterface();
		fileInterface= new FileInterface();
		relationExtractor = new RelationExtractor();
		dict = new Dictionary();
	}
	
	public AnswerReader(){
		
	}
	
	public int read(double target[], double gold[]){
		//List<File> systemFiles =  fileInterface.genFileList(systemPath);
		List<File> goldFiles = fileInterface.genFileList(goldPath);
		int index = 0;
		for(File file: goldFiles){
			String fileName = file.getAbsolutePath();
	        System.out.println(fileName);
	        Document goldDoc = xmlInterface.parseFile(fileName);	
	        Document systemDoc = xmlInterface.parseFile(fileName.replace(goldPath, systemPath));
	        NodeList nodeList = relationExtractor.getTLink(goldDoc);
	        int length =  nodeList.getLength();
	        for(int i = 0; i< length; i++){
	        	if(nodeList.item(i).getAttributes().getNamedItem("eventInstanceID") != null && nodeList.item(i).getAttributes().getNamedItem("relatedToEventInstance")!=null)
	        	{
		        	String lid = nodeList.item(i).getAttributes().getNamedItem("lid").getNodeValue();
		        	Node systemNode = getTLinkNode(systemDoc,lid);
		        	String goldLabel = nodeList.item(i).getAttributes().getNamedItem("relType").getNodeValue();
		        	String systemLabel = systemNode.getAttributes().getNamedItem("relType").getNodeValue();
		        	if(goldLabel.equals("IDENTITY")) goldLabel = "SIMULTANEOUS";
		        	dict.genDict(goldLabel);
		        	dict.genDict(systemLabel);
		        	System.out.println(lid + "\t" +goldLabel + "\t" + systemLabel);
		        	target[index] = dict.dict.get(systemLabel);
		        	gold[index] = dict.dict.get(goldLabel);
		        	index++;
	        	}
	        }
		}
		System.out.println(index);	
		return index;
	}
	
	
	public int read(double target1[], double target2[], double gold[]){
		//List<File> systemFiles =  fileInterface.genFileList(systemPath);
		List<File> goldFiles = fileInterface.genFileList(goldPath);
		int index = 0;
		for(File file: goldFiles){
			String fileName = file.getAbsolutePath();
	        System.out.println(fileName);
	        Document goldDoc = xmlInterface.parseFile(fileName);	
	        Document systemDoc = xmlInterface.parseFile(fileName.replace(goldPath, systemPath));
	        Document systemDoc2 = xmlInterface.parseFile(fileName.replace(goldPath, systemPath2));
	        NodeList nodeList = relationExtractor.getTLink(goldDoc);
	        int length =  nodeList.getLength();
	        for(int i = 0; i< length; i++){
	        	if(nodeList.item(i).getAttributes().getNamedItem("timeID") != null && nodeList.item(i).getAttributes().getNamedItem("relatedToEventInstance")!=null)
	        	{
		        	String lid = nodeList.item(i).getAttributes().getNamedItem("lid").getNodeValue();
		        	Node systemNode = getTLinkNode(systemDoc,lid);
		        	Node systemNode2 = getTLinkNode(systemDoc2,lid);
		        	String goldLabel = nodeList.item(i).getAttributes().getNamedItem("relType").getNodeValue();
		        	String systemLabel = systemNode.getAttributes().getNamedItem("relType").getNodeValue();
		        	String systemLabel2 = systemNode2.getAttributes().getNamedItem("relType").getNodeValue();
		        	if(goldLabel.equals("IDENTITY")) goldLabel = "SIMULTANEOUS";
		        	dict.genDict(goldLabel);
		        	dict.genDict(systemLabel);
		        	dict.genDict(systemLabel2);
		        	if(systemLabel.equals(goldLabel) && !systemLabel2.equals(goldLabel)){
		        	System.out.println(lid + "\t" +goldLabel + "\t" + systemLabel + "\t" + systemLabel2);}
		        	target1[index] = dict.dict.get(systemLabel);
		        	target2[index] = dict.dict.get(systemLabel2);
		        	gold[index] = dict.dict.get(goldLabel);
		        	index++;
	        	}
	        }
		}
		System.out.println(index);	
		return index;
	}
	
	
	public void read(String target1, String target2){
		int pp=0,np=0,pn=0,nn=0;
		try {
			FileReader f1 = new FileReader(new File(target1));
			FileReader f2 = new FileReader(new File(target2));
			BufferedReader bf1 = new BufferedReader(f1);
			BufferedReader bf2 = new BufferedReader(f2);
			String s1 = "",s2 ="";
			while((s1 =bf1.readLine()) != null){
				StringTokenizer st = new StringTokenizer(s1);
				s2 = bf2.readLine();
				StringTokenizer st2 = new StringTokenizer(s2);
				String g1 = st.nextToken();
				String a1 = st.nextToken();
				String g2 = st2.nextToken();
				String a2 = st2.nextToken();
				if(g1.equals(g2)){
					if(a1.equals(g1) && a2.equals(g1)) pp++;
					if(!a1.equals(g1) && a2.equals(g1)) np++;
					if(a1.equals(g1) && !a2.equals(g1)) pn++;
					if(!a1.equals(g1) && !a2.equals(g1)) nn++;
				}else{
					System.out.println("ERROR");
					break;
				}
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(pp + " " + np + " " + pn + " " + nn);
		
		
	}
	
	public int read(String refFileName, double target[], double gold[]){
		Document doc = xmlInterface.parseFile(refFileName);
		int index = 0;
		NodeList nodeList = doc.getElementsByTagName("rel");
		int length =  nodeList.getLength();
		     
	    for(int i = 0; i< length; i++){
	    //	if(nodeList.item(i).getAttributes().getNamedItem("guess").getNodeValue().equals(nodeList.item(i).getAttributes().getNamedItem("relType").getNodeValue()))
	    //	{
	        	
	        	String goldLabel = nodeList.item(i).getAttributes().getNamedItem("relType").getNodeValue();
	        	String systemLabel =  nodeList.item(i).getAttributes().getNamedItem("guess").getNodeValue();
	        	dict.genDict(goldLabel);
	        	dict.genDict(systemLabel);
	        	target[index] = dict.dict.get(systemLabel);
	        	gold[index] = dict.dict.get(goldLabel);
	        	index++;
	    //	}
	    }
		
		System.out.println(index);	
		return index;
	}
	
	private Node getTLinkNode(Document doc, String lid){
		NodeList nodeList = relationExtractor.getTLink(doc);
        int length =  nodeList.getLength();
        for(int i = 0; i< length; i++){
        	String lidTmp = nodeList.item(i).getAttributes().getNamedItem("lid").getNodeValue();
        	if(lidTmp.equals(lid)) return nodeList.item(i);
        }
		return null;
	}
	
	public void contingency(){
		//List<File> systemFiles =  fileInterface.genFileList(systemPath);
		List<File> goldFiles = fileInterface.genFileList(goldPath);
		int pp = 0, pn =0, np = 0, nn =0;
		for(File file: goldFiles){
			String fileName = file.getAbsolutePath();
	        System.out.println(fileName);
	        Document goldDoc = xmlInterface.parseFile(fileName);	
	        Document systemDoc = xmlInterface.parseFile(fileName.replace(goldPath, systemPath));
	        Document systemDoc2 = xmlInterface.parseFile(fileName.replace(goldPath, systemPath2));
	        NodeList nodeList = relationExtractor.getTLink(goldDoc);
	        int length =  nodeList.getLength();
	        for(int i = 0; i< length; i++){	        	
	        	String lid = nodeList.item(i).getAttributes().getNamedItem("lid").getNodeValue();
	        	Node systemNode = getTLinkNode(systemDoc,lid);
	        	Node systemNode2 = getTLinkNode(systemDoc2,lid);
	        	String goldLabel = nodeList.item(i).getAttributes().getNamedItem("relType").getNodeValue();
	        	String systemLabel = systemNode.getAttributes().getNamedItem("relType").getNodeValue();
	        	String systemLabel2 = systemNode2.getAttributes().getNamedItem("relType").getNodeValue();
	        	if(goldLabel.equals("IDENTITY")) goldLabel = "SIMULTANEOUS";
	        	if(systemLabel.equals(goldLabel) && !systemLabel2.equals(goldLabel)) pn++;
	        	if(!systemLabel.equals(goldLabel) && !systemLabel2.equals(goldLabel)) nn++;
	        	if(!systemLabel.equals(goldLabel) && systemLabel2.equals(goldLabel)) np++;
	        	if(systemLabel.equals(goldLabel) && systemLabel2.equals(goldLabel)) pp++;
	        	System.out.println(lid + "\t" +goldLabel + "\t" + systemLabel  + "\t" + systemLabel2);
	        }
		}
		System.out.println(pp + " " +pn + " " + np + " " + nn);
	}
	
	public static void main(String args[]){
		/*AnswerReader myAnswerReader = new AnswerReader("./answer/te3-answerA_eval/","answer/te3-answerB_eval/","./answer/gold_eval/");
		double[] target = new double[6000];
		double[] target2 = new double[6000];
		double[] gold = new double[6000];
		int correct =0;
		int length = myAnswerReader.read(target,  gold);
		for(int i = 0; i < length;i++){
			System.out.println(gold[i] + "\t" + target[i]);
			if(gold[i] == target[i]) correct++;
		}
		System.out.println("Accuracy : "+  correct*100.00/length);
		System.out.println(correct);
		myAnswerReader.contingency();*/
		AnswerReader myAnswerReader = new AnswerReader();
		myAnswerReader.read("EE2.txt", "EE.txt");
		myAnswerReader.read("ETA2.txt", "ETA.txt");
		myAnswerReader.read("ETB2.txt", "ETB.txt");
		
		
	}
}
