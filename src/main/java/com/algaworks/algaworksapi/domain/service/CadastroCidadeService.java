package com.algaworks.algaworksapi.domain.service;

import com.algaworks.algaworksapi.domain.exception.EntidadeEmUsoException;
import com.algaworks.algaworksapi.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algaworksapi.domain.model.Cidade;
import com.algaworks.algaworksapi.domain.model.Estado;
import com.algaworks.algaworksapi.domain.repository.CidadeRepository;
import com.algaworks.algaworksapi.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CadastroCidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    public Cidade salvar(Cidade cidade) {
        Long estadoId = cidade.getEstado().getId();
        Optional<Estado> estado = estadoRepository.findById(estadoId);

        if(estado.isEmpty()) {
            throw new EntidadeNaoEncontradaException(String.format("Não existe cadastro de estado com código %d", estadoId));
        }

        cidade.setEstado(estado.get());
        return cidadeRepository.save(cidade);
    }

    public void excluir(Long cidadeId) {
        try {
            cidadeRepository.deleteById(cidadeId);
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(String.format("Cidade com o código %d não foi encontrada", cidadeId));
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format("Cidade com o código %d está em uso", cidadeId));
        }
    }

}
