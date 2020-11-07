package com.algaworks.algafood;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algaworks.algafood.di.notificacao.NotificadorEmail;
import com.algaworks.algafood.di.service.AtivacaoClienteService;

//@Configuration
public class AlgaConfig {

    @Bean
    public NotificadorEmail notificadorEmail() {
        return new NotificadorEmail();
    }

//    @Bean
//    public AtivacaoClienteService ativacaoClienteService() {
//        return new AtivacaoClienteService(notificadorEmail());
//    }

}
