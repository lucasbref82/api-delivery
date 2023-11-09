package br.com.food.delivery.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.food.delivery.domain.exception.EstadoNaoEncontradoException;
import br.com.food.delivery.domain.model.Estado;
import br.com.food.delivery.messages.Messages;
import br.com.food.delivery.repository.EstadoRepository;

@Service
public class EstadoService {
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	public List<Estado> listar() {
		return estadoRepository.findAll();	
	}

	public Estado salvar(Estado estado) {
		return estadoRepository.save(estado);
	}

	public Estado buscar(Long estadoId) {
		Estado estadoAtual = this.buscarOuFalhar(estadoId);
		return estadoAtual;
	}

	public Estado atualizar(Long estadoId, Estado estado) {
		Estado estadoAtual = this.buscarOuFalhar(estadoId);
		BeanUtils.copyProperties(estado, estadoAtual, "id");
		return estadoRepository.save(estado);
	}

	public void excluir(Long estadoId) { 
		Estado estado = this.buscarOuFalhar(estadoId);
		estadoRepository.delete(estado);
	}

	public Estado buscarOuFalhar(Long id) {
		return estadoRepository.findById(id).orElseThrow(
				() -> new EstadoNaoEncontradoException(String.format(Messages.ESTADO_NAO_ENCONTRATO_ID, id)));
	}

}
