package com.algaworks.algaworksapi.di.service;

import com.algaworks.algaworksapi.di.modelo.Cliente;

public class ClienteAtivadoEvent {

    private Cliente cliente;

    public ClienteAtivadoEvent(Cliente cliente) {
        super();
        this.cliente = cliente;
    }

    public Cliente getCliente() {
        return cliente;
    }
}
