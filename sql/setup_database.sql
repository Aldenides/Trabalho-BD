-- Script de criação das tabelas no PostgreSQL
-- Executar este script para configurar o banco de dados

-- Tabela COZINHEIROS
CREATE TABLE IF NOT EXISTS "COZINHEIROS" (
  "cpf-coz" INT NOT NULL,
  "Nome-coz" VARCHAR(80) NOT NULL,
  "Nome-fantasia" VARCHAR(80) NOT NULL,
  "Dt-contrato-coz" DATE NOT NULL,
  "salario-coz" DECIMAL(8, 2),
  CONSTRAINT "COZINHEIROS_pkey" PRIMARY KEY ("cpf-coz")
);

-- Tabela DEGUSTADORES
CREATE TABLE IF NOT EXISTS "DEGUSTADORES" (
  "Cpf-deg" INT NOT NULL,
  "Nome-deg" VARCHAR(80) NOT NULL,
  "Dt-contrato-deg" DATE NOT NULL,
  "Salario-deg" DECIMAL(8, 2),
  CONSTRAINT "DEGUSTADORES_pkey" PRIMARY KEY ("Cpf-deg")
);

-- Tabela EDITORES
CREATE TABLE IF NOT EXISTS "EDITORES" (
  "Cpf-edit" INT NOT NULL,
  "Nome-edit" VARCHAR(80) NOT NULL,
  "Dt-contrato-edit" DATE NOT NULL,
  "Salario-edit" DECIMAL(8, 2),
  CONSTRAINT "EDITORES_pkey" PRIMARY KEY ("Cpf-edit")
);

-- Tabela CATEGORIA-RECEITA
CREATE TABLE IF NOT EXISTS "CATEGORIA-RECEITA" (
  "Cod-cat-rec" INT NOT NULL,
  "Desc-cat-rec" VARCHAR(40) NOT NULL,
  CONSTRAINT "CATEGORIA-RECEITA_pkey" PRIMARY KEY ("Cod-cat-rec")
);

-- Tabela INGREDIENTES
CREATE TABLE IF NOT EXISTS "INGREDIENTES" (
  "Cod-ingred" INT NOT NULL,
  "Nome-ingred" VARCHAR(40) NOT NULL,
  CONSTRAINT "INGREDIENTES_pkey" PRIMARY KEY ("Cod-ingred")
);

-- Tabela LIVROS
CREATE TABLE IF NOT EXISTS "LIVROS" (
  "ISBN" INT NOT NULL,
  "Titulo-livro" VARCHAR(200) NOT NULL,
  CONSTRAINT "LIVROS_pkey" PRIMARY KEY ("ISBN")
);

-- Tabela RESTAURANTES
CREATE TABLE IF NOT EXISTS "RESTAURANTES" (
  "Cod-rest" INT NOT NULL,
  "Nome-rest" VARCHAR(80) NOT NULL,
  CONSTRAINT "RESTAURANTES_pkey" PRIMARY KEY ("Cod-rest")
);

-- Tabela RECEITAS
CREATE TABLE IF NOT EXISTS "RECEITAS" (
  "Cod-rec" INT NOT NULL,
  "Nome-rec" VARCHAR(80) NOT NULL,
  "Dt-criacao-rec" DATE NOT NULL,
  "C0d-categoria-rec" INT NOT NULL,
  "Cpf-coz" INT NOT NULL,
  "ISBN-rec" INT,
  CONSTRAINT "RECEITAS_pkey" PRIMARY KEY ("Cod-rec"),
  CONSTRAINT "RECEITAS_C0d-categoria-rec_fkey" FOREIGN KEY ("C0d-categoria-rec") 
    REFERENCES "CATEGORIA-RECEITA"("Cod-cat-rec"),
  CONSTRAINT "RECEITAS_Cpf-coz_fkey" FOREIGN KEY ("Cpf-coz") 
    REFERENCES "COZINHEIROS"("cpf-coz"),
  CONSTRAINT "RECEITAS_ISBN-rec_fkey" FOREIGN KEY ("ISBN-rec") 
    REFERENCES "LIVROS"("ISBN")
);

-- Tabela INGREDIENTES-RECEITAS
CREATE TABLE IF NOT EXISTS "INGREDIENTES-RECEITAS" (
  "Cod-rec-ingrec" INT NOT NULL,
  "cod-ing-ingrec" INT NOT NULL,
  "Quant-ingrec" DECIMAL(4, 2) NOT NULL,
  "Med-ingrec" VARCHAR(10) NOT NULL,
  CONSTRAINT "INGREDIENTES-RECEITAS_pkey" PRIMARY KEY ("Cod-rec-ingrec", "cod-ing-ingrec"),
  CONSTRAINT "INGREDIENTES-RECEITAS_Cod-rec-ingrec_fkey" FOREIGN KEY ("Cod-rec-ingrec") 
    REFERENCES "RECEITAS"("Cod-rec"),
  CONSTRAINT "INGREDIENTES-RECEITAS_cod-ing-ingrec_fkey" FOREIGN KEY ("cod-ing-ingrec") 
    REFERENCES "INGREDIENTES"("Cod-ingred")
);

