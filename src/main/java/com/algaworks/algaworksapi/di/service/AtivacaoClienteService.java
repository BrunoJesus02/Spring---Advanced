package com.algaworks.algaworksapi.di.service;

import com.algaworks.algaworksapi.di.modelo.Cliente;
import com.algaworks.algaworksapi.di.notificacao.Notificador;
import com.algaworks.algaworksapi.di.notificacao.NotificadorEmail;
import org.springframework.stereotype.Component;

@Component
public class AtivacaoClienteService {

    private Notificador notificador;

    public AtivacaoClienteService(NotificadorEmail notificador) {
        this.notificador = notificador;

        System.out.println("AtivacaoClienteService " + notificador);
    }

    public void ativar(Cliente cliente) {
        cliente.ativar();

        notificador.notificar(cliente, "Seu cadastro no sistema está ativo!");
    }
}
