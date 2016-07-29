package timeml;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableSet;
import java.util.TreeMap;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import base.Event;
import base.EventInstance;
import base.Timex3;

public class EventExtractor {
	public EventExtractor() {		
	}
	
	public void printNodeList(NodeList nodeList){
		int length = nodeList.getLength();
		for(int i = 0; i < length; i++){
			System.out.println(nodeList.item(i).getNodeName());
		}
	}
	
	public void printNodeList(NodeList nodeList, String attr){
		int length = nodeList.getLength();
		for(int i = 0; i < length; i++){
			System.out.println(nodeList.item(i).getAttributes().getNamedItem(attr).getNodeValue());
		}
	}
	
	public NodeList getEventInstance(org.w3c.dom.Document doc){
		NodeList eventInstanceList;
		eventInstanceList =  doc.getElementsByTagName("MAKEINSTANCE");
		return eventInstanceList;	
	}
	
	public NodeList getEvent(org.w3c.dom.Document doc){
		NodeList eventInstanceList;
		eventInstanceList =  doc.getElementsByTagName("EVENT");
		return eventInstanceList;	
	}
	
	public ArrayList<String> findEventInstance(org.w3c.dom.Document doc, String eventID){
		NodeList eventInstanceList = getEventInstance(doc);
		ArrayList<String> eiidList = new ArrayList<String>();
		//eventInstanceList =  doc.getElementsByTagName("MAKEINSTANCE");
		int length = eventInstanceList.getLength();
		for(int i = 0; i < length; i++){
			String tempValue = eventInstanceList.item(i).getAttributes().getNamedItem("eventID").getNodeValue();
			if(tempValue.equals(eventID)) eiidList.add(eventInstanceList.item(i).getAttributes().getNamedItem("eiid").getNodeValue());
		}
		return eiidList;	
	}
	
	public ArrayList<java.util.Map.Entry<String,String>> getEventStrings(org.w3c.dom.Document doc){
		NodeList eventList =  doc.getElementsByTagName("EVENT");
		ArrayList<java.util.Map.Entry<String,String>> eventString = new ArrayList<java.util.Map.Entry<String,String>>();
		int length = eventList.getLength();
		for(int i = 0; i < length; i++){
			Entry<String,String> pair = new AbstractMap.SimpleEntry<String,String>(eventList.item(i).getAttributes().getNamedItem("eid").getNodeValue(),eventList.item(i).getTextContent());
			eventString.add(pair);
			//System.out.println(pair.getKey() + " " + pair.getValue());
		}
		return eventString;	
	}
	
/*	public void getEventAttributes(Document doc, Event event){	
		NamedNodeMap attributes = eventNode.getAttributes();
		int length = attributes.getLength();
		for(int i = 0; i < length; i++){
			attributes.item(i).getNodeName()			
		}
	}
	public TreeMap<String,String> getIntermediateFeatures(Document doc, String EventID){
		TreeMap<String,String> intermediateFeatures = new TreeMap<String,String>();
		
 		return intermediateFeatures;
	}*/
	
	public String getAttrValue(Node node, String attr){
		Node temp = node.getAttributes().getNamedItem(attr);
		if(temp != null)return temp.getNodeValue();
		else return "none";
	}
	
	public Node findEventNode(org.w3c.dom.Document doc, String attr, String value){
		NodeList eventList =  doc.getElementsByTagName("EVENT");
		int length = eventList.getLength();
		for(int i = 0; i < length; i++){
			String tempValue = eventList.item(i).getAttributes().getNamedItem(attr).getNodeValue();
			if(tempValue.equals(value)) return eventList.item(i);
		}
		return null;
	}
	
	public Node findNode(NodeList eventList, String attr, String value){		
		int length = eventList.getLength();
		for(int i = 0; i < length; i++){
			String tempValue = eventList.item(i).getAttributes().getNamedItem(attr).getNodeValue();
			if(tempValue.equals(value)) return eventList.item(i);
		}
		System.out.println("Can't find node " + value);
		return null;
	}
	
	public boolean isSentence(String text){
		boolean isSentence = false;
		if(text.contains("<s>")) isSentence = true;
		return isSentence;
	}
	
	public void getIntermediateFeatures(Document doc, TreeMap<String,EventInstance> eventInstanceList){
		NodeList instanceList = getEventInstance(doc);
		NavigableSet<String> keys = eventInstanceList.navigableKeySet();
		for(String key : keys){
			Node e = findNode(instanceList,"eiid", key);
			if(e!=null)
			{
				EventInstance eventInstance = eventInstanceList.get(key);
				eventInstance.intermediateFeatures.put("TENSE", getAttrValue(e,"tense"));
				eventInstance.intermediateFeatures.put("ASPECT", getAttrValue(e,"aspect"));
				eventInstance.intermediateFeatures.put("POLARITY", getAttrValue(e,"polarity"));
				eventInstance.intermediateFeatures.put("POS", getAttrValue(e,"pos"));
			}		
		}
	}
	
	public void getIntermediateFeatures2(Document doc, TreeMap<String,EventInstance> eventInstanceList){
		NodeList instanceList = getEvent(doc);
		NavigableSet<String> keys = eventInstanceList.navigableKeySet();
		for(String key : keys){
			Node e = findNode(instanceList,"eid", key);
			if(e!=null)
			{
				EventInstance eventInstance = eventInstanceList.get(key);
				eventInstance.intermediateFeatures.put("TENSE", getAttrValue(e,"tense"));
				eventInstance.intermediateFeatures.put("ASPECT", getAttrValue(e,"aspect"));
				eventInstance.intermediateFeatures.put("POLARITY", getAttrValue(e,"polarity"));
				eventInstance.intermediateFeatures.put("POS", getAttrValue(e,"pos"));
				eventInstance.intermediateFeatures.put("MAINEVENT", getAttrValue(e,"mainevent"));
				eventInstance.intermediateFeatures.put("STEM", getAttrValue(e,"stem"));
			}		
		}
	}
	
	public Timex3 getDCT(Document doc){
		NodeList DCTs = (doc.getElementsByTagName("DCT").item(0).getChildNodes());
		Timex3 t = new Timex3();
		int length = DCTs.getLength();
        for(int i = 0; i<length; i++){
        	Node DCT = DCTs.item(i);
	        if(DCT.getNodeName().equals("DCT")){	        	
		        t.tid = DCT.getAttributes().getNamedItem("tid").getNodeValue();
				t.type = DCT.getAttributes().getNamedItem("type").getNodeValue();
				t.value = DCT.getAttributes().getNamedItem("value").getNodeValue();
				break;
	        }
        }
        return t;
	}
}
