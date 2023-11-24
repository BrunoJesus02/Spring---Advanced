package com.algaworks.algaworksapi.api.controller;

import com.algaworks.algaworksapi.api.converter.input.PedidoModelInputConverter;
import com.algaworks.algaworksapi.api.converter.input.PedidoResumoModelInputConverter;
import com.algaworks.algaworksapi.api.converter.output.PedidoModelOutputConverter;
import com.algaworks.algaworksapi.api.openapi.controller.FluxoPedidoControllerOpenApi;
import com.algaworks.algaworksapi.domain.repository.PedidoRepository;
import com.algaworks.algaworksapi.domain.service.FluxoPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/pedidos/{codigoPedido}", produces = MediaType.APPLICATION_JSON_VALUE)
public class FluxoPedidoController implements FluxoPedidoControllerOpenApi {

    @Autowired
    private PedidoModelInputConverter pedidoModelInputConverter;

    @Autowired
    private PedidoResumoModelInputConverter pedidoResumoModelInputConverter;

    @Autowired
    private PedidoModelOutputConverter pedidoModelOutputConverter;

    @Autowired
    private FluxoPedidoService pedidoService;

    @Autowired
    private PedidoRepository pedidoRepository;

    @PutMapping("/confirmacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> confirmarPedido(@PathVariable String codigoPedido) {
        pedidoService.confirmar(codigoPedido);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/entrega")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> entregarPedido(@PathVariable String codigoPedido) {
        pedidoService.entregar(codigoPedido);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/cancelamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> cancelarPedido(@PathVariable String codigoPedido) {
        pedidoService.cancelar(codigoPedido);

        return ResponseEntity.noContent().build();
    }
}
