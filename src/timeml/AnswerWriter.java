package timeml;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import nu.xom.Attribute;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import toolinterface.FileInterface;
import toolinterface.XMLInterface;

public class AnswerWriter {
	
	public AnswerWriter(){
	
	}

	public void write(String refFileName, List<File> files, String featurePath, String folderPath, String goldPath, String templatePath, String answerPath, String tempAnsPath){
		
		XMLInterface xmlInterface = new XMLInterface();
		Document refDoc = xmlInterface.parseFile(refFileName);
		
		NodeList nodeList2 = refDoc.getElementsByTagName("rel");
		int length2 = nodeList2.getLength();

		for(File file: files){
			String fileName = file.getAbsolutePath().replace(".feature", ".tml").replace(featurePath, folderPath);
			Document doc = xmlInterface.parseFile(fileName.replace(folderPath, tempAnsPath));
			Document doc2 = xmlInterface.parseFile(fileName.replace(folderPath, templatePath));

			NodeList nodeList = doc.getElementsByTagName("TLINK");
			NodeList nodeList3 = doc2.getElementsByTagName("TLINK");
			fileName = (file.getParent()+"/"+file.getName());
			//System.out.println(fileName);
			int length = nodeList.getLength();
			for(int i = 0; i < length; i++){
				Node node = nodeList.item(i);
				Node node3 = nodeList3.item(i);
				String lid = node.getAttributes().getNamedItem("lid").getNodeValue();
				boolean found = false;
				for(int j = 0; j < length2; j++){
					Node node2 = nodeList2.item(j);
					
					String fileName2 = node2.getAttributes().getNamedItem("fileName").getNodeValue();
					String lid2 = node2.getAttributes().getNamedItem("lid").getNodeValue();
					
					lid2 = lid2.replace("i", "");

					if(fileName2.contains(fileName) && lid2.equals(lid)){
						//System.out.println("match: " + fileName2);
						String reverse = node2.getAttributes().getNamedItem("reverse").getNodeValue();
						String answer = node2.getAttributes().getNamedItem("guess").getNodeValue();
						
						if(reverse.equals("false")){
							node.getAttributes().getNamedItem("relType").setNodeValue(answer);
						}else{
							String answer_reverse = "";
							if(answer.equals("BEFORE")) answer_reverse = "AFTER";
							if(answer.equals("AFTER")) answer_reverse = "BEFORE";
							if(answer.equals("IBEFORE")) answer_reverse = "IAFTER";
							if(answer.equals("IAFTER")) answer_reverse = "IBEFORE";
							if(answer.equals("INCLUDES")) answer_reverse = "IS_INCLUDED";
							if(answer.equals("IS_INCLUDED")) answer_reverse = "INCLUDES";
							if(answer.equals("ENDS")) answer_reverse = "ENDED_BY";
							if(answer.equals("BEGINS")) answer_reverse = "BEGUN_BY";
							if(answer.equals("ENDED_BY")) answer_reverse = "ENDS";
							if(answer.equals("BEGUN_BY")) answer_reverse = "BEGINS";
							if(answer.equals("DURING")) answer_reverse = "DURING";
							if(answer.equals("SIMULTANEOUS")) answer_reverse = "SIMULTANEOUS";
							node.getAttributes().getNamedItem("relType").setNodeValue(answer_reverse);
						}
								
						
						((Element)node).setAttribute("BEFORE", node2.getAttributes().getNamedItem("BEFORE").getNodeValue());
						((Element)node).setAttribute("AFTER", node2.getAttributes().getNamedItem("AFTER").getNodeValue());
						((Element)node).setAttribute("IBEFORE", node2.getAttributes().getNamedItem("IBEFORE").getNodeValue());
						((Element)node).setAttribute("IAFTER", node2.getAttributes().getNamedItem("IAFTER").getNodeValue());
						((Element)node).setAttribute("SIMULTANEOUS", node2.getAttributes().getNamedItem("SIMULTANEOUS").getNodeValue());
						((Element)node).setAttribute("IDENTITY", node2.getAttributes().getNamedItem("IDENTITY").getNodeValue());
						((Element)node).setAttribute("INCLUDES", node2.getAttributes().getNamedItem("INCLUDES").getNodeValue());
						((Element)node).setAttribute("IS_INCLUDED", node2.getAttributes().getNamedItem("IS_INCLUDED").getNodeValue());
						((Element)node).setAttribute("BEGINS", node2.getAttributes().getNamedItem("BEGINS").getNodeValue());
						((Element)node).setAttribute("BEGUN_BY", node2.getAttributes().getNamedItem("BEGUN_BY").getNodeValue());
						((Element)node).setAttribute("ENDS", node2.getAttributes().getNamedItem("ENDS").getNodeValue());
						((Element)node).setAttribute("ENDED_BY", node2.getAttributes().getNamedItem("ENDED_BY").getNodeValue());
						((Element)node).setAttribute("DURING", node2.getAttributes().getNamedItem("DURING").getNodeValue());
						((Element)node).setAttribute("DURING_INV", node2.getAttributes().getNamedItem("DURING_INV").getNodeValue());
						((Element)node).setAttribute("FOUND", "yes");
						((Element)node3).setAttribute("FOUND", "yes");

             			found = true;
						break;
					}
				}	
				/*if(!found){
					System.out.println("remove : " + node.getAttributes().getNamedItem("lid").getNodeValue());
					doc.getChildNodes().item(0).removeChild(node);
					length--;
					i--;
					//break;
					doc2.getChildNodes().item(0).removeChild(node3);
				}*/
			}
			fileName = (file.getAbsolutePath()).replace(".feature", ".tml").replace(featurePath, answerPath);

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer;
			try {
				transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(new File(fileName));
				transformer.transform(source, result);
			} catch (TransformerConfigurationException e) {
				e.printStackTrace();
			} catch (TransformerException e) {
				e.printStackTrace();
			}
			
			if(!goldPath.equals("")){
				TransformerFactory transformerFactory2 = TransformerFactory.newInstance();
				Transformer transformer2;
				try {
					transformer2 = transformerFactory2.newTransformer();
					DOMSource source2 = new DOMSource(doc2);
					StreamResult result2 = new StreamResult(new File(fileName.replace(answerPath, goldPath)));
					transformer2.transform(source2, result2);
				} catch (TransformerConfigurationException e) {
					e.printStackTrace();
				} catch (TransformerException e) {
					e.printStackTrace();
				}
			}
		}
	}
	

	
	
	public static void main(String[] args){
		AnswerWriter myAnswerWriter = new AnswerWriter();
		
	}
}
