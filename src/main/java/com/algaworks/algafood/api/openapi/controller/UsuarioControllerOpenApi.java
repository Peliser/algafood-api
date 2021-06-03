package com.algaworks.algafood.api.openapi.controller;

import java.util.List;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.UsuarioDTO;
import com.algaworks.algafood.api.model.input.SenhaInputDTO;
import com.algaworks.algafood.api.model.input.UsuarioComSenhaInputDTO;
import com.algaworks.algafood.api.model.input.UsuarioInputDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Usuários")
public interface UsuarioControllerOpenApi {

    @ApiOperation("Lista os usuários")
    List<UsuarioDTO> listar();

    @ApiOperation("Busca um usuário por ID")
    @ApiResponses({ @ApiResponse(code = 400, message = "ID do usuário inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class) })
    UsuarioDTO buscar(@ApiParam(value = "ID do usuário", example = "1", required = true) Long usuarioId);

    @ApiOperation("Cadastra um usuário")
    @ApiResponses({ @ApiResponse(code = 201, message = "Usuário cadastrado"), })
    UsuarioDTO adicionar(
            @ApiParam(name = "corpo", value = "Representação de um novo usuário", required = true) UsuarioComSenhaInputDTO usuarioInput);

    @ApiOperation("Atualiza um usuário por ID")
    @ApiResponses({ @ApiResponse(code = 200, message = "Usuário atualizado"),
            @ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class) })
    UsuarioDTO atualizar(@ApiParam(value = "ID do usuário", example = "1", required = true) Long usuarioId,
            @ApiParam(name = "corpo", value = "Representação de um usuário com os novos dados", required = true) UsuarioInputDTO usuarioInput);

    @ApiOperation("Atualiza a senha de um usuário")
    @ApiResponses({ @ApiResponse(code = 204, message = "Senha alterada com sucesso"),
            @ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class) })
    void alterarSenha(@ApiParam(value = "ID do usuário", example = "1", required = true) Long usuarioId,
            @ApiParam(name = "corpo", value = "Representação de uma nova senha", required = true) SenhaInputDTO senha);

}