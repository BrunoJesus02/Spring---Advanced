package com.algaworks.algaworksapi.core.validation;

import org.springframework.http.MediaType;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { FileContentTypeValidator.class })
public @interface FileContentType {

    String message() default "content type do arquivo inválido";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] allowed();
}
