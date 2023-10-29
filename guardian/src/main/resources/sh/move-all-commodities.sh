#!/bin/zsh

JAVA_PACKAGE_HOME="/Users/juliovitorino/workspaces/workspace-opensource-backend/guardian/src/main/java/br/com/jcv/security/guardian"
RESOURCE_PACKAGE_HOME="/Users/juliovitorino/workspaces/workspace-opensource-backend/guardian/src/main/resources/br/com/jcv/security/guardian"
TEST_PACKAGE_HOME="/Users/juliovitorino/workspaces/workspace-opensource-backend/guardian/src/test/java/br/com/jcv/security/guardian"
export JAVA_PACKAGE_HOME
export RESOURCE_PACKAGE_HOME
export TEST_PACKAGE_HOME

echo "moving commodities coding to representative folder"

mv $RESOURCE_PACKAGE_HOME/builder/*.java $TEST_PACKAGE_HOME/builder
mv $RESOURCE_PACKAGE_HOME/config/*.java $JAVA_PACKAGE_HOME/config
mv $RESOURCE_PACKAGE_HOME/constantes/*.java $JAVA_PACKAGE_HOME/constantes
mv $RESOURCE_PACKAGE_HOME/controller/*.java $JAVA_PACKAGE_HOME/controller
mv $RESOURCE_PACKAGE_HOME/dto/*.java $JAVA_PACKAGE_HOME/dto
mv $RESOURCE_PACKAGE_HOME/enums/*.java $JAVA_PACKAGE_HOME/enums
mv $RESOURCE_PACKAGE_HOME/exception/*.java $JAVA_PACKAGE_HOME/exception
mv $RESOURCE_PACKAGE_HOME/repository/*.java $JAVA_PACKAGE_HOME/repository
mv $RESOURCE_PACKAGE_HOME/service/*.java $JAVA_PACKAGE_HOME/service
mv $RESOURCE_PACKAGE_HOME/service/impl/*Test.java $TEST_PACKAGE_HOME/service/impl
mv $RESOURCE_PACKAGE_HOME/service/impl/*.java $JAVA_PACKAGE_HOME/service/impl

echo "codegen files were moved!"