package br.com.food.delivery.domain.repository;

import java.util.List;

import br.com.food.delivery.domain.model.Cozinha;

public interface CozinhaRepository {
	List<Cozinha> todas();
	Cozinha buscar(Long id);
	Cozinha salvar(Cozinha cozinha);
	void remover(Cozinha cozinha);
}
