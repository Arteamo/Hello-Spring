@echo off
cd /d "C:\Windows\system32"
chcp 437

setlocal
set service=MySQL80
set do=-1
for /f "tokens=1,4" %%i in ('sc query %service%') do (
	if "%%i"=="STATE" (
		if "%%j"=="RUNNING" (
		set do=0
		) else (
		set do=1
		)
	)
)

if %do% EQU 0 net stop mysql80
if %do% EQU 1 net start mysql80
if %do% EQU -1 echo %service% status is unknown.