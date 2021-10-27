package br.com.restassuredapitesting.tests.booking.tests;

import br.com.restassuredapitesting.base.BaseTest;
import br.com.restassuredapitesting.suites.AcceptanceTests;
import br.com.restassuredapitesting.suites.AllTests;
import br.com.restassuredapitesting.suites.E2eTests;
import br.com.restassuredapitesting.tests.auth.requests.PostAuthRequest;
import br.com.restassuredapitesting.tests.booking.payloads.BookingPayLoads;
import br.com.restassuredapitesting.tests.booking.requests.DeleteBookingRequest;
import br.com.restassuredapitesting.tests.booking.requests.PostBookingRequest;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Feature("Feature - Retorno de Exclusão de Reserva")
public class DeleteBookingTest extends BaseTest {

    // Instanciando as classes que irei precisar
    DeleteBookingRequest deleteBookingRequest = new DeleteBookingRequest();
    PostAuthRequest postAuthRequest = new PostAuthRequest();
    PostBookingRequest postBookingRequest = new PostBookingRequest();
    BookingPayLoads bookingPayLoads = new BookingPayLoads();

    // Criando uma nova reserva e extraindo seu ID
    int id = postBookingRequest.createBooking(bookingPayLoads.payloadValidBooking())
            .then()
            .statusCode(200)
            .extract()
            .path("bookingid");

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Category({AllTests.class, AcceptanceTests.class})
    @DisplayName("Deletar uma reserva utilizando o token")
    public void validarExclusaoDeUmaReservaUtilizandoToken() {
        deleteBookingRequest.deleteBookingId(id, postAuthRequest.getToken())
                .then()
                .statusCode(201); // Código obtido da documentação. Ideal seria código 200 --> ver README.md
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Category({AllTests.class, E2eTests.class})
    @DisplayName("Tentar excluir uma reserva que não existe")
    public void validarTentativaDeExclusaoDeReservaInexistente() {
        //Pegando como ID o máximo valor inteiro da linguagem, garantindo assim que este valor não esteja na
        // lista de IDs de reservas
        int id = Integer.MAX_VALUE;

        deleteBookingRequest.deleteBookingId(id, postAuthRequest.getToken())
                .then()
                .statusCode(405); // Not Allowed, já que o ID informado não existe
    }

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Category({AllTests.class, E2eTests.class})
    @DisplayName("Tentar excluir uma reserva sem autorização")
    public void validarTentativaDeExclusaoDeReservaSemAutorizacao() {
        String tokenInvalido = "";

        deleteBookingRequest.deleteBookingId(id, tokenInvalido)
                .then()
                .statusCode(403); // Forbidden, já que o usuário não tem autorização para deletar esta reserva
    }


}
