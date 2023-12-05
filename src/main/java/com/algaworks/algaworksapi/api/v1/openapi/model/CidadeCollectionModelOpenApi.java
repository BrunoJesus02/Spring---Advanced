package com.algaworks.algaworksapi.api.v1.openapi.model;

import com.algaworks.algaworksapi.api.v1.model.output.CidadeModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("CidadesModel")
@Data
public class CidadeCollectionModelOpenApi {

    private CidadeEmbeddedModelOpenApi _embedded;
    private Links links;

    public class CidadeEmbeddedModelOpenApi {

        private List<CidadeModel> cidades;
    }
}
