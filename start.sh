#!/bin/bash
# gradle 버전 안맞아서 build fail일때 주석 해제
# echo "org.gradle.java.home=/usr/lib/jvm/java-11" > gradle.properties
./gradlew bootjar
docker-compose up -d
