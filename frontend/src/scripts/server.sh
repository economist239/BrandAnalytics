#!/bin/sh

LOG=brandanalytics.log
PID=brandanalytics.pid

start() {
    LANG=ru_RU.UTF8

    CP1=lib/*/*.jar
    CP2=lib/*.jar

    if [ -s $PID ]; then
        echo already started.
        exit
    fi

    java -cp $( echo $CP1 + $CP2 . | sed 's/ /:/g') \
        -Dfile.encoding=UTF8 \
        -Dorg.apache.commons.logging.LogFactory=org.apache.commons.logging.impl.Log4jFactory \
        -Djavax.xml.transform.TransformerFactory=net.sf.saxon.TransformerFactoryImpl \
        -showversion -server -Xverify:none \
        -Xmx512m -Xms128m -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError \
         net.sf.xfresh.util.Starter frontend-beans.xml >> $LOG 2>&1 &

    echo $! > $PID
    echo server started
}

stop() {
     if [ ! -s $PID ]; then
         echo already stopped.
         exit
     fi

     start-stop-daemon --quiet --retry 10 --stop --pidfile $PID 1>/dev/null 2>&1
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