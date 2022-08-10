package biz.gelicon.core.maintenance;

import biz.gelicon.artifacts.ArtifactDescription;
import biz.gelicon.core.annotations.MethodPermission;
import biz.gelicon.core.artifacts.ArtifactCapconfigKind;
import biz.gelicon.core.artifacts.ArtifactDescriptionImpl;
import biz.gelicon.core.artifacts.ArtifactKinds;
import biz.gelicon.core.artifacts.ArtifactManagerImpl;
import biz.gelicon.core.artifacts.ArtifactTranKinds;
import biz.gelicon.core.components.core.accessrole.AccessRole;
import biz.gelicon.core.components.core.accessrole.AccessRoleRepository;
import biz.gelicon.core.components.core.accessrole.AccessRoleService;
import biz.gelicon.core.components.core.accessrolerole.AccessRoleRole;
import biz.gelicon.core.components.core.accessrolerole.AccessRoleRoleRepository;
import biz.gelicon.core.components.core.application.Application;
import biz.gelicon.core.components.core.application.ApplicationRepository;
import biz.gelicon.core.components.core.capresource.Artifact;
import biz.gelicon.core.components.core.capresource.ArtifactRepository;
import biz.gelicon.core.components.core.controlobject.ControlObject;
import biz.gelicon.core.components.core.controlobject.ControlObjectRepository;
import biz.gelicon.core.components.core.document.Document;
import biz.gelicon.core.components.core.document.DocumentRepository;
import biz.gelicon.core.components.core.proguser.ProgUserRepository;
import biz.gelicon.core.components.core.proguser.Proguser;
import biz.gelicon.core.components.core.proguser.ProguserService;
import biz.gelicon.core.reports.ReportDescriptionImpl;
import biz.gelicon.core.reports.ReportManagerImpl;
import biz.gelicon.core.security.Permission;
import biz.gelicon.core.utils.ReflectUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Сервисный класс для обслуживания Системы
 *
 * @author dav
 */
@Service
public class MaintenanceSystemService {

    private static final Logger logger = LoggerFactory.getLogger(MaintenanceSystemService.class);
    private boolean developFlag = false; // Для подрбного логирования установить

    @Autowired
    ControlObjectRepository controlObjectRepository;

    @Autowired
    ReportManagerImpl reportManager;

    @Autowired
    ArtifactManagerImpl artifactManager;

    @Autowired
    ArtifactRepository artifactRepository;

    @Autowired
    DocumentRepository documentRepository;

    @Autowired
    AccessRoleRepository accessRoleRepository;
    @Autowired
    AccessRoleService accessRoleService;
    @Autowired
    ApplicationRepository applicationRepository;
    @Autowired
    ProgUserRepository progUserRepository;
    @Autowired
    ProguserService proguserService;

    /**
     * префикс пакетов системы
     */
    @Value("${gelicon.core.prefix:biz.gelicon.core}")
    private String geliconCorePrefix;

    /**
     * Вспомогательный класс
     */
    private class AccessRoleDop {

        AccessRole accessRole; // Роль доступа
        String applicationName; // Имя прилолжения с которым связывать
        List<String> urlList; // Маска урл которые добавлять
        List<Integer> accessRoleIdList; // Роли входящие в роль

        public AccessRoleDop(AccessRole accessRole, String applicationName, String url) {
            this.accessRole = accessRole;
            this.applicationName = applicationName;
            urlList = new ArrayList<>();
            urlList.add(url);
            accessRoleIdList = new ArrayList<>();
        }
    }

