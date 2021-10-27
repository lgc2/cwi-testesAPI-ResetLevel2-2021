package br.com.restassuredapitesting.tests.booking.tests;

import br.com.restassuredapitesting.base.BaseTest;
import br.com.restassuredapitesting.suites.AcceptanceTests;
import br.com.restassuredapitesting.suites.AllTests;
import br.com.restassuredapitesting.suites.E2eTests;
import br.com.restassuredapitesting.suites.SecurityTests;
import br.com.restassuredapitesting.tests.auth.requests.PostAuthRequest;
import br.com.restassuredapitesting.tests.booking.requests.GetBookingRequest;
import br.com.restassuredapitesting.tests.booking.requests.PutBookingRequest;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.DisplayName;
import org.json.JSONException;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static org.hamcrest.Matchers.greaterThan;

@Feature("Feature - Atualização de Reservas")
public class PutBookingTest extends BaseTest {
    PutBookingRequest putBookingRequest = new PutBookingRequest();
    GetBookingRequest getBookingRequest = new GetBookingRequest();
    PostAuthRequest postAuthRequest = new PostAuthRequest();

    // Nesta Classe optei por não criar novas reservas nos testes, pois já foram criadas muitas na Classe
    // GetBookingTest, sendo assim utilizei ID da primeira reserva da lista nos cenários de testes.

    int primeiroId = getBookingRequest.bookingReturnIds()
            .then()
            .statusCode(200)
            .extract()
            .path("[0].bookingid");

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Category({AllTests.class, AcceptanceTests.class})
    @DisplayName("Alterar uma reserva somente utilizando o token")
    public void validarAlteracaoDeUmaReservaUtilizandoToken() throws JSONException {
        putBookingRequest.updateBookingToken(primeiroId, postAuthRequest.getToken())
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }

    // Teste --> Alterar uma reserva utilizando o Basci auth

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Category({AllTests.class, E2eTests.class, SecurityTests.class})
    @DisplayName("Tentar alterar uma reserva quando o token não for enviado")
    public void validarNaoAlteracaoDeUmaReservaSemEnviarToken() {
        putBookingRequest.updateBookingWithoutToken(primeiroId)
                .then()
                .statusCode(403);
    }

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Category({AllTests.class, E2eTests.class, SecurityTests.class})
    @DisplayName("Tentar alterar uma reserva quando o token enviado for inválido")
    public void validarNaoAlteracaoDeUmaReservaUtilizandoTokenInvalido() {
        String token = "token=tokenInvalido123";

        putBookingRequest.updateBookingToken(primeiroId, token)
                .then()
                .statusCode(403);
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Category({AllTests.class, E2eTests.class})
    @DisplayName("Tentar alterar uma reserva que não existe")
    public void validarNaoAlteracaoDeUmaReservaInexistente() {
        int id = Integer.MAX_VALUE;

        putBookingRequest.updateBookingToken(id, postAuthRequest.getToken())
                .then()
                .statusCode(405);
    }
}
