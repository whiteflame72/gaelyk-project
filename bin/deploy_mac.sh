#! /usr/bin/env bash

#Need to create soft link to your GAEJ home e.g.
#ln -s /Users/ag/appengine-java-sdk-1.5.2 /appengine-java-sdk
#ln -s '/Users/ag' /project_home

#Also need to make sure Java is installed properly, generally available at /usr/bin/java and 
#change JAVA_HOME accordingly

export JAVA_HOME='/System/Library/Frameworks/JavaVM.framework/Home'
export GAE_JAVA_SDK_HOME='/appengine-java-sdk'
#export PATH=${PATH}:$JAVA_HOME/bin:$GAE_JAVA_SDK_HOME/bin
PROJECT_HOME='/project_home'
$GAE_JAVA_SDK_HOME/bin/appcfg.sh --enable_jar_splitting update ${PROJECT_HOME}/gaelyk-project/war
