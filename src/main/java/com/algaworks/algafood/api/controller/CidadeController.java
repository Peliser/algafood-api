package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.stream.Collectors;

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

import com.algaworks.algafood.api.model.CidadeDTO;
import com.algaworks.algafood.api.model.input.CidadeInputDTO;
import com.algaworks.algafood.api.openapi.controller.CidadeControllerOpenApi;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;

@RestController
@RequestMapping(value = "/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeController implements CidadeControllerOpenApi {

    @Autowired
    private CidadeRepository repository;

    @Autowired
    private CadastroCidadeService service;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<CidadeDTO> listar() {
        return repository.findAll().stream().map(entity -> modelMapper.map(entity, CidadeDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CidadeDTO buscar(@PathVariable Long id) {
        return modelMapper.map(service.buscar(id), CidadeDTO.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeDTO adicionar(@RequestBody CidadeInputDTO entity) {
        try {
            Cidade cidade = modelMapper.map(entity, Cidade.class);
            cidade = service.salvar(cidade);
            return modelMapper.map(cidade, CidadeDTO.class);
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("/{id}")
    public CidadeDTO atualizar(@PathVariable Long id, @RequestBody CidadeInputDTO entity) {
        try {
            Cidade cidade = service.buscar(id);
            modelMapper.map(entity, cidade);
            cidade = service.salvar(cidade);
            return modelMapper.map(cidade, CidadeDTO.class);
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        service.excluir(id);
    }

}
