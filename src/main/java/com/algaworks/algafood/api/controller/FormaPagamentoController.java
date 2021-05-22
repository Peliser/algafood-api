package com.algaworks.algafood.api.controller;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

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
    public ResponseEntity<List<FormaPagamentoDTO>> listar(ServletWebRequest request) {
        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());

        String eTag = "0";
        OffsetDateTime maxDataAtualizacao = repository.getDataUltimaAtualizacao();

        if (maxDataAtualizacao != null) {
            eTag = String.valueOf(maxDataAtualizacao.toEpochSecond());
        }

        if (request.checkNotModified(eTag)) {
            return null;
        }

        List<FormaPagamentoDTO> formasPagamentos = repository.findAll().stream()
                .map(entity -> modelMapper.map(entity, FormaPagamentoDTO.class)).collect(Collectors.toList());
        CacheControl cache = CacheControl
                // .cachePublic() // Cache padrão, pode ser cacheada pelo cliente e por um proxy
                // (por exemplo).
                // .cachePrivate() // Cache privado, não permitindo que a resposta seja cacheada
                // por um proxy (ou algo do tipo).
                // .noCache() // Permite o cache, mas o client sempre deve fazer a request para
                // validar o cache no servidor.
                // .noStore(); // Não permite o cache.
                .maxAge(10, TimeUnit.SECONDS);
        return ResponseEntity.ok().cacheControl(cache).eTag(eTag).body(formasPagamentos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FormaPagamentoDTO> buscar(@PathVariable Long id, ServletWebRequest request) {
        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());

        String eTag = "0";
        OffsetDateTime dataAtualizacao = repository.getDataAtualizacaoById(id);

        if (dataAtualizacao != null) {
            eTag = String.valueOf(dataAtualizacao.toEpochSecond());
        }

        if (request.checkNotModified(eTag)) {
            return null;
        }

        final FormaPagamento formaPagamento = service.buscar(id);
        final FormaPagamentoDTO formaPagamentoDto = modelMapper.map(formaPagamento, FormaPagamentoDTO.class);
        return ResponseEntity.ok().cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS)).body(formaPagamentoDto);
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