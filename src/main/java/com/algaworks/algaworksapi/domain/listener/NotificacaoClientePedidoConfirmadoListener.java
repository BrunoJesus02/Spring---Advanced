package com.algaworks.algaworksapi.domain.listener;

import com.algaworks.algaworksapi.domain.event.PedidoConfirmadoEvent;
import com.algaworks.algaworksapi.domain.service.EnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import static com.algaworks.algaworksapi.domain.service.EnvioEmailService.*;

@Component
public class NotificacaoClientePedidoConfirmadoListener {

    @Autowired
    private EnvioEmailService envioEmailService;

    @TransactionalEventListener
    public void aoConfirmarPedido(PedidoConfirmadoEvent event) {
        Mensagem mensagem = Mensagem.builder()
                .assunto(event.getPedido().getRestaurante().getNome() + " - Pedido confirmado")
                .corpo("pedido-confirmado.html")
                .variavel("pedido", event.getPedido())
                .destinatario(event.getPedido().getCliente().getEmail())
                .build();

        envioEmailService.enviar(mensagem);
    }
}
