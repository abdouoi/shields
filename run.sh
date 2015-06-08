#!/bin/bash

SCRIPT_DIR=$(cd $(dirname ${0}) && pwd -P)

cd ${SCRIPT_DIR}

mvn install -DskipTests

LIB_DIR=./target/dependency

export CLASSPATH=$(JARS=(${LIB_DIR}/*.jar); IFS=: ; echo "${JAR_FILE}:${JARS[*]}")
CLASSPATH=$(ls ./target/shields*.jar)${CLASSPATH}

java com.github.nwillc.shields.Shields
