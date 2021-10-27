package br.com.restassuredapitesting.tests.booking.requests;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class DeleteBookingRequest {

    @Step("Retorna uma exclusão de ID de Reservas")
    public Response deleteBookingId(int id, String token) {
        return given()
                .header("Content-Type", "application/json")
                .header("Cookie", token)
                .when()
                .delete("booking/"+id); //método DELETE
    }

}
