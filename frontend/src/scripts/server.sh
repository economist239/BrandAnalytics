#!/bin/sh

LOG=brand-analytics-server.log
PID=brand-analytics-server.pid

start() {
    LANG=ru_RU.UTF8

    #CP1=lib/*/*.jar
    #CP2=lib/*.jar
    CLASSPATH=`find lib -name '*.jar' -printf '%p:'`$CLASSPATH

    if [ -s $PID ]; then
        echo already started.
        return
    fi

    java -classpath $CLASSPATH \
        -Dfile.encoding=UTF8 \
        -Dorg.apache.commons.logging.LogFactory=org.apache.commons.logging.impl.Log4jFactory \
        -Djavax.xml.transform.TransformerFactory=net.sf.saxon.TransformerFactoryImpl \
        -showversion -server -Xverify:none \
        -Xmx175m -Xms128m -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError \
         net.sf.xfresh.util.Starter frontend-beans.xml >> $LOG 2>&1 &

    echo $! > $PID
    echo server started
}

stop() {
     if [ ! -s $PID ]; then
         echo already stopped.
         return
     fi

     kill $(cat $PID)
     rm $PID
     echo server stoped
}


case "$1" in

start)
  start
  ;;

stop)
  stop
  ;;

restart)
  stop
  start

esac