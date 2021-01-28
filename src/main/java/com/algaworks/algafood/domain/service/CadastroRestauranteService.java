package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

    @Autowired
    private RestauranteRepository repository;

    @Autowired
    private CadastroCozinhaService cadastroCozinhaService;

    @Autowired
    private CadastroCidadeService cadastroCidadeService;

    @Autowired
    private CadastroFormaPagamentoService cadastroRestauranteService;

    @Autowired
    private CadastroUsuarioService cadastroUsuario;

    @Transactional
    public Restaurante salvar(Restaurante entity) {
        Long cozinhaId = entity.getCozinha().getId();
        Cozinha cozinha = cadastroCozinhaService.buscar(cozinhaId);
        entity.setCozinha(cozinha);

        Long cidadeId = entity.getEndereco().getCidade().getId();
        Cidade cidade = cadastroCidadeService.buscar(cidadeId);
        entity.getEndereco().setCidade(cidade);

        return repository.save(entity);
    }

    public Restaurante buscar(Long id) {
        return repository.findById(id).orElseThrow(() -> new RestauranteNaoEncontradoException(id));
    }

    @Transactional
    public void ativar(Long id) {
        Restaurante restaurante = buscar(id);
        restaurante.ativar();
    }

    @Transactional
    public void ativar(List<Long> ids) {
        ids.forEach(this::ativar);
    }

    @Transactional
    public void inativar(Long id) {
        Restaurante restaurante = buscar(id);
        restaurante.inativar();
    }

    @Transactional
    public void inativar(List<Long> ids) {
        ids.forEach(this::inativar);
    }

    @Transactional
    public void associarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
        Restaurante restaurante = buscar(restauranteId);
        FormaPagamento formaPagamento = cadastroRestauranteService.buscar(formaPagamentoId);
        restaurante.associarFormaPagamento(formaPagamento);
    }

    @Transactional
    public void desassociarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
        Restaurante restaurante = buscar(restauranteId);
        FormaPagamento formaPagamento = cadastroRestauranteService.buscar(formaPagamentoId);
        restaurante.desassociarFormaPagamento(formaPagamento);
    }

    @Transactional
    public void abrir(Long restauranteId) {
        Restaurante restauranteAtual = buscar(restauranteId);
        restauranteAtual.abrir();
    }

    @Transactional
    public void fechar(Long restauranteId) {
        Restaurante restauranteAtual = buscar(restauranteId);
        restauranteAtual.fechar();
    }

    @Transactional
    public void desassociarResponsavel(Long restauranteId, Long usuarioId) {
        Restaurante restaurante = buscar(restauranteId);
        Usuario usuario = cadastroUsuario.buscar(usuarioId);
        restaurante.removerResponsavel(usuario);
    }

    @Transactional
    public void associarResponsavel(Long restauranteId, Long usuarioId) {
        Restaurante restaurante = buscar(restauranteId);
        Usuario usuario = cadastroUsuario.buscar(usuarioId);
        restaurante.adicionarResponsavel(usuario);
    }

}
