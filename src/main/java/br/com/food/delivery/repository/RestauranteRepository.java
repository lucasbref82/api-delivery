package br.com.food.delivery.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.food.delivery.domain.model.Restaurante;

@Repository
public interface RestauranteRepository extends CustomJpaRepository<Restaurante, Long>, JpaSpecificationExecutor<Restaurante>, RestauranteRepositoryQueries{
	
	List<Restaurante> consultarPorNome(@Param("nome") String nome);
	
}