    /**
     * Добавление ролей для ядра для пользователя proguserName = "FULL_ACCESS"
     */
    public void fillAccessRoleCore() {
        logger.info("Filling Access Role...");
        // Проверим количество. Если больше 6 - значит уже добавлялись и добавлять не надо
        List<AccessRole> accessRoleList = accessRoleRepository.findAll();
        if (accessRoleList.size() > 6) {
            logger.info("Do nothing");
            return;
        }
        // Все пользователи
        List<Proguser> proguserList = progUserRepository.findAll();
        // Найдем пользователя Полный доступ - этому пользователю будут даваться все роли
        String proguserName = "FULL_ACCESS";
        Integer proguserId = proguserList.stream()
                .filter(proguser -> proguser.getProguserName().contains(proguserName))
                .findAny()
                .map(Proguser::getProguserId)
                .orElse(null);
        if (proguserId == null) {
            throw new RuntimeException("Не найден пользователь '" + proguserName + "'");
        }
        // Все контролируемые объекты
        List<ControlObject> controlObjectList = controlObjectRepository.findAll();
        //controlObjectList.forEach(c -> System.out.println(c.getControlObjectUrl()));
        // Все аппликации
        List<Application> applicationList = applicationRepository.findAll();
        // Список ролей, которые надо создать
        List<AccessRoleDop> accessRoleDopList = new ArrayList<>();
        AccessRoleDop ard;
        // !!!!!!!!!!!!!!! Здесь создаем роли создаваемые при инсталляции !!!!!!!!!!!!!!
        // Роль на просмотр дерева ОАУ
        int accessRoleIdSubjectTree = 100000;
        ard = new AccessRoleDop(
                new AccessRole(
                        accessRoleIdSubjectTree,
                        "_SUBJECT_TREE", //
                        "Дерево объектов аналитического учета - просмотр",
                        1),  // Эта роль будет создана
                null, // Это имя модуля с которым роль будет связана (application)
                "refbooks/subject/subject/gettree"
                // Это маска методов контроллеров, которые будут добавляться к этой роли
        );
        // Дополнительные урлы не попадающие под общую маску
        ard.urlList.add("refbooks/subject/subject/find"); // Поиск ОАУ
        ard.urlList.add("refbooks/subject/subject/getlist"); // Поиск ОАУ
        accessRoleDopList.add(ard);
        // Модуль Пользователи
        ard = new AccessRoleDop(
                new AccessRole(
                        1000,
                        "_PROGUSER", //
                        "Пользователи - ведение справочника (просмотр, модификация)",
                        1),  // Эта роль будет создана
                "Пользователи", // Это имя модуля с которым роль будет связана (application)
                "admin/credential/proguser"
                // Это маска методов контроллеров, которые будут добавляться к этой роли
        );
        // Дополнительные урлы не попадающие под общую маску
        ard.urlList.add("admin/credential/accessrole/getlist");
        /*
        ard.urlList.add("refbooks/subject/subject/gettree"); // Получение дерева ОАУ
        ard.urlList.add("refbooks/subject/subject/find"); // Поиск ОАУ
        ard.urlList.add("refbooks/subject/subject/getlist"); // Поиск ОАУ
        */
        ard.accessRoleIdList.add(accessRoleIdSubjectTree);// Добавим роль - дерево ОАУ
        accessRoleDopList.add(ard);
        // Модуль Роли
        ard = new AccessRoleDop(
                new AccessRole(
                        2000,
                        "_ROLE", //
                        "Роли - ведение справочника (просмотр, модификация)",
                        1),  // Эта роль будет создана
                "Роли", // Это имя модуля с которым роль будет связана (application)
                "admin/credential/accessrole" // Это маска методов контроллеров
        );
        // Дополнительные урлы не попадающие под общую маску
        //ard.urlList.add("admin/credential/accessrole/getlist");
        accessRoleDopList.add(ard);

        // Цикл по ним
        for (AccessRoleDop accessRoleDop : accessRoleDopList) {
            Integer applicationId = null;
            if (accessRoleDop.applicationName != null) {
                applicationId = applicationList.stream()
                        .filter(application -> application.getApplicationName()
                                .contains(accessRoleDop.applicationName))
                        .findAny()
                        .map(Application::getApplicationId)
                        .orElse(null);
            }
            /*
            if (applicationId == null) {
                throw new RuntimeException(
                        "Не найден модуль '" + accessRoleDop.applicationName + "'");
            }
            */
            // Добавим роль
            AccessRole accessRole = accessRoleService.add(accessRoleDop.accessRole);
            // Найдем все контролируемые объекты, которые надо связать с этой ролью
            List<ControlObject> lp = controlObjectList.stream()
                    .filter(c -> {
                        // По всем
                        for (String url : accessRoleDop.urlList) {
                            if (c.getControlObjectUrl().contains(url)) {
                                return true;
                            }
                        }
                        return false;
                    })
                    //.filter(c -> c.getControlObjectUrl().contains(accessRoleDop.urlList.get(0)))
                    .collect(Collectors.toList());
            // Добавим их все в controlobjectrole
            for (ControlObject controlObject : lp) {
                accessRoleRepository.bindWithControlObject(
                        accessRole.getAccessRoleId(),
                        controlObject.getControlObjectId(),
                        Permission.EXECUTE);
            }
            if (applicationId != null) {
                // Добавим доступ на аппликацию
                applicationRepository.allow(
                        accessRole.getAccessRoleId(),
                        applicationId
                );
            }
            // Роли ролей
            for (Integer accessRoleId: accessRoleDop.accessRoleIdList) {
                // Добавим роль для роли
                AccessRoleRoleRepository accessRoleRoleRepository = new AccessRoleRoleRepository();
                accessRoleRoleRepository.insert(new AccessRoleRole(
                        null,
                        accessRoleDop.accessRole.getAccessRoleId(),
                        accessRoleId)
                );
            }
            // todo доступ к печатным формам
            if (applicationId != null) { // Это не вспомогательная роль
                // Дадим пользователю FULL_ACCESS доступ на эту роль
                accessRoleRepository.bindWithProgUser(
                        accessRole.getAccessRoleId(),
                        proguserId
                );
            }
        }
        logger.info("Filling Access Role...Ok");
    }

