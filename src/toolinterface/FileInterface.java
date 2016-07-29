package toolinterface;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import base.TrainSplit;

public class FileInterface {
	
	
	
	public FileInterface(){
		
	}
	
	public void printFileList(List<File> files){
		for(File file: files){
			System.out.println(file.getAbsolutePath());
		}
	}
	
	public List<File> genFileList(List<String> folderPath){
		List<File> files = new ArrayList<File>(); 
		for(String path: folderPath){
			File dir = new File(path);
			for(File file: dir.listFiles()) {  		
				if(!file.getName().startsWith(".")) {
					files.add(file);
				}
			}
		}
		return files;
	}
	
	public List<File> genFileList(String folderPath){
		List<File> files = new ArrayList<File>(); 
		
		File dir = new File(folderPath);
		for(File file: dir.listFiles()) {  		
			if(!file.getName().startsWith(".")) {
				files.add(file);
			}
		}
		
		return files;
	}
	
	public List<File> genFileList(String folderPath, String pathName){
		List<File> files = new ArrayList<File>(); 
		
		File dir = new File(folderPath);

		for(File file: dir.listFiles()) {  		
			if(!file.getName().startsWith(".")) {
				String fileName = pathName+"/"+file.getName();
				System.out.println(fileName);
				files.add(new File(fileName));
			}
		}
		
		return files;
	}
	
	public List<File> genFileList(String folderPath, String pathName, String extension){
		List<File> files = new ArrayList<File>(); 
		
		File dir = new File(folderPath);

		for(File file: dir.listFiles()) {  		
			if(!file.getName().startsWith(".")) {
				String fileName = pathName+"/"+file.getName().replace(".tml", extension);
				System.out.println(fileName);
				files.add(new File(fileName));
			}
		}
		
		return files;
	}
	
	public List<File> genFileList(String foldListFileName, int foldNumber, String type, String p1, String p2){
		XMLInterface xmlInterface = new XMLInterface();
		List<File> files = new ArrayList<File>(); 
		
		Document doc = xmlInterface.parseFile(foldListFileName);
		NodeList nodeList = doc.getElementsByTagName("fold");
		String train = "";
		int l = nodeList.getLength();
		for(int i = 0 ; i < l ; i++){
			String foldNum = nodeList.item(i).getAttributes().getNamedItem("foldNumber").getNodeValue();
			if(!foldNum.equals(foldNumber+"")) continue;
			else{
				train = nodeList.item(i).getAttributes().getNamedItem(type).getNodeValue();
				break;
			}
		}
		StringTokenizer st = new StringTokenizer(train);
		while(st.hasMoreTokens()){
			String fileName = st.nextToken(",");
			if(!fileName.trim().equals("")){
				File file = new File(fileName.trim().replace(p1, p2));
				if(file.getAbsolutePath() != null) files.add(file);
			}
		}
		return files;
	}
	
	public List<File> genFileList(String foldListFileName, int foldNumber, String type){
		XMLInterface xmlInterface = new XMLInterface();
		List<File> files = new ArrayList<File>(); 
		Document doc = xmlInterface.parseFile(foldListFileName);
		NodeList nodeList = doc.getElementsByTagName("fold");
		String train = "";
		int l = nodeList.getLength();
		for(int i = 0 ; i < l ; i++){
			String foldNum = nodeList.item(i).getAttributes().getNamedItem("foldNumber").getNodeValue();
			if(!foldNum.equals(foldNumber+"")) continue;
			else{
				train = nodeList.item(i).getAttributes().getNamedItem(type).getNodeValue();
				break;
			}
		}
		StringTokenizer st = new StringTokenizer(train);
		while(st.hasMoreTokens()){
			String fileName = st.nextToken(",");
			if(!fileName.trim().equals("")){
				files.add(new File(fileName.trim()));
			}
		}
		return files;
	}
	
	
	public List<File> genFileList(String foldListFileName, String pathName, int foldNumber, String type){
		XMLInterface xmlInterface = new XMLInterface();
		List<File> files = new ArrayList<File>(); 
		Document doc = xmlInterface.parseFile(foldListFileName);
		NodeList nodeList = doc.getElementsByTagName("fold");
		String train = "";
		int l = nodeList.getLength();
		for(int i = 0 ; i < l ; i++){
			String foldNum = nodeList.item(i).getAttributes().getNamedItem("foldNumber").getNodeValue();
			if(!foldNum.equals(foldNumber+"")) continue;
			else{
				train = nodeList.item(i).getAttributes().getNamedItem(type).getNodeValue();
				break;
			}
		}
		StringTokenizer st = new StringTokenizer(train);
		while(st.hasMoreTokens()){
			String fileName = st.nextToken(",");
			if(!fileName.trim().equals("")){
				files.add(new File(pathName + (new File(fileName.trim())).getName()));
			}
		}
		return files;
	}
	
	public void genFoldList(String path, String fileName, int numFold){
		XMLInterface xmlInterface = new XMLInterface();
		DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
	    DocumentBuilder docBuilder = null;
		try {
			docBuilder = dbfac.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    Element root;
	    Document doc = docBuilder.newDocument();	
	    root = doc.createElement("foldList");
	    doc.appendChild(root);
	    	
		List<TrainSplit> splits = TrainSplit.buildSplits(path, numFold);
		int i = 0;
		for(TrainSplit split: splits){
			Element node = doc.createElement("fold");
			node.setAttribute("foldNumber", ++i+"");
			root.appendChild(node);
			System.out.println("Fold #" + i + " " + split.train.size() + " " + split.test.size());
			String train = "";
			List<File> trainFileList= split.train;
			for(File file : trainFileList){
				train += file.getAbsolutePath()+", ";
			}
			node.setAttribute("train", train);
			String test = "";
			List<File> testFileList= split.test;
			for(File file : testFileList){
				test += file.getAbsolutePath()+", ";
			}
			node.setAttribute("test", test);
		}
		
		xmlInterface.writeToFile(fileName, doc);
	}
	/*String folderPath = "TimeBank/";	
	String folderPath2 = "AQUAINT/";
	File dir1 = new File(folderPath);
	File dir2 = new File(folderPath2);
	if (!dir1.isDirectory()) {
		System.err.println("[ERROR]\tinvalid training directory specified.");
	}
	if (!dir2.isDirectory()) {
		System.err.println("[ERROR]\tinvalid training directory specified.");
	}
	File files1[] = dir1.listFiles();
	File files2[] = dir2.listFiles();
	int size;
	if(files1.length < files2.length){
		size = files1.length;
	}else{
		size = files2.length;
	}
	for(int i = 0; i < size*2; i++){
		if(i%2 ==0){
			if(!files1[i/2].getName().startsWith(".")) {
				files.add(files1[i/2]);
			}
		}else{
			if(!files2[i/2].getName().startsWith(".")) {
				files.add(files2[i/2]);
			}
		}
	}
	if(files1.length < files2.length){
		for(int i = size; i < files2.length; i++){
			if(!files2[i].getName().startsWith(".")) {
				files.add(files2[i]);
			}
		}
	}else{
		for(int i = size; i < files1.length; i++){
			if(!files1[i].getName().startsWith(".")) {
				files.add(files1[i]);
			}
		}
	}		
	
	for(File file: dir.listFiles()) {  		
		if(!file.getName().startsWith(".")) {
			files.add(file);
	}*/
	
	public static void main(String[] args){
		FileInterface myFileInterface = new FileInterface();
		myFileInterface.genFoldList("corpora/TimeBank", "others/fold_g_tb.xml", 10);
	}
	
}
	
