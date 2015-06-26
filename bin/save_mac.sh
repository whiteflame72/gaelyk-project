#! /usr/bin/env bash

echo "You backup Mac project file will be overwritten, are you sure (ctrl + c to exit) ?"
read

#Need to create soft link to your GAEJ home e.g.
#ln -s /Users/ag/appengine-java-sdk-1.5.1 /appengine-java-sdk
#ln -s '/Users/ag' /project_home

#Also need to make sure Java is installed properly, generally available at /usr/bin/java and 
#change JAVA_HOME accordingly

export JAVA_HOME='/System/Library/Frameworks/JavaVM.framework/Home'
export GAE_JAVA_SDK_HOME='/appengine-java-sdk'
#export PATH=${PATH}:$JAVA_HOME/bin:$GAE_JAVA_SDK_HOME/bin
PROJECT_HOME='/project_home'
PROJECT_NAME='gaelyk-project'

echo saving $PROJECT_HOME'/'$PROJECT_NAME'/.classpath to '$PROJECT_HOME'/'$PROJECT_NAME'/bin/.classpath.mac' ...
cp -f $PROJECT_HOME'/'$PROJECT_NAME'/.classpath' $PROJECT_HOME'/'$PROJECT_NAME'/bin/.classpath.mac'
