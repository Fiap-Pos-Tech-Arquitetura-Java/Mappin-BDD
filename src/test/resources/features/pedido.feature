#language: pt

Funcionalidade: Pedido
  @smoke @high
  Cenario: Registrar Pedido
    Dado que um cliente ja foi registrado para registrar um pedido
    Dado que um primeiro produto ja foi registrado para registrar um pedido
    Dado que um segundo produto ja foi registrado para registrar um pedido
    Quando registrar um novo pedido
    Entao o pedido e registrado com sucesso
    E pedido deve ser apresentado
  @smoke @low
  Cenario: Buscar Pedido
    Dado que um cliente ja foi registrado para registrar um pedido
    Dado que um primeiro produto ja foi registrado para registrar um pedido
    Dado que um segundo produto ja foi registrado para registrar um pedido
    Dado que um pedido ja foi registrado
    Quando efetuar a busca do pedido
    Entao o pedido e exibido com sucesso
  @smoke
  Cenario: Alterar Pedido
    Dado que um cliente ja foi registrado para registrar um pedido
    Dado que um primeiro produto ja foi registrado para registrar um pedido
    Dado que um segundo produto ja foi registrado para registrar um pedido
    Dado que um pedido ja foi registrado
    Quando efetuar uma requisicao para alterar pedido
    Entao o pedido e atualizado com sucesso
    E pedido deve ser apresentado
  @high
  Cenario: Remover Pedido
    Dado que um cliente ja foi registrado para registrar um pedido
    Dado que um primeiro produto ja foi registrado para registrar um pedido
    Dado que um segundo produto ja foi registrado para registrar um pedido
    Dado que um pedido ja foi registrado
    Quando requisitar a remocao do pedido
    Entao o pedido e removido com sucesso