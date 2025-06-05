CREATE DATABASE ReceitasDB;
GO

-- Seleciona o banco de dados ReceitasDB
USE ReceitasDB;
GO

-- Criação das tabelas
CREATE TABLE Usuarios (
    Id INT PRIMARY KEY IDENTITY(1,1),
    Nome NVARCHAR(100),
    Email NVARCHAR(100) UNIQUE,
    SenhaHash NVARCHAR(255),
    TipoUsuario NVARCHAR(50)
);

CREATE TABLE Categorias (
    Id INT PRIMARY KEY IDENTITY(1,1),
    Nome NVARCHAR(100) UNIQUE
);

CREATE TABLE Receitas (
    Id INT PRIMARY KEY IDENTITY(1,1),
    Nome NVARCHAR(100),
    Descricao NVARCHAR(255),
    TempoPreparo INT, -- tempo em minutos
    Porcoes INT,
    CategoriaId INT FOREIGN KEY REFERENCES Categorias(Id),
    Dificuldade NVARCHAR(50),
    UsuarioId INT FOREIGN KEY REFERENCES Usuarios(Id),
    MediaAvaliacao DECIMAL(5, 2) NULL,
    DataCriacao DATE DEFAULT GETDATE()
);

CREATE TABLE Ingredientes (
    Id INT PRIMARY KEY IDENTITY(1,1),
    Nome NVARCHAR(100) UNIQUE
);

CREATE TABLE Medidas (
    Id INT PRIMARY KEY IDENTITY(1,1),
    Unidade NVARCHAR(50)
);


CREATE TABLE Ingredientes_Receitas (
    ReceitaId INT FOREIGN KEY REFERENCES Receitas(Id),
    IngredienteId INT FOREIGN KEY REFERENCES Ingredientes(Id),
    Quantidade DECIMAL(10, 2),
    MedidaId INT FOREIGN KEY REFERENCES Medidas(Id),
    PRIMARY KEY (ReceitaId, IngredienteId)
);

CREATE TABLE Instrucoes (
    ReceitaId INT FOREIGN KEY REFERENCES Receitas(Id),
    Etapa INT,
    Descricao NVARCHAR(255),
    PRIMARY KEY (ReceitaId, Etapa)
);

CREATE TABLE Avaliacoes (
    Id INT PRIMARY KEY IDENTITY(1,1),
    ReceitaId INT FOREIGN KEY REFERENCES Receitas(Id),
    UsuarioId INT FOREIGN KEY REFERENCES Usuarios(Id),
    Pontuacao INT CHECK (Pontuacao BETWEEN 1 AND 5),
    Comentario NVARCHAR(255),
    Data DATE
);
GO

-- Triggers para atualizar a média de avaliações

-- Trigger para atualizar a média de avaliações quando uma nova avaliação é inserida
CREATE TRIGGER trg_Avaliacao_Insert
ON Avaliacoes
AFTER INSERT
AS
BEGIN
    UPDATE Receitas
    SET MediaAvaliacao = (
        SELECT AVG(CAST(Pontuacao AS DECIMAL(5, 2)))
        FROM Avaliacoes
        WHERE Avaliacoes.ReceitaId = Receitas.Id
    )
    WHERE Id IN (SELECT ReceitaId FROM inserted);
END;
GO

-- Trigger para atualizar a média de avaliações quando uma avaliação existente é atualizada
CREATE TRIGGER trg_Avaliacao_Update
ON Avaliacoes
AFTER UPDATE
AS
BEGIN
    UPDATE Receitas
    SET MediaAvaliacao = (
        SELECT AVG(CAST(Pontuacao AS DECIMAL(5, 2)))
        FROM Avaliacoes
        WHERE Avaliacoes.ReceitaId = Receitas.Id
    )
    WHERE Id IN (SELECT ReceitaId FROM inserted);
END;
GO

-- Trigger para atualizar a média de avaliações quando uma avaliação é excluída
CREATE TRIGGER trg_Avaliacao_Delete
ON Avaliacoes
AFTER DELETE
AS
BEGIN
    UPDATE Receitas
    SET MediaAvaliacao = (
        SELECT AVG(CAST(Pontuacao AS DECIMAL(5, 2)))
        FROM Avaliacoes
        WHERE Avaliacoes.ReceitaId = Receitas.Id
    )
    WHERE Id IN (SELECT ReceitaId FROM deleted);
END;
GO

-- Inserindo Usuários
INSERT INTO Usuarios (Nome, Email, SenhaHash, TipoUsuario)
VALUES 
('Maria Silva', 'maria.silva@email.com', 'senha_hash_maria', 'Usuario'),
('João Pereira', 'joao.pereira@email.com', 'senha_hash_joao', 'Administrador'),
('Ana Costa', 'ana.costa@email.com', 'senha_hash_ana', 'Usuario'),
('Carlos Souza', 'carlos.souza@email.com', 'senha_hash_carlos', 'Usuario'),
('Fernanda Lima', 'fernanda.lima@email.com', 'senha_hash_fernanda', 'Usuario'),
('Beatriz Oliveira', 'beatriz.oliveira@email.com', 'senha_hash_beatriz', 'Administrador');
GO

-- Inserindo Categorias
INSERT INTO Categorias (Nome)
VALUES 
('Sobremesa'),
('Prato Principal'),
('Entrada'),
('Bebidas'),
('Sopas'),
('Aperitivos');
GO

