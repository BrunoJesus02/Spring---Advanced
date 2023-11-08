package com.algaworks.algaworksapi.api.openapi.model;

import com.algaworks.algaworksapi.api.model.output.CozinhaModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PageModelOpenApi<T> {

    private List<T> content;

    @ApiModelProperty(example = "10", value = "Quantidade de registros por pagina")
    private Long size;

    @ApiModelProperty(example = "50", value = "Total de registros")
    private Long totalElements;

    @ApiModelProperty(example = "10", value = "Total de páginas")
    private Long totalPages;

    @ApiModelProperty(example = "10", value = "Número da pagina")
    private Long number;
}
