package com.algaworks.algaworksapi.api.v1.converter.input;

import com.algaworks.algaworksapi.api.v1.LinksGenerator;
import com.algaworks.algaworksapi.api.v1.controller.RestauranteProdutoController;
import com.algaworks.algaworksapi.api.v1.model.output.ProdutoModel;
import com.algaworks.algaworksapi.core.security.AlgaSecurity;
import com.algaworks.algaworksapi.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ProdutoModelInputConverter
        extends RepresentationModelAssemblerSupport<Produto, ProdutoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LinksGenerator linksGenerator;

    @Autowired
    private AlgaSecurity algaSecurity;

    public ProdutoModelInputConverter() {
        super(RestauranteProdutoController.class, ProdutoModel.class);
    }

    @Override
    public ProdutoModel toModel(Produto produto) {
        ProdutoModel produtoModel = createModelWithId(
                produto.getId(), produto, produto.getRestaurante().getId());

        modelMapper.map(produto, produtoModel);


        if (algaSecurity.podeConsultarRestaurantes()) {
            produtoModel.add(linksGenerator.linkToProdutos(produto.getRestaurante().getId(), "produtos"));

            produtoModel.add(linksGenerator.linkToFotoProduto(
                    produto.getRestaurante().getId(), produto.getId(), "foto"));
        }

        return produtoModel;
    }
}
