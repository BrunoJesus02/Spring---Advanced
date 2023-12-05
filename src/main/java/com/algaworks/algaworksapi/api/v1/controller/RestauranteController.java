package com.algaworks.algaworksapi.api.v1.controller;

import com.algaworks.algaworksapi.api.v1.converter.input.RestauranteApenasNomeModelInputConverter;
import com.algaworks.algaworksapi.api.v1.converter.input.RestauranteBasicoModelInputConverter;
import com.algaworks.algaworksapi.api.v1.converter.input.RestauranteModelInputConverter;
import com.algaworks.algaworksapi.api.v1.converter.output.RestauranteModelOutputConverter;
import com.algaworks.algaworksapi.api.v1.model.input.RestauranteInput;
import com.algaworks.algaworksapi.api.v1.model.output.RestauranteApenasNomeModel;
import com.algaworks.algaworksapi.api.v1.model.output.RestauranteBasicoModel;
import com.algaworks.algaworksapi.api.v1.model.output.RestauranteModel;
import com.algaworks.algaworksapi.api.v1.openapi.controller.RestauranteControllerOpenApi;
import com.algaworks.algaworksapi.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algaworksapi.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algaworksapi.domain.exception.NegocioException;
import com.algaworks.algaworksapi.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algaworksapi.domain.model.Restaurante;
import com.algaworks.algaworksapi.domain.repository.RestauranteRepository;
import com.algaworks.algaworksapi.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private RestauranteBasicoModelInputConverter restauranteBasicoInputConverter;

    @Autowired
    private RestauranteApenasNomeModelInputConverter restauranteApenasNomeInputConverter;

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

//    @JsonView(RestauranteView.Resumo.class)
    @GetMapping
    public CollectionModel<RestauranteBasicoModel> listar() {
        return restauranteBasicoInputConverter.toCollectionModel(restauranteRepository.findAll());
    }

//    @JsonView(RestauranteView.ApenasNome.class)
    @GetMapping(params = "projecao=apenas-nome")
    public CollectionModel<RestauranteApenasNomeModel> listarApenasNomes() {
        return restauranteApenasNomeInputConverter.toCollectionModel(restauranteRepository.findAll());
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
    public ResponseEntity<Void> ativar(@PathVariable Long restauranteId) {
        cadastroRestaurante.ativar(restauranteId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{restauranteId}/inativar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> inativar(@PathVariable Long restauranteId) {
        cadastroRestaurante.inativar(restauranteId);

        return ResponseEntity.noContent().build();
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
    public ResponseEntity<Void> abrir(@PathVariable Long restauranteId) {
        cadastroRestaurante.abrir(restauranteId);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{restauranteId}/fechamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> fechar(@PathVariable Long restauranteId) {
        cadastroRestaurante.fechar(restauranteId);

        return ResponseEntity.noContent().build();
    }
}
