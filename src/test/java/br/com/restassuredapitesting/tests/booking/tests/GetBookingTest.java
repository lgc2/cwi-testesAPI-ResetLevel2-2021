package br.com.restassuredapitesting.tests.booking.tests;

import br.com.restassuredapitesting.base.BaseTest;
import br.com.restassuredapitesting.suites.AcceptanceTests;
import br.com.restassuredapitesting.suites.AllTests;
import br.com.restassuredapitesting.suites.ContractTests;
import br.com.restassuredapitesting.suites.E2eTests;
import br.com.restassuredapitesting.tests.booking.payloads.BookingPayLoads;
import br.com.restassuredapitesting.tests.booking.requests.GetBookingRequest;
import br.com.restassuredapitesting.tests.booking.requests.PostBookingRequest;
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
    PostBookingRequest postBookingRequest = new PostBookingRequest();
    BookingPayLoads bookingPayLoads = new BookingPayLoads();

    // Criando uma nova reserva e extraindo seu ID
    int id = postBookingRequest.createBooking(bookingPayLoads.payloadValidBooking())
            .then()
            .statusCode(200)
            .extract()
            .path("bookingid");

    // Criando uma nova reserva e extraindo atributo "firstname"
    String firstname = postBookingRequest.createBooking(bookingPayLoads.payloadValidBooking())
            .then()
            .statusCode(200)
            .extract()
            .path("booking.firstname");

    // Criando uma nova reserva e extraindo atributo "lastname"
    String lastname = postBookingRequest.createBooking(bookingPayLoads.payloadValidBooking())
            .then()
            .statusCode(200)
            .extract()
            .path("booking.lastname");

    // Criando uma nova reserva e extraindo atributo "checkin"
    String chekin = postBookingRequest.createBooking(bookingPayLoads.payloadValidBooking())
            .then()
            .statusCode(200)
            .extract()
            .path("booking.bookingdates.checkin"); // Extraindo data de checkin da reserva criada

    // Criando uma nova reserva e extraindo atributo "checkout"
    String checkout = postBookingRequest.createBooking(bookingPayLoads.payloadValidBooking())
            .then()
            .statusCode(200)
            .extract()
            .path("booking.bookingdates.checkout"); // Extraindo data de checkout da reserva criada

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
    // pois atrav??s do postman pude constatar que as reservas de l?? tamb??m estavam sem este atributo.
    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Category({AllTests.class, ContractTests.class})
    @DisplayName("Garantir o Schema de retorno de um ID de reserva espec??fico")
    public void validaSchemaDeUmaReservaEspecifica() {
        getBookingRequest.specificBookingReturnId(id)
                .then()
                .statusCode(200)
                .body(matchesJsonSchema(new File(Utils.getSchemaBasePath
                        ("booking", "specificBooking"))));
    }

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Category({AllTests.class, AcceptanceTests.class})
    @DisplayName("Listar uma reserva espec??fica atrav??s do ID")
    public void listaReservaEspecifica() {
        getBookingRequest.specificBookingReturnId(id)
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Category({AllTests.class, AcceptanceTests.class})
    @DisplayName("Listar reservas atrav??s do filtro 'firstname'")
    public void validaListagemDeReservasPeloFiltroFirstname() {
        getBookingRequest.bookingReturnByFirstname(firstname)
                .then()
                .statusCode(200)
                .log().ifValidationFails()
                .body("size()", greaterThan(0));
        // Neste caso a listagem tem que ser maior do que 0 porque, para pegar um "firstname" v??lido,
        //optei por criar uma nova reserva e extrair dela um nome existente.
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Category({AllTests.class, AcceptanceTests.class})
    @DisplayName("Listar reservas atrav??s do filtro 'lastname'")
    public void validaListagemDeReservasPeloFiltroLastname() {
        getBookingRequest.bookingReturnByLastname(lastname)
                .then()
                .statusCode(200)
                .log().ifValidationFails()
                .body("size()", greaterThan(0));
        // Neste caso a listagem tem que ser maior do que 0 porque, para pegar um "lastname" v??lido,
        //optei por buscar um ID v??lido primeiramente, e extrair dele um sobrenome existente.
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Category({AllTests.class, AcceptanceTests.class})
    @DisplayName("Listar reservas atrav??s do filtro 'checkin'")
    public void validaListagemDeReservasPeloFiltroCheckin() {
        String check = "checkin="; // Vari??vel para concatenar 'checkin=' no path

        getBookingRequest.bookingReturnByDate(check, chekin)
                .then()
                .statusCode(200)
                .log().ifValidationFails()
                .body("size()", greaterThan(0)); // Tem que ser > 0 pois peguei de uma reserva existente

        // Consta na documenta????o que, o filtro de datas deveria retornar reservas de mesma data para
        // frente, o que n??o est?? ocorrendo. A API com filtro checkin est?? retornando apenas reservas
        // com datas posteriores a do filtro. No arquivo README.md isto ?? citado.
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Category({AllTests.class, AcceptanceTests.class})
    @DisplayName("Listar reservas atrav??s do filtro 'checkout'")
    public void validaListagemDeReservasPeloFiltroCheckout() {
        String check = "checkout="; // Vari??vel para concatenar 'checkout=' no path

        getBookingRequest.bookingReturnByDate(check, checkout)
                .then()
                .statusCode(200)
                .log().ifValidationFails()
                .body("size()", greaterThan(0)); // Tem que ser > 0 pois peguei de uma reserva existente

        // Consta na documenta????o que, o filtro de datas deveria retornar reservas de mesma data para
        // frente, o que n??o est?? ocorrendo. A API com filtro checkout est?? retornando reservas com datas anteriores
        // ou iguais a do filtro. No arquivo README.md isto ?? citado.
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Category({AllTests.class, AcceptanceTests.class})
    @DisplayName("Listar reservas enviando o filtro 'checkout' duas vezes")
    public void validaListagemDeReservasPeloFiltroCheckoutECheckout() {
        getBookingRequest.bookingReturnByCheckoutAndCheckout(checkout)
                .then()
                .statusCode(500)
                .log().ifValidationFails();

        // N??o ?? aceito que o filtro "checkout" seja repetido duas vezes na Requisi????o, ent??o a concatena????o
        // ...?checkout=AAAA-MM-DD&checkout=AAAA-MM-DD n??o ocorre, fica apenas assim: ...?checkout=2019-01-01.
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Category({AllTests.class, AcceptanceTests.class})
    @DisplayName("Listar reservas atrav??s dos filtros 'firstname', 'chekin' e 'checkout'")
    public void validaListagemDeReservasPelosFiltrosFirstnameECheckinECheckout() {
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

        getBookingRequest.bookingReturnByFirstNameAndCheckinAndCheckout(firstname, checkinDate, checkoutDate)
                .then()
                .statusCode(200)
                .log().ifValidationFails()
                .body("size()", greaterThan(0)); // Deveria ser > 0 pois peguei de uma reserva existente

        // Existe um bug aqui pois est?? retornando um Array vazio, o que n??o deveria ocorrer j?? que peguei dados
        // v??lidos de firstname, checkin e checkout de uma reserva existente (segundo ID de reservas).
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Category({AllTests.class, E2eTests.class})
    @DisplayName("Visualizar erro de servidor 500 quando enviar filtro mal formatado")
    public void validaErro500QuandoEnviarFiltroMalFormatado() {
        String check = "checkiiinnn="; // Vari??vel escrita de maneira ERRADA para concatenar 'checkiiinnn=' no path

        getBookingRequest.bookingReturnByDate(check, chekin)
                .then()
                .log().ifValidationFails()
                .statusCode(500);

        // A API est?? retornando a lista completa de todos os IDs de reservas e, status code 200,
        // quando um filtro inv??lido ?? digitado. Est?? se comportando como se tivesse fornecido a URL:
        // https://treinamento-api.herokuapp.com/booking.
    }


}
