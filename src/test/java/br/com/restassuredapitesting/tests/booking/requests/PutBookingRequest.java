package br.com.restassuredapitesting.tests.booking.requests;

import br.com.restassuredapitesting.tests.booking.payloads.BookingPayLoads;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.json.JSONException;

import static io.restassured.RestAssured.given;

public class PutBookingRequest {

    BookingPayLoads bookingPayLoads = new BookingPayLoads();

    @Step("Atualiza uma reserva específica com o parâmetro token")
    public Response updateBookingToken(int id, String token) throws JSONException {
        return given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Cookie", token)
                .when()
                .body(bookingPayLoads.payloadValidBooking().toString())
                .log().all()
                .put("booking/" + id); //método PUT

    }

    @Step("Tenta atualizar uma reserva sem enviar token")
    public Response updateBookingWithoutToken(int id) {
        return given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
//                .header("Cookie", token) --> O atributo "Cookie" não será enviado para o Header
                .when()
                .body(bookingPayLoads.payloadValidBooking().toString())
                .log().all()
                .put("booking/" + id); //método PUT
    }

}
