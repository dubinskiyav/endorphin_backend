package biz.gelicon.core.components.core.company;

import biz.gelicon.core.utils.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.ConstraintViolation;
import java.util.Set;

@Component
public class CompanyValidator implements Validator {

    @Autowired
    private javax.validation.Validator validator;

    // Проверка на совпадение класса
    @Override
    public boolean supports(Class<?> aClass) {
        return Company.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        // вызов стандартного валидатора
        Set<ConstraintViolation<Object>> validates = validator.validate(target);
        // проверка полей, чтобы у всех было EditionTag == Config.CURRENT_EDITION_TAG
        ValidateUtils.filterFieldConstrains(validates, Company.class);
        // Формируем список ошибок
        ValidateUtils.fillErrors(errors, validates);
        //TODO другие проверки

    }
}

