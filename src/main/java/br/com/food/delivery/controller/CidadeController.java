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

import br.com.food.delivery.domain.model.Cidade;
import br.com.food.delivery.service.CidadeService;

@RestController
@RequestMapping(value = "/cidades")
public class CidadeController {

	@Autowired
	private br.com.food.delivery.repository.CidadeRepository cidadeRepository;

	@Autowired
	private CidadeService cidadeService;

	@GetMapping
	public List<Cidade> listar() {
		return cidadeRepository.findAll();
	}

	@GetMapping("/{cidadeId}")
	@ResponseStatus(code = HttpStatus.ACCEPTED)
	public Cidade buscar(@PathVariable Long cidadeId) {
		return cidadeService.buscar(cidadeId);
	}

	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public Cidade adicionar(@RequestBody Cidade cidade) {
		cidade = cidadeService.salvar(cidade);
		return cidade;

	}

	@PutMapping("/{cidadeId}")
	@ResponseStatus(code = HttpStatus.ACCEPTED)
	public Cidade atualizar(@PathVariable Long cidadeId, @RequestBody Cidade cidade) {
		return cidadeService.atualizar(cidadeId, cidade);
	}

	@DeleteMapping("/{cidadeId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cidadeId) {
		cidadeService.excluir(cidadeId);
	}

}
