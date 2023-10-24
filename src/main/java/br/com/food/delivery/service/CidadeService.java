package br.com.food.delivery.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	public Cidade buscar(Long id) throws EntidadeNaoEncontradaException {
		Cidade cidade = this.buscarOuFalhar(id);
		return cidade;
	}

	public Cidade salvar(Cidade cidade) {
		return cidadeRepository.save(cidade);
	}

	public Cidade atualizar(Long id, Cidade cidade) throws EntidadeNaoEncontradaException {
		Cidade cidadeAtual = this.buscarOuFalhar(id);
		BeanUtils.copyProperties(cidade, cidadeAtual, "id");
		Long estadoId = cidade.getEstado().getId();
		Estado estado = estadoRepository.findById(estadoId).orElseThrow(
				() -> new EntidadeNaoEncontradaException(String.format(Messages.ESTADO_NAO_ENCONTRATO, estadoId)));
		cidade.setEstado(estado);
		return cidadeRepository.save(cidade);

	}

	public void excluir(Long cidadeId) throws EntidadeNaoEncontradaException {
		Cidade cidade = this.buscarOuFalhar(cidadeId);
		cidadeRepository.delete(cidade);
	}

	public Cidade buscarOuFalhar(Long id) throws EntidadeNaoEncontradaException {
		return cidadeRepository.findById(id).orElseThrow(
				() -> new EntidadeNaoEncontradaException(String.format(Messages.CIDADE_NAO_ENCONTRADA, id)));
	}

}
