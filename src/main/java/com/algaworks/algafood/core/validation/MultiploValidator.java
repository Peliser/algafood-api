package com.algaworks.algafood.core.validation;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MultiploValidator implements ConstraintValidator<Multiplo, Number> {

    private BigDecimal numeroMultiplo;

    @Override
    public void initialize(Multiplo constraintAnnotation) {
        this.numeroMultiplo = BigDecimal.valueOf(constraintAnnotation.numero());
    }

    @Override
    public boolean isValid(Number value, ConstraintValidatorContext context) {
        if (value != null) {
            var valorDecimal = BigDecimal.valueOf(value.doubleValue());
            var resto = valorDecimal.remainder(this.numeroMultiplo);
            return BigDecimal.ZERO.compareTo(resto) == 0;
        }
        return true;
    }

}
