:call setEnv.cmd

set JAVA_HOME=C:\jdk1.6.0_29
set GAE_JAVA_SDK_HOME=C:\appengine-java-sdk-1.5.5
set PATH=%PATH%;%JAVA_HOME%\bin;%GAE_JAVA_SDK_HOME%\bin

cd %~dp0
cd ..\..
set PROJECT_HOME=%CD%

:start appcfg --enable_jar_splitting rollback "%PROJECT_HOME%\gaely-project\war"
appcfg --enable_jar_splitting rollback c:\gaely-project\war

:exit
:pause