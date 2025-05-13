@echo off
REM Navega até a pasta do projeto
cd /d "%~dp0"

REM Cria a pasta bin se não existir
if not exist bin (
    mkdir bin
)

echo.
echo === Compilando arquivos .java ===

REM Compilando todos os arquivos .java do src
javac -d bin -cp "lib\postgresql-42.7.5.jar" ^
src\app\*.java ^
src\dao\*.java ^
src\DataBase\*.java ^
src\model\*.java ^
src\Screen\*.java

if %errorlevel% neq 0 (
    echo.
    echo [ERRO] Houve um problema na compilação.
    pause
    exit /b 1
)

echo.
echo === Executando aplicação ===
REM Executa a aplicação
java -cp "bin;lib\postgresql-42.7.5.jar" app.Main

echo.
pause
