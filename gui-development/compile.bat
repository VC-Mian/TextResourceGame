@echo off
echo ========================================
echo Compiling GUI Resource Management Game
echo ========================================

set PATH_TO_FX=C:\javafx-sdk-25\lib

:: Create bin directory if it doesn't exist
if not exist "bin" mkdir bin

:: Compile all Java files
javac --module-path %PATH_TO_FX% --add-modules javafx.controls -d bin model\*.java view\*.java GraphicalGame.java

if %errorlevel% == 0 (
    echo.
    echo ========================================
    echo Compilation SUCCESSFUL!
    echo ========================================
    echo.
) else (
    echo.
    echo ========================================
    echo Compilation FAILED!
    echo ========================================
    pause
    exit /b 1
)