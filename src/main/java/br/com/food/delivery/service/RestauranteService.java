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
import static br.com.food.delivery.repository.spec.RestauranteSpecs.*;

@Service
public class RestauranteService {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CozinhaRepository cozinhaRepository;

	public Restaurante buscar(Long id) {
		Restaurante restauranteEncontrado = restauranteRepository.findById(id).orElse(null);
		if (restauranteEncontrado == null) {
			throw new EntidadeNaoEncontradaException(String.format(Messages.RESTAURANTE_NAO_ENCONTRADO, id));
		}
		return restauranteEncontrado;
	}

	public List<Restaurante> listar() {
		return restauranteRepository.findAll();
	}

	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		Cozinha cozinha = cozinhaRepository.findById(cozinhaId).orElse(null);

		if (cozinha == null) {
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe cadastro de cozinha com código %d", cozinhaId));
		}

		restaurante.setCozinha(cozinha);

		return restauranteRepository.save(restaurante);
	}

	public Restaurante atualizar(Restaurante restaurante, Long id) {
		Restaurante restauranteAtual = restauranteRepository.findById(id).orElseThrow(
				() -> new EntidadeNaoEncontradaException(String.format(Messages.RESTAURANTE_NAO_ENCONTRADO, id)));

		Long cozinhaId = restaurante.getCozinha().getId();
		
		Cozinha cozinhaAtual = cozinhaRepository.findById(cozinhaId).orElseThrow(
				() -> new EntidadeNaoEncontradaException(String.format(Messages.COZINHA_NAO_ENCONTRADA, cozinhaId)));
		
		restauranteAtual.setCozinha(cozinhaAtual);
		BeanUtils.copyProperties(restaurante, restauranteAtual, "id");
		return restauranteRepository.save(restauranteAtual);
	}

	public List<Restaurante> listarComFreteGratisENomeSemelhante(String nome) {
		return restauranteRepository.findAll(comFreteGratis().and(nomeSemelhante(nome)));
	}

}
