package br.com.food.delivery.main;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import br.com.food.delivery.ApiDeliveryApplication;
import br.com.food.delivery.domain.model.Cozinha;
import br.com.food.delivery.repository.CozinhaRepository;

public class CozinhaMain {
	public static void main(String[] args) {
		ApplicationContext applicationContext = 
				new SpringApplicationBuilder(ApiDeliveryApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		CozinhaRepository cozinhas = applicationContext.getBean(CozinhaRepository.class);
		List<Cozinha> todasCozinhas = cozinhas.findAll();
		for (Cozinha cozinha : todasCozinhas) {
			System.out.println(cozinha.getId()+ " " + cozinha.getNome());
		}
		
	}
}