    public void fillCapresource() {
        // Отчеты
        fillCapresourceReport();
        // Нумераторы
        fillCapresourceNumerators();
        // Функции пользователя
        fillCapresourceUserFunction();
    }

    private void fillCapresourceUserFunction() {
        logger.info("Actualizing userfunction to table Capresource ...");
        // Считаем только видимые ресурсы - userfunction из базы
        List<Artifact> resourceList = artifactRepository.getExternalArtifacts(
                ArtifactTranKinds.JAVA,
                ArtifactKinds.USER_FUNC,
                ArtifactCapconfigKind.BASIC// Базовая конфигурация
        );
        // Получим все зарегистрированные userfunction
        Collection<ArtifactDescription> artifactDescriptionCollection =
                new ArrayList<>(artifactManager.getArtifacts(ArtifactKinds.USER_FUNC));
        // Скорректируем в базе данных userfunction
        correctCapresource(
                ArtifactKinds.USER_FUNC,
                resourceList,
                artifactDescriptionCollection
        );
        logger.info("Actualizing user function to table Capresource ...Ok");
    }

    public void fillCapresourceNumerators() {
        // Нумераторы
        logger.info("Actualizing numerators to table Capresource ...");
        // Считаем ресурсы - нумераторы из базы
        List<Artifact> resourceList = artifactRepository.getExternalArtifacts(
                ArtifactTranKinds.JAVA,
                ArtifactKinds.NUMERATOR,
                ArtifactCapconfigKind.BASIC // Базовая конфигурация
        );
        // Получим все зарегистрированные нумераторы
        Collection<ArtifactDescription> artifactDescriptionCollection =
                new ArrayList<>(artifactManager.getArtifacts(ArtifactKinds.NUMERATOR));
        // Скорректируем в базе данных
        correctCapresource(
                ArtifactKinds.NUMERATOR,
                resourceList,
                artifactDescriptionCollection
        );
        logger.info("Actualizing numerators to table Capresource ...Ok");
    }

    public void fillCapresourceReport() {
        logger.info("Actualizing reports to table Capresource ...");
        // Считаем ресурсы - отчеты из базы
        List<Artifact> resourceList = artifactRepository.getExternalArtifacts(
                ArtifactTranKinds.JAVA,
                ArtifactKinds.REPORT,
                ArtifactCapconfigKind.BASIC
        );
        // Склонируем все зарегистрированные отчеты
        List<ReportDescriptionImpl> reportDescriptionList =
                new ArrayList<>(reportManager.getReports());
        // Скорректируем в базе данных все отчеты
        correctCapresourceReport(
                ArtifactKinds.REPORT,
                resourceList,
                reportDescriptionList
        );
        logger.info("Actualizing reports to table Capresource ...Ok");
    }

