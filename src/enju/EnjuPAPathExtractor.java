package enju;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.graph.*;
import edu.uci.ics.jung.graph.event.GraphEvent.Edge;
import toolinterface.EnjuInterface;

public class EnjuPAPathExtractor {
	DijkstraShortestPath<String, String> dsp;
	
	EnjuInterface enjuInterface;
	public EnjuPAPathExtractor(String fileName){
		enjuInterface = new EnjuInterface(fileName);		
	}
	
	
	public Graph<String,String> getGraph(int sentenceNum, boolean useBase){
		Graph<String, String> graph = new DirectedSparseGraph<String, String>();
		List<Node> tokenList = enjuInterface.getTokenList(sentenceNum);    
		//List<Node> consList = enjuInterface.getConsList(sentenceNum);
		int length = tokenList.size();
		
		for(int i = 0; i < length; i++){
			Node node = tokenList.get(i);
			boolean b = graph.addVertex(node.getTextContent()+enjuInterface.getAttrValue(node, "id"));
			if(!b) {
				//System.out.println(node.getTextContent() + " " +b);
				return null;
			}
			//System.out.println(node.getTextContent());
		}
		for(int i = 0; i < length; i++){
			Node node = tokenList.get(i);//.getParentNode();
			String pred = enjuInterface.getAttrValue(node,"pred");
			if(!pred.equals("none")){
				String arg1 = enjuInterface.getAttrValue(node,"arg1");
				String arg2 = enjuInterface.getAttrValue(node,"arg2");
				String arg3 = enjuInterface.getAttrValue(node,"arg3");
				String arg4 = enjuInterface.getAttrValue(node,"arg4");

				if(!arg1.equals("none")  && !arg1.equals("unk")){
					Node tok = getArg(sentenceNum,arg1);
					//System.out.println(pred + " " + node.getTextContent() + " " + tok.getTextContent());
					graph.addEdge(pred+"_arg1_f." + getArgText(node,useBase) +  "="+ getArgText(tok,useBase) + "-" + node.getTextContent()+enjuInterface.getAttrValue(node, "id") + "-" + tok.getTextContent()+enjuInterface.getAttrValue(tok, "id"), node.getTextContent()+enjuInterface.getAttrValue(node, "id"), tok.getTextContent()+enjuInterface.getAttrValue(tok, "id"));
					graph.addEdge(pred+"_arg1_b." + getArgText(tok,useBase) + "=" + getArgText(node,useBase) + "-" + tok.getTextContent()+enjuInterface.getAttrValue(tok, "id") + "-" + node.getTextContent()+enjuInterface.getAttrValue(node, "id"), tok.getTextContent()+enjuInterface.getAttrValue(tok, "id"), node.getTextContent()+enjuInterface.getAttrValue(node, "id"));
					
				}
				if(!arg2.equals("none")  && !arg2.equals("unk")){
					Node tok = getArg(sentenceNum,arg2);
					//System.out.println(pred + " " + node.getTextContent() + " " + tok.getTextContent());
					graph.addEdge(pred+"_arg2_f." + getArgText(node,useBase) +  "="+ getArgText(tok,useBase) + "-" + node.getTextContent()+enjuInterface.getAttrValue(node, "id") + "-" + tok.getTextContent()+enjuInterface.getAttrValue(tok, "id"), node.getTextContent()+enjuInterface.getAttrValue(node, "id"), tok.getTextContent()+enjuInterface.getAttrValue(tok, "id"));
					graph.addEdge(pred+"_arg2_b." + getArgText(tok,useBase) + "=" + getArgText(node,useBase) + "-" + tok.getTextContent()+enjuInterface.getAttrValue(tok, "id") + "-" + node.getTextContent()+enjuInterface.getAttrValue(node, "id"), tok.getTextContent()+enjuInterface.getAttrValue(tok, "id"), node.getTextContent()+enjuInterface.getAttrValue(node, "id"));
					
				}
				if(!arg3.equals("none")  && !arg3.equals("unk")){
					Node tok = getArg(sentenceNum,arg3);
					//System.out.println(pred + " " + node.getTextContent() + " " + tok.getTextContent());
					graph.addEdge(pred+"_arg3_f." + getArgText(node,useBase) +  "="+ getArgText(tok,useBase) + "-" + node.getTextContent()+enjuInterface.getAttrValue(node, "id") + "-" + tok.getTextContent()+enjuInterface.getAttrValue(tok, "id"), node.getTextContent()+enjuInterface.getAttrValue(node, "id"), tok.getTextContent()+enjuInterface.getAttrValue(tok, "id"));
					graph.addEdge(pred+"_arg3_b." + getArgText(tok,useBase) + "=" + getArgText(node,useBase) + "-" + tok.getTextContent()+enjuInterface.getAttrValue(tok, "id") + "-" + node.getTextContent()+enjuInterface.getAttrValue(node, "id"), tok.getTextContent()+enjuInterface.getAttrValue(tok, "id"), node.getTextContent()+enjuInterface.getAttrValue(node, "id"));
					
				}
				if(!arg4.equals("none")  && !arg4.equals("unk")){
					Node tok = getArg(sentenceNum,arg4);
					//System.out.println(pred + " " + node.getTextContent() + " " + tok.getTextContent());
					graph.addEdge(pred+"_arg4_f." + getArgText(node,useBase) +  "="+ getArgText(tok,useBase) + "-" + node.getTextContent()+enjuInterface.getAttrValue(node, "id") + "-" + tok.getTextContent()+enjuInterface.getAttrValue(tok, "id"), node.getTextContent()+enjuInterface.getAttrValue(node, "id"), tok.getTextContent()+enjuInterface.getAttrValue(tok, "id"));
					graph.addEdge(pred+"_arg4_b." + getArgText(tok,useBase) + "=" + getArgText(node,useBase) + "-" + tok.getTextContent()+enjuInterface.getAttrValue(tok, "id") + "-" + node.getTextContent()+enjuInterface.getAttrValue(node, "id"), tok.getTextContent()+enjuInterface.getAttrValue(tok, "id"), node.getTextContent()+enjuInterface.getAttrValue(node, "id"));
					
				}
			}
		}
		return graph;
	}
	
