package com.algaworks.algaworksapi.domain.exception;

public class FormaPagamentoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public FormaPagamentoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public FormaPagamentoNaoEncontradoException(Long formaPagamentoId) {
        this(String.format("Não existe uma forma de pagamento com o código %d", formaPagamentoId));
    }
}
