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

    @Step("Retorna uma Reserva Específica através de seu ID")
    public Response specificBookingReturnId(int id) {
        return given()
                .header("Accept", "application/json")
                .when()
                .get("booking/"+id);
    }

    @Step("Retorna uma Listagem de Reservas utilizando o filto 'firstname'")
    public Response bookingReturnByFirstname(String firstname) {
        return given()
                .when()
//                .log().all()
                .get("booking?firstname="+firstname);
    }

    @Step("Retorna uma Listagem de Reservas utilizando o filto 'lastname'")
    public Response bookingReturnByLastname(String lastname) {
        return given()
                .when()
//                .log().all()
                .get("booking?lastname="+lastname);
    }

    @Step("Retorna uma Listagem de Reservas utilizando o filto 'checkin' ou 'checkout'")
    public Response bookingReturnByDate(String check, String date) {
        return given()
                .when()
                .log().all()
                .get("booking?"+check+date);
    }

    @Step("Retorna uma Listagem de Reservas utilizando os filtos 'checkin' e 'checkout'")
    public Response bookingReturnByCheckinAndCheckout(String checkinDate, String checkoutDate) {
        return given()
                .when()
                .log().all()
                .get("booking?checkin="+checkinDate+"&checkout="+checkoutDate);
    }

    @Step("Retorna uma Listagem de Reservas utilizando os filtos 'firstname', 'checkin' e 'checkout'")
    public Response bookingReturnByFirstNameAndCheckinAndCheckout(String firstname, String checkinDate, String checkoutDate) {
        return given()
                .when()
                .log().all()
                .get("booking?firstname="+firstname+"&checkin="+checkinDate+"&checkout="+checkoutDate);
    }

}
