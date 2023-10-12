package br.com.food.delivery.repository.spec;

import java.math.BigDecimal;
import java.text.MessageFormat;

import org.springframework.data.jpa.domain.Specification;

import br.com.food.delivery.domain.model.Restaurante;

public class RestauranteSpecs {
	
	public static Specification<Restaurante> comFreteGratis() {
		return (root, query, builder) -> builder.equal(root.get("taxaFrete"), BigDecimal.ZERO);
	}
	
	public static Specification<Restaurante> nomeSemelhante(String nome) {
		return (root, query, builder) -> builder.like(root.get("nome"), contais(nome));
	}
	
	private static String contais(String atributo) {
		return MessageFormat.format("%{0}%", atributo);
	}
}
