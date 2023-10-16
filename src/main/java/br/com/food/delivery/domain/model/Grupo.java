package br.com.food.delivery.domain.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Grupo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	
	@Builder.Default
	@ManyToMany
	@JoinTable(name = "GRUPO_PERMISSAO", joinColumns = @JoinColumn(name = "GRUPO_ID"), inverseJoinColumns = @JoinColumn(name = "PERMISSAO_ID"))
	private List<Permissao> permissoes = new ArrayList<>();
}
