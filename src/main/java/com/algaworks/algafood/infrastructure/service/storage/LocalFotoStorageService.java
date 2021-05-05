package com.algaworks.algafood.infrastructure.service.storage;

import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;

import com.algaworks.algafood.core.storage.StorageProperties;
import com.algaworks.algafood.domain.service.FotoStorageService;

public class LocalFotoStorageService implements FotoStorageService {

    @Autowired
    private StorageProperties properties;

    private Path getArquivoPath(String nomeArquivo) {
        return properties.getLocal().getDiretorioFotos().resolve(nomeArquivo);
    }

    @Override
    public void salvar(Foto foto) {
        Path arquivoPath = getArquivoPath(foto.getNomeArquivo());
        try {
            FileCopyUtils.copy(foto.getArquivo(), Files.newOutputStream(arquivoPath));
        } catch (Exception e) {
            throw new StorageException("Não foi possível armazenar o arquivo.", e);
        }
    }

    @Override
    public void remover(String nomeArquivo) {
        Path path = getArquivoPath(nomeArquivo);
        try {
            Files.deleteIfExists(path);
        } catch (Exception e) {
            throw new StorageException("Não foi possível excluir o arquivo.", e);
        }
    }

    @Override
    public FotoRecuperada recuperar(String nomeArquivo) {
        try {
            Path path = getArquivoPath(nomeArquivo);

            return FotoRecuperada.builder().arquivo(Files.newInputStream(path)).build();
        } catch (Exception e) {
            throw new StorageException("Não foi possível recuperar arquivo.", e);
        }
    }

}
