package br.com.restassuredapitesting.tests.booking.requests;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class GetBookingRequest {

    @Step("Retorna os IDs da Listagem de Reservas")
    public Response bookingReturnIds() {
        return given()
                .when()
                .get("booking");
    }

    @Step("Retorna um ID específico de Reservas")
    public Response specificBookingReturnId(int id) {
        return given()
                .header("Accept", "application/json")
                .when()
                .get("booking/"+id);
    }
}
