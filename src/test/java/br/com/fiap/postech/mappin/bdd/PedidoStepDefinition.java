package br.com.fiap.postech.mappin.bdd;

import br.com.fiap.postech.mappin.helper.ClienteHelper;
import br.com.fiap.postech.mappin.helper.PedidoHelper;
import br.com.fiap.postech.mappin.helper.ProdutoHelper;
import br.com.fiap.postech.mappin.model.Cliente;
import br.com.fiap.postech.mappin.model.Pedido;
import br.com.fiap.postech.mappin.model.Produto;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class PedidoStepDefinition extends BaseStepDefinition {

    private Response response;
    private Pedido pedidoResposta;

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

    @Quando("que um cliente ja foi registrado para registrar um pedido")
    public void que_um_cliente_ja_foi_registrado_para_fazer_um_pedido() {
        clienteResposta = registrar_um_novo_cliente();
    }

    @Dado("que um primeiro produto ja foi registrado para registrar um pedido")
    public void que_um_primeiro_produto_ja_foi_registrado_para_registrar_um_pedido() {
        primeiroProdutoResposta = registrar_um_novo_produto();
    }

    @Dado("que um segundo produto ja foi registrado para registrar um pedido")
    public void que_um_segundo_produto_ja_foi_registrado_para_registrar_um_pedido() {
        segundoProdutoResposta = registrar_um_novo_produto();
    }

    @Quando("registrar um novo pedido")
    public Pedido registrar_um_novo_pedido() {
        var pedidoRequisicao = PedidoHelper.getPedido(false, clienteResposta.id(), primeiroProdutoResposta.id(), segundoProdutoResposta.id());
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(pedidoRequisicao)
                .when()
                .post(ENDPOINT_API_PEDIDO);
        return response.then().extract().as(Pedido.class);
    }
    @Entao("o pedido e registrado com sucesso")
    public void o_pedido_e_registrado_com_sucesso() {
        response.then()
                .statusCode(HttpStatus.CREATED.value());
    }
    @Entao("pedido deve ser apresentado")
    public void deve_ser_apresentado() {
        response.then()
                .body(matchesJsonSchemaInClasspath("schemas/pedido.schema.json"));
    }

    @Dado("que um pedido ja foi registrado")
    public void que_um_pedido_ja_foi_publicado() {
        pedidoResposta = registrar_um_novo_pedido();
    }

    @Quando("efetuar a busca do pedido")
    public void efetuar_a_busca_do_pedido() {
        when()
                .get(ENDPOINT_API_PEDIDO + "/{id}", pedidoResposta.id());
    }
    @Entao("o pedido e exibido com sucesso")
    public void o_pedido_e_exibido_com_sucesso() {
        response.then()
                .body(matchesJsonSchemaInClasspath("schemas/pedido.schema.json"));
    }

    @Quando("efetuar uma requisicao para alterar pedido")
    public void efetuar_uma_requisicao_para_alterar_pedido() {
        var novoPedido = new Pedido(
                null,
                null,
                null,
                "PAGAMENTO_REALIZADO",
                null
        );
        response =
                given()
                        //.header(HttpHeaders.AUTHORIZATION, PedidoHelper.getToken(pedidoResposta.email()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body(novoPedido)
                .when()
                        .put(ENDPOINT_API_PEDIDO + "/{id}", pedidoResposta.id());
    }
    @Entao("o pedido e atualizado com sucesso")
    public void o_pedido_e_atualizado_com_sucesso() {
        response.then()
                .statusCode(HttpStatus.ACCEPTED.value());
    }

    @Quando("requisitar a remocao do pedido")
    public void requisitar_a_remocao_do_pedido() {
        response =
                given()
                        //.header(HttpHeaders.AUTHORIZATION, PedidoHelper.getToken(pedidoResposta.email()))
                .when()
                        .delete(ENDPOINT_API_PEDIDO + "/{id}", pedidoResposta.id());
    }
    @Entao("o pedido e removido com sucesso")
    public void o_pedido_e_removido_com_sucesso() {
        response.then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
