package com.algaworks.algaworksapi.api.v1.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class CidadeInput {

    @ApiModelProperty(example = "Uberlandia", required = true)
    @NotBlank
    private String nome;

    @ApiModelProperty(example = "1", required = true)
    @Valid
    @NotNull
    private EstadoIdInput estado;

}
