set PROJECT_HOME=C:\Workspaces\demo

echo 'copying ' %PROJECT_HOME%/gaelyk-project/bin/.classpath.win ' to ' %PROJECT_HOME%/gaelyk-project/.classpath ' ...'
copy %PROJECT_HOME%\gaelyk-project\bin\.classpath.win %PROJECT_HOME%\gaelyk-project\.classpath

pause