    /**
     * @param kind               - тип артефакта
     * @param resourceList       - список в базе данных
     * @param artifactCollection - список разегистрированных нумераторов
     */
    public void correctCapresource(
            ArtifactKinds kind,
            List<Artifact> resourceList,
            Collection<ArtifactDescription> artifactCollection
    ) {
        logDebug("correctCapresourceArtifact ...");
        Map<String, Artifact> artifactMap = resourceList.stream()
                .filter(a -> a.getArtifactCode() != null)
                .collect(Collectors.toMap(Artifact::getArtifactCode, Function.identity(),
                        (r1, r2) -> r1));
        Map<String, Integer> documentIds = documentRepository.findAll().stream()
                .filter(d -> d.getDocumentShortname() != null)
                // придется исключать дубликаты так как в бд не по Shortname нет уникальности
                .collect(Collectors.toMap(Document::getDocumentShortname, Document::getDocumentId,
                        (r1, r2) -> r1));
        Map<String, List<Integer>> codeArtifact = artifactRepository.findAll().stream()
                .filter(a -> a.getArtifactCode() != null)
                .collect(Collectors.groupingBy(Artifact::getArtifactCode,
                        Collectors.mapping(Artifact::getArtifactId, Collectors.toList())));
        List<Map<String, Object>> countMatchesList = artifactRepository.getDocumentBindingCounts();
        // Сравниваем по коду
        artifactCollection
                .stream()
                .map(ac -> (ArtifactDescriptionImpl) ac)
                .forEach(ac -> {
                    String artifactCode = ac.getCode();
                    logDebug("artifact" + artifactCode);
                    // Поиск ресурса по коду
                    Artifact db = artifactMap.get(artifactCode);
                    if (db != null) {
                        // Ресурс найден
                        // Меняем наименование, если оно отличается и обновляем в бд
                        if (!db.getArtifactName().equals(ac.getName())) {
                            db.setArtifactName(ac.getName());
                            db.setArtifactLastmodify(new Date());
                            artifactRepository.updateArtifactByCode(db);
                            logger.info(db.getArtifactName() + " updated");
                        }
                    } else {
                        //если по коду не нашли, то добавляем в бд
                        Artifact artifact = new Artifact();
                        artifact.setArtifactName(ac.getName());
                        artifact.setArtifactCode(ac.getCode());
                        artifact.setResourceTypeId(kind.getResourceType());
                        artifact.setArtifactDate(new Date());
                        artifact.setArtifactLastmodify(artifact.getArtifactDate());
                        artifact.setProguserId(Proguser.SYSDBA_PROGUSER_ID);
                        artifact.setLastProguserId(Proguser.SYSDBA_PROGUSER_ID);
                        artifact.setArtifactVisibleFlag(1);// Видимость
                        artifactRepository.insert(artifact);
                        List<Integer> resources = artifactRepository.getArtifactIdByCode(
                                ac.getCode());
                        if (resources != null) {
                            codeArtifact.put(ac.getCode(), resources);
                        }
                    }

                    // Добавим связь с сущностью
                    String entityName = ac.getEntityName();
                    if (entityName != null) {
                        // Получим из имени documentId
                        Integer documentId = documentIds.get(entityName);
                        if (documentId != null) {//если документ найден
                            List<Integer> resources = codeArtifact.get(ac.getCode());
                            // Отфильтруем только то, для чего нет связи - с помощью countMatchesList
                            resources.stream()
                                    .filter(resource -> !countMatchesList.stream().anyMatch(entry ->
                                            (entry.get("document_id").equals(documentId)
                                                    && entry.get("capresource_id")
                                                    .equals(resource))))
                                    .forEach(resource -> {
                                        artifactRepository.insertDocumentBinding(resource,
                                                Arrays.asList(documentId));
                                    });
                        }
                    }
                });
        logDebug("correctCapresourceArtifact ...Ok");
    }

