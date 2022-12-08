#!/bin/bash

./gradlew bootjar
docker-compose up -d
