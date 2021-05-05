package com.algaworks.algafood.domain.service;

import java.io.InputStream;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.FotoProdutoNaoEncontradaException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import com.algaworks.algafood.domain.service.FotoStorageService.Foto;

@Service
public class CatalogoFotoProdutoService {

    @Autowired
    private ProdutoRepository repository;

    @Autowired
    private FotoStorageService service;

    @Transactional
    public FotoProduto salvar(FotoProduto fotoProduto, InputStream arquivo) {
        Long restauranteId = fotoProduto.getProduto().getRestaurante().getId();
        Long produtoId = fotoProduto.getProduto().getId();
        String nomeArquivoExistente = null;
        String novoNomeArquivo = service.gerarNomeArquivo(fotoProduto.getNomeArquivo());

        fotoProduto.setNomeArquivo(novoNomeArquivo);

        Optional<FotoProduto> fotoExistente = repository.findFotoById(restauranteId, produtoId);
        if (fotoExistente.isPresent()) {
            nomeArquivoExistente = fotoExistente.get().getNomeArquivo();
            repository.delete(fotoExistente.get());
        }

        Foto foto = Foto.builder().nomeArquivo(fotoProduto.getNomeArquivo()).contentType(fotoProduto.getContentType()).arquivo(arquivo).build();

        fotoProduto = repository.save(fotoProduto);
        repository.flush();

        service.substituir(foto, nomeArquivoExistente);

        return fotoProduto;
    }

    public FotoProduto buscar(Long restauranteId, Long produtoId) {
        return repository.findFotoById(restauranteId, produtoId)
                .orElseThrow(() -> new FotoProdutoNaoEncontradaException(restauranteId, produtoId));
    }

    @Transactional
    public void excluir(Long restauranteId, Long produtoId) {
        FotoProduto foto = buscar(restauranteId, produtoId);

        repository.delete(foto);
        repository.flush();

        service.remover(foto.getNomeArquivo());
    }

}
