package com.algaworks.algaworksapi.api.controller;

import com.algaworks.algaworksapi.api.converter.input.PedidoModelInputConverter;
import com.algaworks.algaworksapi.api.converter.input.PedidoResumoModelInputConverter;
import com.algaworks.algaworksapi.api.converter.output.PedidoModelOutputConverter;
import com.algaworks.algaworksapi.api.model.input.PedidoInput;
import com.algaworks.algaworksapi.api.model.output.PedidoModel;
import com.algaworks.algaworksapi.api.model.output.PedidoResumoModel;
import com.algaworks.algaworksapi.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algaworksapi.domain.exception.NegocioException;
import com.algaworks.algaworksapi.domain.model.Pedido;
import com.algaworks.algaworksapi.domain.model.Usuario;
import com.algaworks.algaworksapi.domain.repository.PedidoRepository;
import com.algaworks.algaworksapi.domain.service.EmissaoPedidoService;
import com.algaworks.algaworksapi.domain.service.FluxoPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/pedidos/{pedidoId}")
public class FluxoPedidoController {

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
    public void confirmarPedido(@PathVariable Long pedidoId) {
        pedidoService.confirmar(pedidoId);
    }

    @PutMapping("/entrega")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void entregarPedido(@PathVariable Long pedidoId) {
        pedidoService.entregar(pedidoId);
    }

    @PutMapping("/cancelamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelarPedido(@PathVariable Long pedidoId) {
        pedidoService.cancelar(pedidoId);
    }
}
