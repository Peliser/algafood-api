package com.algaworks.algafood.di.service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.di.modelo.Cliente;
import com.algaworks.algafood.di.notificacao.NivelUrgencia;
import com.algaworks.algafood.di.notificacao.Notificador;
import com.algaworks.algafood.di.notificacao.TipoDoNotificador;

@Component
public class AtivacaoClienteService {

//    @Qualifier("urgente")
    @TipoDoNotificador(NivelUrgencia.NORMAL)
    @Autowired(required = false)
    private Notificador notificador;
//    private List<Notificador> notificadores;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

//    @Autowired
//    public AtivacaoClienteService(Notificador notificador) {
//        this.notificador = notificador;
//
//        System.out.println("AtivacaoClienteService: " + notificador);
//    }

//    public AtivacaoClienteService(String exemplo) {
//
//    }

    public AtivacaoClienteService() {
        System.out.println("AtivacaoClienteService");
    }

    public void ativar(Cliente cliente) {
        cliente.ativar();

        if (notificador != null) {
//            notificador.notificar(cliente, "Seu cadastro no sistema está ativo!");
            eventPublisher.publishEvent(new ClienteAtivadoEvent(cliente));
        } else {
            System.out.println("Não existe notificador, mas cliente foi ativado!");
        }
//        notificadores.forEach(notificador -> notificador.notificar(cliente, "Seu cadastro no sistema está ativo!"));
    }

//    @Autowired
//    public void setNotificador(Notificador notificador) {
//        this.notificador = notificador;
//    }

    @PostConstruct
    public void init() {
        System.out.println("AtivacaoClienteService: INIT");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("AtivacaoClienteService: DESTROY");
    }
}
