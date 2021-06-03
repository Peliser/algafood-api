package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.model.EstadoDTO;
import com.algaworks.algafood.api.model.input.EstadoInputDTO;
import com.algaworks.algafood.api.openapi.controller.EstadoControllerOpenApi;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.CadastroEstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController implements EstadoControllerOpenApi {

    @Autowired
    private EstadoRepository repository;

    @Autowired
    private CadastroEstadoService service;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<EstadoDTO> listar() {
        return repository.findAll().stream().map(entity -> modelMapper.map(entity, EstadoDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public EstadoDTO buscar(@PathVariable Long id) {
        return modelMapper.map(service.buscar(id), EstadoDTO.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstadoDTO adicionar(@RequestBody @Valid EstadoInputDTO entity) {
        Estado estado = modelMapper.map(entity, Estado.class);
        estado = service.salvar(estado);
        return modelMapper.map(estado, EstadoDTO.class);
    }

    @PutMapping("/{id}")
    public EstadoDTO atualizar(@PathVariable Long id, @RequestBody @Valid EstadoInputDTO entity) {
        Estado estado = service.buscar(id);
        modelMapper.map(estado, estado);
        estado = service.salvar(estado);
        return modelMapper.map(estado, EstadoDTO.class);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        service.excluir(id);
    }

}
