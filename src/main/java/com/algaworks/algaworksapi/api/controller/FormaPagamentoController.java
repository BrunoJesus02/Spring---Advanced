package com.algaworks.algaworksapi.api.controller;

import com.algaworks.algaworksapi.api.converter.input.FormaPagamentoInputConverter;
import com.algaworks.algaworksapi.api.converter.output.FormaPagamentoOutputConverter;
import com.algaworks.algaworksapi.api.model.output.FormaPagamentoModel;
import com.algaworks.algaworksapi.api.model.input.FormaPagamentoInput;
import com.algaworks.algaworksapi.domain.model.FormaPagamento;
import com.algaworks.algaworksapi.domain.repository.FormaPagamentoRepository;
import com.algaworks.algaworksapi.domain.service.CadastroFormaPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/formas-pagamento")
public class FormaPagamentoController {

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    @Autowired
    private CadastroFormaPagamentoService formaPagamentoService;

    @Autowired
    private FormaPagamentoInputConverter formaPagamentoInputConverter;

    @Autowired
    private FormaPagamentoOutputConverter formaPagamentoOutputConverter;

    @GetMapping
    public ResponseEntity<List<FormaPagamentoModel>> listar(ServletWebRequest request) {
        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());

        String eTag = "0";
        OffsetDateTime dataUltimaAtualizacao = formaPagamentoRepository.getDataUltimaAtualizacao();

        if(dataUltimaAtualizacao != null) {
            eTag = String.valueOf(dataUltimaAtualizacao.toEpochSecond());
        }

        if(request.checkNotModified(eTag)) {
            return null;
        }

        List<FormaPagamentoModel> formaPagamentoModels = formaPagamentoInputConverter.toCollectionModel(formaPagamentoRepository.findAll());

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
                .eTag(eTag)
                .body(formaPagamentoModels);
    }

    @GetMapping("/{formaPagamentoId}")
    public ResponseEntity<FormaPagamentoModel> buscar(@PathVariable Long formaPagamentoId, ServletWebRequest request) {
        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());

        String eTag = "0";
        OffsetDateTime dataAtualizacao = formaPagamentoRepository.getDataAtualizacaoById(formaPagamentoId);

        if (dataAtualizacao != null) {
            eTag = String.valueOf(dataAtualizacao.toEpochSecond());
        }

        if (request.checkNotModified(eTag)) {
            return null;
        }

        FormaPagamentoModel formaPagamentoModel = formaPagamentoInputConverter
                .toModel(formaPagamentoService.buscarOuFalhar(formaPagamentoId));

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .eTag(eTag)
                .body(formaPagamentoModel);
    }

    @PostMapping
    public FormaPagamentoModel adicionar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
        FormaPagamento formaPagamento = formaPagamentoService
                .salvar(formaPagamentoOutputConverter.toDomainObject(formaPagamentoInput));

        return formaPagamentoInputConverter.toModel(formaPagamento);
    }

    @PutMapping("/{formaPagamentoId}")
    public FormaPagamentoModel atualizar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput, @PathVariable Long formaPagamentoId) {
        FormaPagamento formaPagamento = formaPagamentoService.buscarOuFalhar(formaPagamentoId);

        formaPagamentoOutputConverter.copyToDomainObject(formaPagamentoInput, formaPagamento);

        return formaPagamentoInputConverter.toModel(formaPagamentoService.salvar(formaPagamento));
    }

    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long formaPagamentoId) {
        formaPagamentoService.excluir(formaPagamentoId);
    }
}