	public Graph<String,String> getGraphF(int sentenceNum, boolean useBase){
		Graph<String, String> graph = new DirectedSparseGraph<String, String>();
		List<Node> tokenList = enjuInterface.getTokenList(sentenceNum);    
		//List<Node> consList = enjuInterface.getConsList(sentenceNum);
		int length = tokenList.size();
		
		for(int i = 0; i < length; i++){
			Node node = tokenList.get(i);
			boolean b = graph.addVertex(node.getTextContent()+enjuInterface.getAttrValue(node, "id"));
			if(!b) {
				//System.out.println(node.getTextContent() + " " +b);
				return null;
			}
			//System.out.println(node.getTextContent());
		}
		for(int i = 0; i < length; i++){
			Node node = tokenList.get(i);//.getParentNode();
			String pred = enjuInterface.getAttrValue(node,"pred");
			if(!pred.equals("none")){
				String arg1 = enjuInterface.getAttrValue(node,"arg1");
				String arg2 = enjuInterface.getAttrValue(node,"arg2");
				String arg3 = enjuInterface.getAttrValue(node,"arg3");
				String arg4 = enjuInterface.getAttrValue(node,"arg4");

				if(!arg1.equals("none")  && !arg1.equals("unk")){
					Node tok = getArg(sentenceNum,arg1);
					//System.out.println(pred + " " + node.getTextContent() + " " + tok.getTextContent());
					graph.addEdge(pred+"_arg1_f." + getArgText(node,useBase) +  "="+ getArgText(tok,useBase) + "-" + node.getTextContent()+enjuInterface.getAttrValue(node, "id") + "-" + tok.getTextContent()+enjuInterface.getAttrValue(tok, "id"), node.getTextContent()+enjuInterface.getAttrValue(node, "id"), tok.getTextContent()+enjuInterface.getAttrValue(tok, "id"));
					
				}
				if(!arg2.equals("none")  && !arg2.equals("unk")){
					Node tok = getArg(sentenceNum,arg2);
					//System.out.println(pred + " " + node.getTextContent() + " " + tok.getTextContent());
					graph.addEdge(pred+"_arg2_f." + getArgText(node,useBase) +  "="+ getArgText(tok,useBase) + "-" + node.getTextContent()+enjuInterface.getAttrValue(node, "id") + "-" + tok.getTextContent()+enjuInterface.getAttrValue(tok, "id"), node.getTextContent()+enjuInterface.getAttrValue(node, "id"), tok.getTextContent()+enjuInterface.getAttrValue(tok, "id"));
					
				}
				if(!arg3.equals("none")  && !arg3.equals("unk")){
					Node tok = getArg(sentenceNum,arg3);
					//System.out.println(pred + " " + node.getTextContent() + " " + tok.getTextContent());
					graph.addEdge(pred+"_arg3_f." + getArgText(node,useBase) +  "="+ getArgText(tok,useBase) + "-" + node.getTextContent()+enjuInterface.getAttrValue(node, "id") + "-" + tok.getTextContent()+enjuInterface.getAttrValue(tok, "id"), node.getTextContent()+enjuInterface.getAttrValue(node, "id"), tok.getTextContent()+enjuInterface.getAttrValue(tok, "id"));
					
				}
				if(!arg4.equals("none")  && !arg4.equals("unk")){
					Node tok = getArg(sentenceNum,arg4);
					//System.out.println(pred + " " + node.getTextContent() + " " + tok.getTextContent());
					graph.addEdge(pred+"_arg4_f." + getArgText(node,useBase) +  "="+ getArgText(tok,useBase) + "-" + node.getTextContent()+enjuInterface.getAttrValue(node, "id") + "-" + tok.getTextContent()+enjuInterface.getAttrValue(tok, "id"), node.getTextContent()+enjuInterface.getAttrValue(node, "id"), tok.getTextContent()+enjuInterface.getAttrValue(tok, "id"));
					
				}
			}
		}
		return graph;
	}
		
