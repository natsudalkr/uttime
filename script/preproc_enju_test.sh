#!/bin/bash
mkdir data/text_test
mkdir data/enju_test
path="data/te3-platinum/"
java -cp stanford-corenlp-full-2015-12-09/stanford-corenlp-3.6.0.jar:stanford-corenlp-full-2015-12-09/slf4j-api.jar:stanford-corenlp-full-2015-12-09/slf4j-simple.jar:./bin toolinterface/TextFileGenerator "$path" data/text_test
for f in `find data/text_test -maxdepth 1 -type f -name "*.txt" `
do
  echo "Processing file $f"
  f2="${f/text_test/enju_test}.enju"
  f3="${f2/.txt/}"
  echo "Output to file $f2"
  enju-2.4.2/enju -xml < "$f" > "$f2"
  java -cp bin toolinterface/EnjuInterface "patchfile" "$f2" "$f3"
  echo "Output to file $f3"
  rm "$f2"
done