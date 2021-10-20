package br.com.restassuredapitesting;

import br.com.restassuredapitesting.suites.AllTests;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;

public class ClasseDeTeste {

    @Test
    @Category({AllTests.class})
    public void validaApiOnline() { //também chamado de HealthCheck
        Response responsePing =
            given()
            .header("Content-Type", "application/json")
            .when()
            .get("https://treinamento-api.herokuapp.com/ping");

        responsePing
                .then()
                .statusCode(201); //código obtido da documentação
//                .time(lessThan(2L), TimeUnit.SECONDS);
    }

    @Test
    @Category({AllTests.class})
    public void validaListagemDeIdsDasReservas() {
        Response responseListagemIdsReservas =
                given()
                .when()
                .get("https://treinamento-api.herokuapp.com/booking");

        responseListagemIdsReservas
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0)); //listagem tem que ser maior do que 0
    }
}
