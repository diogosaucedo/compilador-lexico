@echo off
echo === Compilando o Compilador Lexico ===
javac -d out ./src/*.java

echo Copiando arquivos de configuracao...
copy src\*.txt out\ >nul 2>&1


if %errorlevel% neq 0 (
    echo Erro na compilacao!
    pause
    exit /b 1
)

echo Compilacao bem-sucedida!
echo.
echo === Executando o Compilador ===
cd out
java CompiladorMain
cd ..

echo.
echo === Execucao concluida ===

echo === Gerando Jar ===
cd out
echo Main-Class: CompiladorMain > manifest.txt
echo. >> manifest.txt
jar cvfm ../compilador-lexico.jar manifest.txt *.class
del manifest.txt
cd ..

