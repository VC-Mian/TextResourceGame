@echo off
call compile.bat
if %errorlevel% == 0 (
    call run.bat
)