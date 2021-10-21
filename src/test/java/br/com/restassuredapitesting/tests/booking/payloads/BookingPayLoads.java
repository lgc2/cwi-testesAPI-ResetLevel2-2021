package br.com.restassuredapitesting.tests.booking.payloads;

import org.json.JSONException;
import org.json.JSONObject;

public class BookingPayLoads {

    public static JSONObject payloadValidBooking() throws JSONException{
        JSONObject payload = new JSONObject();
        JSONObject bookingDates = new JSONObject();

        bookingDates.put("checkin", "2018-01-01");
        bookingDates.put("checkout", "2019-01-01");

        payload.put("firstname", "Cristiano");
        payload.put("lastname", "Ronaldo");
        payload.put("totalprice", 111);
        payload.put("depositpaid", true);
        payload.put("bookingdates", bookingDates);
        payload.put("additionalneeds", "Breakfast");

        return payload;
    }

}
