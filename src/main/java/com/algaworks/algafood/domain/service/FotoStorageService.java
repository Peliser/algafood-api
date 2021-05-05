package com.algaworks.algafood.domain.service;

import java.io.InputStream;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

public interface FotoStorageService {

    default String gerarNomeArquivo(String nomeOriginal) {
        return String.join("_", UUID.randomUUID().toString(), nomeOriginal);
    }

    default void substituir(Foto foto, String nomeArquivoAntigo) {
        this.salvar(foto);
        if (nomeArquivoAntigo != null) {
            this.remover(nomeArquivoAntigo);
        }
    }

    void salvar(Foto foto);

    void remover(String nomeArquivo);

    FotoRecuperada recuperar(String nomeArquivo);

    @Builder
    @Getter
    class Foto {

        private String nomeArquivo;

        private String contentType;

        private InputStream arquivo;

    }

    @Builder
    @Getter
    class FotoRecuperada {

        private InputStream arquivo;

        private String url;

        public boolean temUrl() {
            return url != null;
        }

        public boolean temArquivo() {
            return arquivo != null;
        }

    }

}
