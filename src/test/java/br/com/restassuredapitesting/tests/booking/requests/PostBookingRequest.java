package br.com.restassuredapitesting.tests.booking.requests;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;

public class PostBookingRequest {

    @Step("Cria uma nova reserva")
    public Response createBooking(JSONObject payload) {
        return given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .when()
                .body(payload.toString())
                .log().ifValidationFails()
                .post("booking"); // Método Post
    }

    @Step("Valida retorno 418 quando o header Accept é invalido")
    public Response createBookingInvalidAcceptHeader(JSONObject payload) {
        return given()
                .header("Content-Type", "application/json")
                .header("Accept", "application") // Atributo "Accept" inválido, está faltando /json
                .when()
                .body(payload.toString())
                .post("booking"); // Método Post
    }

}
