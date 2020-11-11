insert into cozinha(id, nome) values (1, 'Tailandesa');
insert into cozinha(id, nome) values (2, 'Indiana');

insert into restaurante(nome, taxa_frete, cozinha_id) values ('Black Som', 1.5, 1);
insert into restaurante(nome, taxa_frete, cozinha_id) values ('Kakus Beer', 2.5, 2);

insert into forma_pagamento(id, descricao) values (1, 'Dinheiro');
insert into forma_pagamento(id, descricao) values (2, 'Cartão de crédito');

insert into permissao(id, nome, descricao) values (1, 'CONSULTAR_COZINHAS', 'Permite consultar cozinhas');
insert into permissao(id, nome, descricao) values (2, 'EDITAR_COZINHAS', 'Permite editar cozinhas');

insert into estado (id, nome) values (1, 'Minas Gerais');
insert into estado (id, nome) values (2, 'São Paulo');
insert into estado (id, nome) values (3, 'Ceará');

insert into cidade (id, nome, estado_id) values (1, 'Uberlândia', 1);
insert into cidade (id, nome, estado_id) values (2, 'Belo Horizonte', 1);
insert into cidade (id, nome, estado_id) values (3, 'São Paulo', 2);
insert into cidade (id, nome, estado_id) values (4, 'Campinas', 2);
insert into cidade (id, nome, estado_id) values (5, 'Fortaleza', 3);