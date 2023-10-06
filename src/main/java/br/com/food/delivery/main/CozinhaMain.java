package br.com.food.delivery.main;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import br.com.food.delivery.ApiDeliveryApplication;
import br.com.food.delivery.domain.model.Cozinha;
import br.com.food.delivery.infraestructure.repository.CozinhaRepositoryImpl;

public class CozinhaMain {
	public static void main(String[] args) {
		ApplicationContext applicationContext = 
				new SpringApplicationBuilder(ApiDeliveryApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		CozinhaRepositoryImpl cozinhas = applicationContext.getBean(CozinhaRepositoryImpl.class);
		List<Cozinha> todasCozinhas = cozinhas.todas();
		for (Cozinha cozinha : todasCozinhas) {
			System.out.println(cozinha.getId()+ " " + cozinha.getNome());
		}
		
	}
}
