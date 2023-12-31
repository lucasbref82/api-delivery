package br.com.food.delivery.exceptionhandler;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

// Excluir objetos nulos na representação da resposta
@JsonInclude(value = Include.NON_NULL)
@Data
@Getter
@Builder
public class Problema {
	// Status HTTP
	private Integer status;
	// Tipo do problema ou URL contendo informações de como resolver o problema
	private String tipo;
	// Titulo do problema
	private String titulo;
	// Detalhe da excessão
	private String detalhes;
	// Mensagem para usuário final;
	private String mensagem;
	// Data Hora do erro
	private LocalDateTime dataHora;
}
