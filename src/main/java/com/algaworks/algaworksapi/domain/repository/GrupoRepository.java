package com.algaworks.algaworksapi.domain.repository;

import com.algaworks.algaworksapi.domain.model.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Long> { }
