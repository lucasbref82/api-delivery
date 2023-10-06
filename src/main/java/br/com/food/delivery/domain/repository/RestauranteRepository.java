package br.com.food.delivery.domain.repository;

import java.util.List;

import br.com.food.delivery.domain.model.Restaurante;

public interface RestauranteRepository {
	List<Restaurante> todos();
	Restaurante adicionar(Restaurante restaurante);
	Restaurante buscar(Long id);
	void remover(Restaurante restaurante);
}
