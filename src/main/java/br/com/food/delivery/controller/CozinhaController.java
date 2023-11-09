package br.com.food.delivery.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.food.delivery.domain.model.Cozinha;
import br.com.food.delivery.service.CozinhaService;

@Controller
@ResponseBody
@RequestMapping("/cozinhas")
public class CozinhaController {

	@Autowired
	private CozinhaService cozinhaService;

	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)
	public List<Cozinha> listar() {
		return cozinhaService.listar();
	}

	@GetMapping("/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public Cozinha buscar(@PathVariable Long id) {
		return cozinhaService.buscar(id);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long id) {
		cozinhaService.excluir(id);
	}
}
