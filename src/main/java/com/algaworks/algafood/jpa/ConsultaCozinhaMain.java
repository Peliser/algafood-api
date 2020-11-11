package com.algaworks.algafood.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Cozinha;

public class ConsultaCozinhaMain {

    public static void main(String[] args) {
        ApplicationContext context = new SpringApplicationBuilder(AlgafoodApiApplication.class)
                .web(WebApplicationType.NONE).run(args);

        CadastroCozinha cadastroCozinha = context.getBean(CadastroCozinha.class);

        Cozinha cozinha1 = new Cozinha();
        cozinha1.setNome("Brasileira");

        Cozinha cozinha2 = new Cozinha();
        cozinha2.setNome("Japonesa");

        cozinha1 = cadastroCozinha.salvar(cozinha1);
        cozinha2 = cadastroCozinha.salvar(cozinha2);

        Cozinha brasileira = cadastroCozinha.buscar(3L);

        System.out.printf("%d - %s\n", brasileira.getId(), brasileira.getNome());

        Cozinha brasileiraMelhor = new Cozinha();
        brasileiraMelhor.setId(brasileira.getId());
        brasileiraMelhor.setNome("Brasileira, a melhor");
        cadastroCozinha.salvar(brasileiraMelhor);

        System.out.printf("%d - %s\n", brasileiraMelhor.getId(), brasileiraMelhor.getNome());

        Cozinha cozinhaRemover = new Cozinha();
        cozinhaRemover.setId(1L);
        cadastroCozinha.remover(cozinhaRemover);

        List<Cozinha> cozinhas = cadastroCozinha.listar();
        cozinhas.forEach(cozinha -> System.out.printf("%d - %s\n", +cozinha.getId(), cozinha.getNome()));
    }

}
