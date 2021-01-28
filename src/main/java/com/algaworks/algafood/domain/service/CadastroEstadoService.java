package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroEstadoService {

    private static final String MSG_ESTADO_EM_USO = "Estado de código %d não pode ser removido pois está em uso";

    @Autowired
    private EstadoRepository repository;

    @Transactional
    public Estado salvar(final Estado entity) {
        return repository.save(entity);
    }

    @Transactional
    public void excluir(final Long id) {
        try {
            repository.deleteById(id);
            // https://www.algaworks.com/aulas/2006/corrigindo-bug-de-tratamento-de-exception-de-integridade-de-dados-com-flush-do-jpa
            repository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new EstadoNaoEncontradoException(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_ESTADO_EM_USO, id));
        }
    }

    public Estado buscar(final Long id) {
        return repository.findById(id).orElseThrow(() -> new EstadoNaoEncontradoException(id));
    }

}
