package com.algaworks.algaworksapi.api.exceptionhandler;

import com.algaworks.algaworksapi.domain.exception.EntidadeEmUsoException;
import com.algaworks.algaworksapi.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algaworksapi.domain.exception.NegocioException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import org.apache.commons.lang3.exception.ExceptionUtils;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.databind.JsonMappingException.Reference;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String MSG_ERRO_GENERICA_USUARIO_FINAL = "Ocorreu um erro interno no sistema. Tente novamente e se o problema persistir, " +
            "entre em contato com o administrador do sistema";

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Throwable rootCause = ExceptionUtils.getRootCause(ex);

        if (rootCause instanceof InvalidFormatException) {
            return handleInvalidFormat((InvalidFormatException) rootCause, headers, status, request);
        } else if (rootCause instanceof PropertyBindingException) {
            return handlePropertyBinding((PropertyBindingException) rootCause, headers, status, request);
        }

        String detail = "O corpo da requisição está inválido. Verifique erro de sintaxe.";
        Problem problem = createProblemBuilder(status, ProblemType.MENSAGEM_INCOMPREENSIVEL, detail)
                .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
                .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    private ResponseEntity<Object> handleInvalidFormat(InvalidFormatException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String path = joinPath(ex.getPath());

        String detail = String.format("A propriedade '%s' recebeu valor '%s', " +
                "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.", path, ex.getValue(), ex.getTargetType().getSimpleName());

        Problem problem = createProblemBuilder(status, ProblemType.MENSAGEM_INCOMPREENSIVEL, detail)
                .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    private ResponseEntity<Object> handlePropertyBinding(PropertyBindingException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String path = joinPath(ex.getPath());

        String detail = String.format("A propriedade '%s' não existe. "
                + "Corrija ou remova essa propriedade e tente novamente.", path);

        Problem problem = createProblemBuilder(status, ProblemType.MENSAGEM_INCOMPREENSIVEL, detail)
                .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException ex,
                                                                  WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        Problem problem = createProblemBuilder(status, ProblemType.RECURSO_NAO_ENCONTRADA, ex.getMessage())
                .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
                .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> handleNegocio(NegocioException ex,
                                                    WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        Problem problem = createProblemBuilder(status, ProblemType.ERRO_NEGOCIO, ex.getMessage())
                .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
                .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<?> handleEntidadeEmUso(EntidadeEmUsoException ex,
                                                    WebRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;

        Problem problem = createProblemBuilder(status, ProblemType.ENTIDADE_EM_USO, ex.getMessage())
                .userMessage(ex.getMessage())
                .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                          WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        String detail = String.format("O parâmetro de URL '%s' recebeu o valor "
                + "'%s' que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
                ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

        Problem problem = createProblemBuilder(status, ProblemType.PARAMETRO_INVALIDO, detail)
                .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
                .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleUncaught(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ex.printStackTrace();

        Problem problem = createProblemBuilder(status, ProblemType.ERRO_DE_SISTEMA, MSG_ERRO_GENERICA_USUARIO_FINAL)
                .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
                .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String detail = String.format("O recurso '%s' que você tentou acessar é inexistente.",
                ex.getRequestURL());

        Problem problem = createProblemBuilder(status, ProblemType.RECURSO_NAO_ENCONTRADA, detail)
                .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
                .build();

        return super.handleExceptionInternal(ex, problem, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (body == null) {
            body = Problem.builder()
                    .timestamp(LocalDateTime.now())
                    .title(status.getReasonPhrase())
                    .status(status.value())
                    .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
                    .build();
        } else if (body instanceof String) {
            body = Problem.builder()
                    .timestamp(LocalDateTime.now())
                    .title((String) body)
                    .status(status.value())
                    .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
                    .build();
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType problemType, String detail) {
        return Problem.builder()
                .status(status.value())
                .type(problemType.getUri())
                .title(problemType.getTitle())
                .detail(detail)
                .timestamp(LocalDateTime.now());
    }

    private String joinPath(List<Reference> references) {
        return references.stream()
                .map(ref -> ref.getFieldName())
                .collect(Collectors.joining("."));
    }
}
