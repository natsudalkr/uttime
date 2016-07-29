package tempeval;

import java.util.LinkedList;
import java.util.NavigableSet;
import java.util.TreeMap;

import base.Dictionary;
import base.EventInstance;
import base.Link;
import base.Timex3;

public class CTRRelationFeatureExtractor extends CTRFeatureExtractor{
	public CTRRelationFeatureExtractor(){
		
	}
	
	public void extractRelationFeature(EventInstance e1, EventInstance e2, Link tLink, CTRFeatVecSet featVecSet){		
	
			addFeature("EE", featVecSet.baseline);
			addFeature(e1.event.getBasicFeatureWords("E1_BASIC_"), featVecSet.baseline);
	    	addFeature(e1.getIntermediateFeatureWords("E1_INTERMEDIATE_"), featVecSet.baseline);
	    	addFeature(e1.event.getSynset("E1_SYNSET_"), featVecSet.baseline);
	    	addFeature("E1_CLASS_"+e1.event.eventClass, featVecSet.baseline);
	    	addFeature(e2.event.getBasicFeatureWords("E2_BASIC_"),featVecSet.baseline);
	    	addFeature(e2.getIntermediateFeatureWords("E2_INTERMEDIATE_"), featVecSet.baseline);
	    	addFeature(e2.event.getSynset("E2_SYNSET_"), featVecSet.baseline);
	    	addFeature("E2_CLASS_"+e2.event.eventClass, featVecSet.baseline);
	    	addFeature("E_TENSE_BIGRAM_"+ e1.intermediateFeatures.get("TENSE")+"_"+e2.intermediateFeatures.get("TENSE"), featVecSet.baseline);
	    	addFeature("E_ASPECT_BIGRAM_"+ e1.intermediateFeatures.get("ASPECT")+"_"+e2.intermediateFeatures.get("ASPECT"), featVecSet.baseline);
	    	addFeature("E_CLASS_BIGRAM_"+ e1.event.eventClass+"_"+e2.event.eventClass, featVecSet.baseline);
	    	if(e1.intermediateFeatures.get("TENSE").equals(e2.intermediateFeatures.get("TENSE"))){
	    		addFeature("E_TENSE_MATCH_TRUE", featVecSet.baseline);
	    	}else{
	    		addFeature("E_TENSE_MATCH_FALSE", featVecSet.baseline);
	    	}
	    	if(e1.intermediateFeatures.get("ASPECT").equals(e2.intermediateFeatures.get("ASPECT"))){
	    		addFeature("E_ASPECT_MATCH_TRUE", featVecSet.baseline);
	    	}else{
	    		addFeature("E_ASPECT_MATCH_FALSE", featVecSet.baseline);
	    	}
	    	if(e1.event.eventClass.equals(e2.event.eventClass)){
	    		addFeature("E_CLASS_MATCH_TRUE", featVecSet.baseline);
	    	}else{
	    		addFeature("E_CLASS_MATCH_FALSE", featVecSet.baseline);
	    	}
	    	if(e1.event.sentenceNumber < e2.event.sentenceNumber) addFeature("S_BEFORE_TRUE",featVecSet.baseline);
	    	if(e1.event.sentenceNumber > e2.event.sentenceNumber) addFeature("S_AFTER_TRUE",featVecSet.baseline);
	    	if(e1.event.sentenceNumber == e2.event.sentenceNumber){
	    		addFeature("S_SAME_TRUE",featVecSet.baseline);
	    	/*	int distance = (e1.event.tokenNumberS-e2.event.tokenNumberS);
	    		if(distance == -1) addFeature("TOKEN_DIS_NEXT1",featVec);
	    		if(distance == -2) addFeature("TOKEN_DIS_NEXT2",featVec);
	    		if(distance < -1) addFeature("TOKEN_DIS_NEXTB",featVec);
	    		if(distance > 1) addFeature("TOKEN_DIS_NEXTA",featVec);*/
	    	}    
	    	if(!tLink.path.equals("path")){
		    	addFeature("PATH_"+tLink.path, featVecSet.deepsyn);
		    	addFeature("PATH_3GRAMS_", tLink.path3Grams,  featVecSet.deepsyn);
		    	//addFeature(dictionary, tLink.path4Grams, featVec);
		    	//addFeature(dictionary, tLink.path5Grams, featVec);
		    	//addFeature(dictionary, tLink.path6Grams, featVec);
		    	//addFeature(dictionary, "E_PATH_LENGTH" + tLink.pathLength, featVec);
		    	addFeature("PATH_UP_LENGTH_" + tLink.pathUpLength,  featVecSet.deepsyn);
		    	addFeature("PATH_DOWN_LENGTH_" + tLink.pathDownLength,  featVecSet.deepsyn);
		    	addFeature("PA_VWALKS_",tLink.vertexWalks,  featVecSet.deepsyn);
		    	addFeature("PA_EWALKS_",tLink.edgeWalks,  featVecSet.deepsyn);
		    	addFeature("PA_SUBVWALKS_",tLink.subVWalks,  featVecSet.deepsyn);
		    	addFeature("PA_SUBEWALKS_",tLink.subEWalks,  featVecSet.deepsyn);
		    	if(!tLink.PAPath.equals("PAPath")) addFeature("PAPATH_"+tLink.PAPath, featVecSet.deepsyn);
	    	}

	    	addFeature(tLink.neighborFeatures, featVecSet.graph);
	    	
	    	addFeature(tLink.graph_op, featVecSet.graph_op_w);
	    	addFeature(tLink.graph_osp, featVecSet.graph_osp_w);
	    	addFeature(tLink.graph_vev, featVecSet.graph_vev_w);
	    	addFeature(tLink.graph_eve, featVecSet.graph_eve_w);
	    	addFeature(tLink.graph_adj, featVecSet.graph_adj_w);	    	
	    	addFeature(tLink.probPath, featVecSet.probPath);
	    	addFeature(tLink.probPath2, featVecSet.probPath2);
	    	
	    	addFeature(tLink.graph_op_3, featVecSet.graph_op_w_3);
	    	addFeature(tLink.graph_osp_3, featVecSet.graph_osp_w_3);
	    	addFeature(tLink.graph_vev_3, featVecSet.graph_vev_w_3);
	    	addFeature(tLink.graph_eve_3, featVecSet.graph_eve_w_3);
	    	addFeature(tLink.probPath_3, featVecSet.probPath_3);
	    	addFeature(tLink.probPath2_3, featVecSet.probPath2_3);
	    	
	    	addFeature(tLink.graph_op_4, featVecSet.graph_op_w_4);
	    	addFeature(tLink.graph_osp_4, featVecSet.graph_osp_w_4);
	    	addFeature(tLink.graph_vev_4, featVecSet.graph_vev_w_4);
	    	addFeature(tLink.graph_eve_4, featVecSet.graph_eve_w_4);	    	
	    	addFeature(tLink.probPath_4, featVecSet.probPath_4);
	    	addFeature(tLink.probPath2_4, featVecSet.probPath2_4);
	    	
	    	addFeature(tLink.prob, featVecSet.prob);
	    	addFeature(tLink.hasPath+"", featVecSet.hasPath);
	    	addFeature(tLink.hasPath_3+"", featVecSet.hasPath_3);
	    	addFeature(tLink.hasPath_4+"", featVecSet.hasPath_4);
	    	addFeature(tLink.label, featVecSet.label);
	}
	
