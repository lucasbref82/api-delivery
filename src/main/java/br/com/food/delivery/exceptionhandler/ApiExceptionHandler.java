package br.com.food.delivery.exceptionhandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;

import br.com.food.delivery.domain.exception.EntidadeEmUsoException;
import br.com.food.delivery.domain.exception.EntidadeNaoEncontradaException;
import br.com.food.delivery.domain.exception.NegocioException;
import br.com.food.delivery.enumeration.TipoProblema;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	public static final String MSG_ERRO_GENERICA_USUARIO_FINAL
		= "Ocorreu um erro interno inesperado no sistema. Tente novamente e se "
				+ "o problema persistir, entre em contato com o administrador do sistema.";
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;		
		TipoProblema tipoProblema = TipoProblema.ERRO_DE_SISTEMA;
		String detail = MSG_ERRO_GENERICA_USUARIO_FINAL;
		ex.printStackTrace();
		
		Problema problem = createProblemBuilder(status, tipoProblema, detail)
				.mensagem(detail)
				.build();

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, 
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		TipoProblema tipoProblema = TipoProblema.RECURSO_NAO_ENCONTRADO;
		String detail = String.format("O recurso %s, que você tentou acessar, é inexistente.", 
				ex.getRequestURL());
		
		Problema problem = createProblemBuilder(status, tipoProblema, detail)
				.mensagem(MSG_ERRO_GENERICA_USUARIO_FINAL)
				.build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		if (ex instanceof MethodArgumentTypeMismatchException) {
			return handleMethodArgumentTypeMismatch(
					(MethodArgumentTypeMismatchException) ex, headers, status, request);
		}
	
		return super.handleTypeMismatch(ex, headers, status, request);
	}
	
	private ResponseEntity<Object> handleMethodArgumentTypeMismatch(
			MethodArgumentTypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		TipoProblema tipoProblema = TipoProblema.PARAMETRO_INVALIDO;

		String detail = String.format("O parâmetro de URL '%s' recebeu o valor '%s', "
				+ "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
				ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

		Problema problem = createProblemBuilder(status, tipoProblema, detail)
				.mensagem(MSG_ERRO_GENERICA_USUARIO_FINAL)
				.build();

		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Throwable rootCause = ExceptionUtils.getRootCause(ex);
		
		if (rootCause instanceof InvalidFormatException) {
			return handleInvalidFormat((InvalidFormatException) rootCause, headers, status, request);
		} else if (rootCause instanceof PropertyBindingException) {
			return handlePropertyBinding((PropertyBindingException) rootCause, headers, status, request); 
		}
		
		TipoProblema tipoProblema = TipoProblema.MENSAGEM_INCOMPREENSIVEL;
		String detail = "O corpo da requisição está inválido. Verifique erro de sintaxe.";
		
		Problema problem = createProblemBuilder(status, tipoProblema, detail)
				.mensagem(MSG_ERRO_GENERICA_USUARIO_FINAL)
				.build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	private ResponseEntity<Object> handlePropertyBinding(PropertyBindingException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		String path = joinPath(ex.getPath());
		
		TipoProblema tipoProblema = TipoProblema.MENSAGEM_INCOMPREENSIVEL;
		String detail = String.format("A propriedade '%s' não existe. "
				+ "Corrija ou remova essa propriedade e tente novamente.", path);

		Problema problem = createProblemBuilder(status, tipoProblema, detail)
				.mensagem(MSG_ERRO_GENERICA_USUARIO_FINAL)
				.build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	private ResponseEntity<Object> handleInvalidFormat(InvalidFormatException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		String path = joinPath(ex.getPath());
		
		TipoProblema tipoProblema = TipoProblema.MENSAGEM_INCOMPREENSIVEL;
		String detail = String.format("A propriedade '%s' recebeu o valor '%s', "
				+ "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
				path, ex.getValue(), ex.getTargetType().getSimpleName());
		
		Problema problem = createProblemBuilder(status, tipoProblema, detail)
				.mensagem(MSG_ERRO_GENERICA_USUARIO_FINAL)
				.build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException ex,
			WebRequest request) {
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		TipoProblema tipoProblema = TipoProblema.RECURSO_NAO_ENCONTRADO;
		String detail = ex.getMessage();
		
		Problema problem = createProblemBuilder(status, tipoProblema, detail)
				.mensagem(detail)
				.build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> handleEntidadeEmUso(EntidadeEmUsoException ex, WebRequest request) {
		
		HttpStatus status = HttpStatus.CONFLICT;
		TipoProblema tipoProblema = TipoProblema.ENTIDADE_EM_USO;
		String detail = ex.getMessage();
		
		Problema problem = createProblemBuilder(status, tipoProblema, detail)
				.mensagem(detail)
				.build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> handleNegocio(NegocioException ex, WebRequest request) {

		HttpStatus status = HttpStatus.BAD_REQUEST;
		TipoProblema tipoProblema = TipoProblema.ERRO_NEGOCIO;
		String detail = ex.getMessage();
		
		Problema problem = createProblemBuilder(status, tipoProblema, detail)
				.mensagem(detail)
				.build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		if (body == null) {
			body = Problema.builder()
				.dataHora(LocalDateTime.now())
				.titulo(status.getReasonPhrase())
				.status(status.value())
				.mensagem(MSG_ERRO_GENERICA_USUARIO_FINAL)
				.build();
		} else if (body instanceof String) {
			body = Problema.builder()
				.dataHora(LocalDateTime.now())
				.titulo((String) body)
				.status(status.value())
				.mensagem(MSG_ERRO_GENERICA_USUARIO_FINAL)
				.build();
		}
		
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}
	
	private Problema.ProblemaBuilder createProblemBuilder(HttpStatus status,
			TipoProblema tipoProblema, String detail) {
		
		return Problema.builder()
			.dataHora(LocalDateTime.now())
			.status(status.value())
			.tipo(tipoProblema.getUri())
			.titulo(tipoProblema.getTitle())
			.detalhes(detail);
	}

	private String joinPath(List<Reference> references) {
		return references.stream()
			.map(ref -> ref.getFieldName())
			.collect(Collectors.joining("."));
	}
	
}