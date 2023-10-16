package br.com.food.delivery.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "RESTAURANTE")
@Data
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
public class Restaurante {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String nome;

	@Column(name = "taxa_frete", nullable = false)
	private BigDecimal taxaFrete;

	@ManyToOne
	@JoinColumn(name = "cozinha_id", nullable = false)
	private Cozinha cozinha;
	
	@Embedded
	@JsonIgnore
	private Endereco endereco;
	
	@CreationTimestamp
	@Column(nullable = false)
	private LocalDateTime dataCadastro;
	
	@UpdateTimestamp
	@Column(nullable = false)
	private LocalDateTime dataAtualizacao;

	@Builder.Default
	@ManyToMany
	@JoinTable(name = "RESTAURANTE_FORMA_PAGAMENTO", joinColumns = @JoinColumn(name = "RESTAURANTE_ID"), 
	inverseJoinColumns = @JoinColumn(name = "FORMA_PAGAMENTO_ID"))
	@JsonIgnore
	private List<FormaPagamento> formasPagamentos = new ArrayList<>();
	
	@Builder.Default
	@JsonIgnore	
	@OneToMany(mappedBy = "produto")
	private List<Restaurante> restaurantes = new ArrayList<>();
	


}
