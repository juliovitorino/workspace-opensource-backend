#!/bin/zsh

JAVA_PACKAGE_HOME="/Users/juliovitorino/workspaces/workspace-opensource-backend/notifier/src/main/java/br/com/jcv/notifier"
RESOURCE_PACKAGE_HOME="/Users/juliovitorino/workspaces/workspace-opensource-backend/notifier/src/main/resources/br/com/jcv/notifier"
TEST_PACKAGE_HOME="/Users/juliovitorino/workspaces/workspace-opensource-backend/notifier/src/test/java/br/com/jcv/notifier"
export JAVA_PACKAGE_HOME
export RESOURCE_PACKAGE_HOME
export TEST_PACKAGE_HOME

echo "creating project folders ${JAVA_PACKAGE_HOME}"

mkdir $JAVA_PACKAGE_HOME/config
mkdir $JAVA_PACKAGE_HOME/constantes
mkdir $JAVA_PACKAGE_HOME/controller
mkdir $JAVA_PACKAGE_HOME/controller/v1
mkdir $JAVA_PACKAGE_HOME/controller/v1/business
mkdir $JAVA_PACKAGE_HOME/dto
mkdir $JAVA_PACKAGE_HOME/exception
mkdir $JAVA_PACKAGE_HOME/enums
mkdir $JAVA_PACKAGE_HOME/model
mkdir $JAVA_PACKAGE_HOME/repository
mkdir $JAVA_PACKAGE_HOME/service
mkdir $JAVA_PACKAGE_HOME/service/impl


echo "creating project resource folders ${RESOURCE_PACKAGE_HOME}"

mkdir $RESOURCE_PACKAGE_HOME/analyser
mkdir $RESOURCE_PACKAGE_HOME/builder
mkdir $RESOURCE_PACKAGE_HOME/config
mkdir $RESOURCE_PACKAGE_HOME/constantes
mkdir $RESOURCE_PACKAGE_HOME/controller
mkdir $RESOURCE_PACKAGE_HOME/dto
mkdir $RESOURCE_PACKAGE_HOME/enums
mkdir $RESOURCE_PACKAGE_HOME/exception
mkdir $RESOURCE_PACKAGE_HOME/interfaces
mkdir $RESOURCE_PACKAGE_HOME/repository
mkdir $RESOURCE_PACKAGE_HOME/service
mkdir $RESOURCE_PACKAGE_HOME/service/impl
mkdir $RESOURCE_PACKAGE_HOME/DDL

echo "creating project test folders ${TEST_PACKAGE_HOME}"

mkdir $TEST_PACKAGE_HOME/builder
mkdir $TEST_PACKAGE_HOME/service
mkdir $TEST_PACKAGE_HOME/service/impl

echo "all folders have been created"