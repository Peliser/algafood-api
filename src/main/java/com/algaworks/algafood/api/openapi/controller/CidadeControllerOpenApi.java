package com.algaworks.algafood.api.openapi.controller;

import java.util.List;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.CidadeDTO;
import com.algaworks.algafood.api.model.input.CidadeInputDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Cidades")
public interface CidadeControllerOpenApi {

    @ApiOperation("Lista as cidades")
    List<CidadeDTO> listar();

    @ApiOperation("Busca uma cidade por ID")
    @ApiResponses({ @ApiResponse(code = 400, message = "ID da cidade inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class) })
    CidadeDTO buscar(@ApiParam(value = "ID de uma cidade", example = "1", required = true) Long id);

    @ApiOperation("Cadastra uma cidade")
    @ApiResponses({ @ApiResponse(code = 201, message = "Cidade cadastrada") })
    CidadeDTO adicionar(
            @ApiParam(name = "corpo", value = "Representação de uma nova cidade", required = true) CidadeInputDTO entity);

    @ApiOperation("Atualiza uma cidade por ID")
    @ApiResponses({ @ApiResponse(code = 200, message = "Cidade atualizada"),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class) })
    CidadeDTO atualizar(@ApiParam(value = "ID da cidade", example = "1", required = true) Long id,
            @ApiParam(name = "corpo", value = "Representação de uma cidade para atualizar", required = true) CidadeInputDTO entity);

    @ApiOperation("Excluir uma cidade por ID")
    @ApiResponses({ @ApiResponse(code = 204, message = "Cidade excluída"),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class) })
    void remover(@ApiParam(value = "ID da cidade", example = "1", required = true) Long id);

}
