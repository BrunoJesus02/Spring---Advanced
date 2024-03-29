package com.algaworks.algaworksapi.api.v1.openapi.model;

import com.algaworks.algaworksapi.api.v1.model.output.CozinhaModel;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("CozinhasModel")
@Setter
@Getter
public class CozinhasModelOpenApi {

    private CozinhasEmbeddedModelOpenApi _embedded;
    private Links links;
    private PagedModelOpenApi page;

    public class CozinhasEmbeddedModelOpenApi {

        private List<CozinhaModel> cozinhas;
    }

}
