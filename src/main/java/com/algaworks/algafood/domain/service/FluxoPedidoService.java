package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.repository.PedidoRepository;

@Service
public class FluxoPedidoService {

    @Autowired
    private PedidoRepository repository;

    @Autowired
    private EmissaoPedidoService emissaoPedidoService;

    @Transactional
    public void confirmar(String codigo) {
        Pedido pedido = emissaoPedidoService.buscar(codigo);
        pedido.confirmar();

        repository.save(pedido);
    }

    @Transactional
    public void cancelar(String codigo) {
        Pedido pedido = emissaoPedidoService.buscar(codigo);
        pedido.cancelar();

        repository.save(pedido);
    }

    @Transactional
    public void entregar(String codigo) {
        Pedido pedido = emissaoPedidoService.buscar(codigo);
        pedido.entregar();
    }

}
