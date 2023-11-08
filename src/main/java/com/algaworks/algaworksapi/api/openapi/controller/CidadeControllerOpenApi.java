package com.algaworks.algaworksapi.api.openapi.controller;

import com.algaworks.algaworksapi.api.exceptionhandler.Problem;
import com.algaworks.algaworksapi.api.model.input.CidadeInput;
import com.algaworks.algaworksapi.api.model.output.CidadeModel;
import io.swagger.annotations.*;

import java.util.List;

@Api(tags = "Cidades")
public interface CidadeControllerOpenApi {

    @ApiOperation("Lista as cidades")
    List<CidadeModel> listar();

    @ApiOperation("Busca uma cidade por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID da cidade inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "cidade não encontrada", response = Problem.class)
    })
    CidadeModel buscar(@ApiParam(value = "ID de uma cidade", example = "1", required = true) Long cidadeId);

    @ApiOperation("Cadastra uma cidade")
    @ApiResponses({
            @ApiResponse(code = 201, message = "cidade cadastrada")
    })
    CidadeModel adicionar(@ApiParam(name = "corpo", value = "Representacao de uma nova cidade", required = true)
                                 CidadeInput cidadeInput);

    @ApiOperation("Atualiza uma cidade por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "cidade atualizada"),
            @ApiResponse(code = 404, message = "cidade não encontrada", response = Problem.class)
    })
    CidadeModel atualizar(@ApiParam(value = "ID de uma cidade", example = "1", required = true)
                                 Long cidadeId,
                                 @ApiParam(name = "corpo", value = "Representacao de uma nova cidade com os novos dados")
                                 CidadeInput cidadeInput);

    @ApiOperation("Deleta uma cidade por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "cidade excluida"),
            @ApiResponse(code = 404, message = "cidade não encontrada", response = Problem.class)
    })
    void remover(@ApiParam(value = "ID de uma cidade", example = "1", required = true) Long cidadeId);
}
