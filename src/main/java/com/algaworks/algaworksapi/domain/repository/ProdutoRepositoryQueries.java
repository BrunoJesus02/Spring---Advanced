package com.algaworks.algaworksapi.domain.repository;

import com.algaworks.algaworksapi.domain.model.FotoProduto;

public interface ProdutoRepositoryQueries {

    FotoProduto save(FotoProduto foto);
    void delete(FotoProduto foto);
}