-- Inserindo Receitas
INSERT INTO Receitas (Nome, Descricao, TempoPreparo, Porcoes, CategoriaId, Dificuldade, UsuarioId)
VALUES 
('Bolo de Chocolate', 'Um bolo delicioso e fácil de fazer.', 60, 8, 1, 'Fácil', 1),
('Lasanha de Carne', 'Lasanha tradicional com carne moída.', 90, 6, 2, 'Médio', 2),
('Salada de Frutas', 'Uma salada refrescante com diversas frutas.', 15, 4, 3, 'Fácil', 3),
('Suco de Laranja', 'Um suco fresco e saudável.', 5, 2, 4, 'Fácil', 4),
('Sopa de Legumes', 'Uma sopa nutritiva e saborosa.', 45, 4, 5, 'Médio', 5),
('Bruschetta', 'Aperitivo italiano com tomate e manjericão.', 20, 6, 6, 'Fácil', 6);
GO

-- Inserindo Ingredientes
INSERT INTO Ingredientes (Nome)
VALUES 
('Farinha de Trigo'),
('Ovo'),
('Chocolate em Pó'),
('Carne Moída'),
('Massa de Lasanha'),
('Frutas Diversas'),
('Laranja'),
('Cenoura'),
('Tomate'),
('Manjericão'),
('Pão Italiano'),
('Alho'),
('Queijo Parmesão'),
('Caldo de Legumes');
GO

-- Inserindo Medidas
INSERT INTO Medidas (Unidade)
VALUES 
('Gramas'),
('Unidade'),
('Colher de Sopa'),
('Xícara'),
('Litro');
GO

-- Associando Ingredientes às Receitas
INSERT INTO Ingredientes_Receitas (ReceitaId, IngredienteId, Quantidade, MedidaId)
VALUES 
(1, 1, 200, 1),  -- 200 gramas de farinha de trigo para o Bolo de Chocolate
(1, 2, 3, 2),    -- 3 ovos para o Bolo de Chocolate
(1, 3, 100, 1),  -- 100 gramas de chocolate em pó para o Bolo de Chocolate
(2, 4, 500, 1),  -- 500 gramas de carne moída para a Lasanha de Carne
(2, 5, 200, 1),  -- 200 gramas de massa de lasanha para a Lasanha de Carne
(3, 6, 300, 1),  -- 300 gramas de frutas diversas para a Salada de Frutas
(4, 7, 4, 2),    -- 4 laranjas para Suco de Laranja
(5, 8, 2, 1),    -- 2 cenouras para Sopa de Legumes
(5, 9, 3, 1),    -- 3 tomates para Sopa de Legumes
(5, 14, 1, 5),   -- 1 litro de caldo de legumes para Sopa de Legumes
(6, 9, 2, 1),    -- 2 tomates para Bruschetta
(6, 10, 5, 2),   -- 5 folhas de manjericão para Bruschetta
(6, 11, 4, 2);   -- 4 fatias de pão italiano para Bruschetta


-- Inserindo Instruções para as Receitas
INSERT INTO Instrucoes (ReceitaId, Etapa, Descricao)
VALUES 
(1, 1, 'Misture os ingredientes secos.'),
(1, 2, 'Adicione os ovos e mexa bem.'),
(1, 3, 'Coloque a massa em uma forma untada e leve ao forno por 40 minutos a 180 graus.'),
(2, 1, 'Refogue a carne moída até dourar.'),
(2, 2, 'Monte a lasanha em camadas alternando entre massa, carne e molho.'),
(2, 3, 'Leve ao forno por 30 minutos a 200 graus.'),
(3, 1, 'Corte as frutas em pedaços pequenos.'),
(3, 2, 'Misture todas as frutas em uma tigela.'),
(4, 1, 'Esprema as laranjas e sirva o suco fresco.'),
(5, 1, 'Refogue os legumes em uma panela.'),
(5, 2, 'Adicione o caldo de legumes e cozinhe até que os legumes estejam macios.'),
(6, 1, 'Toste o pão italiano.'),
(6, 2, 'Cubra o pão com tomates picados e manjericão.');


-- Inserindo Avaliações
INSERT INTO Avaliacoes (ReceitaId, UsuarioId, Pontuacao, Comentario, Data)
VALUES 
(1, 1, 5, 'Delicioso! Ficou incrível.', '2024-09-01'),
(1, 2, 4, 'Muito bom, mas poderia ter um pouco mais de chocolate.', '2024-09-02'),
(2, 3, 3, 'A lasanha estava boa, mas um pouco seca.', '2024-09-03'),
(2, 4, 4, 'Gostei muito, saborosa e bem feita.', '2024-09-04'),
(3, 5, 5, 'Muito simples e refrescante. Ideal para o verão.', '2024-09-05'),
(4, 6, 4, 'Ótimo suco, muito refrescante.', '2024-09-06'),
(5, 1, 4, 'Sopa muito nutritiva, mas poderia ter mais tempero.', '2024-09-07'),
(6, 2, 5, 'Ótima bruschetta, muito saborosa.', '2024-09-08'),
(1, 3, 5, 'Maravilhoso! Perfeito para qualquer ocasião.', '2024-09-09'),
(1, 4, 3, 'Achei o bolo muito seco, precisa de mais cobertura.', '2024-09-10'),
(2, 1, 4, 'Boa lasanha, mas poderia ser um pouco mais temperada.', '2024-09-11'),
(2, 5, 4, 'Gostei bastante, boa combinação de ingredientes.', '2024-09-12'),
(3, 2, 4, 'A salada estava boa, mas poderia ter mais variedade de frutas.', '2024-09-13'),
(4, 3, 5, 'O melhor suco de laranja que já provei!', '2024-09-14'),
(5, 2, 3, 'A sopa estava ok, mas esperava mais sabor.', '2024-09-15'),
(6, 4, 5, 'A bruschetta estava excelente, recomendo!', '2024-09-16');









