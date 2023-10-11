package br.com.food.delivery.repository.spec;

import java.math.BigDecimal;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import br.com.food.delivery.domain.model.Restaurante;

public class RestauranteComFreteGratisSpec implements Specification<Restaurante>{

	private static final long serialVersionUID = -6228396725501071971L;

	@Override
	public Predicate toPredicate(Root<Restaurante> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		return criteriaBuilder.equal(root.get("taxaFrete"), BigDecimal.ZERO);
	}

}
