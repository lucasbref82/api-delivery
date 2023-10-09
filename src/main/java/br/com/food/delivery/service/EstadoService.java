package br.com.food.delivery.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.food.delivery.domain.exception.EntidadeEmUsoException;
import br.com.food.delivery.domain.exception.EntidadeNaoEncontradaException;
import br.com.food.delivery.domain.model.Estado;
import br.com.food.delivery.repository.EstadoRepository;

@Service
public class EstadoService {
	@Autowired
	private EstadoRepository estadoRepository;

	public Estado salvar(Estado estado) {
		return estadoRepository.save(estado);
	}

	public void excluir(Long estadoId) {
		try {
			Estado estado = estadoRepository.findById(estadoId).orElse(null);
			if (estado == null) {
				throw new EntidadeNaoEncontradaException(
						String.format("Não existe um cadastro de estado com código %d", estadoId));
			}
			estadoRepository.delete(estado);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Estado de código %d não pode ser removido, pois está em uso", estadoId));
		}
	}
}
