package timeml;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.NavigableSet;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;

import toolinterface.XMLInterface;

public class ReferenceFileInterface {
	DocumentBuilderFactory dbfac;
    DocumentBuilder docBuilder;
    Document doc;
    boolean initializedXml = false;
    Element root;
  
	public ReferenceFileInterface(){
		dbfac = DocumentBuilderFactory.newInstance();
	}
	
	public void readFile(String fileName){
		try {			
			InputStream inputStream = new FileInputStream(new File(fileName)); 
			Reader reader = new InputStreamReader(inputStream,"UTF-8");
			 
			InputSource is = new InputSource(reader);
			is.setEncoding("UTF-8");
	        doc = dbfac.newDocumentBuilder().parse(is);   
	        doc.getDocumentElement().normalize();	      
		 } catch (Exception e) { 
		     e.printStackTrace(); 
		 } 
	}
	
	public void printRefToTextFile(String fileName, String relType, String lid, String sentence1, String relatedID, String sentence2, String refFileName){
		BufferedWriter out;
		FileWriter fstream;
		try {
			fstream = new FileWriter(refFileName, true);
			out = new BufferedWriter(fstream);
			
			out.write(fileName + "\t" + relType + "\t" + lid + "\t" + sentence1 + "\t" + relatedID + "\t"+ sentence2);
			out.newLine();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void initializeXmlFile(){		
        try {
			docBuilder = dbfac.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        doc = docBuilder.newDocument();
      //create the root element and add it to the document
        root = doc.createElement("ref");
        doc.appendChild(root);
	}
	
	public void printRefToXmlFile(String fileName, String relType, String lid, String ID, String relatedID, int lineNumber, String linkType){		
		try {
            
           if(!initializedXml) {
        	   initializeXmlFile();
        	   initializedXml=true;
           }
           
		//create child element, add an attribute, and add to root
		Element child = doc.createElement("rel");
		child.setAttribute("fileName", fileName);
		child.setAttribute("relType", relType);
		child.setAttribute("lid", lid);
		child.setAttribute("ID", ID);
		child.setAttribute("relatedID", relatedID);
		child.setAttribute("lineNumber", Integer.toString(lineNumber));
		child.setAttribute("linkType", linkType);
		child.setAttribute("BEFORE", "");
		child.setAttribute("AFTER", "");
		child.setAttribute("IBEFORE", "");
		child.setAttribute("IAFTER", "");
		child.setAttribute("INCLUDES", "");
		child.setAttribute("IS_INCLUDED", "");
		child.setAttribute("SIMULTANEOUS", "");
		child.setAttribute("IDENTITY", "");
		child.setAttribute("DURING", "");
		child.setAttribute("DURING_INV", "");
		child.setAttribute("BEGINS", "");
		child.setAttribute("ENDS", "");
		child.setAttribute("BEGUN_BY", "");
		child.setAttribute("ENDED_BY", "");
		root.appendChild(child);
		
		//add a text element to the child
	//	Text text = doc.createTextNode("Filler, ... I could have had a foo!");
	//	child.appendChild(text);

           // writeToFile("test.xml");
        } catch (Exception e) {
            System.out.println(e);
        }
	}
	
	public void printRefToXmlFile(String fileName, String relType, String lid, String ID, String relatedID, int lineNumber){		
		try {
            
           if(!initializedXml) {
        	   initializeXmlFile();
        	   initializedXml=true;
           }
           
		//create child element, add an attribute, and add to root
		Element child = doc.createElement("rel");
		child.setAttribute("fileName", fileName);
		child.setAttribute("relType", relType);
		child.setAttribute("lid", lid);
		child.setAttribute("ID", ID);
		child.setAttribute("relatedID", relatedID);
		child.setAttribute("lineNumber", Integer.toString(lineNumber));
		child.setAttribute("BEFORE", "");
		child.setAttribute("AFTER", "");
		child.setAttribute("IBEFORE", "");
		child.setAttribute("IAFTER", "");
		child.setAttribute("INCLUDES", "");
		child.setAttribute("IS_INCLUDED", "");
		child.setAttribute("SIMULTANEOUS", "");
		child.setAttribute("IDENTITY", "");
		child.setAttribute("DURING", "");
		child.setAttribute("DURING_INV", "");
		child.setAttribute("BEGINS", "");
		child.setAttribute("ENDS", "");
		child.setAttribute("BEGUN_BY", "");
		child.setAttribute("ENDED_BY", "");
		root.appendChild(child);
		
		//add a text element to the child
	//	Text text = doc.createTextNode("Filler, ... I could have had a foo!");
	//	child.appendChild(text);

           // writeToFile("test.xml");
        } catch (Exception e) {
            System.out.println(e);
        }
	}
	
	public void printRefToXmlFile(String fileName, String relType, String lid, String ID, String relatedID, String reverse, int lineNumber){		
		try {
            
           if(!initializedXml) {
        	   initializeXmlFile();
        	   initializedXml=true;
           }
           
		//create child element, add an attribute, and add to root
		Element child = doc.createElement("rel");
		child.setAttribute("fileName", fileName);
		child.setAttribute("relType", relType);
		child.setAttribute("lid", lid);
		child.setAttribute("ID", ID);
		child.setAttribute("relatedID", relatedID);
		child.setAttribute("lineNumber", Integer.toString(lineNumber));
		child.setAttribute("BEFORE", "");
		child.setAttribute("AFTER", "");
		child.setAttribute("IBEFORE", "");
		child.setAttribute("IAFTER", "");
		child.setAttribute("INCLUDES", "");
		child.setAttribute("IS_INCLUDED", "");
		child.setAttribute("SIMULTANEOUS", "");
		child.setAttribute("IDENTITY", "");
		child.setAttribute("DURING", "");
		child.setAttribute("DURING_INV", "");
		child.setAttribute("BEGINS", "");
		child.setAttribute("ENDS", "");
		child.setAttribute("BEGUN_BY", "");
		child.setAttribute("ENDED_BY", "");
		child.setAttribute("reverse", reverse);
		root.appendChild(child);
		
		//add a text element to the child
	//	Text text = doc.createTextNode("Filler, ... I could have had a foo!");
	//	child.appendChild(text);

           // writeToFile("test.xml");
        } catch (Exception e) {
            System.out.println(e);
        }
	}
	
	public void writeGuessedAnswer(double[] target,TreeMap<Integer,String> label){
		Element child = null;
		
		NodeList nodeList = doc.getElementsByTagName("rel");
		int length = nodeList.getLength();
		//System.out.println("num node " + length);
		for(int i =0; i < target.length; i++){			
			for(int j = 0; j < length; j++){
				Node node = nodeList.item(j);
				//System.out.println(node.getAttributes().getNamedItem("lineNumber").getNodeValue());
				if(Integer.parseInt(node.getAttributes().getNamedItem("lineNumber").getNodeValue().replace("line", "")) == i){
					child = (Element) node;
					
					//System.out.println(child.getAttributes().getNamedItem("lineNumber").getNodeValue());
					break;
				}
			}
			//child = (Element) doc.getElementsByTagName("line"+i).item(0);
			//System.out.println((int)target[i]);
		    //System.out.println(i + " " +label.get((int)target[i]));
			//System.out.println("line number " + child.getAttributes().getNamedItem("lineNumber").getNodeValue());

			child.setAttribute("guess", label.get((int)target[i]).replace("LABEL_REL_EE_", "").replace("LABEL_REL_TE_", "").replace("LABEL_REL_ET_", "").replace("LABEL_REL_TT_", ""));
			
		}
	}
	
	public void writeGuessedAnswer(double[] target,TreeMap<Integer,String> label, ArrayList<TreeMap<String, Double>> prob){
		Element child = null;
		NodeList nodeList = doc.getElementsByTagName("rel");
		int length = nodeList.getLength();
		//System.out.println("num node " + length);
		for(int i =0; i < target.length; i++){			
			for(int j = 0; j < length; j++){
				Node node = nodeList.item(j);
				//System.out.println(node.getAttributes().getNamedItem("lineNumber").getNodeValue());
				if(Integer.parseInt(node.getAttributes().getNamedItem("lineNumber").getNodeValue().replace("line", "")) == i){
					child = (Element) node;
					//System.out.println(child.getAttributes().getNamedItem("lineNumber").getNodeValue());
					break;
				}
			}
			//child = (Element) doc.getElementsByTagName("line"+i).item(0);
			//System.out.println((int)target[i]);
			//System.out.println(i + " " +label.get((int)target[i]));
			//System.out.println("line number " + child.getAttributes().getNamedItem("lineNumber").getNodeValue());

			child.setAttribute("guess", label.get((int)target[i]).replace("LABEL_REL_EE_", "").replace("LABEL_REL_TE_", "").replace("LABEL_REL_ET_", "").replace("LABEL_REL_TT_", ""));
			NavigableSet<String> keys = prob.get(i).navigableKeySet();
			for(String key: keys){
				child.setAttribute(key, prob.get(i).get(key)+"");
			}
		}
	}
	
	public String[] creatMapLineNumberAndLid(TreeMap<String,Integer> map2){
		
		//XMLInterface xmlInterface = new XMLInterface();
		//Document doc = xmlInterface.parseFile(relFileName);		
		
		NodeList nodeList = doc.getElementsByTagName("rel");
		int length = nodeList.getLength();
		String[] map = new String[length];
		//map2 = new TreeMap<String,Integer>();
		for(int i = 0; i < length; i++){
			String lid = nodeList.item(i).getAttributes().getNamedItem("lid").getNodeValue();
			int index = Integer.parseInt(nodeList.item(i).getAttributes().getNamedItem("lineNumber").getNodeValue().replace("line", ""));
			map[index] = lid;
			//map2[Integer.parseInt(lid.replace("l", ""))] = index;
			map2.put(lid, index);
			System.out.println(lid + "  " + index);
		}
		return map;
	}
	
	public void writeToFile(String refFileName){
        //set up a transformer
        TransformerFactory transfac = TransformerFactory.newInstance();
        Transformer trans;
		try {
			trans = transfac.newTransformer();
			trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
	        trans.setOutputProperty(OutputKeys.INDENT, "yes");
       
	        FileWriter fw = new FileWriter(refFileName);
	        StreamResult result = new StreamResult(fw);
	        DOMSource source = new DOMSource(doc);
	        trans.transform(source, result);    
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
	}
	
	
	
	public static void main(String args[]){
		ReferenceFileInterface myReferenceFileInterface = new ReferenceFileInterface();
		myReferenceFileInterface.printRefToXmlFile("1", "2", "3", "4", "5",1);
		myReferenceFileInterface.printRefToXmlFile("7", "8", "9", "10", "11",1);
		myReferenceFileInterface.writeToFile("reffile.xml");
	}
}
