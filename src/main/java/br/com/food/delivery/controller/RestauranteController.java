package br.com.food.delivery.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.food.delivery.domain.model.Restaurante;
import br.com.food.delivery.service.RestauranteService;

@RestController
@RequestMapping(value = "/restaurantes")
public class RestauranteController {

	@Autowired
	private RestauranteService restauranteService;

	@GetMapping
	public List<Restaurante> listar() {
		List<Restaurante> restaurantes = restauranteService.listar();
		return restaurantes;
	}

	@GetMapping("/{restauranteId}")
	public Restaurante buscar(@PathVariable Long restauranteId) {
		return restauranteService.buscar(restauranteId);
	}

	@PostMapping
	public Restaurante adicionar(@RequestBody Restaurante restaurante) {
		restaurante = restauranteService.salvar(restaurante);
		return restaurante;

	}

	@PutMapping("/{restauranteId}")
	public Restaurante atualizar(@PathVariable Long restauranteId, @RequestBody Restaurante restaurante) {
		restaurante = restauranteService.atualizar(restaurante, restauranteId);
		return restaurante;
	}

	@GetMapping("/frete-gratis-e-nome-semelhante")
	public List<Restaurante> listarComFreteGratisENomeSemelhante(@RequestParam(name = "nome") String nome) {
		List<Restaurante> restaurantes = restauranteService.listarComFreteGratis(nome);
		return restaurantes;
	}

}
