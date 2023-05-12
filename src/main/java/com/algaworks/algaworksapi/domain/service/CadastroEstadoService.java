package com.algaworks.algaworksapi.domain.service;

import com.algaworks.algaworksapi.domain.exception.EntidadeEmUsoException;
import com.algaworks.algaworksapi.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algaworksapi.domain.model.Estado;
import com.algaworks.algaworksapi.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CadastroEstadoService {

    @Autowired
    private EstadoRepository estadoRepository;

    public Estado salvar(Estado estado) {
        return estadoRepository.salvar(estado);
    }

    public void excluir(Long estadoId) {
        try {
            estadoRepository.remover(estadoId);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format("Estado com código %d não pode ser removido pois está em uso", estadoId));
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(String.format("Estado com código %d não encontrado", estadoId));
        }
    }
}
