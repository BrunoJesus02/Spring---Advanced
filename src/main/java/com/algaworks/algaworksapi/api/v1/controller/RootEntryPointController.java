package com.algaworks.algaworksapi.api.v1.controller;

import com.algaworks.algaworksapi.api.v1.LinksGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class RootEntryPointController {

    @Autowired
    private LinksGenerator linksGenerator;

    @GetMapping
    public RootEntryPointModel root() {
        RootEntryPointModel rootEntryPointModel = new RootEntryPointModel();
        rootEntryPointModel.add(linksGenerator.linkToCozinhas("cozinhas"));
        rootEntryPointModel.add(linksGenerator.linkToPedidos("pedidos"));
        rootEntryPointModel.add(linksGenerator.linkToRestaurantes("restaurantes"));
        rootEntryPointModel.add(linksGenerator.linkToGrupos("grupos"));
        rootEntryPointModel.add(linksGenerator.linkToUsuarios("usuarios"));
        rootEntryPointModel.add(linksGenerator.linkToPermissoes("permissoes"));
        rootEntryPointModel.add(linksGenerator.linkToFormasPagamento("formas-pagamentos"));
        rootEntryPointModel.add(linksGenerator.linkToEstados("estados"));
        rootEntryPointModel.add(linksGenerator.linkToCidades("cidades"));
        rootEntryPointModel.add(linksGenerator.linkToEstatisticas("estatisticas"));
        return rootEntryPointModel;
    }

    public class RootEntryPointModel extends RepresentationModel<RootEntryPointModel> {

    }
}
