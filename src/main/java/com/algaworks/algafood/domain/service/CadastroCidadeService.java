package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;

@Service
public class CadastroCidadeService {

    private static final String MSG_CIDADE_EM_USO = "Cidade de código %d não pode ser removida, pois está em uso";

    @Autowired
    private CidadeRepository repository;

    @Autowired
    private CadastroEstadoService cadastroEstadoService;

    @Transactional
    public Cidade salvar(Cidade entity) {
        Long estadoId = entity.getEstado().getId();
        Estado estado = cadastroEstadoService.buscar(estadoId);
        entity.setEstado(estado);
        return repository.save(entity);
    }

    @Transactional
    public void excluir(Long id) {
        try {
            repository.deleteById(id);
            // https://www.algaworks.com/aulas/2006/corrigindo-bug-de-tratamento-de-exception-de-integridade-de-dados-com-flush-do-jpa
            repository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new CidadeNaoEncontradaException(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_CIDADE_EM_USO, id));
        }
    }

    public Cidade buscar(Long id) {
        return repository.findById(id).orElseThrow(() -> new CidadeNaoEncontradaException(id));
    }

}
