#!/bin/sh

LANG=ru_RU.UTF8

CP1=lib/*/*.jar
CP2=lib/*.jar

echo you must wait until the end of the process

java -cp $( echo $CP1 + $CP2 . | sed 's/ /:/g') \
    -Dfile.encoding=UTF8 \
    -Dorg.apache.commons.logging.LogFactory=org.apache.commons.logging.impl.Log4jFactory \
    -Djavax.xml.transform.TransformerFactory=net.sf.saxon.TransformerFactoryImpl \
    -showversion -server -Xverify:none \
    -Xmx512m -Xms128m -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError \
     net.sf.xfresh.util.Starter indexer-beans.xml &

echo process finished
