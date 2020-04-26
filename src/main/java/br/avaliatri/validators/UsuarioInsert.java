package br.avaliatri.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UsuarioInsertValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UsuarioInsert {

    String message() default "Usuario invalido";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}