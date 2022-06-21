package com.algaworks.algaworksapi.listener;

import com.algaworks.algaworksapi.di.service.ClienteAtivadoEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EmissaoNotaFiscalService {

    @EventListener
    public void clienteAtivadoListener(ClienteAtivadoEvent event) {
        System.out.println("Emitindo nota fical para cliente " + event.getCliente().getNome());
    }
}
