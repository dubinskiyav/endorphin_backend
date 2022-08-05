package biz.gelicon.core.utils;

import biz.gelicon.core.annotations.OnlyEdition;
import biz.gelicon.core.config.Config;
import org.springframework.validation.Errors;

import javax.validation.ConstraintViolation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Set;

public class ValidateUtils {
    public static void fillErrors(Errors errors, Set<ConstraintViolation<Object>> validates) {
        for (ConstraintViolation<Object> constraintViolation : validates) {
            errors.rejectValue(constraintViolation.getPropertyPath().toString(), "",
                    constraintViolation.getMessage());
        }
    }


    /**Метод для фильтрации ошибок
     * В данном методе, если есть ошибка, в которой не выполняется условие OnlyEdition == Config.CURRENT_EDITION_TAG
     * происходит удаление этой ошибки из validates
     *
     * @param validates Set ошибок, которые выявились при валидации
     * @param cl Класс, для которого происходит валидация
    */
    public static void filterFieldConstrains(Set<ConstraintViolation<Object>> validates, Class cl){
        validates.removeIf(objectConstraintViolation -> {
            String fieldName = objectConstraintViolation.getPropertyPath().toString();
            try {
                Field field = cl.getDeclaredField(fieldName);
                return field.isAnnotationPresent(OnlyEdition.class) &&
                        Arrays.asList(field.getAnnotation(OnlyEdition.class).tags()).stream()
                                .anyMatch(ed->ed != Config.CURRENT_EDITION_TAG);
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        });
    }

}
