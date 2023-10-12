package br.com.food.delivery.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.food.delivery.domain.exception.EntidadeEmUsoException;
import br.com.food.delivery.domain.exception.EntidadeNaoEncontradaException;
import br.com.food.delivery.domain.model.Cidade;
import br.com.food.delivery.domain.model.Estado;
import br.com.food.delivery.messages.Messages;
import br.com.food.delivery.repository.CidadeRepository;
import br.com.food.delivery.repository.EstadoRepository;

@Service
public class CidadeService {

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private EstadoRepository estadoRepository;

	public Cidade salvar(Cidade cidade) {
		Long estadoId = cidade.getEstado().getId();
		Estado estado = estadoRepository.findById(estadoId).orElse(null);
		if (estado == null) {
			throw new EntidadeNaoEncontradaException(
					String.format(Messages.ESTADO_NAO_ENCONTRATO, estadoId));
		}
		cidade.setEstado(estado);
		return cidadeRepository.save(cidade);
	}

	public void excluir(Long cidadeId) {
		try {
			Cidade cidade = cidadeRepository.findById(cidadeId).orElse(null);
			if (cidade == null) {
				throw new EntidadeNaoEncontradaException(
						String.format(Messages.CIDADE_NAO_ENCONTRADA, cidadeId));
			}
			cidadeRepository.delete(cidade);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(Messages.CIDADE_NAO_PODE_SER_REMOVIDA, cidadeId));
		}
	}

}
