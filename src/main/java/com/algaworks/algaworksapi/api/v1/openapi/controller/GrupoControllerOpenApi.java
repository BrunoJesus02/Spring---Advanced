package com.algaworks.algaworksapi.api.v1.openapi.controller;

import com.algaworks.algaworksapi.api.exceptionhandler.Problem;
import com.algaworks.algaworksapi.api.v1.model.input.GrupoInput;
import com.algaworks.algaworksapi.api.v1.model.output.GrupoModel;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;

@Api(tags = "Grupos")
public interface GrupoControllerOpenApi {

    @ApiOperation("Lista os grupos")
    CollectionModel<GrupoModel> listar();

    @ApiOperation("Busca um grupo por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID da grupo inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
    })
    GrupoModel buscar(
            @ApiParam(value = "ID de um grupo", example = "1", required = true)
            Long grupoId);

    @ApiOperation("Cadastra um grupo")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Grupo cadastrado"),
    })
    GrupoModel adicionar(
            @ApiParam(name = "corpo", value = "Representação de um novo grupo", required = true)
            GrupoInput grupoInput);

    @ApiOperation("Atualiza um grupo por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Grupo atualizado"),
            @ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
    })
    GrupoModel atualizar(
            @ApiParam(name = "corpo", value = "Representação de um grupo com os novos dados")
            GrupoInput grupoInput,

            @ApiParam(value = "ID de um grupo", example = "1", required = true)
            Long grupoId);

    @ApiOperation("Exclui um grupo por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Grupo excluído"),
            @ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
    })
    void remover(
            @ApiParam(value = "ID de um grupo", example = "1", required = true)
            Long grupoId);

}
