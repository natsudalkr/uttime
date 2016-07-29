package timeml;

import java.util.NavigableSet;
import java.util.TreeMap;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import base.EventInstance;
import base.Link;
import base.TLink;

public class RelationExtractor {
	
	public RelationExtractor(){
		
	}
	
	
	public void extractRelation4(String fileName, Document doc, TreeMap<String,Link> LinkListEE, TreeMap<String,Link> LinkListTE, TreeMap<String,Link> LinkListET, TreeMap<String,Link> LinkListTT, TreeMap<String,Link> SLinkList, TreeMap<String,Link> ALinkList){
		extractTRelationBetweenEvents(fileName, doc, LinkListEE);
		extractTRelationBetweenTimeAndEvent3(fileName, doc, LinkListET);
		extractTRelationBetweenEventAndTime(fileName,doc, LinkListET);
		extractTRelationBetweenTimes(fileName,doc, LinkListTT);
		
	}
	
	
	public void extractTRelationBetweenEvents(String fileName, Document doc, TreeMap<String,Link> TLinkList){
		NodeList TLinkNodeList = getTLink(doc);
		int length = TLinkNodeList.getLength();
		//System.out.println("TLINK: " + length);
		for(int i = 0; i < length; i++){
			Node node = TLinkNodeList.item(i);
			String lid = node.getAttributes().getNamedItem("lid").getNodeValue();
			String ID = "";
			if(node.getAttributes().getNamedItem("eventInstanceID")!=null){
				ID = node.getAttributes().getNamedItem("eventInstanceID").getNodeValue();
			}else{	
				continue;
			}
			String relatedID = "";        
			if(node.getAttributes().getNamedItem("relatedToEventInstance")!=null){
				relatedID = node.getAttributes().getNamedItem("relatedToEventInstance").getNodeValue();
			}else{
				continue;
			}
			
			String relType = node.getAttributes().getNamedItem("relType").getNodeValue();
			TLinkList.put(lid, new Link(lid,relType,ID,relatedID,fileName));
		}
	}
	

	
	public void extractTRelationBetweenEventAndTime(String fileName, Document doc, TreeMap<String,Link> TLinkList){
		NodeList TLinkNodeList = getTLink(doc);
		int length = TLinkNodeList.getLength();
		for(int i = 0; i < length; i++){
			Node node = TLinkNodeList.item(i);
			String lid = node.getAttributes().getNamedItem("lid").getNodeValue();
			String ID = "";
			String relatedID = "";
			if(node.getAttributes().getNamedItem("eventInstanceID")!=null){
				ID = node.getAttributes().getNamedItem("eventInstanceID").getNodeValue();
				if(node.getAttributes().getNamedItem(("relatedToTime"))!=null){
					relatedID = node.getAttributes().getNamedItem("relatedToTime").getNodeValue();
				}else{
					continue;
				}
			}else{	
				continue;
			}
			
			String relType = node.getAttributes().getNamedItem("relType").getNodeValue();
			TLinkList.put(lid, new Link(lid,relType,ID,relatedID,fileName));
		}
	}
	
	
	public void extractTRelationBetweenTimes(String fileName, Document doc, TreeMap<String,Link> TLinkList){
		NodeList TLinkNodeList = getTLink(doc);
		int length = TLinkNodeList.getLength();
		for(int i = 0; i < length; i++){
			Node node = TLinkNodeList.item(i);
			String lid = node.getAttributes().getNamedItem("lid").getNodeValue();
			String ID = "";
			String relatedID = "";
			if(node.getAttributes().getNamedItem("timeID")!=null){
				ID = node.getAttributes().getNamedItem("timeID").getNodeValue();
				if(node.getAttributes().getNamedItem("relatedToTime")!=null){
					relatedID = node.getAttributes().getNamedItem("relatedToTime").getNodeValue();
				}else{
					continue;
				}
			}else{	
				continue;
			}
			
			String relType = node.getAttributes().getNamedItem("relType").getNodeValue();
			TLinkList.put(lid, new Link(lid,relType,ID,relatedID,fileName));
		}
	}
	

	
	public void extractTRelationBetweenTimeAndEvent3(String fileName, Document doc, TreeMap<String,Link> TLinkList){
		NodeList TLinkNodeList = getTLink(doc);
		int length = TLinkNodeList.getLength();
		for(int i = 0; i < length; i++){
			Node node = TLinkNodeList.item(i);
			String lid = node.getAttributes().getNamedItem("lid").getNodeValue();
			String ID = "";
			String relatedID = "";
			if(node.getAttributes().getNamedItem("timeID")!=null){
				ID = node.getAttributes().getNamedItem("timeID").getNodeValue();
				if(node.getAttributes().getNamedItem("relatedToEventInstance")!=null){
					relatedID = node.getAttributes().getNamedItem("relatedToEventInstance").getNodeValue();
				}else{
					continue;
				}
			}else{	
				continue;
			}
			
			String relType = node.getAttributes().getNamedItem("relType").getNodeValue();
			//TLinkList.put(lid, new Link(lid,relType,ID,relatedID,fileName));
			String relType_i = "";
			if(relType.equals("BEFORE")) relType_i = "AFTER";
			if(relType.equals("AFTER")) relType_i = "BEFORE";
			if(relType.equals("IBEFORE")) relType_i = "IAFTER";
			if(relType.equals("IAFTER")) relType_i = "IBEFORE";
			if(relType.equals("INCLUDES")) relType_i = "IS_INCLUDED";
			if(relType.equals("IS_INCLUDED")) relType_i = "INCLUDES";
			if(relType.equals("ENDS")) relType_i = "ENDED_BY";
			if(relType.equals("BEGINS")) relType_i = "BEGUN_BY";
			if(relType.equals("ENDED_BY")) relType_i = "ENDS";
			if(relType.equals("BEGUN_BY")) relType_i = "BEGINS";
			if(relType.equals("DURING")) relType_i = "DURING";
			if(relType.equals("SIMULTANEOUS")) relType_i = "SIMULTANEOUS";
			if(relType.equals("IDENTITY")) relType_i = "SIMULTANEOUS";
			if(relType.equals("UNDEF")) relType_i = "UNDEF";
			Link link = new Link(lid+"i",relType_i,relatedID,ID,fileName);
			
			link.reverse = "true";
			TLinkList.put(lid+"i", link);
		}
	}
	
	

	
	public NodeList getTLink(Document doc){
		NodeList TlinkNodeList;
		TlinkNodeList =  doc.getElementsByTagName("TLINK");
		return TlinkNodeList;	
	}

	
	public void printRelationList(TreeMap<String, Link> TLinkListEE, TreeMap<String, Link> TLinkListTE, TreeMap<String, Link> TLinkListET, TreeMap<String, Link> TLinkListTT, TreeMap<String, Link> SLinkList, TreeMap<String, Link> ALinkList){
		NavigableSet<String> keys = TLinkListEE.navigableKeySet();
       for(String key: keys){
        	TLinkListEE.get(key).printRelation();
        }	 
        keys = TLinkListTE.navigableKeySet();
        for(String key: keys){
        	TLinkListTE.get(key).printRelation();
        } 
        keys = TLinkListET.navigableKeySet();
        for(String key: keys){
        	TLinkListET.get(key).printRelation();
        }
            keys = TLinkListTT.navigableKeySet();
        for(String key: keys){
        	TLinkListTT.get(key).printRelation();
        }	
    
	}
	
}
