package br.com.food.delivery.messages;

public class Messages {
	
	public static final String ESTADO_NAO_ENCONTRATO_ID = "Não existe cadastro de estado com código %d.";
	public static final String ESTADO_NAO_ENCONTRADO = "Estado informado não existe.";
	
	public static final String CIDADE_NAO_ENCONTRADA_ID = "Não existe um cadastro de cidade com código %d.";
	public static final String CIDADE_NAO_ENCONTRADA = "Cidade informada não existe";
	public static final String CIDADE_NAO_PODE_SER_REMOVIDA = "Cidade de código %d não pode ser removida, pois está em uso.";
	
	public static final String RESTAURANTE_NAO_ENCONTRADO_ID = "Não existe um cadastro de restaurante com código %d.";
	public static final String RESTAURANTE_NAO_ENCONTRADO = "Restaurante informado não existe.";
	
	public static final String COZINHA_NAO_ENCONTRADA_ID = "Não existe um cadastro de cozinha com código %d.";
	public static final String COZINHA_NAO_ENCONTRADA = "Cozinha informada não existe.";
	
	private Messages() {
		
	}
}
