package br.com.food.delivery.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.food.delivery.domain.exception.EntidadeNaoEncontradaException;
import br.com.food.delivery.domain.model.Cozinha;
import br.com.food.delivery.domain.model.Restaurante;
import br.com.food.delivery.messages.Messages;
import br.com.food.delivery.repository.CozinhaRepository;
import br.com.food.delivery.repository.RestauranteRepository;

@Service
public class RestauranteService {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CozinhaRepository cozinhaRepository;

	public Restaurante buscar(Long id) throws EntidadeNaoEncontradaException {
		Restaurante restauranteEncontrado = this.buscarOuFalhar(id);
		return restauranteEncontrado;
	}

	public List<Restaurante> listar() {
		return restauranteRepository.findAll();
	}

	public Restaurante salvar(Restaurante restaurante) throws EntidadeNaoEncontradaException {
		Long cozinhaId = restaurante.getCozinha().getId();
		Cozinha cozinha = this.buscarOuFalharCozinha(cozinhaId);
		restaurante.setCozinha(cozinha);
		return restauranteRepository.save(restaurante);
	}

	public Restaurante atualizar(Restaurante restaurante, Long id) throws EntidadeNaoEncontradaException {
		Restaurante restauranteAtual = this.buscarOuFalhar(id);
		Long cozinhaId = restaurante.getCozinha().getId();
		Cozinha cozinhaAtual = this.buscarOuFalharCozinha(cozinhaId);
		restauranteAtual.setCozinha(cozinhaAtual);
		BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamentos", "endereco");
		return restauranteRepository.save(restauranteAtual);
	}

	public List<Restaurante> listarComFreteGratis(String nome) {
		return restauranteRepository.listarComFreteGratis(nome);
	}

	public Restaurante buscarOuFalhar(Long id) throws EntidadeNaoEncontradaException {
		return restauranteRepository.findById(id).orElseThrow(
				() -> new EntidadeNaoEncontradaException(String.format(Messages.RESTAURANTE_NAO_ENCONTRADO, id)));
	}

	public Cozinha buscarOuFalharCozinha(Long id) throws EntidadeNaoEncontradaException {
		return cozinhaRepository.findById(id).orElseThrow(
				() -> new EntidadeNaoEncontradaException(String.format(Messages.COZINHA_NAO_ENCONTRADA, id)));
	}

}
