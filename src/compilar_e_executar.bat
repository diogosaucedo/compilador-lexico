@echo off
echo === Compilando o Compilador Lexico ===
javac *.java

if %errorlevel% neq 0 (
    echo Erro na compilacao!
    pause
    exit /b 1
)

echo Compilacao bem-sucedida!
echo.
echo === Executando o Compilador ===
java CompiladorMain

echo.
echo === Execucao concluida ===
pause
