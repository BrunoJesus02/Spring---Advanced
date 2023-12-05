package com.algaworks.algaworksapi.api.v1.openapi.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("PageModel")
@Getter
@Setter
public class PagedModelOpenApi {

    @ApiModelProperty(example = "10", value = "Quantidade de registros por pagina")
    private Long size;

    @ApiModelProperty(example = "50", value = "Total de registros")
    private Long totalElements;

    @ApiModelProperty(example = "10", value = "Total de páginas")
    private Long totalPages;

    @ApiModelProperty(example = "10", value = "Número da pagina")
    private Long number;
}
