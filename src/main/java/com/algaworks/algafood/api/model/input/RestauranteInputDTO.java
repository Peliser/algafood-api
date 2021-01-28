package com.algaworks.algafood.api.model.input;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.algaworks.algafood.core.validation.Multiplo;
import com.algaworks.algafood.core.validation.TaxaFrete;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteInputDTO {

    @NotBlank
    private String nome;

    @NotNull
//  @PositiveOrZero
    @TaxaFrete
    @Multiplo(numero = 5)
    private BigDecimal taxaFrete;

    @Valid
    @NotNull
    private EntityIdInputDTO cozinha;

    @Valid
    @NotNull
    private EnderecoInputDTO endereco;

}
