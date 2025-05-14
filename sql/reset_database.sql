-- Script para remover as tabelas existentes e permitir nova criação
-- Este script deve ser executado antes do setup_database.sql

-- Remover todas as tabelas em ordem reversa (devido às chaves estrangeiras)
DROP TABLE IF EXISTS "EDITA" CASCADE;
DROP TABLE IF EXISTS "TESTES" CASCADE;
DROP TABLE IF EXISTS "RESTAURANTES-COZINHEIRO" CASCADE;
DROP TABLE IF EXISTS "INGREDIENTES-RECEITAS" CASCADE;
DROP TABLE IF EXISTS "RECEITAS" CASCADE;
DROP TABLE IF EXISTS "CATEGORIA-RECEITA" CASCADE;
DROP TABLE IF EXISTS "INGREDIENTES" CASCADE;
DROP TABLE IF EXISTS "RESTAURANTES" CASCADE;
DROP TABLE IF EXISTS "LIVROS" CASCADE;
DROP TABLE IF EXISTS "COZINHEIROS" CASCADE;
DROP TABLE IF EXISTS "DEGUSTADORES" CASCADE;
DROP TABLE IF EXISTS "EDITORES" CASCADE;

-- Limpar quaisquer índices ou sequências que possam causar conflitos
DROP INDEX IF EXISTS "INGREDIENTES_Nome-ingred_key";
DROP INDEX IF EXISTS "LIVROS_Titulo-livro_key";
DROP INDEX IF EXISTS "RECEITAS_Nome-rec_key";

-- Confirmar que as tabelas foram removidas
SELECT 'Tabelas removidas com sucesso. Execute o script setup_database.sql para recriar o banco de dados.' as message; 