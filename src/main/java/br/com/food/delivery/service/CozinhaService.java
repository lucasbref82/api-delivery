package br.com.food.delivery.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.food.delivery.domain.exception.CozinhaNaoEncontradaException;
import br.com.food.delivery.domain.exception.EntidadeEmUsoException;
import br.com.food.delivery.domain.exception.EntidadeNaoEncontradaException;
import br.com.food.delivery.domain.model.Cozinha;
import br.com.food.delivery.messages.Messages;
import br.com.food.delivery.repository.CozinhaRepository;

@Service
public class CozinhaService {

	@Autowired
	private CozinhaRepository cozinhaRepository;

	public Cozinha salvar(Cozinha cozinha) {
		return cozinhaRepository.save(cozinha);
	}

	public void excluir(Long cozinhaId) {
		Cozinha cozinha = cozinhaRepository.findById(cozinhaId).orElse(null);
		if (cozinha == null) {
			throw new CozinhaNaoEncontradaException(
					String.format(Messages.COZINHA_NAO_ENCONTRADA_ID, cozinhaId));
		}
		try {
			cozinhaRepository.delete(cozinha);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("Cozinha de id '%d' não pode ser removida pois está em uso.", cozinhaId));
		}
		
	}


	public List<Cozinha> listar() {
		return cozinhaRepository.findAll();
	}

	public Cozinha buscar(Long id) throws EntidadeNaoEncontradaException {
		Cozinha cozinhaAtual = this.buscarOuFalhar(id);
		return cozinhaAtual;
	}

	public Cozinha buscarOuFalhar(Long id) {
		return cozinhaRepository.findById(id).orElseThrow(
				() -> new CozinhaNaoEncontradaException(String.format(Messages.COZINHA_NAO_ENCONTRADA_ID, id)));
	}

}
