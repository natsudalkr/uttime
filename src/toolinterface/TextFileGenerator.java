package toolinterface;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.util.CoreMap;

public class TextFileGenerator {
	
	FileInterface myFileInterface;
	List<File> files;
	XMLInterface myXmlInterface;
	StanfordAnnotatorInterface myAnnotatorInterface;
	BufferedWriter out;
	FileWriter fstream;
	
	public TextFileGenerator(List<String> folderPath){
		myFileInterface = new FileInterface();
		myXmlInterface = new XMLInterface();
		myAnnotatorInterface = new StanfordAnnotatorInterface("tokenize, cleanxml, ssplit");
		files = myFileInterface.genFileList(folderPath);
		System.out.println(files.size());		
	}
	
	public void process(String outputFolder){
		for(File file: files) {
			
			String fileName2 =  outputFolder+"/"+file.getName().replace(".tml",".txt");
	        System.out.println(fileName2);
			try {
				fstream = new FileWriter(fileName2, false);
				out = new BufferedWriter(fstream);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			
	        String fileName = file.getAbsolutePath();
	        System.out.println(fileName);
	        
	        Document myDoc = myXmlInterface.parseFile(fileName);	
	        NodeList nl = myDoc.getElementsByTagName("TEXT");
	        for(int i =0; i < nl.getLength(); i++){
		        String text = myXmlInterface.serializeNode(nl.item(i));
		        List<CoreMap> sentences = myAnnotatorInterface.annotate(text);
		        
		        for(CoreMap sentence:sentences)
	        	{        	
		        
		        	String word = "";
		        	List<CoreLabel> tokens = sentence.get(TokensAnnotation.class);
		        	for(CoreLabel token: tokens) {			 
		   			  word += " " + token.get(TextAnnotation.class);
		        	}
		        	System.out.println(word);
		        	try {
						out.write(word);
						out.newLine();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        	}
		        	
        	}	
	        try {
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String args[]){	
		String[] folderPath = {"/Users/ononon1/Desktop/ace_2005_td_v7/data/English/wl/timex2norm/"};
		String outputFolder = "wl2";
		System.out.println(args.length);
		if(args.length > 1){

			folderPath[0] = args[0];
			outputFolder = args[1];
			System.out.println(folderPath[0]);
		}else if(args.length > 0){
			folderPath[0] = args[0];
		}
		
		TextFileGenerator myTextFileGenerator = new TextFileGenerator(Arrays.asList(folderPath));
		myTextFileGenerator.process(outputFolder);
		
		
	}
}
