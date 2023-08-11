package com.algaworks.algaworksapi.core.modelmapper;

import com.algaworks.algaworksapi.api.model.input.ItemPedidoInput;
import com.algaworks.algaworksapi.api.model.output.EnderecoModel;
import com.algaworks.algaworksapi.domain.model.Endereco;
import com.algaworks.algaworksapi.domain.model.ItemPedido;
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

        modelMapper.createTypeMap(ItemPedidoInput.class, ItemPedido.class)
                .addMappings(mapper -> mapper.skip(ItemPedido::setId));
        return modelMapper;
    }
}
