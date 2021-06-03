package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.model.PermissaoDTO;
import com.algaworks.algafood.api.openapi.controller.GrupoPermissaoControllerOpenApi;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.service.CadastroGrupoService;

@RestController
@RequestMapping(value = "/grupos/{id}/permissoes")
public class GrupoPermissaoController implements GrupoPermissaoControllerOpenApi {

    @Autowired
    private CadastroGrupoService cadastroGrupo;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<PermissaoDTO> listar(@PathVariable Long id) {
        Grupo grupo = cadastroGrupo.buscar(id);
        return grupo.getPermissoes().stream().map(permissao -> modelMapper.map(permissao, PermissaoDTO.class))
                .collect(Collectors.toList());
    }

    @PutMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long id, @PathVariable Long permissaoId) {
        cadastroGrupo.associarPermissao(id, permissaoId);
    }

    @DeleteMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long id, @PathVariable Long permissaoId) {
        cadastroGrupo.desassociarPermissao(id, permissaoId);
    }

}