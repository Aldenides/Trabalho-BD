#!/bin/bash

# Script para compilar e executar a aplicação
echo "Compilando a aplicação..."

# Criar diretório para os arquivos compilados
mkdir -p bin

# Compilar os arquivos Java
javac -d bin -cp ".:lib/*" $(find src -name "*.java")

if [ $? -eq 0 ]; then
    echo "Compilação bem-sucedida!"
    
    # Testar conexão e configurar banco de dados primeiro
    echo "Testando conexão e configurando banco de dados..."
    java -cp "bin:lib/*" DataBase.SetupDatabase
    
    if [ $? -eq 0 ]; then
        echo "Iniciando a aplicação..."
        java -cp "bin:lib/*" app.Main
    else
        echo "Falha ao configurar o banco de dados. Verifique sua conexão PostgreSQL."
    fi
else
    echo "Falha na compilação. Verifique os erros acima."
fi 