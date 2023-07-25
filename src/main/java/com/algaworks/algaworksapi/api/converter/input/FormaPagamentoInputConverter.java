package com.algaworks.algaworksapi.api.converter.input;

import com.algaworks.algaworksapi.api.model.FormaPagamentoModel;
import com.algaworks.algaworksapi.domain.model.FormaPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FormaPagamentoInputConverter {

    @Autowired
    private ModelMapper modelMapper;

    public FormaPagamentoModel toModel(FormaPagamento formaPagamento) {
        return modelMapper.map(formaPagamento, FormaPagamentoModel.class);
    }

    public List<FormaPagamentoModel> toCollectionModel(List<FormaPagamento> formaPagamentos) {
        return formaPagamentos.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
}
