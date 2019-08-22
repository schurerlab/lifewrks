#!/bin/bash
echo "---------------------------------"
echo "LIFE Framework: @author: Sam Abeyruwan"
echo "---------------------------------"
THE_CLASSPATH=
for i in `ls ../lib/*.jar`
do
  THE_CLASSPATH=${THE_CLASSPATH}:${i}
  echo ${i}
done

#---------------------------#
# rut the program #
#---------------------------#
echo "Run the program with config file $1"

java -Xmx2048m -cp ".:${THE_CLASSPATH}"  \
          edu.miami.ccs.life.Main $1
