package com.algaworks.algaworksapi.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@ApiModel("Problema")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
public class Problem {

    @ApiModelProperty(example = "400", position = 1)
    private Integer status;

    @ApiModelProperty(example = "https://algafood.api.com.br/dados-invalidos", position = 2)
    private String type;

    @ApiModelProperty(example = "Dados Invalidos", position = 3)
    private String title;

    @ApiModelProperty(example = "Um ou mais campos estão invalidos", position = 4)
    private String detail;

    @ApiModelProperty(example = "Objeto que gerou o erro", position = 5)
    private String userMessage;

    @ApiModelProperty(example = "2019-12-01T18:12:00Z", position = 6)
    private OffsetDateTime timestamp;

    private List<Object> objects;

    @ApiModel("ObjetoProblema")
    @Getter
    @Builder
    public static class Object {

        @ApiModelProperty(example = "Preço")
        private String name;

        @ApiModelProperty(example = "Preço é obrigatório")
        private String userMessage;
    }

}
