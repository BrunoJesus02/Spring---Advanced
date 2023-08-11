package com.algaworks.algaworksapi.api.controller;

import com.algaworks.algaworksapi.api.converter.input.EstadoModelInputConverter;
import com.algaworks.algaworksapi.api.converter.output.EstadoModelOutputConverter;
import com.algaworks.algaworksapi.api.model.output.EstadoModel;
import com.algaworks.algaworksapi.api.model.input.EstadoInput;
import com.algaworks.algaworksapi.domain.model.Estado;
import com.algaworks.algaworksapi.domain.repository.EstadoRepository;
import com.algaworks.algaworksapi.domain.service.CadastroEstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/estados", produces = MediaType.APPLICATION_JSON_VALUE)
public class EstadoController {

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private CadastroEstadoService cadastroEstado;

    @Autowired
    private EstadoModelInputConverter estadoInputConverter;

    @Autowired
    private EstadoModelOutputConverter estadoOutputConverter;

    @GetMapping
    public List<EstadoModel> listar() {
        return estadoInputConverter.toCollectionModel(estadoRepository.findAll());
    }

    @GetMapping("/{estadoId}")
    public EstadoModel buscar(@PathVariable Long estadoId) {
        return estadoInputConverter.toModel(cadastroEstado.buscarOuFalhar(estadoId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstadoModel adicionar(@RequestBody @Valid EstadoInput estadoInput) {
        Estado estado = estadoOutputConverter.toDomainObject(estadoInput);

        estado = cadastroEstado.salvar(estado);

        return estadoInputConverter.toModel(estado);
    }

    @PutMapping("/{estadoId}")
    public EstadoModel atualizar(@PathVariable Long estadoId, @RequestBody @Valid EstadoInput estadoInput) {
        Estado estadoAtual = cadastroEstado.buscarOuFalhar(estadoId);

        estadoOutputConverter.copyToDomainObject(estadoInput, estadoAtual);

        return estadoInputConverter.toModel(cadastroEstado.salvar(estadoAtual));
    }

    @DeleteMapping("/{estadoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long estadoId) {
        cadastroEstado.excluir(estadoId);
    }

}
