package br.com.fiap.postech.mappin.bdd;

import br.com.fiap.postech.mappin.helper.ProdutoHelper;
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

public class ProdutoStepDefinition extends BaseStepDefinition {

    private Response response;
    private Produto produtoResposta;

    @Quando("registrar um novo produto")
    public Produto registrar_um_novo_produto() {
        var produtoRequisicao = ProdutoHelper.getProduto(false);
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(produtoRequisicao)
                .when()
                .post(ENDPOINT_API_PRODUTO);
        return response.then().extract().as(Produto.class);
    }
    @Entao("o produto e registrado com sucesso")
    public void o_produto_e_registrado_com_sucesso() {
        response.then()
                .statusCode(HttpStatus.CREATED.value());
    }
    @Entao("produto deve ser apresentado")
    public void deve_ser_apresentado() {
        response.then()
                .body(matchesJsonSchemaInClasspath("schemas/produto.schema.json"));
    }

    @Dado("que um produto ja foi registrado")
    public void que_um_produto_ja_foi_publicado() {
        produtoResposta = registrar_um_novo_produto();
    }

    @Quando("efetuar a busca do produto")
    public void efetuar_a_busca_do_produto() {
        when()
                .get(ENDPOINT_API_PRODUTO + "/{id}", produtoResposta.id());
    }
    @Entao("o produto e exibido com sucesso")
    public void o_produto_e_exibido_com_sucesso() {
        response.then()
                .body(matchesJsonSchemaInClasspath("schemas/produto.schema.json"));
    }

    @Quando("efetuar uma requisicao para alterar produto")
    public void efetuar_uma_requisicao_para_alterar_produto() {
        var novoProduto = new Produto(
                null,
                produtoResposta.nome() + "XXX",
                null,
                null,
                null
        );
        response =
                given()
                        //.header(HttpHeaders.AUTHORIZATION, ProdutoHelper.getToken(produtoResposta.email()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body(novoProduto)
                .when()
                        .put(ENDPOINT_API_PRODUTO + "/{id}", produtoResposta.id());
    }
    @Entao("o produto e atualizado com sucesso")
    public void o_produto_e_atualizado_com_sucesso() {
        response.then()
                .statusCode(HttpStatus.ACCEPTED.value());
    }

    @Quando("requisitar a remocao do produto")
    public void requisitar_a_remocao_do_produto() {
        response =
                given()
                        //.header(HttpHeaders.AUTHORIZATION, ProdutoHelper.getToken(produtoResposta.email()))
                .when()
                        .delete(ENDPOINT_API_PRODUTO + "/{id}", produtoResposta.id());
    }
    @Entao("o produto e removido com sucesso")
    public void o_produto_e_removido_com_sucesso() {
        response.then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
