package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.GrupoInputDisassembler;
import com.algaworks.algafood.api.assembler.GrupoModelAssembler;
import com.algaworks.algafood.api.model.GrupoModel;
import com.algaworks.algafood.api.model.input.GrupoInput;
import com.algaworks.algafood.api.openapi.controller.GrupoControllerOpenApi;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.repository.GrupoRepository;
import com.algaworks.algafood.domain.service.CadastroGrupoService;

@RestController
@RequestMapping(value = "/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoController implements GrupoControllerOpenApi {

    @Autowired
    private GrupoRepository repository;

    @Autowired
    private CadastroGrupoService service;

    @Autowired
    private GrupoModelAssembler grupoModelAssembler;

    @Autowired
    private GrupoInputDisassembler grupoInputDisassembler;

    @Override
    @GetMapping
    public CollectionModel<GrupoModel> listar() {
        List<Grupo> todosGrupos = repository.findAll();

        return grupoModelAssembler.toCollectionModel(todosGrupos);
    }

    @Override
    @GetMapping("/{grupoId}")
    public GrupoModel buscar(@PathVariable Long grupoId) {
        Grupo grupo = service.buscar(grupoId);

        return grupoModelAssembler.toModel(grupo);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoModel adicionar(@RequestBody @Valid GrupoInput grupoInput) {
        Grupo grupo = grupoInputDisassembler.toDomainObject(grupoInput);

        grupo = service.salvar(grupo);

        return grupoModelAssembler.toModel(grupo);
    }

    @Override
    @PutMapping("/{grupoId}")
    public GrupoModel atualizar(@PathVariable Long grupoId, @RequestBody @Valid GrupoInput grupoInput) {
        Grupo grupoAtual = service.buscar(grupoId);

        grupoInputDisassembler.copyToDomainObject(grupoInput, grupoAtual);

        grupoAtual = service.salvar(grupoAtual);

        return grupoModelAssembler.toModel(grupoAtual);
    }

    @Override
    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long grupoId) {
        service.excluir(grupoId);
    }

}