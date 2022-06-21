package com.algaworks.algaworksapi.di.notificacao;

import com.algaworks.algaworksapi.di.modelo.Cliente;

public interface Notificador {
    void notificar(Cliente cliente, String mensagem);
}
