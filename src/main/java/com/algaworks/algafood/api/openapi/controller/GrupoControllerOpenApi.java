package com.algaworks.algafood.api.openapi.controller;

import java.util.List;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.GrupoDTO;
import com.algaworks.algafood.api.model.input.GrupoInputDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Grupos")
public interface GrupoControllerOpenApi {

    @ApiOperation("Lista os grupos")
    List<GrupoDTO> listar();

    @ApiOperation("Busca um grupo por ID")
    @ApiResponses({ @ApiResponse(code = 400, message = "ID da grupo inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class) })
    GrupoDTO buscar(@ApiParam(value = "ID de um grupo", example = "1", required = true) Long grupoId);

    @ApiOperation("Cadastra um grupo")
    @ApiResponses({ @ApiResponse(code = 201, message = "Grupo cadastrado"), })
    GrupoDTO adicionar(
            @ApiParam(name = "corpo", value = "Representação de um novo grupo", required = true) GrupoInputDTO grupoInput);

    @ApiOperation("Atualiza um grupo por ID")
    @ApiResponses({ @ApiResponse(code = 200, message = "Grupo atualizado"),
            @ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class) })
    GrupoDTO atualizar(@ApiParam(value = "ID de um grupo", example = "1", required = true) Long grupoId,
            @ApiParam(name = "corpo", value = "Representação de um grupo com os novos dados", required = true) GrupoInputDTO grupoInput);

    @ApiOperation("Exclui um grupo por ID")
    @ApiResponses({ @ApiResponse(code = 204, message = "Grupo excluído"),
            @ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class) })
    void remover(@ApiParam(value = "ID de um grupo", example = "1", required = true) Long grupoId);

}