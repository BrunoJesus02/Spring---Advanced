package com.algaworks.algaworksapi.domain.listener;

import com.algaworks.algaworksapi.domain.event.PedidoCanceladoEvent;
import com.algaworks.algaworksapi.domain.service.EnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class NotificacaoClientePedidoCanceladoListener {

    @Autowired
    private EnvioEmailService envioEmailService;

    @TransactionalEventListener
    public void aoCancelarPedido(PedidoCanceladoEvent event) {
        EnvioEmailService.Mensagem mensagem = EnvioEmailService.Mensagem.builder()
                .assunto(event.getPedido().getRestaurante().getNome() + " - Pedido cancelado")
                .corpo("pedido-cancelado.html")
                .variavel("pedido", event.getPedido())
                .destinatario(event.getPedido().getCliente().getEmail())
                .build();

        envioEmailService.enviar(mensagem);
    }
}
