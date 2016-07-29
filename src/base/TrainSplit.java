package base;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TrainSplit {

    // training files for this split
    public List<File> train = new ArrayList<File>(); 
    // test files for this split;
    public List<File> test = new ArrayList<File>();

  

    static public List<TrainSplit> getFolds(List<File> files, int numFolds) {
      List<TrainSplit> splits = new ArrayList<TrainSplit>();
      int i = 0;
      for( Integer fold=0; fold<numFolds; fold++ ) {
        TrainSplit split = new TrainSplit();
        i=0;
        for(File file: files) {
      	  if( (i%numFolds)== fold) {
      		  split.test.add(file);
      	  } else {
      		  split.train.add(file);
      	  }
      	  i++;
        }
        splits.add(split);
      }
      return splits;
    }
  

    public static List<TrainSplit> buildSplits(String path, int numFolds) {
    	
		File trainDir = new File(path);
		if (!trainDir.isDirectory()) {
			System.err.println("[ERROR]\tinvalid training directory specified.  ");
		}
		List<TrainSplit> splits = new ArrayList<TrainSplit>();
		System.out.println("[INFO]\tPerforming 10-fold cross-validation on data set:\t"+ trainDir.getAbsolutePath());
		List<File> files = new ArrayList<File>();
		for(File dir: trainDir.listFiles()) {  		
			if(!dir.getName().startsWith(".")) {
				files.add(dir);
			}
		}
		splits = getFolds(files, numFolds);
		return splits;
    }

    
public static List<TrainSplit> buildSplits(List<File> files, int numFolds) {
    	
	
		List<TrainSplit> splits = new ArrayList<TrainSplit>();

		splits = getFolds(files, numFolds);
		return splits;
    }
}

