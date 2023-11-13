package br.com.food.delivery.service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.food.delivery.domain.exception.CozinhaNaoEncontradaException;
import br.com.food.delivery.domain.exception.RestauranteNaoEncontradoException;
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

	public Restaurante buscar(Long id) {
		Restaurante restauranteEncontrado = this.buscarOuFalhar(id);
		return restauranteEncontrado;
	}

	public List<Restaurante> listar() {
		return restauranteRepository.findAll();
	}

	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		Cozinha cozinha = this.buscarOuFalharCozinha(cozinhaId);
		restaurante.setCozinha(cozinha);
		return restauranteRepository.save(restaurante);
	}

	public Restaurante atualizar(Restaurante restaurante, Long id) {
		Restaurante restauranteAtual = this.buscarOuFalhar(id);
		Long cozinhaId = restaurante.getCozinha().getId();
		Cozinha cozinhaAtual = this.buscarOuFalharCozinha(cozinhaId);
		restauranteAtual.setCozinha(cozinhaAtual);
		BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamentos", "endereco");
		return restauranteRepository.save(restauranteAtual);
	}

	public void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino,
			HttpServletRequest request) {
		ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
			
			Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);
			
			dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
				Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
				field.setAccessible(true);
				
				Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
				
				ReflectionUtils.setField(field, restauranteDestino, novoValor);
			});
		} catch (IllegalArgumentException e) {
			Throwable rootCause = ExceptionUtils.getRootCause(e);
			throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
		}
	}
	
	public List<Restaurante> listarComFreteGratis(String nome) {
		return restauranteRepository.listarComFreteGratis(nome);
	}

	public Restaurante buscarOuFalhar(Long id)  {
		return restauranteRepository.findById(id).orElseThrow(
				() -> new RestauranteNaoEncontradoException(String.format(Messages.RESTAURANTE_NAO_ENCONTRADO_ID, id)));
	}

	public Cozinha buscarOuFalharCozinha(Long id) {
		return cozinhaRepository.findById(id).orElseThrow(
				() -> new CozinhaNaoEncontradaException(String.format(Messages.COZINHA_NAO_ENCONTRADA_ID, id)));
	}

}