	private String getArgText(Node node, boolean useBase){
		if(!useBase){
			if(!enjuInterface.getAttrValue(node ,"pos").equals("IN")) return enjuInterface.getAttrValue(node ,"pos");		
		/*	if(enjuInterface.getAttrValue(node ,"cat").equals("V")) return "";
			else if(enjuInterface.getAttrValue(node , "cat").equals("N")) return "";
			else if(enjuInterface.getAttrValue(node , "cat").equals("ADJ")) return "";
		*/	else return enjuInterface.getAttrValue(node , "base");
		}else return enjuInterface.getAttrValue(node , "base");
	}
	
	
	private Node getArg(int sentenceNum,String arg){
		Node node = enjuInterface.getNodeFromID(sentenceNum,arg);
		while(!node.getNodeName().equals("tok")){
			//System.out.println(node.getNodeName());
			node = enjuInterface.getNodeFromID(sentenceNum, enjuInterface.getAttrValue(node, "sem_head"));
		}
		return node;
	}

	public List<String> getShortestPath(int sentenceNum, String tok1, String tok2, boolean useBase){
		Graph<String, String> g = getGraph(sentenceNum, useBase);
		dsp = new DijkstraShortestPath<String, String>(g);
		//System.out.println("vertex count : " + g.getVertexCount());
		
		//System.out.println(g.getEdgeCount());
		Collection<String> e = g.getVertices();
		//for(String s : e){
		//	System.out.println(s);
		//}
		//System.out.println(tok1 + " " + tok2);
		List<String> p = null;
		if(g.containsVertex(tok1) && g.containsVertex(tok2)){
			p = dsp.getPath(tok1, tok2);	
		}
				
		return p;
	}
	
	public String getPAPath(int sentenceNum, String tok1, String tok2,int tokid_1, int tokid_2){
		int tokid1 = enjuInterface.getTokId(sentenceNum, tok1, tokid_1);
		int tokid2 = enjuInterface.getTokId(sentenceNum, tok2, tokid_2);
		String path = "";
		if(tokid1 != 2000 && tokid2 != 2000){
			List<String> p = getShortestPath(sentenceNum,tok1+"t"+tokid1,tok2+"t"+tokid2, false);
			
			StringTokenizer st;
			/*if(p.size()==0) {
				p = getShortestPath(sentenceNum,tok2,tok1);
				path = "b_";
			}*/
			if(p!=null){
				for(String s :p){
					st = new StringTokenizer(s);
					path += st.nextToken(".")+"_" ;
				}
			}
		}
		return path;
	}
	
