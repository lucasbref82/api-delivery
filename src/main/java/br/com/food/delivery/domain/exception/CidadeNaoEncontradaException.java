package br.com.food.delivery.domain.exception;

import br.com.food.delivery.messages.Messages;

public class CidadeNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public CidadeNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	
	public CidadeNaoEncontradaException(Long cidadeId) {
		this(String.format(Messages.CIDADE_NAO_ENCONTRADA, cidadeId));
	}
	
}
