#!/bin/bash

SCRIPT_DIR=$(cd $(dirname ${0}) && pwd -P)

cd ${SCRIPT_DIR}

mvn install -DskipTests

java -cp target/classes:target/dependency/* com.github.nwillc.shields.Shields