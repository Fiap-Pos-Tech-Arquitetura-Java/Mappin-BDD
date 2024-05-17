#language: pt

Funcionalidade: Entrega
  @smoke @high
  Cenario: Registrar Entrega
    Dado que um cliente ja foi registrado para registrar um pedido de uma entrega
    Dado que um primeiro produto ja foi registrado para registrar um pedido de uma entrega
    Dado que um segundo produto ja foi registrado para registrar um pedido de uma entrega
    Dado que um primeiro pedido ja foi registrado para gerar uma entrega
    Dado que um segundo pedido ja foi registrado para gerar uma entrega
    Dado que os pedidos estejam pagos, com status AGUARDANDO_ENTREGA
    Quando registrar um novo entrega
    Então o entrega e registrado com sucesso
    E entrega deve ser apresentado
  @smoke @low
  Cenario: Buscar Entrega
    Dado que um cliente ja foi registrado para registrar um pedido de uma entrega
    Dado que um primeiro produto ja foi registrado para registrar um pedido de uma entrega
    Dado que um segundo produto ja foi registrado para registrar um pedido de uma entrega
    Dado que um primeiro pedido ja foi registrado para gerar uma entrega
    Dado que um segundo pedido ja foi registrado para gerar uma entrega
    Dado que os pedidos estejam pagos, com status AGUARDANDO_ENTREGA
    Dado que um entrega ja foi registrado
    Quando efetuar a busca do entrega
    Então a lista de entregas e exibido com sucesso
  @smoke
  Cenario: Alterar Entrega
    Dado que um cliente ja foi registrado para registrar um pedido de uma entrega
    Dado que um primeiro produto ja foi registrado para registrar um pedido de uma entrega
    Dado que um segundo produto ja foi registrado para registrar um pedido de uma entrega
    Dado que um primeiro pedido ja foi registrado para gerar uma entrega
    Dado que um segundo pedido ja foi registrado para gerar uma entrega
    Dado que os pedidos estejam pagos, com status AGUARDANDO_ENTREGA
    Dado que um entrega ja foi registrado
    Quando efetuar uma requisicao para alterar entrega
    Então o entrega e atualizado com sucesso
    E a alteracao da entrega deve ser apresentada
  @high
  Cenario: Remover Entrega
    Dado que um cliente ja foi registrado para registrar um pedido de uma entrega
    Dado que um primeiro produto ja foi registrado para registrar um pedido de uma entrega
    Dado que um segundo produto ja foi registrado para registrar um pedido de uma entrega
    Dado que um primeiro pedido ja foi registrado para gerar uma entrega
    Dado que um segundo pedido ja foi registrado para gerar uma entrega
    Dado que os pedidos estejam pagos, com status AGUARDANDO_ENTREGA
    Dado que um entrega ja foi registrado
    Quando requisitar a remocao do entrega
    Então a entrega e removida com sucesso