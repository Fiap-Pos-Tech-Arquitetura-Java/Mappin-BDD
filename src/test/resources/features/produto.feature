#language: pt

Funcionalidade: Produto
  @smoke @high
  Cenario: Registrar Produto
    Quando registrar um novo produto
    Entao o produto e registrado com sucesso
    E produto deve ser apresentado
  @smoke @low
  Cenario: Buscar Produto
    Dado que um produto ja foi registrado
    Quando efetuar a busca do produto
    Entao o produto e exibido com sucesso
  @smoke
  Cenario: Alterar Produto
    Dado que um produto ja foi registrado
    Quando efetuar uma requisicao para alterar produto
    Entao o produto e atualizado com sucesso
    E produto deve ser apresentado
  @high
  Cenario: Remover Produto
    Dado que um produto ja foi registrado
    Quando requisitar a remocao do produto
    Entao o produto e removido com sucesso