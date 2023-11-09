package br.com.food.delivery.domain.exception;

import br.com.food.delivery.messages.Messages;

public class CozinhaNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public CozinhaNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	
	public CozinhaNaoEncontradaException(Long cozinhaId) {
		this(String.format(Messages.COZINHA_NAO_ENCONTRADA, cozinhaId));
	}
	
}
