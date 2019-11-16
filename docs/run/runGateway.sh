#!/usr/bin/env bash

BASE=`dirname "$0"`
CONF_DIR=./config/

JAVA_OPTS=""
JAVA_OPTS="$JAVA_OPTS -Dspring.cloud.config.label=master"
JAVA_OPTS="$JAVA_OPTS -Dport=7060 -Dmgmt-port=701 -Deureka.instance.hostname=localhost"
JAVA_OPTS="$JAVA_OPTS -Dlogging.config=http://localhost:7010/tdoer-gateway/prod/master/logback-spring.xml"
JAVA_OPTS="$JAVA_OPTS -Deureka.client.service-url.defaultZone=http://localhost:7020/eureka/"

echo $JAVA_OPTS

java -cp $CONF_DIR:./* $JAVA_OPTS -jar tdoer-gateway-1.0.0-RELEASE.jar
