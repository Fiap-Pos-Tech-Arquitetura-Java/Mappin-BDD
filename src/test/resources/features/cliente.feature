#language: pt

  Funcionalidade: Cliente
    @smoke @high
    Cenario: Registrar Cliente
      Quando registrar um novo cliente
      Entao o cliente e registrado com sucesso
      E cliente deve ser apresentado
    @smoke @low
    Cenario: Buscar Cliente
      Dado que um cliente ja foi registrado
      Quando efetuar a busca do cliente
      Entao o cliente e exibido com sucesso
    @smoke
    Cenario: Alterar Cliente
      Dado que um cliente ja foi registrado
      Quando efetuar uma requisicao para alterar cliente
      Entao o cliente e atualizado com sucesso
      E cliente deve ser apresentado
    @high
    Cenario: Remover Cliente
      Dado que um cliente ja foi registrado
      Quando requisitar a remocao do cliente
      Entao o cliente e removido com sucesso