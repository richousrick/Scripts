::takes input and outputs it to cmd and prints the output to bat stdout
::useful to access cmd where it has been blocked but command prompt script processing has not
@ECHO OFF
set exit="exit"
:beginning
set /P command="%cd%>"
%command%
if "%command%" == "exit" goto end
goto beginning
:end
echo ended
pause
