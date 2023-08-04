package com.algaworks.algaworksapi.api.controller;

import com.algaworks.algaworksapi.api.converter.input.PedidoModelInputConverter;
import com.algaworks.algaworksapi.api.model.PedidoModel;
import com.algaworks.algaworksapi.domain.model.Pedido;
import com.algaworks.algaworksapi.domain.repository.PedidoRepository;
import com.algaworks.algaworksapi.domain.service.EmissaoPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {

    @Autowired
    private PedidoModelInputConverter pedidoModelInputConverter;

    @Autowired
    private EmissaoPedidoService pedidoService;

    @Autowired
    private PedidoRepository pedidoRepository;

    @GetMapping
    public List<PedidoModel> listar() {
        List<Pedido> todosPedidos = pedidoRepository.findAll();

        return pedidoModelInputConverter.toCollectionModel(todosPedidos);
    }

    @GetMapping("/{pedidoId}")
    public PedidoModel buscar(@PathVariable Long pedidoId) {
        Pedido pedido = pedidoService.buscarOuFalhar(pedidoId);

        return pedidoModelInputConverter.toModel(pedido);
    }
}
