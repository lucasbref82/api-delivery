package br.com.food.delivery.domain.exception;

import br.com.food.delivery.messages.Messages;

public class EstadoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public EstadoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public EstadoNaoEncontradoException() {
		this(Messages.ESTADO_NAO_ENCONTRADO);
	}
	
}
