package com.algaworks.algaworksapi.api.v1.converter.input;

import com.algaworks.algaworksapi.api.v1.LinksGenerator;
import com.algaworks.algaworksapi.api.v1.controller.PedidoController;
import com.algaworks.algaworksapi.api.v1.model.output.PedidoResumoModel;
import com.algaworks.algaworksapi.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class PedidoResumoModelInputConverter extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LinksGenerator linksGenerator;

    public PedidoResumoModelInputConverter() {
        super(PedidoController.class, PedidoResumoModel.class);
    }

    @Override
    public PedidoResumoModel toModel(Pedido pedido) {
        PedidoResumoModel pedidoModel = createModelWithId(pedido.getCodigo(), pedido);
        modelMapper.map(pedido, pedidoModel);

        pedidoModel.add(linksGenerator.linkToPedidos("pedidos"));

        pedidoModel.getRestaurante().add(
                linksGenerator.linkToRestaurante(pedido.getRestaurante().getId()));

        pedidoModel.getCliente().add(linksGenerator.linkToUsuario(pedido.getCliente().getId()));

        return pedidoModel;
    }
}
