package br.com.food.delivery.domain.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class ItemPedido {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private BigDecimal precoUnitario;
	private BigDecimal precoTotal;
	private Integer quantidade;
	private String observacao;

	@ManyToOne
	@JoinColumn(name = "PEDIDO_ID", nullable = false)
	private Pedido pedido;

	@ManyToOne
	@JoinColumn(name = "PRODUTO_ID", nullable = false)
	private Produto produto;

}
