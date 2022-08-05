package biz.gelicon.core.components.erp.todo.documentrealtransit;

import biz.gelicon.core.components.erp.todo.documentreal.DocumentRealRepository;
import biz.gelicon.core.components.erp.todo.documenttransit.DocumentTransitRepository;
import biz.gelicon.core.utils.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.ConstraintViolation;
import java.util.Set;

@Component
public class DocumentRealTransitValidator implements Validator {

    @Autowired
    private javax.validation.Validator validator;

    @Autowired
    DocumentRealRepository documentrealRepository;

    @Autowired
    DocumentTransitRepository documentTransitRepository;

    // Проверка на совпадение класса
    @Override
    public boolean supports(Class<?> aClass) {
        return DocumentRealTransit.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        // вызов стандартного валидатора
        Set<ConstraintViolation<Object>> validates = validator.validate(target);
        // Формируем список ошибок
        ValidateUtils.fillErrors(errors, validates);
        if (errors.hasErrors()) {return;} // Нече дальше проверять

    }
}

