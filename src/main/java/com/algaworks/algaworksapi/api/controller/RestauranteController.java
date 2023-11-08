package com.algaworks.algaworksapi.api.controller;

import com.algaworks.algaworksapi.api.converter.input.RestauranteModelInputConverter;
import com.algaworks.algaworksapi.api.converter.output.RestauranteModelOutputConverter;
import com.algaworks.algaworksapi.api.model.input.RestauranteInput;
import com.algaworks.algaworksapi.api.model.output.RestauranteModel;
import com.algaworks.algaworksapi.api.model.view.RestauranteView;
import com.algaworks.algaworksapi.api.openapi.controller.RestauranteControllerOpenApi;
import com.algaworks.algaworksapi.api.openapi.model.RestauranteBasicoModelOpenApi;
import com.algaworks.algaworksapi.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algaworksapi.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algaworksapi.domain.exception.NegocioException;
import com.algaworks.algaworksapi.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algaworksapi.domain.model.Restaurante;
import com.algaworks.algaworksapi.domain.repository.RestauranteRepository;
import com.algaworks.algaworksapi.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/restaurantes", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteController implements RestauranteControllerOpenApi {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    @Autowired
    private RestauranteModelInputConverter restauranteInputConverter;

    @Autowired
    private RestauranteModelOutputConverter restauranteOutputConverter;

//    @GetMapping
//    public MappingJacksonValue listar(@RequestParam(required = false) String projecao) {
//        List<Restaurante> restaurantes = restauranteRepository.findAll();
//        List<RestauranteModel> restauranteModels = restauranteInputConverter.toCollectionModel(restaurantes);
//
//        MappingJacksonValue restaurantesWrapper = new MappingJacksonValue(restauranteModels);
//
//        restaurantesWrapper.setSerializationView(RestauranteView.Resumo.class);
//
//        if ("apenas-nome".equals(projecao)) {
//            restaurantesWrapper.setSerializationView(RestauranteView.ApenasNome.class);
//        } else if ("completo".equals(projecao)) {
//            restaurantesWrapper.setSerializationView(null);
//        }
//
//        return restaurantesWrapper;
//    }

    @ApiOperation(value = "Lista restaurantes", response = RestauranteBasicoModelOpenApi.class)
    @ApiImplicitParams({
        @ApiImplicitParam(value = "Nome da projeção de pedidos", allowableValues = "apenas-nome",
                name = "projecao", paramType = "query", type = "string")
    })
    @JsonView(RestauranteView.Resumo.class)
    @GetMapping
    public List<RestauranteModel> listar() {
        return restauranteInputConverter.toCollectionModel(restauranteRepository.findAll());
    }

    @ApiOperation(value = "Lista restaurantes", hidden = true)
    @JsonView(RestauranteView.ApenasNome.class)
    @GetMapping(params = "projecao=apenas-nome")
    public List<RestauranteModel> listarApenasNomes() {
        return listar();
    }

    @GetMapping("/{restauranteId}")
    public RestauranteModel buscar(@PathVariable Long restauranteId) {
        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);

        return restauranteInputConverter.toModel(restaurante);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteModel adicionar(@RequestBody @Valid RestauranteInput restauranteInput) {
        try {
            return restauranteInputConverter.toModel(cadastroRestaurante.salvar(
                    restauranteOutputConverter.toDomainObject(restauranteInput)));
        } catch (CozinhaNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{restauranteId}")
    public RestauranteModel atualizar(@RequestBody @Valid RestauranteInput restauranteInput, @PathVariable Long restauranteId) {
        try {
            Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(restauranteId);

            restauranteOutputConverter.copyToDomainObject(restauranteInput, restauranteAtual);

            return restauranteInputConverter.toModel(cadastroRestaurante.salvar(restauranteAtual));
        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{restauranteId}/ativar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativar(@PathVariable Long restauranteId) {
        cadastroRestaurante.ativar(restauranteId);
    }

    @DeleteMapping("/{restauranteId}/inativar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativar(@PathVariable Long restauranteId) {
        cadastroRestaurante.inativar(restauranteId);
    }

    @PutMapping("/ativacoes")
    public void ativarMultiplos(@RequestBody List<Long> restauranteIds) {
        try {
            cadastroRestaurante.ativar(restauranteIds);
        } catch (RestauranteNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e.getCause());
        }
    }

    @DeleteMapping("/inativacoes")
    public void inativarMultiplos(@RequestBody List<Long> restauranteIds) {
        try {
            cadastroRestaurante.inativar(restauranteIds);
        } catch (RestauranteNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e.getCause());
        }
    }

    @PutMapping("/{restauranteId}/abertura")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void abrir(@PathVariable Long restauranteId) {
        cadastroRestaurante.abrir(restauranteId);
    }

    @PutMapping("/{restauranteId}/fechamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void fechar(@PathVariable Long restauranteId) {
        cadastroRestaurante.fechar(restauranteId);
    }
}
