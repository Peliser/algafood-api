package com.algaworks.algafood.api.model.input;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoInput {

    @Valid
    @NotNull
    private EntityIdInput restaurante;

    @Valid
    @NotNull
    private EnderecoInput enderecoEntrega;

    @Valid
    @NotNull
    private EntityIdInput formaPagamento;

    @Valid
    @Size(min = 1)
    @NotNull
    private List<ItemPedidoInput> itens;

}
