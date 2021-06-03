package com.algaworks.algafood.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@ApiModel("Problema")
@JsonInclude(Include.NON_NULL)
@Getter
@Builder
public class Problem {

    @ApiModelProperty(example = "400", position = 1)
    private Integer status;

    @ApiModelProperty(example = "https://algafood.com.br/erro-de-sistema", position = 5)
    private String type;

    @ApiModelProperty(example = "Erro de sistema", position = 10)
    private String title;

    @ApiModelProperty(example = "Ocorreu um erro interno inesperado no sistema. Tente novamente e se o problema persistir, entre em contato com o administrador do sistema.", position = 15)
    private String detail;

    @ApiModelProperty(example = "Ocorreu um erro interno inesperado no sistema. Tente novamente e se o problema persistir, entre em contato com o administrador do sistema.", position = 20)
    private String userMessage;

    @ApiModelProperty(example = "2021-05-23T20:16:47.2931276Z", position = 25)
    private OffsetDateTime timestamp;

    @ApiModelProperty(value = "Campos que geraram o(s) erro(s)", position = 30)
    private List<Error> errors;

    @ApiModel("ObjetoProblema")
    @Getter
    @Builder
    public static class Error {

        @ApiModelProperty(example = "preco")
        private String name;

        @ApiModelProperty(example = "O preço é obrigatório")
        private String userMessage;

    }

}
