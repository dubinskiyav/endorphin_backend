package biz.gelicon.core.components.erp.todo.documentreal;

import biz.gelicon.core.utils.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.ConstraintViolation;
import java.util.Set;

@Component
public class DocumentRealValidator implements Validator {

    @Autowired
    private javax.validation.Validator validator;
    @Autowired
    private DocumentRealRepository documentRealRepository;

    // Проверка на совпадение класса
    @Override
    public boolean supports(Class<?> aClass) {
        return DocumentReal.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        DocumentReal docreal = (DocumentReal) target;
        boolean modeInsert = docreal.getDocumentId() == null;

        // вызов стандартного валидатора
        Set<ConstraintViolation<Object>> validates = validator.validate(target);
        // Формируем список ошибок
        ValidateUtils.fillErrors(errors, validates);

        if(!errors.hasErrors()) {
            //проверяем уникальность номеров в соответствии с uniquetype
            if(!documentRealRepository.validateUniqueNumber(docreal)) {
                errors.rejectValue("documentRealNumber",
                        String.format("Документ с номером %s уже существует в заданном интервале уникальности",
                                docreal.getDocumentRealNumber()));
            }

            if(!modeInsert) {
                // если пытаемся разместить документ в самом себе
                if(docreal.getParentId()==docreal.getDocumentRealId()) {
                    errors.rejectValue("documentRealId","Неверный идентификатор родительского документа. Нельзя поместить документ внутрь себя.");
                }
            }
            // является ли родительский document папкой или композитным документом
            DocumentReal parentDoc = documentRealRepository.findById(docreal.getParentId());
            if(parentDoc.getDocumentRealLevel()==DocumentReal.DocumentLevel.SIMPLE_DOCUMENT.ordinal()) {
                errors.rejectValue("documentRealId","Неверный тип родительского документа. Он не может содержать другие документы");
            }

        }

    }
}

