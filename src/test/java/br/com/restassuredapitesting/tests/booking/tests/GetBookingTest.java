package br.com.restassuredapitesting.tests.booking.tests;

import br.com.restassuredapitesting.base.BaseTest;
import br.com.restassuredapitesting.suites.AcceptanceTests;
import br.com.restassuredapitesting.suites.AllTests;
import br.com.restassuredapitesting.suites.ContractTests;
import br.com.restassuredapitesting.suites.E2eTests;
import br.com.restassuredapitesting.tests.booking.requests.GetBookingRequest;
import br.com.restassuredapitesting.utils.Utils;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.File;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.hamcrest.Matchers.greaterThan;

@Feature("Feature - Retorno de Reservas")
public class GetBookingTest extends BaseTest{

    GetBookingRequest getBookingRequest = new GetBookingRequest();

    int segundoId = getBookingRequest.bookingReturnIds()
            .then()
            .statusCode(200)
            .extract()
            .path("[1].bookingid"); // Extraindo ID do segundo elemento (segunda reserva)

    String firstname = getBookingRequest.specificBookingReturnId(segundoId)
            .then()
            .statusCode(200)
            .extract()
            .path("firstname"); // Extraindo firstname da segunda reserva

    String lastname = getBookingRequest.specificBookingReturnId(segundoId)
            .then()
            .statusCode(200)
            .extract()
            .path("lastname"); // Extraindo lastname da segunda reserva

    String checkinDate = getBookingRequest.specificBookingReturnId(segundoId)
            .then()
            .statusCode(200)
            .extract()
            .path("bookingdates.checkin"); // Extraindo data de checkin da segunda reserva