	public void extractRelationFeature(Timex3 e1, EventInstance e2, Link tLink, CTRFeatVecSet featVecSet){	   	
		
		//if(Configuration.useBaseLine){
			addFeature("TE", featVecSet.baseline);
			addFeature(e1.getBasicFeatureWords("T1_"),featVecSet.baseline);
			addFeature("T1_DCT_"+e1.isDCT, featVecSet.baseline);
	    	//addFeature(dictionary, e1.getSynset("T_SYNSET_"), featVec);
	    	addFeature(e2.event.getBasicFeatureWords("E2_BASIC_"), featVecSet.baseline);
	    	addFeature(e2.getIntermediateFeatureWords("E2_INTERMEDIATE_"), featVecSet.baseline);
	    	addFeature(e2.event.getSynset("E2_SYNSET_"), featVecSet.baseline);
	    	addFeature("E2_CLASS_"+e2.event.eventClass, featVecSet.baseline);
		//}
		//if(Configuration.useDeepSyn){
	    if(!tLink.path.equals("path")){
	    	addFeature("PATH_"+tLink.path, featVecSet.deepsyn);
	    	addFeature("PATH_3GRAMS_", tLink.path3Grams, featVecSet.deepsyn);
	    	//addFeature(dictionary, tLink.path4Grams, featVec);
	    	//addFeature(dictionary, tLink.path5Grams, featVec);
	    	//addFeature(dictionary, tLink.path6Grams, featVec);
	    	//addFeature(dictionary, "E_PATH_LENGTH" + tLink.pathLength, featVec);
	    	addFeature("PATH_UP_LENGTH_" + tLink.pathUpLength, featVecSet.deepsyn);
	    	addFeature("PATH_DOWN_LENGTH_" + tLink.pathDownLength, featVecSet.deepsyn);
	    	addFeature("PA_VWALKS_",tLink.vertexWalks, featVecSet.deepsyn);
	    	addFeature("PA_EWALKS_",tLink.edgeWalks, featVecSet.deepsyn);
	    	addFeature("PA_SUBVWALKS_",tLink.subVWalks, featVecSet.deepsyn);
	    	addFeature("PA_SUBEWALKS_",tLink.subEWalks, featVecSet.deepsyn);
	    	if(!tLink.PAPath.equals("PAPath")) addFeature("PAPATH_"+tLink.PAPath, featVecSet.deepsyn);
	    }
		//}
		//if(Configuration.useNeighbor){			
			addFeature(tLink.neighborFeatures, featVecSet.graph);			
	    	addFeature(tLink.graph_op, featVecSet.graph_op);
	    	addFeature(tLink.graph_osp, featVecSet.graph_osp);
	    	addFeature(tLink.graph_vev, featVecSet.graph_vev);
	    	addFeature(tLink.graph_eve, featVecSet.graph_eve);
	    	addFeature(tLink.graph_adj, featVecSet.graph_adj);
	    	addFeature(tLink.probPath, featVecSet.probPath);
	    	addFeature(tLink.probPath2, featVecSet.probPath2);
			/*	NavigableSet<String> keys = tLink.probPath.navigableKeySet();
	    	for(String key: keys){
	    		addFeature(dictionary, key, featVec, tLink.probPath.get(key));
	    	}
	    	keys = tLink.probPath2.navigableKeySet();
	    	for(String key: keys){
	    		addFeature(dictionary, key, featVec, tLink.probPath2.get(key));
	    	}*/
		//}
	}
	
