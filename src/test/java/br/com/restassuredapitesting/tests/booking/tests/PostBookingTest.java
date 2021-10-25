package br.com.restassuredapitesting.tests.booking.tests;

import br.com.restassuredapitesting.base.BaseTest;
import br.com.restassuredapitesting.suites.AcceptanceTests;
import br.com.restassuredapitesting.suites.AllTests;
import br.com.restassuredapitesting.suites.E2eTests;
import br.com.restassuredapitesting.tests.booking.payloads.BookingPayLoads;
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
    BookingPayLoads bookingPayLoads = new BookingPayLoads();

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Category({AllTests.class, AcceptanceTests.class})
    @DisplayName("Criar uma nova reserva")
    public void validarCriacaoDeUmaReserva() {
        postBookingRequest.createBooking(bookingPayLoads.payloadValidBooking())
                .then()
                .statusCode(200)
                .log().all()
                .body("size()", greaterThan(0));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Category({AllTests.class, E2eTests.class})
    @DisplayName("Validar retorno 500 quando o payload da reserva estiver inválido")
    public void validarErro500QuandoPayloadInvalidoNaCriacaoDeUmaReserva() {
        // Atributo "firstname" manipulado no payload para que ele esteja inválido
        postBookingRequest.createBooking(bookingPayLoads.bookingInvalidPayload())
                .then()
                .log().all()
                .statusCode(500);
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Category({AllTests.class, E2eTests.class})
    @DisplayName("Validar a criação de mais de uma reserva em sequência")
    public void validarCriacaoDeMaisDeUmaReservaEmSequencia() {
        // Postou 1ª vez
        postBookingRequest.createBooking(bookingPayLoads.payloadValidBooking())
                .then()
                .statusCode(200)
                .log().all()
                .body("size()", greaterThan(0));

        // Postou 2ª vez
        postBookingRequest.createBooking(bookingPayLoads.payloadValidBooking())
                .then()
                .statusCode(200)
                .log().all()
                .body("size()", greaterThan(0));

        // Postou 3ª vez
        postBookingRequest.createBooking(bookingPayLoads.payloadValidBooking())
                .then()
                .statusCode(200)
                .log().all()
                .body("size()", greaterThan(0));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Category({AllTests.class, E2eTests.class})
    @DisplayName("Criar uma nova reserva enviando parâmetros adicionais no payload")
    public void validarCriacaoDeUmaReservaComParametrosAdicionaisNoPayload() {
        postBookingRequest.createBooking(bookingPayLoads.addMoreParametersInThePayloadBooking())
                .then()
                .statusCode(200)
                .log().all()
                .body("size()", greaterThan(0));

        // Parâmetro "nickname" foi adicionado ao corpo de um payload válido.
        // O que ocorre é que a reserva é criada normalmente simplesmente ignorando os parâmetros extras.
    }

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Category({AllTests.class, E2eTests.class})
    @DisplayName("Validar retorno 418 quando o header Accept for inválido na criação de uma reserva")
    public void validarCodigo418NaCriacaoDeUmaReservaComAccpetInvalidoNoHeader() {
        postBookingRequest.createBookingInvalidAcceptHeader(bookingPayLoads.payloadValidBooking())
                .then()
                .log().all()
                .statusCode(418);
    }

}
