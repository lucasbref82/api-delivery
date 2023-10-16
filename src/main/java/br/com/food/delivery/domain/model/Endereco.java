package br.com.food.delivery.domain.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class Endereco {

	@Column(name = "ENDERECO_CEP")
	private String cep;
	@Column(name = "ENDERECO_LOGRADOURO")
	private String logradouro;
	@Column(name = "ENDERECO_NUMERO")
	private String numero;
	@Column(name = "ENDERECO_COMPLEMENTO")
	private String complemento;
	@Column(name = "ENDERECO_BAIRRO")
	private String bairro;
	
	@ManyToOne
	@JoinColumn(name = "ENDERECO_CIDADE_ID", nullable = false)
	private Cidade cidade;
}
