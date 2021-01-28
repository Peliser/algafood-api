package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.model.UsuarioDTO;
import com.algaworks.algafood.api.model.input.SenhaInputDTO;
import com.algaworks.algafood.api.model.input.UsuarioComSenhaInputDTO;
import com.algaworks.algafood.api.model.input.UsuarioInputDTO;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CadastroUsuarioService cadastroUsuario;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<UsuarioDTO> listar() {
        return usuarioRepository.findAll().stream().map(entity -> modelMapper.map(entity, UsuarioDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{usuarioId}")
    public UsuarioDTO buscar(@PathVariable Long usuarioId) {
        Usuario usuario = cadastroUsuario.buscar(usuarioId);
        return modelMapper.map(usuario, UsuarioDTO.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioDTO adicionar(@RequestBody @Valid UsuarioComSenhaInputDTO usuarioInput) {
        Usuario usuario = modelMapper.map(usuarioInput, Usuario.class);
        usuario = cadastroUsuario.salvar(usuario);
        return modelMapper.map(usuario, UsuarioDTO.class);
    }

    @PutMapping("/{id}")
    public UsuarioDTO atualizar(@PathVariable Long id, @RequestBody @Valid UsuarioInputDTO entityDto) {
        Usuario usuario = cadastroUsuario.buscar(id);
        modelMapper.map(entityDto, usuario);
        usuario = cadastroUsuario.salvar(usuario);
        return modelMapper.map(usuario, UsuarioDTO.class);
    }

    @PutMapping("/{id}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void alterarSenha(@PathVariable Long id, @RequestBody @Valid SenhaInputDTO senhaInputDto) {
        cadastroUsuario.alterarSenha(id, senhaInputDto.getSenhaAtual(), senhaInputDto.getNovaSenha());
    }

}