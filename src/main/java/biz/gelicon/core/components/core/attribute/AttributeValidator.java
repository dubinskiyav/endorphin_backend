package biz.gelicon.core.components.core.attribute;

import biz.gelicon.core.components.core.capcode.CapCode;
import biz.gelicon.core.utils.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.ConstraintViolation;
import java.util.Set;

@Component
public class AttributeValidator implements Validator {

    @Autowired
    private javax.validation.Validator validator;
    @Autowired
    private AttributeRepository attributeRepository;

    // Проверка на совпадение класса
    @Override
    public boolean supports(Class<?> aClass) {
        return Attribute.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        // вызов стандартного валидатора
        Set<ConstraintViolation<Object>> validates = validator.validate(target);
        // Формируем список ошибок
        ValidateUtils.fillErrors(errors, validates);

        Attribute attribute = (Attribute) target;

        if(!errors.hasErrors()) {
            Attribute original = attributeRepository.findById(attribute.getId());
            // тип атрибута менять нельзя
            if(original!=null && !original.getCapcodeId().equals(attribute.getCapcodeId())) {
                errors.rejectValue("capcodeId", "", "Нельзя изменить тип атрибута");
            }
            // проверка условно обязательных полей
            if(attribute.getCapcodeId()== CapCode.TYPEATTR_SUBJECT) {
                if(attribute.getSubjectId()==null) {
                    errors.rejectValue("subjectId", "", "Необходимо определить уровень ОАУ");
                }
            } else {
                attribute.setSubjectId(null);
            }
            if(attribute.getCapcodeId()== CapCode.TYPEATTR_REFBOOK) {
                if(attribute.getCapclasstypeId()==null) {
                    errors.rejectValue("capclasstypeId", "", "Необходимо определить Тип справочника");
                }
            } else {
                attribute.setCapclasstypeId(null);
            }
        }

    }
}

