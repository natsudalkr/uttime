package toolinterface;

import java.io.StringWriter;
import java.util.List;
import java.util.Properties;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import edu.stanford.nlp.ling.CoreAnnotations.CharacterOffsetBeginAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.CharacterOffsetEndAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.XmlContextAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class StanfordAnnotatorInterface {
	Properties props; 
    StanfordCoreNLP pipeline; 

    Annotation document;
    
    
	public StanfordAnnotatorInterface(){
		props = new Properties();		
        //props.put("tokenize.options", "americanize=false");
	    props.put("annotators", "tokenize, ssplit");
	    pipeline = new StanfordCoreNLP(props);		 
	}
	
	public StanfordAnnotatorInterface(String annotation){
		props = new Properties();		
	    props.put("annotators", annotation);
        //props.put("tokenize.options", "americanize=false");
	    pipeline = new StanfordCoreNLP(props);		 
	}
	
	
	
	public List<CoreMap> annotate(String text){	
		document = new Annotation(text);
		//System.out.println(stw.toString());
	    pipeline.annotate(document);
	    List<CoreMap> sentences = document.get(SentencesAnnotation.class);
	    return sentences;
	}
	
	
	public static void main(String args[]){
		StanfordAnnotatorInterface myAnnotatorInterface = new StanfordAnnotatorInterface("tokenize, cleanxml, ssplit, pos, lemma");
		
		String fileName = "./data/TimeBank/APW19980227.0487.tml";
        System.out.println(fileName);
        XMLInterface myXmlInterface = new XMLInterface();
        Document myDoc = myXmlInterface.parseFile(fileName);	  
        String text = myXmlInterface.serializeNode(myDoc.getElementsByTagName("TEXT").item(0));
        //String text = myXmlInterface.serializeNode(myDoc);
        List<CoreMap> sentences = myAnnotatorInterface.annotate(text);
        int offset1 = text.indexOf("<TEXT>")+6, offset2;
    	for(CoreMap sentence:sentences)
    	{
    		//List<String> context = sentence.get(XmlContextAnnotation.class);
    		offset2 = sentence.get(CharacterOffsetBeginAnnotation.class);	        	
        	String sentenceString = text.substring(offset1, offset2).trim() + sentence.toString();
    		System.out.println("Sentence :" + sentenceString);
    		String context = "";
    		String pos = "";
    		String lemma = "";
    		for(CoreLabel token: sentence.get(TokensAnnotation.class)){
    			context += token.get(TextAnnotation.class) + " / ";
    			pos += token.get(PartOfSpeechAnnotation.class) + " / ";
    			lemma += token.get(LemmaAnnotation.class) + " / ";
    		}
    		offset1 = sentence.get(CharacterOffsetEndAnnotation.class);
    		System.out.println(context);
    		System.out.println(lemma);
    		System.out.println(pos);
    		System.out.println();
    		
    	}
	}
}