-- Tabela RESTAURANTES-COZINHEIRO
CREATE TABLE IF NOT EXISTS "RESTAURANTES-COZINHEIRO" (
  "Cod-coz-restcoz" INT NOT NULL,
  "Cod-rest-restcoz" INT NOT NULL,
  "Dt-contratacao" DATE NOT NULL,
  CONSTRAINT "RESTAURANTES-COZINHEIRO_pkey" PRIMARY KEY ("Cod-coz-restcoz", "Cod-rest-restcoz"),
  CONSTRAINT "RESTAURANTES-COZINHEIRO_Cod-coz-restcoz_fkey" FOREIGN KEY ("Cod-coz-restcoz") 
    REFERENCES "COZINHEIROS"("cpf-coz"),
  CONSTRAINT "RESTAURANTES-COZINHEIRO_Cod-rest-restcoz_fkey" FOREIGN KEY ("Cod-rest-restcoz") 
    REFERENCES "RESTAURANTES"("Cod-rest")
);

-- Tabela TESTES
CREATE TABLE IF NOT EXISTS "TESTES" (
  "Cpf-deg-test" INT NOT NULL,
  "Cod-rec-test" INT NOT NULL,
  "Dt-teste" DATE NOT NULL,
  "Nota-teste" NUMERIC NOT NULL,
  CONSTRAINT "TESTES_pkey" PRIMARY KEY ("Cpf-deg-test", "Cod-rec-test"),
  CONSTRAINT "TESTES_Cpf-deg-test_fkey" FOREIGN KEY ("Cpf-deg-test") 
    REFERENCES "DEGUSTADORES"("Cpf-deg"),
  CONSTRAINT "TESTES_Cod-rec-test_fkey" FOREIGN KEY ("Cod-rec-test") 
    REFERENCES "RECEITAS"("Cod-rec")
);

-- Tabela EDITA
CREATE TABLE IF NOT EXISTS "EDITA" (
  "id" SERIAL NOT NULL,
  "livroId" INT NOT NULL,
  "editorId" INT NOT NULL,
  CONSTRAINT "EDITA_pkey" PRIMARY KEY ("id"),
  CONSTRAINT "EDITA_livroId_fkey" FOREIGN KEY ("livroId") 
    REFERENCES "LIVROS"("ISBN"),
  CONSTRAINT "EDITA_editorId_fkey" FOREIGN KEY ("editorId") 
    REFERENCES "EDITORES"("Cpf-edit"),
  CONSTRAINT "EDITA_livro_editor_unique" UNIQUE ("livroId", "editorId")
);

-- Índices únicos para campos que devem ser únicos
CREATE UNIQUE INDEX IF NOT EXISTS "INGREDIENTES_Nome-ingred_key" ON "INGREDIENTES"("Nome-ingred");
CREATE UNIQUE INDEX IF NOT EXISTS "LIVROS_Titulo-livro_key" ON "LIVROS"("Titulo-livro");
CREATE UNIQUE INDEX IF NOT EXISTS "RECEITAS_Nome-rec_key" ON "RECEITAS"("Nome-rec");

-- Inserir alguns dados de exemplo

-- Inserir categorias
INSERT INTO "CATEGORIA-RECEITA" ("Cod-cat-rec", "Desc-cat-rec") VALUES 
    (1, 'Massas'),
    (2, 'Carnes'),
    (3, 'Sobremesas'),
    (4, 'Vegetariano')
ON CONFLICT DO NOTHING;

-- Inserir cozinheiros
INSERT INTO "COZINHEIROS" ("cpf-coz", "Nome-coz", "Nome-fantasia", "Dt-contrato-coz", "salario-coz") VALUES 
    (123456789, 'Carlos Silva', 'Chef Carlos', '2020-01-15', 5500.00),
    (234567890, 'Maria Oliveira', 'Chefe Maria', '2021-03-10', 6500.00),
    (345678901, 'José Santos', 'Mestre José', '2019-11-05', 4800.00)
ON CONFLICT DO NOTHING;

-- Inserir ingredientes
INSERT INTO "INGREDIENTES" ("Cod-ingred", "Nome-ingred") VALUES 
    (1, 'Farinha de Trigo'),
    (2, 'Ovos'),
    (3, 'Leite'),
    (4, 'Açúcar'),
    (5, 'Sal'),
    (6, 'Carne Moída'),
    (7, 'Tomate'),
    (8, 'Cebola')
ON CONFLICT DO NOTHING;

-- Inserir livros
INSERT INTO "LIVROS" ("ISBN", "Titulo-livro") VALUES
    (123456, 'Receitas Brasileiras'),
    (234567, 'Culinária Internacional'),
    (345678, 'Doces e Sobremesas')
ON CONFLICT DO NOTHING;

-- Inserir restaurantes
INSERT INTO "RESTAURANTES" ("Cod-rest", "Nome-rest") VALUES
    (1, 'Sabor Gourmet'),
    (2, 'Cantina Italiana'),
    (3, 'Casa de Carnes')
ON CONFLICT DO NOTHING;

-- Inserir degustadores
INSERT INTO "DEGUSTADORES" ("Cpf-deg", "Nome-deg", "Dt-contrato-deg", "Salario-deg") VALUES
    (456789012, 'Ana Pereira', '2020-05-10', 3800.00),
    (567890123, 'Paulo Souza', '2019-08-15', 3600.00)
ON CONFLICT DO NOTHING;

-- Inserir editores
INSERT INTO "EDITORES" ("Cpf-edit", "Nome-edit", "Dt-contrato-edit", "Salario-edit") VALUES
    (678901234, 'Lucia Ferreira', '2018-07-20', 4200.00),
    (789012345, 'Roberto Alves', '2019-11-12', 4500.00)
ON CONFLICT DO NOTHING; 