    /**
     * @param kind                  - тип артефакта
     * @param resourceList          - список в базе данных
     * @param reportDescriptionList - список разегистрированных репортов
     */
    public void correctCapresourceReport(
            ArtifactKinds kind,
            List<Artifact> resourceList,
            List<ReportDescriptionImpl> reportDescriptionList
    ) {
        logDebug("correctCapresourceReport ...");
        Map<String, Artifact> artifactMap = resourceList.stream()
                .filter(a -> a.getArtifactCode() != null)
                .collect(Collectors.toMap(Artifact::getArtifactCode, Function.identity(),
                        (r1, r2) -> r1));
        Map<String, Integer> documentIds = documentRepository.findAll().stream()
                .filter(d -> d.getDocumentShortname() != null && d.getDocumentShortname().isEmpty())
                // придется исключать дубликаты так как в бд не по Shortname нет уникальности
                .collect(Collectors.toMap(Document::getDocumentShortname, Document::getDocumentId,
                        (r1, r2) -> r1));
        Map<String, List<Integer>> codeArtifact = artifactRepository.findAll().stream()
                .filter(a -> a.getArtifactCode() != null)
                .collect(Collectors.groupingBy(Artifact::getArtifactCode,
                        Collectors.mapping(Artifact::getArtifactId, Collectors.toList())));
        List<Map<String, Object>> countMatchesList = artifactRepository.getDocumentBindingCounts();
        // Сравниваем по коду
        reportDescriptionList
                .forEach(ac -> {
                    String artifactCode = ac.getCode();
                    logDebug("artifact" + artifactCode);
                    // Поиск ресурса по коду
                    Artifact db = artifactMap.get(artifactCode);
                    if (db != null) {
                        // Ресурс найден
                        // Меняем наименование, если оно отличается и обновляем в бд
                        if (!db.getArtifactName().equals(ac.getName())) {
                            db.setArtifactName(ac.getName());
                            db.setArtifactLastmodify(new Date());
                            artifactRepository.updateArtifactByCode(db);
                            logger.info(db.getArtifactName() + " updated");
                        }
                    } else {
                        //если по коду не нашли, то добавляем в бд
                        Artifact artifact = new Artifact();
                        artifact.setArtifactName(ac.getName());
                        artifact.setArtifactCode(ac.getCode());
                        artifact.setResourceTypeId(kind.getResourceType());
                        artifact.setArtifactDate(new Date());
                        artifact.setArtifactLastmodify(artifact.getArtifactDate());
                        artifact.setProguserId(Proguser.SYSDBA_PROGUSER_ID);
                        artifact.setLastProguserId(Proguser.SYSDBA_PROGUSER_ID);
                        artifact.setArtifactVisibleFlag(1);// Видимость
                        artifactRepository.insert(artifact);
                        List<Integer> resources = artifactRepository.getArtifactIdByCode(
                                ac.getCode());
                        if (resources != null) {
                            codeArtifact.put(ac.getCode(), resources);
                        }
                    }

                    // Добавим связь с сущностью
                    String entityName = ac.getEntityName();
                    if (entityName != null) {
                        // Получим из имени documentId
                        Integer documentId = documentIds.get(entityName);
                        if (documentId != null) {//если документ найден
                            List<Integer> resources = codeArtifact.get(ac.getCode());
                            // Отфильтруем только то, для чего нет связи - с помощью countMatchesList
                            resources.stream()
                                    .filter(resource -> !countMatchesList.stream().anyMatch(entry ->
                                            (entry.get("document_id").equals(documentId)
                                                    && entry.get("capresource_id")
                                                    .equals(resource))))
                                    .forEach(resource -> {
                                        artifactRepository.insertDocumentBinding(resource,
                                                Arrays.asList(documentId));
                                    });
                        }
                    }
                });
        logDebug("correctCapresourceReport ...Ok");
    }

    /**
     * автоматическое заполнение таблицы ControlObject при запуске бекенда Сканирует все аннотации
     * RestController
     *
     * @param prefix префикс пакетов для сканирования, например, "biz.gelicon.core"
     */
    public void fillControlObject(String prefix) {
        logger.info("Actualizing table ControlObject ...");
        if (prefix == null) {
            prefix = "biz.gelicon.core";
        }
        // Считаем все объекты контроля из базы.
        List<ControlObject> controlObjectList = controlObjectRepository.findAll();
        // Получим все контроллеры просканировав пакеты
        logDebug("Reading @RestController annotation...");
        // Считываем все аннотации RestController
        Set<Class<?>> set = getTypesAnnotatedWithRestController(prefix);
        // Получим все аннотации всех методов контроллеров
        List<AnnotationControlObject> annotationControlObjectList = getAnnotationControlObjectList(
                set);
        // Скорректируем базу данных
        correctControlObjectRepository(controlObjectList, annotationControlObjectList);
        logger.info("Actualizing table ControlObject ...Ok");
    }

