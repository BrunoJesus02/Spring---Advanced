package com.algaworks.algaworksapi.di.service;

import com.algaworks.algaworksapi.di.modelo.Cliente;
import com.algaworks.algaworksapi.di.notificacao.NivelUrgencia;
import com.algaworks.algaworksapi.di.notificacao.Notificador;
import com.algaworks.algaworksapi.di.notificacao.TipoDoNotificador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AtivacaoClienteService {

    @TipoDoNotificador(NivelUrgencia.SEM_URGENCIA)
    @Autowired()
    private Notificador notificador;

    public void ativar(Cliente cliente) {
        cliente.ativar();

        notificador.notificar(cliente, "Seu cadastro no sistema está ativo!");
    }

}
