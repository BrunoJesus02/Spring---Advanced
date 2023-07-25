package com.algaworks.algaworksapi.core.modelmapper;

import com.algaworks.algaworksapi.api.model.EnderecoModel;
import com.algaworks.algaworksapi.api.model.RestauranteModel;
import com.algaworks.algaworksapi.domain.model.Endereco;
import com.algaworks.algaworksapi.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();

//        modelMapper.createTypeMap(Restaurante.class, RestauranteModel.class)
//                .addMapping(Restaurante::getTaxaFrete, RestauranteModel::setPrecoFrete);

        TypeMap<Endereco, EnderecoModel> enderecoModelTypeMap =
                modelMapper.createTypeMap(Endereco.class, EnderecoModel.class);

        enderecoModelTypeMap.<String>addMapping(src -> src.getCidade().getEstado().getNome(),
                (dest, valor) -> dest.getCidade().setEstado(valor));
        return modelMapper;
    }
}
