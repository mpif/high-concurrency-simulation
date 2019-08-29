#!/bin/bash
export CATALINA_HOME=/csz/installed/apache-tomcat-8.5.35
export CATALINA_BASE=/csz/catalina-bases/high-concurrency-simulation
$CATALINA_HOME/bin/shutdown.sh -config $CATALINA_BASE/conf/server.xml
ps -aef | grep java|grep "/high-concurrency-simulation/"| grep -v grep | sed 's/ [ ]*/:/g' |cut -d: -f2|kill `cat`
