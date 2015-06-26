echo OFF
cd %~dp0
cd ..\..
set PROJECT_HOME=%CD%

echo You backup Windows project file will be overwritten, are you sure (ctrl + c/close to exit) ?
pause
del %PROJECT_HOME%\gaelyk-project\bin\.classpath.win
echo saving '%PROJECT_HOME%\gaelyk-project\.classpath' to '%PROJECT_HOME%\gaelyk-project\bin\.classpath.win' ...
copy %PROJECT_HOME%\gaelyk-project\.classpath %PROJECT_HOME%\gaelyk-project\bin\.classpath.win

pause