	public ArrayList<String> getVertexWalks(int sentenceNum, String tok1, String tok2 ,int tokid_1, int tokid_2){
		int tokid1 = enjuInterface.getTokId(sentenceNum, tok1, tokid_1);
		int tokid2 = enjuInterface.getTokId(sentenceNum, tok2, tokid_2);
		List<String> p = getShortestPath(sentenceNum,tok1+"t"+tokid1,tok2+"t"+tokid2, false);
		
		StringTokenizer st;
		ArrayList<String> vertexWalks= new ArrayList<String>();		
		if(p!=null)
			for(String s :p){
				st = new StringTokenizer(s);
				vertexWalks.add(st.nextToken("-"));
			}
		
		p = getShortestPath(sentenceNum,tok1+"t"+tokid1,tok2+"t"+tokid2, true);

		if(p!=null)
			for(String s :p){
				st = new StringTokenizer(s);
				vertexWalks.add(st.nextToken("-")) ;
			}
		
		return vertexWalks;
	}
	
	public ArrayList<String> getEdgeWalks(int sentenceNum, String tok1, String tok2 ,int tokid_1, int tokid_2){
		int tokid1 = enjuInterface.getTokId(sentenceNum, tok1, tokid_1);
		int tokid2 = enjuInterface.getTokId(sentenceNum, tok2, tokid_2);
		List<String> p = getShortestPath(sentenceNum,tok1+"t"+tokid1,tok2+"t"+tokid2, false);
		
		StringTokenizer st,st2;
		ArrayList<String> edgeWalks= new ArrayList<String>();		
		
		if(p!=null)
		if(p.size() > 1){
			for(int i = 0; i< p.size()-1 ; i++){
				st = new StringTokenizer(p.get(i));
				st2 = new StringTokenizer(p.get(i+1));
				edgeWalks.add(st.nextToken("=")+"#"+st2.nextToken(".")) ;
			}
		}
		
		p = getShortestPath(sentenceNum,tok1+"t"+tokid1,tok2+"t"+tokid2, true);
		
		if(p!=null)
		if(p.size() > 1){
			for(int i = 0; i< p.size()-1 ; i++){
				st = new StringTokenizer(p.get(i));
				st2 = new StringTokenizer(p.get(i+1));
				edgeWalks.add(st.nextToken("=")+"#"+st2.nextToken(".")) ;
			}
		}
		
		return edgeWalks;
	}
	
	public ArrayList<String> getSubVWalks(ArrayList<String> VWalks){
		StringTokenizer st;
		String tok1,tok2,tok3;
		ArrayList<String> subVWalks = new ArrayList<String>();
		for(String walk : VWalks){
			st = new StringTokenizer(walk);
			//System.out.println(walk);
			tok1 = st.nextToken(".");
			tok2 = st.nextToken("=");
			if(st.hasMoreTokens())tok3 = st.nextToken();
			else tok3 = "";
			subVWalks.add(tok1+".*=" + tok3);
			subVWalks.add(tok1+tok2+"=*");
			subVWalks.add(tok1 + ".*=*");
		}
		
		return subVWalks;
	}
	
	public ArrayList<String> getSubEWalks(ArrayList<String> EWalks){
		StringTokenizer st;
		String tok1,tok2,tok3;
		ArrayList<String> subEWalks = new ArrayList<String>();
		for(String walk : EWalks){
			st = new StringTokenizer(walk);
			//System.out.println(walk);
			tok1 = st.nextToken(".");
			tok2 = st.nextToken("#");
			if(st.hasMoreTokens())tok3 = st.nextToken();
			else tok3 = "";
			subEWalks.add("*"+tok2+"#"+tok3);
			subEWalks.add(tok1+tok2+"#*");
			subEWalks.add("*"+tok2+ "#*");
		}
		
		return subEWalks;
	}
		
	public static void main (String args[]){
		String folderPath = "./data/xml/";
		EnjuPAPathExtractor enjuPAPathExtractor = new EnjuPAPathExtractor(folderPath+"ABC19980114.1830.0611.tml");
		List<String> p = enjuPAPathExtractor.getShortestPath(8, "tries", "raise", false);
		//System.out.println(p.size());
		//for(String s :p){
		//	System.out.println(s);
		//}
		//System.out.println(enjuPAPathExtractor.getPAPath(8, "tries", "raise",0,0));
	}
}
