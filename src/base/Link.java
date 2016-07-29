package base;

import java.util.ArrayList;
import java.util.TreeMap;

public class Link {
	public String lid;
	public String relType;
	public String category;
	public String ID;
	public String relatedID;
	public String fileName;
	public String path="path";
	public String reverse="false";
	public ArrayList<String> path3Grams;
	public ArrayList<String> path4Grams;
	public ArrayList<String> path5Grams;
	public ArrayList<String> path6Grams;
	public ArrayList<String> vertexWalks;
	public ArrayList<String> edgeWalks;
	public ArrayList<String> subVWalks;
	public ArrayList<String> subEWalks;


	public int pathLength;
	public int pathUpLength;
	public int pathDownLength;
	public String PAPath = "PAPath";
	
	public ArrayList<String> neighborFeatures;
	
	public TreeMap<String, Double> graph_op;
	public TreeMap<String, Double> graph_osp;
	public TreeMap<String, Double> graph_vev;
	public TreeMap<String, Double> graph_eve;
	public TreeMap<String, Double> graph_adj;
	public TreeMap<String, Double> prob;
	public TreeMap<String, Double> probPath;
	public TreeMap<String, Double> probPath2;
	public boolean hasPath;
  
	public TreeMap<String, Double> graph_op_3;
	public TreeMap<String, Double> graph_osp_3;
	public TreeMap<String, Double> graph_vev_3;
	public TreeMap<String, Double> graph_eve_3;
	public TreeMap<String, Double> probPath_3;
	public TreeMap<String, Double> probPath2_3;
	public boolean hasPath_3;
	
	public TreeMap<String, Double> graph_op_4;
	public TreeMap<String, Double> graph_osp_4;
	public TreeMap<String, Double> graph_vev_4;
	public TreeMap<String, Double> graph_eve_4;
	public TreeMap<String, Double> probPath_4;
	public TreeMap<String, Double> probPath2_4;
	public boolean hasPath_4;
	
	public String label;
	
	public int numPath2 = 0, numPath3 = 0, numPath4 = 0;
	
	public Link(String lid, String relType, String ID, String relatedID, String fileName){
		this.lid = lid;
		this.relType = relType;
		this.ID = ID;
		this.relatedID = relatedID;
		this.fileName = fileName;
		path3Grams=new ArrayList<String>();
		path4Grams=new ArrayList<String>();
		path5Grams=new ArrayList<String>();
		path6Grams=new ArrayList<String>();
		vertexWalks=new ArrayList<String>();
		edgeWalks=new ArrayList<String>();
		subVWalks=new ArrayList<String>();
		subEWalks=new ArrayList<String>();
		
		neighborFeatures=new ArrayList<String>();
		prob = new TreeMap<String,Double>();
		
		graph_op = new TreeMap<String, Double>();
		graph_osp = new TreeMap<String, Double>();
		graph_vev = new TreeMap<String, Double>();
		graph_eve = new TreeMap<String, Double>();
		graph_adj = new TreeMap<String, Double>();
		probPath = new TreeMap<String, Double>();
		probPath2 = new TreeMap<String, Double>();
		hasPath = false;
		
		graph_op_3 = new TreeMap<String, Double>();
		graph_osp_3 = new TreeMap<String, Double>();
		graph_vev_3 = new TreeMap<String, Double>();
		graph_eve_3 = new TreeMap<String, Double>();
		probPath_3 = new TreeMap<String, Double>();
		probPath2_3 = new TreeMap<String, Double>();
		hasPath_3 = false;
		
		graph_op_4 = new TreeMap<String, Double>();
		graph_osp_4 = new TreeMap<String, Double>();
		graph_vev_4 = new TreeMap<String, Double>();
		graph_eve_4 = new TreeMap<String, Double>();
		probPath_4 = new TreeMap<String, Double>();
		probPath2_4 = new TreeMap<String, Double>();
		hasPath_4  = false;
		label = "";
	}
	
	public void printRelation(){
		System.out.println(lid + " : " + ID + " " + relType + " " + relatedID);
	}
}