    String checkoutDate = getBookingRequest.specificBookingReturnId(segundoId)
            .then()
            .statusCode(200)
            .extract()
            .path("bookingdates.checkout"); // Extraindo data de checkout da segunda reserva

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Category({AllTests.class, AcceptanceTests.class})
    @DisplayName("Listar ID's de reservas")
    public void validaListagemDeIdsDasReservas() {
        getBookingRequest.bookingReturnIds()
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0)); //listagem tem que ser maior do que 0
    }

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Category({AllTests.class, ContractTests.class})
    @DisplayName("Garantir o Schema de retorno da listagem de reservas")
    public void validaSchemaDaListagemDeReservas() {
        getBookingRequest.bookingReturnIds()
                .then()
                .statusCode(200)
                .body(matchesJsonSchema(new File(Utils.getSchemaBasePath("booking", "bookings"))));
    }

    // Foi deletado do arquivo .json, em "required properties", o atributo "additionalneeds",
    // pois através do postman pude constatar que as reservas de lá também estavam sem este atributo.
    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Category({AllTests.class, ContractTests.class})
    @DisplayName("Garantir o Schema de retorno de um ID de reserva específico")
    public void validaSchemaDeUmaReservaEspecifica() {
        getBookingRequest.specificBookingReturnId(segundoId)
                .then()
                .statusCode(200)
                .body(matchesJsonSchema(new File(Utils.getSchemaBasePath
                        ("booking", "specificBooking"))));
    }

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Category({AllTests.class, AcceptanceTests.class})
    @DisplayName("Listar uma reserva específica através do ID")
    public void listaReservaEspecifica() {
        getBookingRequest.specificBookingReturnId(segundoId)
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Category({AllTests.class, AcceptanceTests.class})
    @DisplayName("Listar reservas através do filtro 'firstname'")
    public void validaListagemDeReservasPeloFiltroFirstname() {
        getBookingRequest.bookingReturnByFirstname(firstname)
                .then()
//                .log().all()
                .statusCode(200)
                .body("size()", greaterThan(0));
        // Neste caso a listagem tem que ser maior do que 0 porque, para pegar um "firstname" válido,
        //optei por buscar um ID válido primeiramente, e extrair dele um nome existente.
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Category({AllTests.class, AcceptanceTests.class})
    @DisplayName("Listar reservas através do filtro 'lastname'")
    public void validaListagemDeReservasPeloFiltroLastname() {
        getBookingRequest.bookingReturnByLastname(lastname)
                .then()
//                .log().all()
                .statusCode(200)
                .body("size()", greaterThan(0));
        // Neste caso a listagem tem que ser maior do que 0 porque, para pegar um "lastname" válido,
        //optei por buscar um ID válido primeiramente, e extrair dele um sobrenome existente.
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Category({AllTests.class, AcceptanceTests.class})
    @DisplayName("Listar reservas através do filtro 'checkin'")
    public void validaListagemDeReservasPeloFiltroCheckin() {
        String check = "checkin="; // Variável para concatenar 'checkin=' no path
        String date = getBookingRequest.specificBookingReturnId(segundoId)
                .then()
                .statusCode(200)
                .extract()
                .path("bookingdates.checkin"); // Extraindo data de checkin da segunda reserva

        getBookingRequest.bookingReturnByDate(check, date)
                .then()
                .log().all()
                .statusCode(200)
                .body("size()", greaterThan(0)); // Tem que ser > 0 pois peguei de uma reserva existente

        // Consta na documentação que, o filtro de datas deveria retornar reservas de mesma data para
        // frente, o que não está ocorrendo. A API com filtro checkin está retornando apenas reservas
        // com datas posteriores a do filtro. No arquivo pdf isto é citado.
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Category({AllTests.class, AcceptanceTests.class})
    @DisplayName("Listar reservas através do filtro 'checkout'")
    public void validaListagemDeReservasPeloFiltroCheckout() {
        String check = "checkout="; // Variável para concatenar 'checkout=' no path
        String date = getBookingRequest.specificBookingReturnId(segundoId)
                .then()
                .statusCode(200)
                .extract()
                .path("bookingdates.checkout"); // Extraindo data de checkout da segunda reserva

        getBookingRequest.bookingReturnByDate(check, date)
                .then()
                .log().all()
                .statusCode(200)
                .body("size()", greaterThan(0)); // Tem que ser > 0 pois peguei de uma reserva existente

        // Consta na documentação que, o filtro de datas deveria retornar reservas de mesma data para
        // frente, o que não está ocorrendo. A API com filtro checkout está retornando reservas com datas anteriores
        // ou iguais a do filtro. No arquivo pdf isto é citado.
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Category({AllTests.class, AcceptanceTests.class})
    @DisplayName("Listar reservas através dos filtros 'chekin' e 'checkout'")
    public void validaListagemDeReservasPelosFiltrosCheckinECheckout() {
        getBookingRequest.bookingReturnByCheckinAndCheckout(checkinDate, checkoutDate)
                .then()
                .statusCode(200)
                .log().all()
                .body("size()", greaterThan(0)); // Deveria ser > 0 pois peguei de uma reserva existente

        // Existe um bug aqui pois está retornando um Array vazio, o que não deveria ocorrer já que peguei dados
        // válidos de checkin e checkout de uma reserva existente (segundo ID de reservas).
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Category({AllTests.class, AcceptanceTests.class})
    @DisplayName("Listar reservas através dos filtros 'firstname', 'chekin' e 'checkout'")
    public void validaListagemDeReservasPelosFiltrosFirstnameECheckinECheckout() {
        getBookingRequest.bookingReturnByFirstNameAndCheckinAndCheckout(firstname, checkinDate, checkoutDate)
                .then()
                .statusCode(200)
                .log().all()
                .body("size()", greaterThan(0)); // Deveria ser > 0 pois peguei de uma reserva existente

        // Existe um bug aqui pois está retornando um Array vazio, o que não deveria ocorrer já que peguei dados
        // válidos de firstname, checkin e checkout de uma reserva existente (segundo ID de reservas).
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Category({AllTests.class, E2eTests.class})
    @DisplayName("Visualizar erro de servidor 500 quando enviar filtro mal formatado")
    public void validaErro500QuandoEnviarFiltroMalFormatado() {
        String check = "checkiiinnn="; // Variável escrita de maneira ERRADA para concatenar 'checkiiinnn=' no path
        String date = getBookingRequest.specificBookingReturnId(segundoId)
                .then()
                .statusCode(200)
                .extract()
                .path("bookingdates.checkin"); // Extraindo data de checkin da segunda reserva

        getBookingRequest.bookingReturnByDate(check, date)
                .then()
                .log().all()
                .statusCode(500);

        // Erroneamente a API está retornando a lista completa de todos os IDs de reservas e, status code 200,
        // quando um filtro inválido é digitado. Está se comportando como se tivesse fornecido a URL:
        // https://treinamento-api.herokuapp.com/booking.
    }


}
