package com.algaworks.algaworksapi.domain.service;

import com.algaworks.algaworksapi.domain.exception.PedidoNaoEncontradoException;
import com.algaworks.algaworksapi.domain.model.Pedido;
import com.algaworks.algaworksapi.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmissaoPedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public Pedido buscarOuFalhar(Long pedidoId) {
        return pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new PedidoNaoEncontradoException(pedidoId));
    }
}
