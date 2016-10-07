#!/bin/bash

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}" )" > /dev/null 2>&1 && pwd -P)"

cd ${SCRIPT_DIR}

echo Rebuilding...
./gradlew -q clean oneJar -x test

JAVA_OPTS=-Djava.awt.headless=true

echo Start server...
java ${JAVA_OPTS} -jar build/libs/shields-*-standalone.jar $*
