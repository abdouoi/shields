#!/bin/bash

SCRIPT_DIR=$(cd $(dirname ${0}) && pwd -P)

cd ${SCRIPT_DIR}

./gradlew -q stage

java -cp build/dependency:build/dependency/* com.github.nwillc.shields.Shields $*