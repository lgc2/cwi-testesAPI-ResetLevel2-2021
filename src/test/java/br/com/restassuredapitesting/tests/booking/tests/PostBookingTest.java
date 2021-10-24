package br.com.restassuredapitesting.tests.booking.tests;

import br.com.restassuredapitesting.base.BaseTest;
import br.com.restassuredapitesting.suites.AcceptanceTests;
import br.com.restassuredapitesting.suites.AllTests;
import br.com.restassuredapitesting.tests.booking.requests.PostBookingRequest;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static org.hamcrest.Matchers.greaterThan;

@Feature("Feature - Criação de Reserva")
public class PostBookingTest extends BaseTest {
    PostBookingRequest postBookingRequest = new PostBookingRequest();

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Category({AllTests.class, AcceptanceTests.class})
    @DisplayName("Criar uma nova reserva")
    public void validarCriacaoDeUmaReserva() {
        postBookingRequest.createBooking()
                .then()
                .statusCode(200)
                .log().all()
                .body("size()", greaterThan(0));
    }

}
