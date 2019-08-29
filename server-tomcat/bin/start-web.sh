#!/bin/bash
#chown 555 -R /export/home/tomcat/domains/
export CATALINA_HOME=/csz/installed/apache-tomcat-8.5.35
export CATALINA_BASE=/csz/catalina-bases/high-concurrency-simulation
###JAVA
export JAVA_HOME=/csz/installed/java/jdk1.8.0_181
export JAVA_BIN=/csz/installed/java/jdk1.8.0_181/bin
export PATH=/usr/lib64/qt-3.3/bin:/usr/local/sbin:/usr/local/bin:/sbin:/bin:/usr/sbin:/usr/bin:/root/bin
export CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
export JAVA_OPTS="-Djava.library.path=/usr/local/lib -server -Xms512m -Xmx1024m -XX:MaxMetaspaceSize=512m -XX:+UseConcMarkSweepGC -XX:CMSFullGCsBeforeCompaction=5 -XX:+UseCMSCompactAtFullCollection -XX:CMSInitiatingOccupancyFraction=80 -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=../logs/ -Djava.awt.headless=true -Dsun.net.client.defaultConnectTimeout=60000 -Dsun.net.client.defaultReadTimeout=60000 -Djmagick.systemclassloader=no -Dnetworkaddress.cache.ttl=300 -Dsun.net.inetaddr.ttl=300 -DserverName=server1 -Duser.home=/csz -Ddiamond.client.appName=app -Ddiamond.client.appKey=letv115"
export JAVA_HOME JAVA_BIN PATH CLASSPATH JAVA_OPTS
$CATALINA_HOME/bin/startup.sh -config $CATALINA_BASE/conf/server.xml
