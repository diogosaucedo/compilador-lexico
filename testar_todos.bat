@echo off
echo === COMPILADOR LEXICO - TESTES ===
echo.

REM Compila o projeto
echo Compilando...
javac *.java

if %errorlevel% neq 0 (
    echo Erro na compilacao!
    pause
    exit /b 1
)

echo Compilacao bem-sucedida!
echo.

REM Testa o arquivo com erros
echo ========================================
echo TESTE 1: Programa com erros
echo ========================================
java CompiladorMain Programa_Fonte.txt
echo.
pause

REM Testa o programa simples
echo ========================================
echo TESTE 2: Programa simples correto
echo ========================================
java CompiladorMain Programa_Simples.txt
echo.
pause

REM Testa o programa complexo
echo ========================================
echo TESTE 3: Programa complexo correto
echo ========================================
java CompiladorMain Programa_Correto.txt
echo.

echo === TESTES CONCLUIDOS ===
pause
