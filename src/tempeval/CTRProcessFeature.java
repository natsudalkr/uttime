package tempeval;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.NavigableSet;
import java.util.StringTokenizer;
import java.util.TreeMap;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import timeml.ReferenceFileInterface;
import toolinterface.FileInterface;
import toolinterface.XMLInterface;

import base.Dictionary;
import base.Link;

public class CTRProcessFeature {
	Dictionary dict; 
	Dictionary labelSet;
	
	XMLInterface xmlInterface;
	
	BufferedWriter out;
	FileWriter fstream;
	
	public CTRProcessFeature(){
		dict = new Dictionary();
		labelSet = new Dictionary();	
		xmlInterface = new XMLInterface();
	}

	
	public void process(List<File> files, String fileNameEE, String fileNameET, ReferenceFileInterface rfEE, ReferenceFileInterface rfET, boolean useDeepSyn){
		try {
			fstream = new FileWriter(fileNameEE, false);
			fstream = new FileWriter(fileNameET, false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int lineNumberEE = 0;
		int lineNumberET = 0;
		rfEE.initializeXmlFile();
		rfET.initializeXmlFile();
		for(File file: files){
			Document doc = xmlInterface.parseFile(file.getAbsolutePath());
			NodeList nodelist = doc.getElementsByTagName("TLINK");
			int l = nodelist.getLength();
			for(int i = 0; i< l;i++){
				Node node = nodelist.item(i);	
				String label = getValue(node, "relType");
				
				String deepSyn = "";
				if(useDeepSyn) deepSyn = getValue(node, "deepsyn")  +" ";
				String feature  =  getValue(node, "baseline") +" "+ deepSyn + "REVERSE_"+getValue(node, "reverse");

				if(getValue(node, "type").equals("EE")){
					writeToFeatureFile(dict, fileNameEE, feature, label);
				    rfEE.printRefToXmlFile(file.getAbsolutePath(), label,getLid(node), getValue(node, "ID"),getValue(node, "relatedID"), getValue(node, "reverse"),lineNumberEE);
				    lineNumberEE++;
				}
				if(getValue(node, "type").equals("ET")){
					writeToFeatureFile(dict, fileNameET, feature, label);
				    rfET.printRefToXmlFile(file.getAbsolutePath(), label,getLid(node), getValue(node, "ID"),getValue(node, "relatedID"), getValue(node, "reverse"), lineNumberET);
				    lineNumberET++;
				}				
			}
		}
	}	

	public TreeMap<Integer, String> findLabel(){
		return labelSet.getConvertedDict();
	}
	
	public String getValue(org.w3c.dom.Document doc, String lid, String feature){
		NodeList links =  doc.getElementsByTagName("lid");
		Node link = links.item(0);		
		String value = link.getAttributes().getNamedItem(feature).getNodeValue();
		return value;
	}
	
	public String getValue(Node node, String feature){
		String value = node.getAttributes().getNamedItem(feature).getNodeValue();
		return value;
	}

	public String getLid(Node node){
		String value = node.getAttributes().getNamedItem("lid").getNodeValue();
		return value;
	}

	public void writeToFeatureFile(Dictionary dict, String fileName, String feature, String label){
		try {
			fstream = new FileWriter(fileName, true);
			out = new BufferedWriter(fstream);
			labelSet.genDict(label);
			out.write(labelSet.dict.get(label) + "\t");
			TreeMap<Integer,Double> featVec = new TreeMap<Integer,Double>();
			StringTokenizer st = new StringTokenizer(feature);
			while(st.hasMoreTokens()){
				String tok = st.nextToken();
				dict.genDict(tok);
				int featNum = dict.dict.get(tok);
				featVec.put(featNum, 1.0);
				//out.write(dict.dict.get(tok)+":1.0\t" );
			}		
			NavigableSet<Integer> keys = featVec.navigableKeySet();
			String feat = "";
			for(int key : keys){
				feat +=  key + ":" + featVec.get(key) + "\t";
			}
			out.write(feat);
			out.newLine();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]){

	}
	
}
