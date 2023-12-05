package com.algaworks.algaworksapi.api.v1.openapi.model;

import com.algaworks.algaworksapi.api.v1.model.output.PedidoResumoModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("PedidosResumoModel")
public class PedidosResumoModelOpenApi {

    private PedidosResumoEmbeddedModelOpenApi _embedded;
    private Links _links;
    private PagedModelOpenApi page;

    @ApiModel("PedidosResumoEmbeddedModel")
    @Data
    public class PedidosResumoEmbeddedModelOpenApi {

        private List<PedidoResumoModel> pedidos;

    }
}
