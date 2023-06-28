package com.algaworks.algaworksapi.api.controller;

import com.algaworks.algaworksapi.domain.exception.EntidadeEmUsoException;
import com.algaworks.algaworksapi.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algaworksapi.domain.model.Estado;
import com.algaworks.algaworksapi.domain.repository.EstadoRepository;
import com.algaworks.algaworksapi.domain.service.CadastroEstadoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/estados", produces = MediaType.APPLICATION_JSON_VALUE)
public class EstadoController {

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private CadastroEstadoService cadastroEstado;

    @GetMapping
    public List<Estado> listar() {
        return estadoRepository.findAll();
    }

    @GetMapping("/{estadoId}")
    public Estado buscar(@PathVariable Long estadoId) {
        return cadastroEstado.buscarOuFalhar(estadoId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Estado adicionar(@RequestBody @Valid Estado estado) {
        return cadastroEstado.salvar(estado);
    }

    @PutMapping("/{estadoId}")
    public Estado atualizar(@PathVariable Long estadoId, @RequestBody @Valid Estado estado) {
        Estado estadoAtual = cadastroEstado.buscarOuFalhar(estadoId);

        BeanUtils.copyProperties(estado, estadoAtual, "id");

        return cadastroEstado.salvar(estadoAtual);
    }

    @DeleteMapping("/{estadoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long estadoId) {
        cadastroEstado.excluir(estadoId);
    }

}
