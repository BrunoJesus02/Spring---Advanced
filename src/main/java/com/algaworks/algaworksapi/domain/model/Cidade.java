package com.algaworks.algaworksapi.domain.model;

import com.algaworks.algaworksapi.Groups;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Entity
public class Cidade {

    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String nome;

    @Valid
    @ConvertGroup(from = Default.class, to = Groups.EstadoId.class)
    @NotNull
    @JoinColumn(nullable = false)
    @ManyToOne
    private Estado estado;
}
