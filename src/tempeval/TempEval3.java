package tempeval;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NavigableSet;
import java.util.StringTokenizer;
import java.util.TreeMap;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.bwaldvogel.liblinear.Linear;
import de.bwaldvogel.liblinear.Model;
import de.bwaldvogel.liblinear.Parameter;
import de.bwaldvogel.liblinear.Problem;
import de.bwaldvogel.liblinear.SolverType;

import edu.stanford.nlp.ling.CoreAnnotations.CharacterOffsetBeginAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.CharacterOffsetEndAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.util.CoreMap;


import timeml.AnswerWriter;
import timeml.EventAnalyzer;
import timeml.EventExtractor;
import timeml.AnswerFormatWriter;

import toolinterface.FileInterface;
import toolinterface.LiblinearInterface;
import timeml.ReferenceFileInterface;
import toolinterface.XMLInterface;

import base.Dictionary;
import base.LabelWeight;

public class TempEval3 {
	
	FileInterface fileInterface;
	
	List<File> files;	
	CTRProcessFeature processFeature;
 
	ReferenceFileInterface referenceFileInterface_trainEE;
	ReferenceFileInterface referenceFileInterface_testEE;
	ReferenceFileInterface referenceFileInterface_trainET;
	ReferenceFileInterface referenceFileInterface_testET;

	Dictionary labels;
	LiblinearInterface liblinearInterface;
	
	AnswerWriter answerWriter;
	AnswerFormatWriter answerFormatWriter; 
	
	int lineNumber = 0;
 	boolean useDeepSyn = false;

 	String corporaPath = "";
 	String ansPath = "";
 	String goldPath = "";
 	String ansEvalPath = "";
 	String goldEvalPath = "";

    public static void main(String args[]){
        String featurePath = args[1];
        String outputPath = args[2];
        
        String EEFileName = outputPath + "/EE-feature.txt";      
        String ETFileName = outputPath + "/ET-feature.txt";

        String EEModelFileName = outputPath + "/EE-model.txt";
        String ETModelFileName = outputPath + "/ET-model.txt";

    	TempEval3 myTempEval3 = new TempEval3(outputPath);
    	myTempEval3.useDeepSyn = Boolean.parseBoolean(args[3]);
    	if(args[0].equals("train")){
	    	myTempEval3.train(featurePath, EEFileName, ETFileName, EEModelFileName, ETModelFileName);	
	    	myTempEval3.processFeature.labelSet.printDictToFile(outputPath + "/label.txt");
	    	myTempEval3.processFeature.dict.printDictToFile(outputPath + "/dict.txt");
	    }else if(args[0].equals("test")){
    		myTempEval3.processFeature.dict.loadDictionary(outputPath + "/dict.txt");
    		myTempEval3.processFeature.labelSet.loadDictionary(outputPath + "/label.txt");
			myTempEval3.test(featurePath, EEFileName+"_test", ETFileName+"_test", EEModelFileName, ETModelFileName);
    	}	
    }
	
	public TempEval3(String outputPath){	
		corporaPath = "data/te3-platinum/";
        ansPath = outputPath + "/answer/answer";
    	goldPath = outputPath + "/answer/gold";   		
    	ansEvalPath = outputPath + "/answer/answer_eval";
    	goldEvalPath = outputPath + "/answer/gold_eval";
    	new File(ansPath).mkdirs();
    	new File(goldPath).mkdir();
    	new File(ansEvalPath).mkdir();
    	new File(goldEvalPath).mkdir();
		fileInterface = new FileInterface();
		liblinearInterface = new LiblinearInterface();
		answerWriter = new AnswerWriter();
		answerFormatWriter = new AnswerFormatWriter();
		labels = new Dictionary();
 		processFeature = new CTRProcessFeature();
		referenceFileInterface_trainEE = new ReferenceFileInterface();
		referenceFileInterface_testEE = new ReferenceFileInterface();
		referenceFileInterface_trainET = new ReferenceFileInterface();
		referenceFileInterface_testET = new ReferenceFileInterface();
	}

	public void train(String featurePath, String EEFileName, String ETFileName, String EEModelFileName, String ETModelFileName){
		double C = 0.125;
    	double eps = 0.02;
		LabelWeight labelWeight_EE = new LabelWeight("EE");
    	LabelWeight labelWeight_ET = new LabelWeight("ET");
		files = fileInterface.genFileList(featurePath);
		fileInterface.printFileList(files);
		processFeature.process(files, EEFileName , ETFileName, referenceFileInterface_trainEE ,referenceFileInterface_trainET, useDeepSyn);
		liblinearInterface.trainModel(SolverType.L2R_LR, C, eps, labelWeight_EE, EEFileName, EEModelFileName);
    	liblinearInterface.trainModel(SolverType.L2R_LR, C, eps, labelWeight_ET, ETFileName, ETModelFileName);
	}
	
	public void test(String featurePath, String EEFileName, String ETFileName, String EEModelFileName, String ETModelFileName){
		files = fileInterface.genFileList(featurePath);
		fileInterface.printFileList(files);
		processFeature.process(files, EEFileName , ETFileName, referenceFileInterface_testEE ,referenceFileInterface_testET, useDeepSyn);
		liblinearInterface.testModel(EEFileName, EEModelFileName,  "", processFeature.labelSet, referenceFileInterface_testEE , EEFileName+"_ref");
    	liblinearInterface.testModel(ETFileName, ETModelFileName, "", processFeature.labelSet, referenceFileInterface_testET , ETFileName+"_ref");

    	answerWriter.write(EEFileName+"_ref", files, featurePath, corporaPath, goldPath, corporaPath, ansPath, corporaPath);
   		answerWriter.write(ETFileName+"_ref", files, featurePath, corporaPath, goldPath, goldPath, ansPath, ansPath);
		
		answerFormatWriter.writeFiles(goldPath, goldEvalPath);
		answerFormatWriter.writeFiles(ansPath, ansEvalPath);
	}
	


}
