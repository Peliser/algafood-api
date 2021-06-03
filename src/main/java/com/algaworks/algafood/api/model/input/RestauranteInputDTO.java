package com.algaworks.algafood.api.model.input;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.algaworks.algafood.core.validation.Multiplo;
import com.algaworks.algafood.core.validation.TaxaFrete;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteInputDTO {

    @ApiModelProperty(example = "Thai Gourmet", required = true)
    @NotBlank
    private String nome;

    @ApiModelProperty(example = "12.00", required = true)
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
