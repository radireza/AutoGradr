@echo off
cd %~dp0\env\
for /r %%a in (.) do (javac %%a\*.java)
