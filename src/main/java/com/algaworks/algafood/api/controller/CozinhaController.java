package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

import com.algaworks.algafood.api.model.CozinhaDTO;
import com.algaworks.algafood.api.model.CozinhasXmlWrapper;
import com.algaworks.algafood.api.model.input.CozinhaInputDTO;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaRepository repository;

    @Autowired
    private CadastroCozinhaService service;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public Page<CozinhaDTO> listar(@PageableDefault(size = 2) Pageable pageable) {
        Page<Cozinha> cozinhasPage = repository.findAll(pageable);
        List<CozinhaDTO> cozinhas = cozinhasPage.getContent().stream()
                .map(entity -> modelMapper.map(entity, CozinhaDTO.class)).collect(Collectors.toList());
        return new PageImpl<>(cozinhas, pageable, cozinhasPage.getTotalElements());
    }

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public CozinhasXmlWrapper listarXml() {
        return new CozinhasXmlWrapper(repository.findAll());
    }

    @GetMapping("/{id}")
    public CozinhaDTO buscar(@PathVariable Long id) {
        return modelMapper.map(service.buscar(id), CozinhaDTO.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaDTO adicionar(@RequestBody @Valid CozinhaInputDTO entity) {
        Cozinha cozinha = modelMapper.map(entity, Cozinha.class);
        cozinha = service.salvar(cozinha);
        return modelMapper.map(cozinha, CozinhaDTO.class);
    }

    @PutMapping("/{id}")
    public CozinhaDTO atualizar(@PathVariable Long id, @RequestBody @Valid CozinhaInputDTO entity) {
        Cozinha cozinha = service.buscar(id);
        modelMapper.map(entity, cozinha);
        cozinha = service.salvar(cozinha);
        return modelMapper.map(cozinha, CozinhaDTO.class);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        service.excluir(id);
    }

}
