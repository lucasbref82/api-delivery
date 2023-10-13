package br.com.food.delivery.repository;

import java.math.BigDecimal;
import java.util.List;

import br.com.food.delivery.domain.model.Restaurante;

public interface RestauranteRepositoryQueries {
	
	List<Restaurante> buscarComFiltro(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);
	List<Restaurante> buscarComCriteria(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);
	List<Restaurante> listarComFreteGratis(String nome);
}
