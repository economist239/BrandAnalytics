#!/bin/sh



case "$1" in

start)

LANG=ru_RU.UTF8

CP1=lib/*/*.jar
CP2=lib/*.jar

    java -cp $( echo $CP1 + $CP2 . | sed 's/ /:/g') \
        -Dfile.encoding=UTF8 \
        -Dorg.apache.commons.logging.LogFactory=org.apache.commons.logging.impl.Log4jFactory \
        -Djavax.xml.transform.TransformerFactory=net.sf.saxon.TransformerFactoryImpl \
        -showversion -server -Xverify:none \
        -Xmx512m -Xms128m -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError \
        net.sf.xfresh.util.Starter frontend-beans.xml &

;;
stop)

killall java

;;

esac