package br.com.food.delivery;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

// Define que o tomcat vai subir o conteiner para teste em uma porta aleatoria.
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CadastroCozinhaApiTest {

	// Pega a porta local em que o container está subindo.
	@LocalServerPort
	private int port;
	
	@Test
	public void testarRetornar200AoConsultarCozinhas() {
		/**
		 * Ao habilitar esse recurso, se uma validação falhar durante a execução de um teste, 
		 * RestAssured registrará detalhes como cabeçalhos, corpo da solicitação, 
		 * status da resposta, corpo da resposta, etc. 
		 * Isso pode facilitar a depuração e resolução de problemas quando os testes não passam conforme o esperado. **/
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		// Passando os dados do end-point
		given()
			.basePath("/cozinhas")
			.port(port)
			.accept(ContentType.JSON)
		// Quando for get
		.when()
			.get()
		// Então o retorno deve ser 200
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void testaRetornarQuatroObjetos() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		given()
			.basePath("/cozinhas")
			.accept(ContentType.JSON)
			.port(port)
		.when()
			.get()
		.then()
			.body("", hasSize(4));
			// .body("nome", hasItems("Tailandesa, Indiana"));
	}

}
