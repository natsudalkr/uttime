package toolinterface;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;
import javax.xml.parsers.*;

import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
public class XMLInterface {
	DocumentBuilderFactory documentBuilderFactory;
	StringWriter stw ;
    Transformer serializer;
	
	public XMLInterface(){
		documentBuilderFactory = DocumentBuilderFactory.newInstance(); 
	}
	
	public org.w3c.dom.Document parseFile(String fileName){
		org.w3c.dom.Document doc = null;
		String text = "text";
		 try {			
			InputStream inputStream = new FileInputStream(new File(fileName)); 
			Reader reader = new InputStreamReader(inputStream,"UTF-8");
			 
			InputSource is = new InputSource(reader);
			is.setEncoding("UTF-8");
	        doc = documentBuilderFactory.newDocumentBuilder().parse(is);   
	        doc.getDocumentElement().normalize();	      
		 } catch (Exception e) { 
		     e.printStackTrace(); 
		 } 
		 return doc;
	}
	
	public org.w3c.dom.Document parseString(String text){
		org.w3c.dom.Document doc = null;
		text = "<t>" + text + "</t>";
		 try {			  
	        doc = documentBuilderFactory.newDocumentBuilder().parse(new ByteArrayInputStream(text.getBytes("UTF-8")));
	        doc.getDocumentElement().normalize();	      
		 } catch (Exception e) { 
		     e.printStackTrace(); 
		 } 
		 return doc;
	}
	
	public String serializeNode(Node pNode){
		stw = new StringWriter();
		try {
			serializer = TransformerFactory.newInstance().newTransformer();
			serializer.transform(new DOMSource(pNode), new StreamResult(stw));
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return stw.toString();
	}
	
	public String serializeDoc(Document doc){
		stw = new StringWriter();
		try {
			serializer = TransformerFactory.newInstance().newTransformer();
			serializer.transform(new DOMSource(doc), new StreamResult(stw));
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return stw.toString();
	}
	
	public static void main(String args[]){
		/*String fileName = "/Users/ononon1/Documents/workspace/ClassifyTemporalEvent/TimeBank/AP900816-0139.tml";
        //String fileName = file.getAbsolutePath();
        System.out.println(fileName);
        XMLInterface myXmlInterface = new XMLInterface();
        Document myDoc = myXmlInterface.parseFile(fileName);	  
        String text = myXmlInterface.serializeNode(myDoc.getElementsByTagName("TEXT").item(0));
        System.out.println(text);*/
		XMLInterface myXmlInterface = new XMLInterface();
		myXmlInterface.writeDomXmlExample();
		
	}
	
	public void writeDomXmlExample() {
        try {
            /////////////////////////////
            //Creating an empty XML Document

            //We need a Document
            DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            ////////////////////////
            //Creating the XML tree

            //create the root element and add it to the document
            Element root = doc.createElement("root");
            doc.appendChild(root);

            //create a comment and put it in the root element
            Comment comment = doc.createComment("Just a thought");
            root.appendChild(comment);

            //create child element, add an attribute, and add to root
            Element child = doc.createElement("child");
            child.setAttribute("name", "value");
            root.appendChild(child);

            //add a text element to the child
            Text text = doc.createTextNode("Filler, ... I could have had a foo!");
            child.appendChild(text);

            /////////////////
            //Output the XML

            //set up a transformer
            TransformerFactory transfac = TransformerFactory.newInstance();
            Transformer trans = transfac.newTransformer();
            trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            trans.setOutputProperty(OutputKeys.INDENT, "yes");

            //create string from xml tree
            StringWriter sw = new StringWriter();
            StreamResult result = new StreamResult(sw);
            DOMSource source = new DOMSource(doc);
            trans.transform(source, result);
            String xmlString = sw.toString();

            //print xml
            System.out.println("Here's the xml:\n\n" + xmlString);

        } catch (Exception e) {
            System.out.println(e);
        }
    }
	

	public void writeToFile(String fileName, Document doc){
        //set up a transformer
        TransformerFactory transfac = TransformerFactory.newInstance();
        Transformer trans;
		try {
			trans = transfac.newTransformer();
			trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
	        trans.setOutputProperty(OutputKeys.INDENT, "yes");
       
	        FileWriter fw = new FileWriter(fileName);
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
}
