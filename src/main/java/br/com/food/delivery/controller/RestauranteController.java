package br.com.food.delivery.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.food.delivery.domain.exception.EntidadeNaoEncontradaException;
import br.com.food.delivery.domain.model.Restaurante;
import br.com.food.delivery.service.RestauranteService;

@RestController
@RequestMapping(value = "/restaurantes")
public class RestauranteController {

	@Autowired
	private RestauranteService restauranteService;

	@GetMapping
	public ResponseEntity<List<Restaurante>> listar() {
		List<Restaurante> restaurantes = restauranteService.listar();
		return ResponseEntity.ok(restaurantes);
	}

	@GetMapping("/{restauranteId}")
	public ResponseEntity<?> buscar(@PathVariable Long restauranteId) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(restauranteService.buscar(restauranteId));
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante) {
		try {
			restaurante = restauranteService.salvar(restaurante);
			return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("/{restauranteId}")
	public ResponseEntity<?> atualizar(@PathVariable Long restauranteId, @RequestBody Restaurante restaurante) {
		try {
			restaurante = restauranteService.atualizar(restaurante, restauranteId);
			return ResponseEntity.ok(restaurante);

		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping("/frete-gratis-e-nome-semelhante")
	public ResponseEntity<List<Restaurante>> listarComFreteGratisENomeSemelhante(@RequestParam(name = "nome") String nome) {
		List<Restaurante> restaurantes = restauranteService.listarComFreteGratis(nome);
		return ResponseEntity.ok(restaurantes);
	}

}
