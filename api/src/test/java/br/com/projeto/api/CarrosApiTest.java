package br.com.projeto.api;

import br.com.projeto.api.domain.dto.CarroDTO;
import br.com.projeto.api.domain.model.Carro;
import br.com.projeto.api.domain.servico.CarroService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CarrosApiTest {

	@Autowired
	protected TestRestTemplate rest;

	@Autowired
	private CarroService service;

	// Get de um objeto em conjunto com a URL, o rest.getForEntity exige dois parâmetros
	// O primeiro é a url para fazer a requisição, o segundo qual seria a classe de retorno.
	private ResponseEntity<CarroDTO> getCarro(String url){
		return rest.getForEntity(url, CarroDTO.class);
	}

	private ResponseEntity<List<CarroDTO>> getCarros(String url){
		return rest.exchange(
				url,
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<List<CarroDTO>>() {
				});
	}

//	@Test
//	public void testSave() {
//		Carro carro = new Carro();
//		carro.setNome("Porsche");
//		carro.setTipo("esportivos");
//
//		// Faz o insert
//		ResponseEntity response = rest.postForEntity("/api/v1/carros", carro, null);
//		System.out.println(response);
//
//		// Verificar se criou
//		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
//
//		// Buscar o objeto
//		// O location é um parâmetro que foi construído assim que eu fizer o get
//		// Ele vai me dar a localização de onde que está esse objeto na url.
//		String location = response.getHeaders().get("location").get(0);
//		CarroDTO c = getCarro(location).getBody();
//
//		assertNotNull(c);
//		assertEquals("Porsche", c.getNome());
//		assertEquals("esportivos", c.getTipo());
//
//
//		// Deletar o objeto
//		rest.delete(location);
//
//		// Verificar se deletou
//		assertEquals(HttpStatus.NOT_FOUND, getCarro(location).getStatusCode());
//	}

//	@Test
//	public void testLista() {
//		List<CarroDTO> carros = getCarros("/api/v1/carros").getBody();
//		assertNotNull(carros);
//		assertEquals(30, carros.size());
//	}

//	@Test
//	public void getOk() {
//		ResponseEntity<CarroDTO> response = getCarro("/api/v1/carros/11");
//		assertEquals(response.getStatusCode(), HttpStatus.UNAUTHORIZED);
//
//		CarroDTO c = response.getBody();
//		assertEquals("Ferrari FF", c.getNome());
//	}

//	@Test
//	public void testListaPorTipo() {
//		assertEquals(10, getCarros("/api/v1/carros/tipo/classicos").getBody().size());
//		assertEquals(10, getCarros("/api/v1/carros/tipo/esportivos").getBody().size());
//		assertEquals(10, getCarros("/api/v1/carros/tipo/luxo").getBody().size());
//
//		assertEquals(HttpStatus.NO_CONTENT, getCarros("/api/v1/carros/tipo/xxx").getStatusCode());
//	}

	@Test
	public void testGetNotFound() {
		ResponseEntity response = getCarro("/api/v1/carros/112");
		assertEquals(response.getStatusCode(), HttpStatus.UNAUTHORIZED);
	}
}
