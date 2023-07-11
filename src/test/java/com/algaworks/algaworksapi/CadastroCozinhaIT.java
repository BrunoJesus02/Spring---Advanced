package com.algaworks.algaworksapi;

import com.algaworks.algaworksapi.domain.model.Cozinha;
import com.algaworks.algaworksapi.domain.repository.CozinhaRepository;
import com.algaworks.algaworksapi.util.DatabaseCleaner;
import com.algaworks.algaworksapi.util.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.enableLoggingOfRequestAndResponseIfValidationFails;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource( {"/application-test.properties"} )
public class CadastroCozinhaIT {

    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    private static final int COZINHA_ID_INEXISTENTE = 100;

    private Cozinha cozinhaAmericana;
    private int quantidadeCozinhasCadastradas;
    private String jsonCorretoCozinhaChinesa;

    @Before
    public void setUp() {
        enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/cozinhas";

        jsonCorretoCozinhaChinesa = ResourceUtils.getContentFromResource(
                "/json/correto/cozinha-chinesa.json");

        databaseCleaner.clearTables();
        prepararDados();
    }

    @Test
    public void deveRetornarStatus200QuandoConsultarCozinhas() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void deveConterQuantidadeDeCozinhasCadastradasQuandoConsultarCozinhas() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .body("", hasSize(quantidadeCozinhasCadastradas));
    }

    @Test
    public void deveRetornarStatus201QuandoCadastrarCozinha() {
        given()
            .body(jsonCorretoCozinhaChinesa)
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void deveRetornarRespostaEStatusCorretoQuandoConsultarCozinhaExistente() {
        given()
            .pathParam("cozinhaId", cozinhaAmericana.getId())
            .accept(ContentType.JSON)
        .when()
            .get("/{cozinhaId}")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("nome", equalTo(cozinhaAmericana.getNome()));
    }

    @Test
    public void deveRetornar404QuandoConsultarCozinhaInexistente() {
        given()
            .pathParam("cozinhaId", COZINHA_ID_INEXISTENTE)
            .accept(ContentType.JSON)
        .when()
            .get("/{cozinhaId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    private void prepararDados() {
        Cozinha cozinhaTailandesa = new Cozinha();
        cozinhaTailandesa.setNome("Tailandesa");
        cozinhaRepository.save(cozinhaTailandesa);

        cozinhaAmericana = new Cozinha();
        cozinhaAmericana.setNome("Americana");
        cozinhaRepository.save(cozinhaAmericana);

        quantidadeCozinhasCadastradas = (int) cozinhaRepository.count();
    }
}
