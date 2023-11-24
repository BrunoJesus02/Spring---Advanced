package com.algaworks.algaworksapi.api.converter.input;

import com.algaworks.algaworksapi.api.LinksGenerator;
import com.algaworks.algaworksapi.api.controller.*;
import com.algaworks.algaworksapi.api.model.output.PedidoModel;
import com.algaworks.algaworksapi.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class PedidoModelInputConverter extends RepresentationModelAssemblerSupport<Pedido, PedidoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LinksGenerator linksGenerator;

    public PedidoModelInputConverter() {
        super(PedidoController.class, PedidoModel.class);
    }

    @Override
    public PedidoModel toModel(Pedido pedido) {
        PedidoModel pedidoModel = createModelWithId(pedido.getCodigo(), pedido);
        modelMapper.map(pedido, pedidoModel);

        pedidoModel.add(linksGenerator.linkToPedidos("pedidos"));

        if(pedido.podeSerConfirmado()) {
            pedidoModel.add(linksGenerator.linkToConfirmacaoPedido(pedido.getCodigo(), "confirmar"));
        }

        if(pedido.podeSerCancelado()) {
            pedidoModel.add(linksGenerator.linkToCancelamentoPedido(pedido.getCodigo(), "cancelar"));
        }

        if(pedido.podeSerEntregue()) {
            pedidoModel.add(linksGenerator.linkToEntregarPedido(pedido.getCodigo(), "entregar"));
        }

        pedidoModel.getRestaurante().add(
                linksGenerator.linkToRestaurante(pedido.getRestaurante().getId()));

        pedidoModel.getCliente().add(
                linksGenerator.linkToUsuario(pedido.getCliente().getId()));

        pedidoModel.getFormaPagamento().add(
                linksGenerator.linkToFormaPagamento(pedido.getFormaPagamento().getId()));

        pedidoModel.getEnderecoEntrega().getCidade().add(
                linksGenerator.linkToCidade(pedido.getEnderecoEntrega().getCidade().getId()));

        pedidoModel.getItens().forEach(item -> {
            item.add(linksGenerator.linkToProduto(
                    pedidoModel.getRestaurante().getId(), item.getProdutoId(), "produto"));
        });

        return pedidoModel;
    }
}
