package biz.gelicon.core.components.erp.todo.appendix;

import biz.gelicon.core.utils.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.ConstraintViolation;
import java.util.Set;

@Component
public class AppendixValidator implements Validator {

    @Autowired
    private javax.validation.Validator validator;

    // Проверка на совпадение класса
    @Override
    public boolean supports(Class<?> aClass) {
        return Appendix.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        // вызов стандартного валидатора
        Set<ConstraintViolation<Object>> validates = validator.validate(target);
        // Формируем список ошибок
        ValidateUtils.fillErrors(errors, validates);

    }
}

