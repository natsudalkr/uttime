package timeml;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import toolinterface.FileInterface;
import toolinterface.XMLInterface;

public class AnswerFormatWriter {
	
	FileReader fileReader;
	BufferedReader bufferedReader;
	
	BufferedWriter bufferedWriter;
	FileWriter fileWriter;
	
	public AnswerFormatWriter(){
		
	}
	
	public void write(String inFileName, String outFileName){
		try {
			fileReader = new FileReader(new File(inFileName));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bufferedReader = new BufferedReader(fileReader);
		String tmpFileName = inFileName+".tmp";
		try {
			fileWriter = new FileWriter(tmpFileName, false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		bufferedWriter = new BufferedWriter(fileWriter);
		
		String line = "";
		try {
			while((line=bufferedReader.readLine())!=null){		
				if(line.contains("</TimeML")){
					line = line.replace("</TimeML", "\n\r</TimeML");
				}
				if(line.contains("</TempEval")){
					line = line.replace("</TempEval", "\n\r</TempEval");
				}
				if(line.contains("<TLINK")){
					line = line.replaceAll("<TLINK", "\n\r<TLINK");
				}
				bufferedWriter.write(line);
			//	System.out.println(line);
				bufferedWriter.newLine();
			}
			
			bufferedReader.close();			
			bufferedWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			fileReader = new FileReader(new File(tmpFileName));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bufferedReader = new BufferedReader(fileReader);
		try {
			fileWriter = new FileWriter(outFileName, false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bufferedWriter = new BufferedWriter(fileWriter);
	
		try {
			while((line=bufferedReader.readLine())!=null){
				if(line.contains("relType=\"UNKNOWN\"")) continue;
				if(line.contains("TLINK") && !line.contains("FOUND")) continue;
				if(line.contains("TLINK")){
					StringTokenizer tokenizer = new StringTokenizer(line);
					//System.out.println(line);
					String lid = "";
					String relType = "";
					String eiid = "";
					String tid = "";
					String reiid = "";
					String rtid = "";
					String found = "";
					String task = "";
					while(tokenizer.hasMoreTokens()){
						String token = tokenizer.nextToken();
						if(token.contains("lid=")){
							lid = token + " ";
						}else if(token.contains("relType")){
							relType = token + " ";
						}else if(token.contains("eventInstanceID")){
							eiid = token + " ";
						}else if(token.contains("eventID")){
							eiid = token + " ";
						}else if(token.contains("timeID")){
							tid = token + " ";
						}else if(token.contains("relatedToEventInstance")){
							reiid = token  + " ";
						}else if(token.contains("relatedToEvent")){
							reiid = token  + " ";
						}else if(token.contains("relatedToTime")){
							rtid = token  + " ";
						}else if(token.contains("task")){
							task = token;
						}else if(token.contains("FOUND")){
							found =   " " + token;
						}
					}
					line = "<TLINK " + lid +  relType + eiid + tid + reiid + rtid +task;// + found;
					line = line.replace("/>", "");
					line += "/>";
				}
				bufferedWriter.write(line);
		//		System.out.println(line);
				bufferedWriter.newLine();
			}
			bufferedReader.close();
			bufferedWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		File tempFile = new File(tmpFileName);
		tempFile.delete();
	}
	
	public void writeFiles(String answerPath, String answerPath2){
		FileInterface fileInterface = new FileInterface();

		List<File> files = fileInterface.genFileList(Arrays.asList(answerPath));

		for(File file: files){
			String fileName = file.getAbsolutePath();
			write(fileName,fileName.replace(answerPath, answerPath2));
		}
	}
	
	public static void main(String args[]){
		AnswerFormatWriter myAnswerFormatWriter = new AnswerFormatWriter();
		myAnswerFormatWriter.writeFiles("./answer/answerA/answerA1","./answer/answerA/answerA1_eval");
	}
}
