package com.algaworks.algaworksapi.core.jackson;

import com.algaworks.algaworksapi.api.model.mixin.CidadeMixin;
import com.algaworks.algaworksapi.api.model.mixin.CozinhaMixin;
import com.algaworks.algaworksapi.api.model.mixin.RestauranteMixin;
import com.algaworks.algaworksapi.domain.model.Cidade;
import com.algaworks.algaworksapi.domain.model.Cozinha;
import com.algaworks.algaworksapi.domain.model.Restaurante;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.stereotype.Component;

@Component
public class JacksonMixinModule extends SimpleModule {

    public JacksonMixinModule() {
        setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
        setMixInAnnotation(Cozinha.class, CozinhaMixin.class);
        setMixInAnnotation(Cidade.class, CidadeMixin.class);
    }
}
