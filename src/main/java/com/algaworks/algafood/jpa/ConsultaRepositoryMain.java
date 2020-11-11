package com.algaworks.algafood.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import com.algaworks.algafood.domain.repository.PermissaoRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

public class ConsultaRepositoryMain {

    public static void main(String[] args) {
        ApplicationContext context = new SpringApplicationBuilder(AlgafoodApiApplication.class)
                .web(WebApplicationType.NONE).run(args);

        CozinhaRepository cozinhaRepository = context.getBean(CozinhaRepository.class);

        Cozinha cozinha1 = new Cozinha();
        cozinha1.setNome("Brasileira");

        Cozinha cozinha2 = new Cozinha();
        cozinha2.setNome("Japonesa");

        cozinha1 = cozinhaRepository.salvar(cozinha1);
        cozinha2 = cozinhaRepository.salvar(cozinha2);

        Cozinha brasileira = cozinhaRepository.buscar(3L);

        System.out.printf("%d - %s\n", brasileira.getId(), brasileira.getNome());

        Cozinha brasileiraMelhor = new Cozinha();
        brasileiraMelhor.setId(brasileira.getId());
        brasileiraMelhor.setNome("Brasileira, a melhor");
        cozinhaRepository.salvar(brasileiraMelhor);
//
//        Cozinha cozinhaRemover = new Cozinha();
//        cozinhaRemover.setId(1L);
//        cozinhaRepository.remover(cozinhaRemover);

        List<Cozinha> cozinhas = cozinhaRepository.listar();
        cozinhas.forEach(cozinha -> System.out.printf("%d - %s\n", cozinha.getId(), cozinha.getNome()));

        RestauranteRepository restauranteRepository = context.getBean(RestauranteRepository.class);

        List<Restaurante> restaurantes = restauranteRepository.listar();
        restaurantes.forEach(restaurante -> System.out.printf("%d - %s - %s - %s\n", restaurante.getId(),
                restaurante.getNome(), restaurante.getTaxaFrete().toString(), restaurante.getCozinha().getNome()));

        FormaPagamentoRepository formaPagamentoRepository = context.getBean(FormaPagamentoRepository.class);

        List<FormaPagamento> formaPagamentos = formaPagamentoRepository.listar();
        formaPagamentos.forEach(entity -> System.out.printf("%d - %s\n", entity.getId(), entity.getDescricao()));

        PermissaoRepository permissaoRepository = context.getBean(PermissaoRepository.class);

        List<Permissao> permissoes = permissaoRepository.listar();
        permissoes.forEach(
                entity -> System.out.printf("%d - %s - %s\n", entity.getId(), entity.getNome(), entity.getDescricao()));

        EstadoRepository estadoRepository = context.getBean(EstadoRepository.class);

        List<Estado> estados = estadoRepository.listar();
        estados.forEach(entity -> System.out.printf("%d - %s\n", entity.getId(), entity.getNome()));

        CidadeRepository cidadeRepository = context.getBean(CidadeRepository.class);

        List<Cidade> cidades = cidadeRepository.listar();
        cidades.forEach(entity -> System.out.printf("%d - %s - %s\n", entity.getId(), entity.getNome(),
                entity.getEstado().getNome()));
    }

}
