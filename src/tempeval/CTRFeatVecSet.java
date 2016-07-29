package tempeval;

import java.util.LinkedList;
import java.util.TreeMap;

public class CTRFeatVecSet {
	LinkedList<String> baseline;
	LinkedList<String> deepsyn;
	LinkedList<String> graph;
	LinkedList<String> graph_adj;
	LinkedList<String> graph_op;
	LinkedList<String> graph_osp;
	LinkedList<String> graph_vev;
	LinkedList<String> graph_eve;
	
	TreeMap<String, Double> baseline_w;
	TreeMap<String, Double> deepsyn_w;
	TreeMap<String, Double> graph_w;
	TreeMap<String, Double> graph_adj_w;
	TreeMap<String, Double> graph_op_w;
	TreeMap<String, Double> graph_osp_w;
	TreeMap<String, Double> graph_vev_w;
	TreeMap<String, Double> graph_eve_w;
	TreeMap<String, Double> probPath;
	TreeMap<String, Double> probPath2;
	
	TreeMap<String, Double> graph_w_3;
	TreeMap<String, Double> graph_op_w_3;
	TreeMap<String, Double> graph_osp_w_3;
	TreeMap<String, Double> graph_vev_w_3;
	TreeMap<String, Double> graph_eve_w_3;
	TreeMap<String, Double> probPath_3;
	TreeMap<String, Double> probPath2_3;

	TreeMap<String, Double> graph_w_4;
	TreeMap<String, Double> graph_op_w_4;
	TreeMap<String, Double> graph_osp_w_4;
	TreeMap<String, Double> graph_vev_w_4;
	TreeMap<String, Double> graph_eve_w_4;
	TreeMap<String, Double> probPath_4;
	TreeMap<String, Double> probPath2_4;
	
	TreeMap<String, Double> prob;
	
	TreeMap<String, Double> hasPath;
	TreeMap<String, Double> hasPath_3;
	TreeMap<String, Double> hasPath_4;
	
	TreeMap<String, Double> label;
	
	
	public CTRFeatVecSet(){
		baseline = new LinkedList<String>();
		deepsyn = new LinkedList<String>();
		graph = new LinkedList<String>();
		graph_adj = new LinkedList<String>();
		graph_op = new LinkedList<String>();
		graph_osp = new LinkedList<String>();
		graph_vev = new LinkedList<String>();
		graph_eve = new LinkedList<String>();
		
		baseline_w = new TreeMap<String, Double>();
		deepsyn_w = new TreeMap<String, Double>();
		
		graph_w = new TreeMap<String, Double>();
		graph_adj_w = new TreeMap<String, Double>();
		graph_op_w = new TreeMap<String, Double>();
		graph_osp_w = new TreeMap<String, Double>();
		graph_vev_w = new TreeMap<String, Double>();
		graph_eve_w = new TreeMap<String, Double>();		
		probPath = new TreeMap<String, Double>();
		probPath2 = new TreeMap<String, Double>();
	
		graph_w_3 = new TreeMap<String, Double>();
		graph_op_w_3 = new TreeMap<String, Double>();
		graph_osp_w_3 = new TreeMap<String, Double>();
		graph_vev_w_3 = new TreeMap<String, Double>();
		graph_eve_w_3 = new TreeMap<String, Double>();		
		probPath_3 = new TreeMap<String, Double>();
		probPath2_3 = new TreeMap<String, Double>();
	
		graph_w_4 = new TreeMap<String, Double>();
		graph_op_w_4 = new TreeMap<String, Double>();
		graph_osp_w_4 = new TreeMap<String, Double>();
		graph_vev_w_4 = new TreeMap<String, Double>();
		graph_eve_w_4 = new TreeMap<String, Double>();		
		probPath_4 = new TreeMap<String, Double>();
		probPath2_4 = new TreeMap<String, Double>();
		
		prob = new TreeMap<String, Double>();
		hasPath = new TreeMap<String, Double>();
		hasPath_3 = new TreeMap<String, Double>();
		hasPath_4 = new TreeMap<String, Double>();
		label = new TreeMap<String, Double>();
	}
	
}
