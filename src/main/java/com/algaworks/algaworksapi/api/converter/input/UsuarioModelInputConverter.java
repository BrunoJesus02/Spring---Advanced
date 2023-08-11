package com.algaworks.algaworksapi.api.converter.input;

import com.algaworks.algaworksapi.api.model.output.UsuarioModel;
import com.algaworks.algaworksapi.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsuarioModelInputConverter {

    @Autowired
    private ModelMapper modelMapper;

    public UsuarioModel toModel(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioModel.class);
    }

    public List<UsuarioModel> toCollectionModel(Collection<Usuario> usuarios) {
        return usuarios.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
}
