package com.algaworks.algaworksapi.domain.repository;

import com.algaworks.algaworksapi.domain.model.Cidade;
import com.algaworks.algaworksapi.domain.model.Estado;

import java.util.List;

public interface CidadeRepository {

    List<Cidade> listar();
    Cidade buscar(Long id);
    Cidade salvar(Cidade cidade);
    void remover(Cidade cidade);
}
