package br.com.food.delivery.domain.exception;

import br.com.food.delivery.messages.Messages;

public class RestauranteNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public RestauranteNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public RestauranteNaoEncontradoException(Long restauranteId) {
		this(String.format(Messages.RESTAURANTE_NAO_ENCONTRADO, restauranteId));
	}
	
}
