package com.algaworks.algaworksapi.api.converter.output;

import com.algaworks.algaworksapi.api.model.input.PedidoInput;
import com.algaworks.algaworksapi.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PedidoModelOutputConverter {

    @Autowired
    private ModelMapper modelMapper;

    public Pedido toDomainObject(PedidoInput pedidoInput) {
        return modelMapper.map(pedidoInput, Pedido.class);
    }

    public void copyToDomainObject(Pedido pedido, PedidoInput pedidoInput) {
        modelMapper.map(pedidoInput, pedido);
    }
}
