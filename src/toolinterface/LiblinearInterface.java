package toolinterface;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.NavigableSet;
import java.util.TreeMap;

import timeml.ReferenceFileInterface;

import base.Dictionary;
import base.LabelWeight;

import de.bwaldvogel.liblinear.Feature;
import de.bwaldvogel.liblinear.InvalidInputDataException;
import de.bwaldvogel.liblinear.Linear;
import de.bwaldvogel.liblinear.Model;
import de.bwaldvogel.liblinear.Parameter;
import de.bwaldvogel.liblinear.Problem;
import de.bwaldvogel.liblinear.SolverType;

public class LiblinearInterface {
	public LiblinearInterface(){
		
	}
	
	public Problem readProblem(String fileName){
		Problem problem = null;
		File trainFile = new File(fileName);
		try {
			problem = Problem.readFromFile(trainFile, 1.0);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvalidInputDataException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return problem;
	}

        
	public void genWeight(TreeMap<String,Integer> dict, TreeMap<String,Double> labelWeight, Parameter parameter){
		double[] weights = new double[labelWeight.size()];
		int[] weightLabels = new int[labelWeight.size()];
		NavigableSet<String> keys = labelWeight.navigableKeySet(); 
		int i = 0;
		for(String key: keys){
			weights[i] = labelWeight.get(key);
			weightLabels[i] = dict.get(key);
			System.out.println(weightLabels[i] + " " + weights[i]);
			i++;
		}
		parameter.setWeights(weights, weightLabels);
	}
	
	public Model trainModel(SolverType solver, double C, double eps, LabelWeight labelWeight, String featureFileName, String modelFileName){
		Problem problem = readProblem(featureFileName);
		Parameter parameter = new Parameter(solver, C, eps);
		//genWeight(dictionary.dict, labelWeight.labelWeight, parameter);		
		Model model = Linear.train(problem, parameter);
		File modelFile = new File(modelFileName);
		try {
			model.save(modelFile);
			// load model or use it directly
			//model = Model.load(modelFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return model;
	}
	
	public void testModel(String featureFileName, String modelFileName, String labelPrefix, Dictionary dictionary, ReferenceFileInterface referenceFileInterface, String relFileName){
		Problem problem = readProblem(featureFileName);
		File modelFile = new File(modelFileName);
		double target[] = new double[problem.l];
		double probability[] = new double[1];
		TreeMap<Integer,String> label = dictionary.findLabel(labelPrefix);
		try {
			Model model = Model.load(modelFile);
			int i = 0;
			int correct = 0;			
			for(Feature[] x: problem.x){				
				target[i]= Linear.predict(model, x);
				//System.out.println(problem.y[i] + "\t" + target[i] + "\t" + probability[0]);
				if(problem.y[i]==target[i]) correct++;
				i++;
			}
			System.out.println("Accuracy: " + correct*100.0/i);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		referenceFileInterface.writeGuessedAnswer(target,label);
		referenceFileInterface.writeToFile(relFileName);
	}
	
}
