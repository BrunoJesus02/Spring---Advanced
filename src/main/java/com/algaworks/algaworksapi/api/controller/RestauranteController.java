package com.algaworks.algaworksapi.api.controller;

import com.algaworks.algaworksapi.api.model.CozinhaModel;
import com.algaworks.algaworksapi.api.model.RestauranteModel;
import com.algaworks.algaworksapi.api.model.input.RestauranteInput;
import com.algaworks.algaworksapi.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algaworksapi.domain.exception.NegocioException;
import com.algaworks.algaworksapi.domain.model.Cozinha;
import com.algaworks.algaworksapi.domain.model.Restaurante;
import com.algaworks.algaworksapi.domain.repository.RestauranteRepository;
import com.algaworks.algaworksapi.domain.service.CadastroRestauranteService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    @Autowired
    private SmartValidator validator;

    @GetMapping
    public List<RestauranteModel> listar() {
        List<Restaurante> restaurantes = restauranteRepository.findAll();
        return toCollectionModel(restaurantes);
    }

    @GetMapping("/{restauranteId}")
    public RestauranteModel buscar(@PathVariable Long restauranteId) {
        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);

        return toModel(restaurante);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteModel adicionar(@RequestBody @Valid RestauranteInput restauranteInput) {
        try {
            return toModel(cadastroRestaurante.salvar(toDomainObject(restauranteInput)));
        } catch (CozinhaNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{restauranteId}")
    public RestauranteModel atualizar(@RequestBody @Valid RestauranteInput restauranteInput, @PathVariable Long restauranteId) {
        try {
            Restaurante restaurante = toDomainObject(restauranteInput);

            Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(restauranteId);
            BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento",
                    "endereco", "dataCadastro", "produtos");

            return toModel(cadastroRestaurante.salvar(restauranteAtual));
        } catch (CozinhaNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    private static RestauranteModel toModel(Restaurante restaurante) {
        RestauranteModel model = new RestauranteModel();
        model.setId(restaurante.getId());
        model.setNome(restaurante.getNome());
        model.setTaxaFrete(restaurante.getTaxaFrete());

        CozinhaModel cozinhaModel = new CozinhaModel();
        cozinhaModel.setId(restaurante.getCozinha().getId());
        cozinhaModel.setNome(restaurante.getCozinha().getNome());
        model.setCozinha(cozinhaModel);
        return model;
    }

    private List<RestauranteModel> toCollectionModel(List<Restaurante> restaurantes) {
        return restaurantes.stream()
                .map(RestauranteController::toModel)
                .collect(Collectors.toList());
    }

    private Restaurante toDomainObject(RestauranteInput restauranteInput) {
        Restaurante restaurante = new Restaurante();
        restaurante.setNome(restauranteInput.getNome());
        restaurante.setTaxaFrete(restauranteInput.getTaxaFrete());

        Cozinha cozinha = new Cozinha();
        cozinha.setId(restauranteInput.getCozinha().getId());
        restaurante.setCozinha(cozinha);

        return restaurante;
    }
}
