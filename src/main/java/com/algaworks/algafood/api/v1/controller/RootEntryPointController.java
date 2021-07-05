package com.algaworks.algafood.api.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.v1.AlgaLinks;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class RootEntryPointController {

    @Autowired
    private AlgaLinks algaLinks;

    @GetMapping
    public RootEntryPointModel root() {
        var root = new RootEntryPointModel();
        root.add(algaLinks.linkToCozinhas("cozinhas"));
        root.add(algaLinks.linkToPedidos("pedidos"));
        root.add(algaLinks.linkToRestaurantes("restaurantes"));
        root.add(algaLinks.linkToGrupos("grupos"));
        root.add(algaLinks.linkToUsuarios("usuarios"));
        root.add(algaLinks.linkToPermissoes("permissoes"));
        root.add(algaLinks.linkToFormasPagamento("formas-pagamento"));
        root.add(algaLinks.linkToEstados("estados"));
        root.add(algaLinks.linkToCidades("cidades"));
        root.add(algaLinks.linkToEstatisticas("estatisticas"));
        return root;
    }

    private static class RootEntryPointModel extends RepresentationModel<RootEntryPointModel> {
    }

}
