package br.com.food.delivery.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.food.delivery.domain.model.Estado;
import br.com.food.delivery.service.EstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController {

	@Autowired
	private EstadoService estadoService;

	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)
	public List<Estado> listar() {
		return estadoService.listar();
	}

	@GetMapping("/{estadoId}")
	@ResponseStatus(code = HttpStatus.OK)
	public Estado buscar(@PathVariable Long estadoId) {
		return estadoService.buscar(estadoId);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Estado adicionar(@RequestBody Estado estado) {
		return estadoService.salvar(estado);
	}

	@PutMapping("/{estadoId}")
	public Estado atualizar(@PathVariable Long estadoId, @RequestBody Estado estado) {
		return estadoService.atualizar(estadoId, estado);
	}

	@DeleteMapping("/{estadoId}")
	public void remover(@PathVariable Long estadoId) {
		estadoService.excluir(estadoId);
	}

}
