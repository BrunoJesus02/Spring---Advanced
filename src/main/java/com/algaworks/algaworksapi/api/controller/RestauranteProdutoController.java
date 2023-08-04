package com.algaworks.algaworksapi.api.controller;

import com.algaworks.algaworksapi.api.converter.input.ProdutoModelInputConverter;
import com.algaworks.algaworksapi.api.converter.output.ProdutoModelOutputConverter;
import com.algaworks.algaworksapi.api.model.ProdutoModel;
import com.algaworks.algaworksapi.api.model.input.ProdutoInput;
import com.algaworks.algaworksapi.domain.model.Produto;
import com.algaworks.algaworksapi.domain.model.Restaurante;
import com.algaworks.algaworksapi.domain.repository.ProdutoRepository;
import com.algaworks.algaworksapi.domain.service.CadastroProdutoService;
import com.algaworks.algaworksapi.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {

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

    @GetMapping
    public List<ProdutoModel> listar(@PathVariable Long restauranteId) {
        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);

        List<Produto> todosProdutos = produtoRepository.findByRestaurante(restaurante);

        return produtoModelInputConverter.toCollectionModel(todosProdutos);
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
