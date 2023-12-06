package com.algaworks.algaworksapi.api.v1.controller;

import com.algaworks.algaworksapi.api.v1.converter.input.PedidoModelInputConverter;
import com.algaworks.algaworksapi.api.v1.converter.input.PedidoResumoModelInputConverter;
import com.algaworks.algaworksapi.api.v1.converter.output.PedidoModelOutputConverter;
import com.algaworks.algaworksapi.api.v1.model.input.PedidoInput;
import com.algaworks.algaworksapi.api.v1.model.output.PedidoModel;
import com.algaworks.algaworksapi.api.v1.model.output.PedidoResumoModel;
import com.algaworks.algaworksapi.api.v1.openapi.controller.PedidoControllerOpenApi;
import com.algaworks.algaworksapi.core.data.PageWrapper;
import com.algaworks.algaworksapi.core.data.PageableTranslator;
import com.algaworks.algaworksapi.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algaworksapi.domain.exception.NegocioException;
import com.algaworks.algaworksapi.domain.model.Pedido;
import com.algaworks.algaworksapi.domain.model.Usuario;
import com.algaworks.algaworksapi.domain.repository.PedidoRepository;
import com.algaworks.algaworksapi.domain.filter.PedidoFilter;
import com.algaworks.algaworksapi.domain.service.EmissaoPedidoService;
import com.algaworks.algaworksapi.infrastructure.repository.spec.PedidoSpecs;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/v1/pedidos", produces = MediaType.APPLICATION_JSON_VALUE)
public class PedidoController implements PedidoControllerOpenApi {

    @Autowired
    private PedidoModelInputConverter pedidoModelInputConverter;

    @Autowired
    private PedidoResumoModelInputConverter pedidoResumoModelInputConverter;

    @Autowired
    private PedidoModelOutputConverter pedidoModelOutputConverter;

    @Autowired
    private EmissaoPedidoService pedidoService;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PagedResourcesAssembler<Pedido> pagedResourcesAssembler;

    @GetMapping
    public PagedModel<PedidoResumoModel> pesquisar(PedidoFilter filtro, Pageable pageable) {
        Pageable pageableTraduzido = traduzirPageable(pageable);

        Page<Pedido> pedidosPage = pedidoRepository.findAll(PedidoSpecs.usandoFiltro(filtro), pageableTraduzido);

        pedidosPage = new PageWrapper<>(pedidosPage, pageable);

        return pagedResourcesAssembler.toModel(pedidosPage, pedidoResumoModelInputConverter);
    }

    @GetMapping("/{codigoPedido}")
    public PedidoModel buscar(@PathVariable String codigoPedido) {
        Pedido pedido = pedidoService.buscarOuFalhar(codigoPedido);

        return pedidoModelInputConverter.toModel(pedido);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoModel adicionar(@Valid @RequestBody PedidoInput pedidoInput) {
        try {
            Pedido novoPedido = pedidoModelOutputConverter.toDomainObject(pedidoInput);

            // TODO pegar usuário autenticado
            novoPedido.setCliente(new Usuario());
            novoPedido.getCliente().setId(1L);

            return pedidoModelInputConverter.toModel(pedidoService.emitir(novoPedido));
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    private Pageable traduzirPageable(Pageable pageable) {
        var mapeamento = ImmutableMap.of(
                "codigo", "codigo",
                "restaurante.nome", "restaurante.nome",
                "nomeCliente", "cliente.nome",
                "valorTotal", "valorTotal"
        );

        return PageableTranslator.translate(pageable, mapeamento);
    }
}
