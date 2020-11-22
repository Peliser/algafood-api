package com.algaworks.algafood.core.validation;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;

import org.springframework.beans.BeanUtils;

public class ValorZeroIncluiDescricaoValidator implements ConstraintValidator<ValorZeroIncluiDescricao, Object> {

    private String valorField;
    private String descricaoField;
    private String descricaoObrigatoria;

    @Override
    public void initialize(ValorZeroIncluiDescricao constraint) {
        this.valorField = constraint.valorField();
        this.descricaoField = constraint.descricaoField();
        this.descricaoObrigatoria = constraint.descricaoObrigatoria();
    }

    @Override
    public boolean isValid(Object objetoParaValidar, ConstraintValidatorContext context) {
        try {
            BigDecimal valor = (BigDecimal) BeanUtils
                    .getPropertyDescriptor(objetoParaValidar.getClass(), this.valorField).getReadMethod()
                    .invoke(objetoParaValidar);
            if (valor != null && BigDecimal.ZERO.compareTo(valor) == 0) {
                String descricao = (String) BeanUtils
                        .getPropertyDescriptor(objetoParaValidar.getClass(), this.descricaoField).getReadMethod()
                        .invoke(objetoParaValidar);
                return descricao.toLowerCase().contains(this.descricaoObrigatoria.toLowerCase());
            }
        } catch (Exception e) {
            throw new ValidationException(e);
        }
        return true;
    }

}
