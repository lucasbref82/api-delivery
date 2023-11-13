package br.com.food.delivery.enumeration;

import lombok.Getter;

@Getter
public enum TipoProblema {
	RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrato", "Recurso não encontrado."),
	ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso."),
	ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negocio."), 
	MENSAGEM_INCOMPREENSIVEL("/mensagem-imcompreensivel", "Mensagem imcompreensível."), 
	PARAMETRO_INVALIDO("/parametro-inválido", "Parâmetro inválido"), 
	ERRO_DE_SISTEMA("/erro-de-sistema", "Erro interno no sistema.");
	
	private String title;
	private String uri;
	
	private TipoProblema(String path, String title) {
		this.uri = "https://api-delivery.com.br" + path;
		this.title = title;
	}
}
