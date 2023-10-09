package br.com.food.delivery.main;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import br.com.food.delivery.ApiDeliveryApplication;
import br.com.food.delivery.domain.model.Restaurante;
import br.com.food.delivery.repository.RestauranteRepository;

public class RestauranteMain {
	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(ApiDeliveryApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		RestauranteRepository repository = applicationContext.getBean(RestauranteRepository.class);
		List<Restaurante> restaurantes = repository.findAll();	
		for (Restaurante restaurante : restaurantes) {
			System.out.println(restaurante.getId() + " " + restaurante.getNome());
		}
	}
}
