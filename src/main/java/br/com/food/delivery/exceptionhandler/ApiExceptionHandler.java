package br.com.food.delivery.exceptionhandler;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;

import br.com.food.delivery.domain.exception.EntidadeEmUsoException;
import br.com.food.delivery.domain.exception.EntidadeNaoEncontradaException;
import br.com.food.delivery.domain.exception.NegocioException;
import br.com.food.delivery.enumeration.ProblemType;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Throwable throwable = ExceptionUtils.getRootCause(ex);
		if (throwable instanceof InvalidFormatException) {
			return handleInvalidFormatException((InvalidFormatException) throwable, headers, status, request);
		}
		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		String detail = "O corpo da requisição está válido. Verifique erro de sintaxe";
		Problema problema = criarBuilderProblema(status, problemType, detail).build();

		return handleExceptionInternal(ex, problema, headers, status, request);
	}

	private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		String path = getFieldName(ex.getPath());

		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		String detail = String.format(
				"A propriedade '%s' recebeu o valor '%s', "
						+ "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
				path, ex.getValue(), ex.getTargetType().getSimpleName());

		Problema problema = criarBuilderProblema(status, problemType, detail).build();

		return handleExceptionInternal(ex, problema, headers, status, request);
	}

	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> handleTratarEntidadeNaoEncontradaException(EntidadeNaoEncontradaException ex,
			WebRequest request) {
		// Padrão RFC 7807
		HttpStatus status = HttpStatus.NOT_FOUND;
		ProblemType problemType = ProblemType.ENTIDADE_NAO_ENCONTRADA;
		String detail = ex.getMessage();
		Problema problema = criarBuilderProblema(status, problemType, detail).build();

		return handleExceptionInternal(ex, problema, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> tratarEntidadeEmUsoException(EntidadeEmUsoException ex, WebRequest request) {
		HttpStatus httpStatus = HttpStatus.CONFLICT;
		ProblemType problemType = ProblemType.ENTIDADE_EM_USO;
		String detail = ex.getMessage();
		Problema problema = criarBuilderProblema(httpStatus, problemType, detail).build();

		return handleExceptionInternal(ex, problema, new HttpHeaders(), httpStatus, request);
	}

	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> tratarNegocioException(NegocioException ex, WebRequest request) {
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		ProblemType problemType = ProblemType.ERRO_NEGOCIO;
		String detail = ex.getMessage();
		Problema problema = criarBuilderProblema(httpStatus, problemType, detail).build();
		return handleExceptionInternal(ex, problema, new HttpHeaders(), httpStatus, request);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<?> tratarViolacaoDeContraints(DataIntegrityViolationException ex, WebRequest request) {
		return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT, request);
	}

	private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		String path = getFieldName(ex.getPath());
	    ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
	    String detail = String.format("A propriedade '%s' não existe. "
	            + "Corrija ou remova essa propriedade e tente novamente.", path);
	    Problema problema = criarBuilderProblema(status, problemType, detail).build();
	    return handleExceptionInternal(ex, problema, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		if (body == null) {
			body = Problema.builder().status(status.value()).title(status.getReasonPhrase()).build();
		} else if (body instanceof String) {
			body = Problema.builder().status(status.value()).title((String) body).build();
		}

		return super.handleExceptionInternal(ex, body, headers, status, request);
	}
	
	private String getFieldName(List<Reference> path) {
		return path.stream().map(reference -> reference.getFieldName()).collect(Collectors.joining("."));
	}

	private Problema.ProblemaBuilder criarBuilderProblema(HttpStatus status, ProblemType problemType, String detail) {
		return Problema.builder().status(status.value()).type(problemType.getUri()).title(problemType.getTitle())
				.detail(detail);
	}
}
