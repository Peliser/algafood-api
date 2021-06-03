package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.algaworks.algafood.api.model.GrupoDTO;
import com.algaworks.algafood.api.model.input.GrupoInputDTO;
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
    private ModelMapper modelMapper;

    @GetMapping
    public List<GrupoDTO> listar() {
        return repository.findAll().stream().map(entity -> modelMapper.map(entity, GrupoDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public GrupoDTO buscar(@PathVariable Long id) {
        Grupo grupo = service.buscar(id);
        return modelMapper.map(grupo, GrupoDTO.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoDTO adicionar(@RequestBody @Valid GrupoInputDTO entityDto) {
        Grupo grupo = modelMapper.map(entityDto, Grupo.class);
        grupo = service.salvar(grupo);
        return modelMapper.map(grupo, GrupoDTO.class);
    }

    @PutMapping("/{id}")
    public GrupoDTO atualizar(@PathVariable Long id, @RequestBody @Valid GrupoInputDTO entityDto) {
        Grupo grupo = service.buscar(id);
        modelMapper.map(entityDto, grupo);
        grupo = service.salvar(grupo);
        return modelMapper.map(grupo, GrupoDTO.class);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        service.excluir(id);
    }

}