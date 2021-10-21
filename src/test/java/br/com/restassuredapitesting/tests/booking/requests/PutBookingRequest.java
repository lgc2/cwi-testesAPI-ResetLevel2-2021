package br.com.restassuredapitesting.tests.booking.requests;

import br.com.restassuredapitesting.tests.booking.payloads.BookingPayLoads;
import io.restassured.response.Response;
import org.json.JSONException;

import static io.restassured.RestAssured.given;

public class PutBookingRequest {

    BookingPayLoads bookingPayLoads = new BookingPayLoads();

    public Response updateBookingToken(int id, String token) throws JSONException {
        return given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Cookie", token)
                .when()
                .body(bookingPayLoads.payloadValidBooking().toString())
                .put("booking/" + id); //método PUT

    }
}