    /**
     * Корректирует ControlObject в базе на основе всех аннотаций всех методов
     */
    public void correctControlObjectRepository(
            List<ControlObject> controlObjectList,
            List<AnnotationControlObject> annotationControlObjectList
    ) {
        // Удалим из базы все, которых нет в аннотациях
        // Сравниваем по УРЛу
        controlObjectList.forEach(c -> { // Цикл по всем объектам из базы
            // Попробуем найти в аннотациях по урл
            AnnotationControlObject aco = annotationControlObjectList.stream()
                    .filter(a -> a.getControlObjectUrl().equals(c.getControlObjectUrl()))
                    .findFirst()
                    .orElse(null);
            if (aco != null) { // Есть в аннотациях - update
                // Подправим наименование, если они не равны
                String newName = aco.controllerTagName + ": " + aco.methodOperationSummary;
                if (!c.getControlObjectName().equals(newName)) {
                    c.setControlObjectName(newName);
                    controlObjectRepository.update(c); // изменим в бд
                }
                // Удалим из коллекции, чтобы потом не проверять
                annotationControlObjectList.remove(aco);
            } else { // Нет в аннотациях - delete
                controlObjectRepository.deleteCascade(c.controlObjectId);
            }
        });
        // Скорректируем генератор
        controlObjectRepository.correctSequence();
        // Добавим из аннотаций оставшиеся, которых нет в базе
        annotationControlObjectList.forEach(a -> {
            ControlObject co = new ControlObject();
            co.setControlObjectName(a.controllerTagName + ": " + a.methodOperationSummary);
            co.setControlObjectUrl(a.getControlObjectUrl());
            controlObjectRepository.insert(co);
        });
    }

    /**
     * Возвращает все аннотации всех методов аннотированных RequestMapping для списка классов,
     * переданных в качестве параметра
     *
     * @return список объектов
     */
    public List<AnnotationControlObject> getAnnotationControlObjectList(
            Set<Class<?>> classSet
    ) {
        List<AnnotationControlObject> list = new ArrayList<>();
        // Цикл по всем классам, аннотированным как RestController
        classSet.forEach(s -> {
            list.addAll(getAnnotationControlObjectClassList(s));
        });
        return list;
    }

    /**
     * Возвращает список описания аннотированных методов для класса
     *
     * @param s
     * @return
     */
    public List<AnnotationControlObject> getAnnotationControlObjectClassList(
            Class<?> s
    ) {
        List<AnnotationControlObject> list = new ArrayList<>();
        String controllerClassName = s.getName(); // Имя контроллера
        logDebug(controllerClassName);
        Annotation[] annotations = s.getAnnotations(); // Все аннотации контроллера
        // Найдем аннотацию MethodPermission
        if (isMethodPermissionNoStore(annotations)) {
            // если она есть - не добавляем контроллер
            logDebug("  Установлен MethodPermissionNoStore - не добавляем");
            return list;
        }
        // Найдем аннотацию Tag
        String controllerTagName = getControllerTagName(annotations);
        // Если аннотации нет - просто имя класса
        if (controllerTagName == null) {
            controllerTagName = controllerClassName;
        }
        logDebug(" " + controllerTagName);
        // Найдем аннотацию RequestMapping
        String controllerRequestMappingValue = getRequestMappingValue(annotations);
        logDebug("   " + controllerRequestMappingValue);
        // Список методов
        Method[] methods = s.getMethods();
        String finalControllerTagName = controllerTagName;
        Arrays.stream(methods) // Цикл по методам
                .forEach(m -> {
                    String methodName = m.getName(); // Имя метода
                    logDebug("   " + methodName);
                    // Список аннотаций метода
                    Annotation[] mAnnotations = m.getAnnotations();
                    // Найдем аннотацию Operation для метода
                    String methodOperationSummary = getOperationSummary(mAnnotations);
                    // Если аннотации нет - просто имя метода
                    if (methodOperationSummary == null) {
                        methodOperationSummary = methodName;
                    }
                    logDebug("      " + methodOperationSummary);
                    String methodOperationDescription = getOperationDescription(
                            mAnnotations);
                    logDebug("      " + methodOperationDescription);
                    // Найдем аннотацию RequestMapping value для метода
                    String methodRequestMappingValue = getRequestMappingValue(
                            mAnnotations);
                    logDebug("      " + methodRequestMappingValue);
                    // Урл у метода должен быть
                    if (methodRequestMappingValue == null) {
                        return;
                    }
                    // сохранять этот метод или нет
                    boolean needToSave = ReflectUtils
                            .isRequestMappingMethodWithSuffix(m, methodRequestMappingValue);
                    if (!needToSave) {
                        // Писать не надо
                        logDebug("         Писать не надо");
                        return;
                    }
                    // Полный урл
                    String controlObjectUrl = controllerRequestMappingValue + "/"
                            + methodRequestMappingValue;
                    Map<String, String> methodList = new HashMap<>();
                    // Проверим, не save ли это метод
                    if (ReflectUtils.isSaveMethod(m)) { // Надо создать две записи
                        methodList.put(
                                controlObjectUrl + "#ins",
                                methodOperationSummary + " - вставка"
                        );
                        methodList.put(
                                controlObjectUrl + "#upd",
                                methodOperationSummary + " - изменение"
                        );
                    } else { // Одна запись
                        methodList.put(
                                controlObjectUrl,
                                methodOperationSummary
                        );
                    }
                    // Добавим две или одну записи
                    for (Map.Entry<String, String> entry : methodList.entrySet()) {
                        // Сожраним в коллекции
                        list.add(new AnnotationControlObject(
                                controllerClassName,
                                finalControllerTagName,
                                controllerRequestMappingValue,
                                methodName,
                                entry.getValue(),
                                methodOperationDescription,
                                methodRequestMappingValue,
                                entry.getKey(),
                                needToSave
                        ));
                    }
                });
        return list;
    }

