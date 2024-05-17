package br.com.fiap.postech.mappin.bdd;

import br.com.fiap.postech.mappin.helper.ClienteHelper;
import br.com.fiap.postech.mappin.model.Cliente;
import br.com.fiap.postech.mappin.model.Endereco;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class ClienteStepDefinition extends BaseStepDefinition {

    private Response response;
    private Cliente clienteResposta;

    @Quando("registrar um novo cliente")
    public Cliente registrar_um_novo_cliente() {
        var clienteRequisicao = ClienteHelper.getCliente(false);
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(clienteRequisicao)
                .when()
                .post(ENDPOINT_API_CLIENTE);
        return response.then().extract().as(Cliente.class);
    }
    @Entao("o cliente e registrado com sucesso")
    public void o_cliente_e_registrado_com_sucesso() {
        response.then()
                .statusCode(HttpStatus.CREATED.value());
    }
    @Entao("cliente deve ser apresentado")
    public void deve_ser_apresentado() {
        response.then()
                .body(matchesJsonSchemaInClasspath("schemas/cliente.schema.json"));
    }

    @Dado("que um cliente ja foi registrado")
    public void que_um_cliente_ja_foi_publicado() {
        clienteResposta = registrar_um_novo_cliente();
    }

    @Quando("efetuar a busca do cliente")
    public void efetuar_a_busca_do_cliente() {
        when()
                .get(ENDPOINT_API_CLIENTE + "/{id}", clienteResposta.id());
    }
    @Entao("o cliente e exibido com sucesso")
    public void o_cliente_e_exibido_com_sucesso() {
        response.then()
                .body(matchesJsonSchemaInClasspath("schemas/cliente.schema.json"));
    }

    @Quando("efetuar uma requisicao para alterar cliente")
    public void efetuar_uma_requisicao_para_alterar_cliente() {
        var novoCliente = new Cliente(
                null,
                clienteResposta.nome() + "XXX",
                null,
                new Endereco(null, "rua do projeto","1","95880970","Estrela/RS/Brasil")
        );
        response =
                given()
                        //.header(HttpHeaders.AUTHORIZATION, ClienteHelper.getToken(clienteResposta.email()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body(novoCliente)
                .when()
                        .put(ENDPOINT_API_CLIENTE + "/{id}", clienteResposta.id());
    }
    @Entao("o cliente e atualizado com sucesso")
    public void o_cliente_e_atualizado_com_sucesso() {
        response.then()
                .statusCode(HttpStatus.ACCEPTED.value());
    }

    @Quando("requisitar a remocao do cliente")
    public void requisitar_a_remocao_do_cliente() {
        response =
                given()
                        //.header(HttpHeaders.AUTHORIZATION, ClienteHelper.getToken(clienteResposta.email()))
                .when()
                        .delete(ENDPOINT_API_CLIENTE + "/{id}", clienteResposta.id());
    }
    @Entao("o cliente e removido com sucesso")
    public void o_cliente_e_removido_com_sucesso() {
        response.then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
