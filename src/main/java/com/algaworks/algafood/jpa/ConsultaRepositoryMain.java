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

        cozinha1 = cozinhaRepository.save(cozinha1);
        cozinha2 = cozinhaRepository.save(cozinha2);

        Cozinha brasileira = cozinhaRepository.findById(3L).orElseThrow();

        System.out.printf("%d - %s\n", brasileira.getId(), brasileira.getNome());
        
        List<Cozinha> cozinhas = cozinhaRepository.findTodasByNomeContaining("Tailandesa");

        cozinhas.forEach(cozinha -> System.out.printf("findBy: %d - %s\n", cozinha.getId(), cozinha.getNome()));

        Cozinha brasileiraMelhor = new Cozinha();
        brasileiraMelhor.setId(brasileira.getId());
        brasileiraMelhor.setNome("Brasileira, a melhor");
        cozinhaRepository.save(brasileiraMelhor);
//
//        Cozinha cozinhaRemover = new Cozinha();
//        cozinhaRemover.setId(1L);
//        cozinhaRepository.remover(cozinhaRemover);

        cozinhas = cozinhaRepository.findAll();
        cozinhas.forEach(cozinha -> System.out.printf("%d - %s\n", cozinha.getId(), cozinha.getNome()));

        RestauranteRepository restauranteRepository = context.getBean(RestauranteRepository.class);

        List<Restaurante> restaurantes = restauranteRepository.findAll();
        restaurantes.forEach(restaurante -> System.out.printf("%d - %s - %s - %s\n", restaurante.getId(),
                restaurante.getNome(), restaurante.getTaxaFrete().toString(), restaurante.getCozinha().getNome()));

        FormaPagamentoRepository formaPagamentoRepository = context.getBean(FormaPagamentoRepository.class);

        List<FormaPagamento> formaPagamentos = formaPagamentoRepository.findAll();
        formaPagamentos.forEach(entity -> System.out.printf("%d - %s\n", entity.getId(), entity.getDescricao()));

        PermissaoRepository permissaoRepository = context.getBean(PermissaoRepository.class);

        List<Permissao> permissoes = permissaoRepository.findAll();
        permissoes.forEach(
                entity -> System.out.printf("%d - %s - %s\n", entity.getId(), entity.getNome(), entity.getDescricao()));

        EstadoRepository estadoRepository = context.getBean(EstadoRepository.class);

        List<Estado> estados = estadoRepository.findAll();
        estados.forEach(entity -> System.out.printf("%d - %s\n", entity.getId(), entity.getNome()));

        CidadeRepository cidadeRepository = context.getBean(CidadeRepository.class);

        List<Cidade> cidades = cidadeRepository.findAll();
        cidades.forEach(entity -> System.out.printf("%d - %s - %s\n", entity.getId(), entity.getNome(),
                entity.getEstado().getNome()));
    }

}
