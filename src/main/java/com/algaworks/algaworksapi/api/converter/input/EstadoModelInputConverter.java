package com.algaworks.algaworksapi.api.converter.input;

import com.algaworks.algaworksapi.api.model.output.EstadoModel;
import com.algaworks.algaworksapi.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EstadoModelInputConverter {

    @Autowired
    private ModelMapper modelMapper;

    public EstadoModel toModel(Estado estado) {
        return modelMapper.map(estado, EstadoModel.class);
    }

    public List<EstadoModel> toCollectionModel(List<Estado> estados) {
        return estados.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
}
