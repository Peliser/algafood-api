package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;

@Service
public class CadastroCozinhaService {

    private static final String MSG_COZINHA_EM_USO = "Cozinha de código %d não pode ser removida pois está em uso";

    @Autowired
    private CozinhaRepository repository;

    @Transactional
    public Cozinha salvar(Cozinha entity) {
        return repository.save(entity);
    }

    @Transactional
    public void excluir(Long id) {
        try {
            repository.deleteById(id);
            // https://www.algaworks.com/aulas/2006/corrigindo-bug-de-tratamento-de-exception-de-integridade-de-dados-com-flush-do-jpa
            repository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new CozinhaNaoEncontradaException(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_COZINHA_EM_USO, id));
        }
    }

    public Cozinha buscar(Long id) {
        return repository.findById(id).orElseThrow(() -> new CozinhaNaoEncontradaException(id));
    }

}
