CREATE TABLE produtos
(
    id               SERIAL PRIMARY KEY,
    nome             VARCHAR(150)   NOT NULL,
    descricao        TEXT           NOT NULL,
    preco            decimal(10, 2) NOT NULL,
    quantidade       INTEGER        NOT NULL,
    data_cadastro    TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP NULL,
    data_exclusao    TIMESTAMP NULL,
    ativo            INT            NOT NULL DEFAULT 1
);