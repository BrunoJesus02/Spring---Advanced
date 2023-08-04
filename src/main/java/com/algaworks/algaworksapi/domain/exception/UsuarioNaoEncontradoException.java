package com.algaworks.algaworksapi.domain.exception;

public class UsuarioNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public UsuarioNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public UsuarioNaoEncontradoException(Long grupoId) {
        this(String.format("Não existe um usuário com o código %d", grupoId));
    }
}
