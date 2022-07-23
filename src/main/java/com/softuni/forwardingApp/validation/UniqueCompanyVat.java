package com.softuni.forwardingApp.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Constraint(validatedBy = UniqueCompanyVatValidator.class)
public @interface UniqueCompanyVat {

    String message() default "Company VAT must be Unique !";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
