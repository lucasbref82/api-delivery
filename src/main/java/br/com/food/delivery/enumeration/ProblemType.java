package br.com.food.delivery.enumeration;

import lombok.Getter;

@Getter
public enum ProblemType {
	ENTIDADE_NAO_ENCONTRADA("/entidade-nao-encontrada", "Entidade não encontrada."),
	ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso."),
	ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negocio."), 
	MENSAGEM_INCOMPREENSIVEL("/mensagem-imcompreensivel", "Mensagem imcompreensível.");
	
	private String title;
	private String uri;
	
	private ProblemType(String path, String title) {
		this.uri = "https://algafood.com.br" + path;
		this.title = title;
	}
}