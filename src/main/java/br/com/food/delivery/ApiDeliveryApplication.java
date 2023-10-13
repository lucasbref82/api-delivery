package br.com.food.delivery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import br.com.food.delivery.repositoryimpl.CustomJpaRepositoryImpl;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)
public class ApiDeliveryApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiDeliveryApplication.class, args);
	}

}
