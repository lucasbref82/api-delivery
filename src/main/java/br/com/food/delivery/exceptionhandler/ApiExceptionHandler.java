package br.com.food.delivery.exceptionhandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.food.delivery.domain.exception.EntidadeNaoEncontradaException;

@ControllerAdvice
public class ApiExceptionHandler {
	
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public ResponseEntity<?> tratarHttpMediaTypeNotSupportedException() {
		Problema problema = Problema.builder()
				.dataHora(formatter.format(LocalDateTime.now()))
				.mensagem("O tipo de mídia não é aceito.").build();
		
		return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
				.body(problema);
	}
	
	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> tratarEntidadeNaoEncontradaException(EntidadeNaoEncontradaException e){
		Problema problema = Problema.builder()
				.dataHora(formatter.format(LocalDateTime.now()))
				.mensagem(e.getMessage())
				.build();
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problema);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<?> tratarViolacaoDeContraints(DataIntegrityViolationException e) {
		Problema problema = Problema.builder()
				.dataHora(formatter.format(LocalDateTime.now()))
				.mensagem(e.getMessage())
				.build();
		
		return ResponseEntity.status(HttpStatus.CONFLICT).body(problema);
	}
}
