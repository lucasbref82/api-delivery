package br.com.food.delivery.exceptionhandler;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@Builder
public class Problema {
	private String dataHora;
	private String mensagem;
}
