package com.algaworks.algaworksapi.core.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { ValorZeroIncluirDescricaoValidator.class })
public @interface ValorZeroIncluirDescricao {

    String message() default "descrição obrigatória inválida";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String valorField();

    String descricaoField();

    String descricaoObrigatoria() ;
}