	public void extractRelationFeature(EventInstance e1, Timex3 e2,Link tLink, CTRFeatVecSet featVecSet){	
		
			addFeature("ET", featVecSet.baseline);
			addFeature(e1.event.getBasicFeatureWords("E1_BASIC_"), featVecSet.baseline);
	    	addFeature(e1.getIntermediateFeatureWords("E1_INTERMEDIATE_"),featVecSet.baseline);
	    	addFeature(e1.event.getSynset("E1_SYNSET_"), featVecSet.baseline);
	    	addFeature("E1_CLASS_"+e1.event.eventClass, featVecSet.baseline);
	    	addFeature(e2.getBasicFeatureWords("T2_"), featVecSet.baseline);
	    	addFeature("T2_DCT_"+e2.isDCT, featVecSet.baseline);
	    	// 	addFeature(50000,dictionary, e2.getSynset("T_SYNSET_"), featVec);
	    	if(!tLink.path.equals("path")){
		    	addFeature("PATH_"+tLink.path, featVecSet.deepsyn);
		    	addFeature("PATH_3GRAMS_", tLink.path3Grams, featVecSet.deepsyn);
		    	addFeature("PATH_UP_LENGTH_" + tLink.pathUpLength, featVecSet.deepsyn);
		    	addFeature("PATH_DOWN_LENGTH_" + tLink.pathDownLength, featVecSet.deepsyn);
		    	addFeature("PA_VWALKS_",tLink.vertexWalks, featVecSet.deepsyn);
		    	addFeature("PA_EWALKS_",tLink.edgeWalks, featVecSet.deepsyn);
		    	addFeature("PA_SUBVWALKS_",tLink.subVWalks, featVecSet.deepsyn);
		    	addFeature("PA_SUBEWALKS_",tLink.subEWalks,  featVecSet.deepsyn);
		    	if(!tLink.PAPath.equals("PAPath")) addFeature("PAPATH_"+tLink.PAPath, featVecSet.deepsyn);
	    	}
	    	addFeature(tLink.neighborFeatures, featVecSet.graph);
	    	addFeature(tLink.graph_op, featVecSet.graph_op_w);
	    	addFeature(tLink.graph_osp, featVecSet.graph_osp_w);
	    	addFeature(tLink.graph_vev, featVecSet.graph_vev_w);
	    	addFeature(tLink.graph_eve, featVecSet.graph_eve_w);
	    	addFeature(tLink.graph_adj, featVecSet.graph_adj_w);
	    	addFeature(tLink.probPath, featVecSet.probPath);
	    	addFeature(tLink.probPath2, featVecSet.probPath2);
	    	addFeature(tLink.graph_op_3, featVecSet.graph_op_w_3);
	    	addFeature(tLink.graph_osp_3, featVecSet.graph_osp_w_3);
	    	addFeature(tLink.graph_vev_3, featVecSet.graph_vev_w_3);
	    	addFeature(tLink.graph_eve_3, featVecSet.graph_eve_w_3);
	    	addFeature(tLink.probPath_3, featVecSet.probPath_3);
	    	addFeature(tLink.probPath2_3, featVecSet.probPath2_3);
	    	addFeature(tLink.graph_op_4, featVecSet.graph_op_w_4);
	    	addFeature(tLink.graph_osp_4, featVecSet.graph_osp_w_4);
	    	addFeature(tLink.graph_vev_4, featVecSet.graph_vev_w_4);
	    	addFeature(tLink.graph_eve_4, featVecSet.graph_eve_w_4);
	    	addFeature(tLink.probPath_4, featVecSet.probPath_4);
	    	addFeature(tLink.probPath2_4, featVecSet.probPath2_4);
	    	
	    	addFeature(tLink.prob, featVecSet.prob);
	    	addFeature(tLink.hasPath+"", featVecSet.hasPath);
	    	addFeature(tLink.hasPath_3+"", featVecSet.hasPath_3);
	    	addFeature(tLink.hasPath_4+"", featVecSet.hasPath_4);
	    	addFeature(tLink.label, featVecSet.label);
   	
	}
	
