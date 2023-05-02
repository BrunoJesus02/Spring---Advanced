package com.algaworks.algaworksapi.domain.repository;

import com.algaworks.algaworksapi.domain.model.Estado;

import java.util.List;

public interface EstadoRepository {

    List<Estado> listar();
    Estado buscar(Long id);
    Estado salvar(Estado estado);
    void remover(Estado estado);
}
