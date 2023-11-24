package com.algaworks.algaworksapi.api.converter.input;

import com.algaworks.algaworksapi.api.LinksGenerator;
import com.algaworks.algaworksapi.api.controller.CidadeController;
import com.algaworks.algaworksapi.api.controller.PedidoController;
import com.algaworks.algaworksapi.api.controller.RestauranteController;
import com.algaworks.algaworksapi.api.controller.UsuarioController;
import com.algaworks.algaworksapi.api.model.output.PedidoModel;
import com.algaworks.algaworksapi.api.model.output.PedidoResumoModel;
import com.algaworks.algaworksapi.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
