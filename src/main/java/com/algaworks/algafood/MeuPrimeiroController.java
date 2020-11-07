package com.algaworks.algafood;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.algaworks.algafood.di.modelo.Cliente;
import com.algaworks.algafood.di.service.AtivacaoClienteService;

@Controller
@RequestMapping("/hello")
public class MeuPrimeiroController {

    private AtivacaoClienteService ativacaoClienteService;

    public MeuPrimeiroController(AtivacaoClienteService ativacaoClienteService) {
        super();
        this.ativacaoClienteService = ativacaoClienteService;
        
        System.out.println("MeuPrimeiroController: " + ativacaoClienteService);
    }

    @GetMapping
    @ResponseBody
    public String hello() {
        Cliente joao = new Cliente("João", "joao@gmail.com", "666");
        ativacaoClienteService.ativar(joao);
        return "Hello world!";
    }

}
