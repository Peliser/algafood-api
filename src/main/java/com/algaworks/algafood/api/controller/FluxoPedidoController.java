package com.algaworks.algafood.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.openapi.controller.FluxoPedidoControllerOpenApi;
import com.algaworks.algafood.domain.service.FluxoPedidoService;

@RestController
@RequestMapping("/pedidos/{codigo}")
public class FluxoPedidoController implements FluxoPedidoControllerOpenApi {

    @Autowired
    private FluxoPedidoService service;

    @PutMapping("/confirmacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void confirmar(@PathVariable String codigo) {
        service.confirmar(codigo);
    }

    @PutMapping("/cancelamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelar(@PathVariable String codigo) {
        service.cancelar(codigo);
    }

    @PutMapping("/entrega")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void entregar(@PathVariable String codigo) {
        service.entregar(codigo);
    }

}
