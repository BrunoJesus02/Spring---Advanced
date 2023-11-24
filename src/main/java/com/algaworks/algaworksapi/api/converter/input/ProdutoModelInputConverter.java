package com.algaworks.algaworksapi.api.converter.input;

import com.algaworks.algaworksapi.api.LinksGenerator;
import com.algaworks.algaworksapi.api.controller.RestauranteProdutoController;
import com.algaworks.algaworksapi.api.model.output.ProdutoModel;
import com.algaworks.algaworksapi.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProdutoModelInputConverter
        extends RepresentationModelAssemblerSupport<Produto, ProdutoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LinksGenerator linksGenerator;

    public ProdutoModelInputConverter() {
        super(RestauranteProdutoController.class, ProdutoModel.class);
    }

    @Override
    public ProdutoModel toModel(Produto produto) {
        ProdutoModel produtoModel = createModelWithId(
                produto.getId(), produto, produto.getRestaurante().getId());

        modelMapper.map(produto, produtoModel);

        produtoModel.add(linksGenerator.linkToProdutos(produto.getRestaurante().getId(), "produtos"));

        produtoModel.add(linksGenerator.linkToFotoProduto(
                produto.getRestaurante().getId(), produto.getId(), "foto"));

        return produtoModel;
    }
}
