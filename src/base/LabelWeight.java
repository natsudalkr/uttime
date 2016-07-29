package base;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class LabelWeight {
	public TreeMap<String,Double> labelWeight;
	
	public LabelWeight(String linkType){
		labelWeight = new TreeMap<String,Double>();
		addWeight(linkType);
	}
	
	public void addWeight(String linkType){
		String fileName = "./weight2/" +  linkType + ".txt";
		try {
			FileReader fileReader;
			BufferedReader bufferedReader;
			fileReader = new FileReader(new File(fileName));
			bufferedReader = new BufferedReader(fileReader);
			String line;
			while((line = bufferedReader.readLine()) != null){
				StringTokenizer tokenizer = new StringTokenizer(line);
				System.out.println(line);
				String label = "LABEL_REL_" + tokenizer.nextToken();
				Double weight = Double.parseDouble(tokenizer.nextToken());
				labelWeight.put(label, weight);
			}
			bufferedReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}


/*		labelWeight.put("LABEL_REL_TE_INCLUDES", 0.163194444);
labelWeight.put("LABEL_REL_TE_BEFORE", 13.48837209);
labelWeight.put("LABEL_REL_TE_DURING", 92.45);
labelWeight.put("LABEL_REL_TE_AFTER", 5.068181818);
labelWeight.put("LABEL_REL_TE_ENDED_BY", 92.45);
labelWeight.put("LABEL_REL_TE_IS_INCLUDED", 3.134955752);
labelWeight.put("LABEL_REL_TE_SIMULTANEOUS", 168.9090909);
labelWeight.put("LABEL_REL_TE_ENDS", 132.5);
labelWeight.put("LABEL_REL_TE_BEGINS", 41.47727273);
labelWeight.put("LABEL_REL_TE_IDENTITY", 622.0);
labelWeight.put("LABEL_REL_TE_BEGUN_BY", 933.500);
labelWeight.put("LABEL_REL_TE_IAFTER", 1868.0);
labelWeight.put("LABEL_REL_TE_IBEFORE", 1868.0);
*/