package br.com.food.delivery;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import br.com.food.delivery.domain.exception.EntidadeEmUsoException;
import br.com.food.delivery.domain.exception.EntidadeNaoEncontradaException;
import br.com.food.delivery.domain.model.Cozinha;
import br.com.food.delivery.service.CozinhaService;

@SpringBootTest
class CadastroCozinhaIT {

	@Autowired
	private CozinhaService cozinhaService;

	@Test
	public void testarCadastroCozinhaComSucesso() {
		Cozinha cozinha = new Cozinha();
		cozinha.setNome("Chinesa");
		cozinha = cozinhaService.salvar(cozinha);
		assertThat(cozinha).isNotNull();
		assertThat(cozinha.getId()).isNotNull();
	}

	@Test
	public void testarFalhaAoCadastrarCozinhaSemNome() {
		Cozinha cozinha = new Cozinha();
		cozinha.setNome(null);

		DataIntegrityViolationException erroEsperado = assertThrows(DataIntegrityViolationException.class, () -> {
			cozinhaService.salvar(cozinha);
		});
		assertThat(erroEsperado).isNotNull();
	}

	@Test
	public void testeFalhaAoTentarExcluirUmaCozinhaInexistente() {
		EntidadeNaoEncontradaException entidadeNaoEncontradaException = assertThrows(
				EntidadeNaoEncontradaException.class, () -> cozinhaService.excluir(6l));
		assertThat(entidadeNaoEncontradaException).isNotNull();
	}

	@Test
	public void testeFalhaAoTentarExcluirUmaCozinhaEmUso() {
		EntidadeEmUsoException entidadeEmUsoException = assertThrows(EntidadeEmUsoException.class,
				() -> cozinhaService.excluir(1L));
		assertThat(entidadeEmUsoException).isNotNull();
	}

}
