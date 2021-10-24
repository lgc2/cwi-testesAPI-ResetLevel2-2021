package br.com.restassuredapitesting.tests.booking.requests;

import br.com.restassuredapitesting.tests.booking.payloads.BookingPayLoads;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class PostBookingRequest {

    BookingPayLoads bookingPayLoads = new BookingPayLoads();

    @Step("Cria uma nova reserva")
    public Response createBooking() {
        return given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .when()
                .body(bookingPayLoads.payloadValidBooking().toString())
                .log().all()
                .post("booking"); // Método Post
    }
}
