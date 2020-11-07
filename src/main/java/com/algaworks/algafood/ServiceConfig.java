package com.algaworks.algafood;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algaworks.algafood.di.notificacao.NotificadorEmail;
import com.algaworks.algafood.di.service.AtivacaoClienteService;

@Configuration
public class ServiceConfig {

//    @Bean
//    public AtivacaoClienteService ativacaoClienteService(NotificadorEmail notificadorEmail) {
//        return new AtivacaoClienteService(notificadorEmail);
//    }

//    @Bean(initMethod = "init", destroyMethod = "destroy")
//    public AtivacaoClienteService ativacaoClienteService() {
//        return new AtivacaoClienteService();
//    }

}
