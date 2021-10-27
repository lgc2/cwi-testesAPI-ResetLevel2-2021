CWI Reset Level 2 - Turma de Testes de Software

Projeto para aprendizado de Automação de Testes em APIs.

* SUGESTÕES/MELHORIAS:

    --> Suite Healthcheck, Verificar de API está online: aqui o sucesso é retornado com código 201 Created, que é mais utilizado com o
    método POST. Seria mais adequado utilizar o statuscode 200 OK;

    --> Suite Acceptance, Exluir uma reserva com sucesso: código de exclusão com sucesso é o 201 Created. Como citado acima, não é um
    statuscode ideal para se empregar numa exclusão de reserva. O código 200 OK seria mais adequado;

    --> Suite Acceptance, Criar uma nova reserva: Como suesso na criação retorna o stauscode 200 OK, poderia retornar 201 Created.
    
    
    
* PROBLEMAS ENCONTRADOS:
    
    --> Suite Acceptance, Listar IDs de reservas utilizando o filtro checkin: Consta na documentação que, o filtro de datas deveria retornar reservas de mesma data ou posteriores, o que não está ocorrendo. A API com filtro checkin está retornando apenas reservas
    com datas posteriores a do filtro;

    --> Suite Acceptance, Listar IDs de reservas utilizando o filtro checkout: Consta na documentação que, o filtro de datas deveria retornar reservas de mesma data ou posteriores, o que não está ocorrendo. A API com filtro checkout está retornando reservas com datas anteriores ou iguais a do filtro;

    --> Suite Acceptance, Listar IDs de reservas utilizando o filtro checkout duas vezes: Não é aceito que o filtro "checkout" seja repetido duas vezes na Requisição, então a concatenação ...?checkout=AAAA-MM-DD&checkout=AAAA-MM-DD não ocorre, fica apenas assim:
    ...?checkout=AAAA-MM-DD. Erro 500 é retornado;

    --> Suite Acceptance, Listar IDs de reservas utilizando o filtro name, checkin and checkout date: Existe um bug aqui, pois está retornando um Array vazio, o que não deveria ocorrer já que peguei dados válidos de firstname, checkin e checkout de uma reserva existente (segundo ID de reservas), sendo assim, no mínimo um resultado deveria ser apresentado;

    --> Suite Acceptance, Alterar uma reserva usando o Basic auth: Há um problema com esta Requisição, pois mesmo enviando dados válidos
    de Content-Type, Accept e Authorisation (Basic YWRtaW46cGFzc3dvcmQxMjM=), no Header, e body JSON válido, é retornado erro 403 Forbidden;

    --> Suite E2e, Visualizar erro de servidor 500 quando enviar filtro mal formatado: A API está retornando a lista completa de todos os IDs de reservas e, status code 200, quando um filtro inválido é digitado. Está se comportando como se tivesse fornecido a URL:
    https://treinamento-api.herokuapp.com/booking.

    * OBSERVAÇÃO:

    --> Suite E2e, Criar uma reserva enviando mais parâmetros no payload da reserva: Criou normalmente ignorando atributos adicionais.