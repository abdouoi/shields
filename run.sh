#!/bin/bash


SCRIPT_DIR=$(cd $(dirname ${0}) && pwd)
LIB_DIR=${SCRIPT_DIR}/target/dependency

export CLASSPATH=$(JARS=(${LIB_DIR}/*.jar); IFS=: ; echo "${JAR_FILE}:${JARS[*]}")
CLASSPATH=$(ls ${SCRIPT_DIR}/target/shields*.jar)${CLASSPATH}

java com.github.nwillc.shields.Shields
