package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.model.PedidoDTO;
import com.algaworks.algafood.api.model.PedidoResumoDTO;
import com.algaworks.algafood.api.model.input.PedidoInputDTO;
import com.algaworks.algafood.api.openapi.controller.PedidoControllerOpenApi;
import com.algaworks.algafood.core.data.PageableHandler;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.filter.PedidoFilter;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.service.EmissaoPedidoService;
import com.algaworks.algafood.infrastructure.repository.spec.PedidoSpecs;
import com.google.common.collect.ImmutableMap;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController implements PedidoControllerOpenApi {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private EmissaoPedidoService emissaoPedido;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public Page<PedidoResumoDTO> pesquisar(PedidoFilter filtro, @PageableDefault(size = 10) Pageable pageable) {
        pageable = handlePageable(pageable);
        Page<Pedido> pedidosPage = pedidoRepository.findAll(PedidoSpecs.usandoFiltro(filtro), pageable);
        List<PedidoResumoDTO> pedidos = pedidosPage.getContent().stream()
                .map(pedido -> modelMapper.map(pedido, PedidoResumoDTO.class)).collect(Collectors.toList());
        return new PageImpl<>(pedidos, pageable, pedidosPage.getTotalElements());
    }

//    @GetMapping
//    public MappingJacksonValue listar(@RequestParam(required = false) String campos) {
//        List<Pedido> pedidos = pedidoRepository.findAll();
//        List<PedidoResumoDTO> pedidosDtos = pedidos.stream()
//                .map(pedido -> modelMapper.map(pedido, PedidoResumoDTO.class)).collect(Collectors.toList());
//
//        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
//        SimpleBeanPropertyFilter pedidoFilter;
//        if (StringUtils.isNotBlank(campos)) {
//            pedidoFilter = SimpleBeanPropertyFilter.filterOutAllExcept(campos.split(","));
//        } else {
//            pedidoFilter = SimpleBeanPropertyFilter.serializeAll();
//        }
//        filterProvider.addFilter("pedidoFilter", pedidoFilter);
//
//        MappingJacksonValue wrapper = new MappingJacksonValue(pedidosDtos);
//        wrapper.setFilters(filterProvider);
//        return wrapper;
//    }

    @GetMapping("/{codigo}")
    public PedidoDTO buscar(@PathVariable String codigo) {
        Pedido pedido = emissaoPedido.buscar(codigo);
        return modelMapper.map(pedido, PedidoDTO.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoDTO adicionar(@Valid @RequestBody PedidoInputDTO pedidoInput) {
        try {
            Pedido novoPedido = modelMapper.map(pedidoInput, Pedido.class);
            // TODO pegar usuário autenticado
            novoPedido.setCliente(new Usuario());
            novoPedido.getCliente().setId(1L);
            novoPedido = emissaoPedido.emitir(novoPedido);
            return modelMapper.map(novoPedido, PedidoDTO.class);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    private Pageable handlePageable(Pageable pageable) {
        ImmutableMap<String, String> mapeamento = ImmutableMap.of("codigo", "codigo", "restaurante.nome",
                "restaurante.nome", "cliente.nome", "cliente.nome");
        return PageableHandler.handle(pageable, mapeamento);
    }

}