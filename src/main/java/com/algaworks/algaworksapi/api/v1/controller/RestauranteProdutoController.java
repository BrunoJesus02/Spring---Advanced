package com.algaworks.algaworksapi.api.v1.controller;

import com.algaworks.algaworksapi.api.v1.LinksGenerator;
import com.algaworks.algaworksapi.api.v1.converter.input.ProdutoModelInputConverter;
import com.algaworks.algaworksapi.api.v1.converter.output.ProdutoModelOutputConverter;
import com.algaworks.algaworksapi.api.v1.model.output.ProdutoModel;
import com.algaworks.algaworksapi.api.v1.model.input.ProdutoInput;
import com.algaworks.algaworksapi.api.v1.openapi.controller.RestauranteProdutoControllerOpenApi;
import com.algaworks.algaworksapi.domain.model.Produto;
import com.algaworks.algaworksapi.domain.model.Restaurante;
import com.algaworks.algaworksapi.domain.repository.ProdutoRepository;
import com.algaworks.algaworksapi.domain.service.CadastroProdutoService;
import com.algaworks.algaworksapi.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/produtos", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteProdutoController implements RestauranteProdutoControllerOpenApi {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CadastroProdutoService cadastroProduto;

    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    @Autowired
    private ProdutoModelInputConverter produtoModelInputConverter;

    @Autowired
    private ProdutoModelOutputConverter produtoModelOutputConverter;

    @Autowired
    private LinksGenerator linksGenerator;

    @GetMapping
    public CollectionModel<ProdutoModel> listar(@PathVariable Long restauranteId,
                                                @RequestParam(required = false) Boolean incluirInativos) {
        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);
        List<Produto> todosProdutos = null;

        if (incluirInativos) {
            todosProdutos = produtoRepository.findTodosByRestaurante(restaurante);
        } else {
            todosProdutos = produtoRepository.findAtivosByRestaurante(restaurante);
        }

        return produtoModelInputConverter.toCollectionModel(todosProdutos)
                .add(linksGenerator.linkToProdutos(restauranteId));
    }

    @GetMapping("/{produtoId}")
    public ProdutoModel buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        Produto produto = cadastroProduto.buscarOuFalhar(restauranteId, produtoId);

        return produtoModelInputConverter.toModel(produto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoModel adicionar(@PathVariable Long restauranteId,
                                  @RequestBody @Valid ProdutoInput produtoInput) {
        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);

        Produto produto = produtoModelOutputConverter.toDomainObject(produtoInput);
        produto.setRestaurante(restaurante);

        produto = cadastroProduto.salvar(produto);

        return produtoModelInputConverter.toModel(produto);
    }

    @PutMapping("/{produtoId}")
    public ProdutoModel atualizar(@PathVariable Long restauranteId, @PathVariable Long produtoId,
                                  @RequestBody @Valid ProdutoInput produtoInput) {
        Produto produtoAtual = cadastroProduto.buscarOuFalhar(restauranteId, produtoId);

        produtoModelOutputConverter.copyToDomainObject(produtoInput, produtoAtual);

        produtoAtual = cadastroProduto.salvar(produtoAtual);

        return produtoModelInputConverter.toModel(produtoAtual);
    }
}
