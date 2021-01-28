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

import com.algaworks.algafood.api.model.FormaPagamentoDTO;
import com.algaworks.algafood.api.model.input.FormaPagamentoInputDTO;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import com.algaworks.algafood.domain.service.CadastroFormaPagamentoService;

@RestController
@RequestMapping("/formas-pagamento")
public class FormaPagamentoController {

    @Autowired
    private FormaPagamentoRepository repository;

    @Autowired
    private CadastroFormaPagamentoService service;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<FormaPagamentoDTO> listar() {
        return repository.findAll().stream().map(entity -> modelMapper.map(entity, FormaPagamentoDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public FormaPagamentoDTO buscar(@PathVariable final Long id) {
        final FormaPagamento formaPagamento = service.buscar(id);
        return modelMapper.map(formaPagamento, FormaPagamentoDTO.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamentoDTO adicionar(@RequestBody @Valid final FormaPagamentoInputDTO entityDto) {
        FormaPagamento formaPagamento = modelMapper.map(entityDto, FormaPagamento.class);

        formaPagamento = service.salvar(formaPagamento);

        return modelMapper.map(formaPagamento, FormaPagamentoDTO.class);
    }

    @PutMapping("/{id}")
    public FormaPagamentoDTO atualizar(@PathVariable final Long id,
            @RequestBody @Valid final FormaPagamentoInputDTO entityDto) {
        FormaPagamento formaPagamento = service.buscar(id);
        modelMapper.map(entityDto, formaPagamento);
        formaPagamento = service.salvar(formaPagamento);
        return modelMapper.map(formaPagamento, FormaPagamentoDTO.class);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable final Long id) {
        service.excluir(id);
    }

}