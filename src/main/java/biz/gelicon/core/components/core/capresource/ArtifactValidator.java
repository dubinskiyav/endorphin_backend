package biz.gelicon.core.components.core.capresource;

import biz.gelicon.core.artifacts.ArtifactKinds;
import biz.gelicon.core.utils.UsefulUtils;
import biz.gelicon.core.utils.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.ConstraintViolation;
import java.util.Set;

@Component
public class ArtifactValidator implements Validator {

    @Autowired
    private javax.validation.Validator validator;
    @Autowired
    private ArtifactRepository  artifactRepository;

    // Проверка на совпадение класса
    @Override
    public boolean supports(Class<?> aClass) {
        return Artifact.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        // вызов стандартного валидатора
        Set<ConstraintViolation<Object>> validates = validator.validate(target);
        // Формируем список ошибок
        ValidateUtils.fillErrors(errors, validates);

        Artifact artifact = (Artifact) target;

        if(!errors.hasErrors()) {
            boolean modeEdit = artifact.getArtifactId() != null;
            Artifact original = null;
            if(modeEdit) {
                original = artifactRepository.findById(artifact.getArtifactId());
            }

            // Правила для ПФ и нумераторов
            if(UsefulUtils.indexOf(ArtifactKinds.getExternalResourceType(),artifact.getResourceTypeId())>=0) {
                //для ПФ и нумераторов запретить менять Код артефакта
                // так как код и имя артефакта в библиотеках
                if(modeEdit) {
                    if(!original.getArtifactCode().equalsIgnoreCase(artifact.getArtifactCode())) {
                        errors.rejectValue("artifactCode", "", "Для данного типа артефакта изменение кода запрещено");
                    }
                    if(!original.getArtifactName().equalsIgnoreCase(artifact.getArtifactName())) {
                        errors.rejectValue("artifactName", "", "Для данного типа артефакта изменение имени запрещено");
                    }
                }
                // вставки таких ресурсов запрещена
                else {
                    errors.rejectValue("artifactId", "", "Вставка артефактов данного типа запрещена");

                }
            }
            // Правила. При изменении нельзя менять типы всякие
            if(modeEdit) {
                if(original.getResourceTypeId()!=artifact.getResourceTypeId()) {
                    errors.rejectValue("resourceTypeId", "", "Изменение типа артефакта запрещено");
                }
                if(original.getResourcetrantypeId() !=artifact.getResourcetrantypeId()) {
                    errors.rejectValue("resourcetrantypeId", "", "Изменение языка реализации артефакта запрещено");
                }

            }
        }

    }

}

