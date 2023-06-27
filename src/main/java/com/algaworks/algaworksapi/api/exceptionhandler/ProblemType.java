package com.algaworks.algaworksapi.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

    RECURSO_NAO_ENCONTRADA("/recurso-nao-encontrado", "Recurso não encontrado"),
    ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
    ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio"),
    MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem incompreesivel"),
    PARAMETRO_INVALIDO("/parametro-invalido", "Parametro inválido"),
    ERRO_DE_SISTEMA("/erro-de-sistema", "Erro de sistema");

    private final String title;
    private final String uri;

    ProblemType(String path, String title) {
        this.uri = "https://algafood.com.br" + path;
        this.title = title;
    }
}
