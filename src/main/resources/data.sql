INSERT INTO VENDEDORES(NOME, CPF, EMAIL, DATA_CADASTRO) VALUES('Agenor Ronega', '07631031144', 'agenor.ronega@empresa.com.br', '2022-05-09 13:42:00');
INSERT INTO VENDEDORES(NOME, CPF, EMAIL, DATA_CADASTRO) VALUES('Emilio Oilime', '07832052249', 'emilio.oilime@empresa.com.br', '2022-05-09 14:11:00');

INSERT INTO PRODUTOS(NOME, VALOR_PRODUTO, DATA_CADASTRO) VALUES('Caderno', '20.0', '2022-05-09 15:00:00');
INSERT INTO PRODUTOS(NOME, VALOR_PRODUTO, DATA_CADASTRO) VALUES('Caneta Azul', '2.0', '2022-05-09 15:26:00');
INSERT INTO PRODUTOS(NOME, VALOR_PRODUTO, DATA_CADASTRO) VALUES('Caneta Vermelha', '2.5', '2022-05-09 15:49:00');

INSERT INTO VENDAS(DATA_CADASTRO, VALOR_VENDA, VENDEDOR_ID) VALUES('2022-05-10 10:00:00', '22.0', 1);
INSERT INTO VENDAS(DATA_CADASTRO, VALOR_VENDA, VENDEDOR_ID) VALUES('2022-05-10 18:00:00', '25.0', 1);
INSERT INTO VENDAS(DATA_CADASTRO, VALOR_VENDA, VENDEDOR_ID) VALUES('2022-05-12 19:40:00', '20.0', 2);
INSERT INTO VENDAS(DATA_CADASTRO, VALOR_VENDA, VENDEDOR_ID) VALUES('2022-05-12 19:40:00', '5.0', 1);
INSERT INTO VENDAS(DATA_CADASTRO, VALOR_VENDA, VENDEDOR_ID) VALUES('2022-05-13 20:12:00', '4.0', 1);

INSERT INTO VENDA_PRODUTO(VENDA_ID, PRODUTO_ID) VALUES(1, 1), (1, 2);
INSERT INTO VENDA_PRODUTO(VENDA_ID, PRODUTO_ID) VALUES(2, 1), (2, 3), (2, 3);
INSERT INTO VENDA_PRODUTO(VENDA_ID, PRODUTO_ID) VALUES(3, 1);
INSERT INTO VENDA_PRODUTO(VENDA_ID, PRODUTO_ID) VALUES(4, 3), (4, 3);
INSERT INTO VENDA_PRODUTO(VENDA_ID, PRODUTO_ID) VALUES(5, 2), (5, 2);