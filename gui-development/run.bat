@echo off
echo ========================================
echo Running GUI Resource Management Game
echo ========================================
echo.

set PATH_TO_FX=C:\javafx-sdk-25\lib

java --module-path %PATH_TO_FX% --add-modules javafx.controls -cp bin GraphicalGame

echo.
pause