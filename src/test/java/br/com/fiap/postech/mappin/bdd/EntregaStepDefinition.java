package br.com.fiap.postech.mappin.bdd;

import br.com.fiap.postech.mappin.helper.ClienteHelper;
import br.com.fiap.postech.mappin.helper.EntregaHelper;
import br.com.fiap.postech.mappin.helper.PedidoHelper;
import br.com.fiap.postech.mappin.helper.ProdutoHelper;
import br.com.fiap.postech.mappin.model.*;
import br.com.fiap.postech.mappin.model.Entrega;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class EntregaStepDefinition extends BaseStepDefinition {

    private Response response;
    private Entrega entregaResposta;

    private Pedido primeiroPedidoResposta;
    private Pedido segundoPedidoResposta;
    private Cliente clienteResposta;
    private Produto primeiroProdutoResposta;
    private Produto segundoProdutoResposta;

    public Cliente registrar_um_novo_cliente() {
        var clienteRequisicao = ClienteHelper.getCliente(false);
        var clienteResponse = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(clienteRequisicao)
                .when()
                .post(ENDPOINT_API_CLIENTE);
        return clienteResponse.then().extract().as(Cliente.class);
    }

    private Produto registrar_um_novo_produto() {
        var produtoRequisicao = ProdutoHelper.getProduto(false);
        var produtoResponse = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(produtoRequisicao)
                .when()
                .post(ENDPOINT_API_PRODUTO);
        return produtoResponse.then().extract().as(Produto.class);
    }

    public Pedido registrar_um_novo_pedido() {
        var pedidoRequisicao = PedidoHelper.getPedido(false, clienteResposta.id(), primeiroProdutoResposta.id(), segundoProdutoResposta.id());
        var pedidoResponse = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(pedidoRequisicao)
                .when()
                .post(ENDPOINT_API_PEDIDO);
        return pedidoResponse.then().extract().as(Pedido.class);
    }

    @Quando("que um cliente ja foi registrado para registrar um pedido de uma entrega")
    public void que_um_cliente_ja_foi_registrado_para_fazer_um_pedido() {
        clienteResposta = registrar_um_novo_cliente();
    }

    @Dado("que um primeiro produto ja foi registrado para registrar um pedido de uma entrega")
    public void que_um_primeiro_produto_ja_foi_registrado_para_registrar_um_pedido() {
        primeiroProdutoResposta = registrar_um_novo_produto();
    }

    @Dado("que um segundo produto ja foi registrado para registrar um pedido de uma entrega")
    public void que_um_segundo_produto_ja_foi_registrado_para_registrar_um_pedido() {
        segundoProdutoResposta = registrar_um_novo_produto();
    }

    @Dado("que um primeiro pedido ja foi registrado para gerar uma entrega")
    public void que_um_primeiro_pedido_ja_foi_registgrado_para_gerar_uma_entrega() {
        primeiroPedidoResposta = registrar_um_novo_pedido();
    }
    @Dado("que um segundo pedido ja foi registrado para gerar uma entrega")
    public void que_um_segundo_pedido_ja_foi_registgrado_para_gerar_uma_entrega() {
        segundoPedidoResposta = registrar_um_novo_pedido();
    }

    @Dado("que os pedidos estejam pagos, com status AGUARDANDO_ENTREGA")
    public void queOsPedidosEstejamPagosComStatusAGUARDANDO_ENTREGA() {
        alteraStatusPedido(primeiroPedidoResposta.id(), "PAGAMENTO_REALIZADO");
        alteraStatusPedido(primeiroPedidoResposta.id(), "AGUARDANDO_ENTREGA");
        alteraStatusPedido(segundoPedidoResposta.id(), "PAGAMENTO_REALIZADO");
        alteraStatusPedido(segundoPedidoResposta.id(), "AGUARDANDO_ENTREGA");
    }

    private void alteraStatusPedido(UUID id, String status) {
        var alteracaoPedido = new Pedido(null,null,null, status, null);
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(alteracaoPedido)
                .when()
                .put(ENDPOINT_API_PEDIDO + "/{id}", id);
    }

    @Quando("registrar um novo entrega")
    public Entrega registrar_um_novo_entrega() throws Exception {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post(ENDPOINT_API_ENTREGA);
        Entrega[] entregas = response.then().extract().as(Entrega[].class);
        if (entregas.length == 0) {
            throw new Exception("há algo errado, não foi gerada nenhuma entrega");
        } else if (entregas.length != 1) {
            throw new Exception("há algo errado, deveria haver somente uma entrega para dois pedidos para o mesmo cep");
        }
        return entregas[0];
    }
    @Entao("o entrega e registrado com sucesso")
    public void o_entrega_e_registrado_com_sucesso() {
        response.then()
                .statusCode(HttpStatus.CREATED.value());
    }
    @Entao("entrega deve ser apresentado")
    public void deve_ser_apresentado() {
        response.then()
                .body(matchesJsonSchemaInClasspath("schemas/entrega.list.schema.json"));
    }

    @Dado("que um entrega ja foi registrado")
    public void que_um_entrega_ja_foi_publicado() throws Exception {
        entregaResposta = registrar_um_novo_entrega();
    }

    @Quando("efetuar a busca do entrega")
    public void efetuar_a_busca_do_entrega() {
        when()
                .get(ENDPOINT_API_ENTREGA + "/{id}", entregaResposta.id());
    }
    @Entao("a lista de entregas e exibido com sucesso")
    public void o_entrega_e_exibido_com_sucesso() {
        response.then()
                .body(matchesJsonSchemaInClasspath("schemas/entrega.list.schema.json"));
    }

    @Quando("efetuar uma requisicao para alterar entrega")
    public void efetuar_uma_requisicao_para_alterar_entrega() {
        var novoEntrega = new Entrega(
                null,
                null,
                null,
                "ENTREGUE",
                null
        );
        response =
                given()
                        //.header(HttpHeaders.AUTHORIZATION, EntregaHelper.getToken(entregaResposta.email()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body(novoEntrega)
                .when()
                        .put(ENDPOINT_API_ENTREGA + "/{id}", entregaResposta.id());
    }
    @Entao("o entrega e atualizado com sucesso")
    public void o_entrega_e_atualizado_com_sucesso() {
        response.then()
                .statusCode(HttpStatus.ACCEPTED.value());
    }

    @Entao("a alteracao da entrega deve ser apresentada")
    public void a_alteracao_da_entrega_deve_ser_apresentada() {
        response.then()
                .body(matchesJsonSchemaInClasspath("schemas/entrega.schema.json"));
    }

    @Quando("requisitar a remocao do entrega")
    public void requisitar_a_remocao_do_entrega() {
        response =
                given()
                        //.header(HttpHeaders.AUTHORIZATION, EntregaHelper.getToken(entregaResposta.email()))
                .when()
                        .delete(ENDPOINT_API_ENTREGA + "/{id}", entregaResposta.id());
    }
    @Entao("a entrega e removida com sucesso")
    public void o_entrega_e_removido_com_sucesso() {
        response.then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