	public void extractRelationFeature(Timex3 e1, Timex3 e2, Link tLink,  CTRFeatVecSet featVecSet){	
	
		//if(Configuration.useBaseLine){
			addFeature(e1.getBasicFeatureWords("T1_"), featVecSet.baseline);
			//addFeature(dictionary, e1.getSynset("T_SYNSET_"), featVec);
	    	addFeature(e2.getBasicFeatureWords("T2_"), featVecSet.baseline);
	    	//addFeature(500000, dictionary, e2.getSynset("T_SYNSET_"), featVec);
		//}
		//if(Configuration.useNeighbor){
	    	addFeature(tLink.neighborFeatures, featVecSet.graph);
	    	addFeature(tLink.graph_op, featVecSet.graph_op);
	    	addFeature(tLink.graph_osp, featVecSet.graph_osp);
	    	addFeature(tLink.graph_vev, featVecSet.graph_vev);
	    	addFeature(tLink.graph_eve, featVecSet.graph_eve);
	    	addFeature(tLink.graph_adj, featVecSet.graph_adj); 
			/*
	     	NavigableSet<String> keys = tLink.probPath.navigableKeySet();
	    	for(String key: keys){
	    		addFeature(dictionary, key, featVec, tLink.probPath.get(key));
	    	}
	    	keys = tLink.probPath2.navigableKeySet();
	    	for(String key: keys){
	    		addFeature(dictionary, key, featVec, tLink.probPath2.get(key));
	    	}
	    	*/
		//}
	}
}
