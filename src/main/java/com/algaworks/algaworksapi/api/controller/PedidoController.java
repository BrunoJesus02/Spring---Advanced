package com.algaworks.algaworksapi.api.controller;

import com.algaworks.algaworksapi.api.converter.input.PedidoModelInputConverter;
import com.algaworks.algaworksapi.api.converter.input.PedidoResumoModelInputConverter;
import com.algaworks.algaworksapi.api.converter.output.PedidoModelOutputConverter;
import com.algaworks.algaworksapi.api.model.input.PedidoInput;
import com.algaworks.algaworksapi.api.model.output.PedidoModel;
import com.algaworks.algaworksapi.api.model.output.PedidoResumoModel;
import com.algaworks.algaworksapi.core.data.PageableTranslator;
import com.algaworks.algaworksapi.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algaworksapi.domain.exception.NegocioException;
import com.algaworks.algaworksapi.domain.model.Pedido;
import com.algaworks.algaworksapi.domain.model.Usuario;
import com.algaworks.algaworksapi.domain.repository.PedidoRepository;
import com.algaworks.algaworksapi.domain.repository.filter.PedidoFilter;
import com.algaworks.algaworksapi.domain.service.EmissaoPedidoService;
import com.algaworks.algaworksapi.infrastructure.repository.spec.PedidoSpecs;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {

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

    @GetMapping
    public Page<PedidoResumoModel> pesquisar(PedidoFilter filtro, Pageable pageable) {
        pageable = traduzirPageable(pageable);

        Page<Pedido> pedidosPage = pedidoRepository.findAll(PedidoSpecs.usandoFiltro(filtro), pageable);
        List<PedidoResumoModel> pedidosPageModel = pedidoResumoModelInputConverter.toCollectionModel(pedidosPage.getContent());

        return new PageImpl<>(pedidosPageModel, pageable, pedidosPage.getTotalElements());
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