    private void logDebug(String message) {
        if (developFlag) {
            logger.info(message);
        }
    }

    /**
     * Считываем все аннотации RestController
     *
     * @param prefix Откуда считывать
     * @return
     */
    public Set<Class<?>> getTypesAnnotatedWithRestController(String prefix) {
        Reflections reflections = new Reflections(prefix); // Рефлектор
        return reflections.getTypesAnnotatedWith(RestController.class);
    }

    public void setDevelopFlag(boolean developFlag) {
        this.developFlag = developFlag;
    }

    /**
     * Возвращает аннотацию Tag name для контроллера
     *
     * @param annotations
     * @return
     */
    public String getControllerTagName(Annotation[] annotations) {
        // Найдем аннотацию Tag
        return Arrays.stream(annotations)
                .filter(Tag.class::isInstance)
                .map(a -> ((Tag) a).name())
                .findAny()
                .orElse(null);
    }

    /**
     * Проверяет наличие аннотации MethodPermission с noStore=true
     *
     * @param annotations - список аннотаций
     * @return если есть и true - возвращает true, иначе - false
     */
    public boolean isMethodPermissionNoStore(Annotation[] annotations) {
        // Найдем аннотацию MethodPermission
        return Arrays.stream(annotations)
                .filter(MethodPermission.class::isInstance)
                .map(a -> ((MethodPermission) a).noStore())
                .findAny()
                .orElse(false);
    }

    /**
     * Возвращает аннотацию Tag value для контроллера или метода
     *
     * @param annotations
     * @return
     */
    public String getRequestMappingValue(Annotation[] annotations) {
        // Найдем аннотацию Tag
        return Arrays.stream(annotations)
                .filter(RequestMapping.class::isInstance)
                .map(a -> ((RequestMapping) a).value()[0])
                .findAny()
                .orElse(null);
    }

    /**
     * Возвращает аннотацию Operation description для метода
     *
     * @param annotations
     * @return
     */
    public String getOperationDescription(Annotation[] annotations) {
        // Найдем аннотацию Operation
        return Arrays.stream(annotations)
                .filter(Operation.class::isInstance)
                .map(a -> ((Operation) a).description())
                .findAny()
                .orElse(null);
    }

    /**
     * Возвращает аннотацию Operation summary для метода
     *
     * @param annotations
     * @return
     */
    public String getOperationSummary(Annotation[] annotations) {
        // Найдем аннотацию Operation
        return Arrays.stream(annotations)
                .filter(Operation.class::isInstance)
                .map(a -> ((Operation) a).summary())
                .findAny()
                .orElse(null);
    }

}
