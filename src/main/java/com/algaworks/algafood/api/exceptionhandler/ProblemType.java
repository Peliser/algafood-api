package com.algaworks.algafood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

    ACESSO_NEGADO("/acesso-negado", "Acesso negado"),

    DADOS_INVALIDOS("/dados-invalidos", "Dados inválidos"),

    ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),

    ERRO_DE_SISTEMA("/erro-de-sistema", "Erro de sistema"),

    ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio"),

    MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem incompreensível"),

    PARAMETRO_INVALIDO("/parametro-invalido", "Parâmetro inválido"),

    RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado");

    private String uri;
    private String title;

    private ProblemType(String path, String title) {
        this.uri = "https://algafood.com.br" + path;
        this.title = title;
    }

}
