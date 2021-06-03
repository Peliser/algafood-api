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

import com.algaworks.algafood.api.model.UsuarioDTO;
import com.algaworks.algafood.api.openapi.controller.RestauranteUsuarioResponsavelControllerOpenApi;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping(value = "/restaurantes/{id}/responsaveis")
public class RestauranteUsuarioResponsavelController implements RestauranteUsuarioResponsavelControllerOpenApi {

    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<UsuarioDTO> listar(@PathVariable Long id) {
        Restaurante restaurante = cadastroRestaurante.buscar(id);
        return restaurante.getResponsaveis().stream().map(responsavel -> modelMapper.map(responsavel, UsuarioDTO.class))
                .collect(Collectors.toList());
    }

    @PutMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long id, @PathVariable Long usuarioId) {
        cadastroRestaurante.associarResponsavel(id, usuarioId);
    }

    @DeleteMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long id, @PathVariable Long usuarioId) {
        cadastroRestaurante.desassociarResponsavel(id, usuarioId);
    }

}