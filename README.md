###Download and install enju
Download enju from http://www.nactem.ac.uk/enju/#download and install in the root folder
```
cd uttime
tar xvzf enju-2.4.2-macosx.tar.gz
```

###Download and unzip Standford coreNLP
```
wget http://nlp.stanford.edu/software/stanford-corenlp-full-2015-12-09.zip
unzip stanford-corenlp-full-2015-12-09.zip
```

###Download and install wordnet
```
wget http://wordnetcode.princeton.edu/3.0/WordNet-3.0.tar.gz
tar -xvf WordNet-3.0.tar.gz
wget http://lyle.smu.edu/~tspell/jaws/jaws-bin.jar
```

###Download jung
```
wget http://downloads.sourceforge.net/project/jung/jung/jung-2.0.1/jung2-2_0_1.zip
unzip jung2-2_0_1.zip -d jung/
```

###Download nu.xom
```
wget http://ftp.tsukuba.wide.ad.jp/software/apache//xerces/j/binaries/Xerces-J-bin.2.11.0.zip
unzip Xerces-J-bin.2.11.0.zip
wget http://www.cafeconleche.org/XOM/xom-1.0d8.jar
```

##Download liblinear
```
wget http://www.bwaldvogel.de/liblinear-java/liblinear-java-1.95.jar
```

###Download and unzip the training/test data from TempEval-3
```
mkdir data
wget https://www.cs.york.ac.uk/semeval-2013/task1/data/uploads/datasets/tbaq-2013-03.zip
unzip tbaq-2013-03.zip data
wget https://www.cs.york.ac.uk/semeval-2013/task1/data/uploads/datasets/te3-platinumstandard.tar.gz
tar -xvf te3-platinumstandard.tar.gz -C data
```

###Compile java source files
```
mkdir bin
export CLASSPATH=$CLASSPATH:stanford-corenlp-full-2015-12-09/stanford-corenlp-3.6.0.jar:jaws-bin.jar:./jung/jung-api-2.0.1.jar:./jung/jung-algorithms-2.0.1.jar:./jung/jung-graph-impl-2.0.1.jar:xom-1.0d8.jar:stanford-corenlp-full-2015-12-09/slf4j-api.jar:stanford-corenlp-full-2015-12-09/slf4j-simple.jar:stanford-corenlp-full-2015-12-09/stanford-corenlp-3.6.0-models.jar:xerces-2_11_0/xercesImpl.jar:xerces-2_11_0/xml-apis.jar:liblinear-java-1.95.jar:./bin:./src
javac -d bin src/*/*.java
```

### Pre-process the data
#### Training data
```
mkdir data/feature
script/preproc_enju.sh
java tempeval/CTRPreprocessFeature data/TBAQ-cleaned/TimeBank data/feature data/enju
```
#### Test data
```
mkdir data/feature_test
script/preproc_enju_test.sh
java tempeval/CTRPreprocessFeature data/te3-platinum data/feature_test data/enju_test
```

##Train the models
```
mkdir output
java tempeval/TempEval3 train data/feature output true
```

###Test
```
java tempeval/TempEval3 test data/feature_test output true
```

###Download and unzip the evaluation tools
```
wget http://www.cs.rochester.edu/~naushad/tempeval3/tools.zip
unzip tools.zip
```

### Evaluation
```
cd tools
python TE3-evaluation.py ../output/answer/gold_eval/ ../output/answer/answer_eval/
```
