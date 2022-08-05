package biz.gelicon.core.maintenance;

import biz.gelicon.core.artifacts.ArtifactKinds;
import biz.gelicon.core.artifacts.ArtifactManagerImpl;
import biz.gelicon.core.controllers.IntergatedTest;
import biz.gelicon.core.components.core.capresource.Artifact;
import biz.gelicon.core.components.core.document.Document;
import biz.gelicon.core.reports.ReportManagerImpl;
import biz.gelicon.core.components.core.application.ApplicationRepository;
import biz.gelicon.core.components.core.capresource.ArtifactRepository;
import biz.gelicon.core.components.core.document.DocumentRepository;
import biz.gelicon.core.tests.reports.r0001.ReportService0001Impl;
import biz.gelicon.core.tests.reports.r0002.ReportService0002Impl;
import biz.gelicon.core.utils.GridDataOption;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MaintenanceSystemServiceTest extends IntergatedTest {

    @BeforeAll
    public static void setup() {
        token = "e9b3c034-fdd5-456f-825b-4c632f2053ac"; //root
    }

    private static final String CONTOURE = "admin";
    private static final String MODULE = "credential";

    @Autowired
    MaintenanceSystemService maintenanceSystemService;

    /**
     * префикс пакетов системы
     */
    @Value("${gelicon.core.prefix:biz.gelicon.core}")
    private String geliconCorePrefix;

    @Autowired
    ReportManagerImpl manager;

    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    ArtifactRepository artifactRepository;

    @Autowired
    ArtifactManagerImpl artifactManager;

    @Autowired
    DocumentRepository documentRepository;

    @Autowired
    ReportManagerImpl reportManager;

    @Test
    @Transactional
    @Rollback
    public void testFillControlObject() throws Exception {
        // Актуализируем контролируемые объекты из аннотаций
        maintenanceSystemService.fillControlObject(geliconCorePrefix);

        // Проверим на наличие некоторых контроллеров по URL
        // и отсутствия ошибок
        GridDataOption options = new GridDataOption.Builder()
                .pagination(1, 50000)
                .addSort("controlObjectId", Sort.Direction.ASC)
                .addFilter("accessRoleId",1)
                .build();
        this.mockMvc.perform(post(buildUrl("controlobject/getlist",CONTOURE,MODULE))
                .content(new ObjectMapper().writeValueAsString(options))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(content().string(containsString("apps/admin/credential/controlobject")))
                .andExpect(content().string(containsString("apps/admin/credential/proguser/save#upd")))
                .andExpect(content().string(containsString("/apps/refbooks/edizm/edizm/delete")))
                .andExpect(content().string(containsString("apps/refbooks/subject/subject/gettree")))
                .andExpect(content().string(containsString("apps/admin/credential/applicationrole/allow")))
                .andExpect(content().string(containsString("apps/admin/credential/proguser/changepswd")))
                .andExpect(content().string(containsString("apps/admin/credential/artifactrole/restrict")))
                .andExpect(content().string(not(containsString("/security/changepswd"))))
                .andExpect(status().isOk());
        // todo как то бы проверить заполнение capresourceapp
    }

    @Test
    @Transactional
    @Rollback
    public void testfillCapresourceNumerators() throws Exception {
        //добавление нумератора для последующего изменения
        Artifact[] data = new Artifact[]{
                new Artifact(11, "TST_03", "Нумератор тестовый_3",
                        ArtifactKinds.NUMERATOR.getResourceType())
        };
        artifactRepository.insert(Arrays.asList(data));

        Document document = new Document(4, "Test_02", "TST02", 0, 0);
        documentRepository.insert(document);
        document = new Document(5, "Test_03", "TST03", 0, 0);
        documentRepository.insert(document);

        artifactManager.registerArtifact(ArtifactKinds.NUMERATOR.getResourceType(),"TST_02","Нумератор тестовый 2")
                .forEntity("TST02");//новый нумератор
        artifactManager.registerArtifact(ArtifactKinds.NUMERATOR.getResourceType(),"TST_03","Нумератор тестовый 3(изменен)")
                .forEntity("TST03");//измененный нумератор

        // Актуализируем нумераторы
        maintenanceSystemService.fillCapresourceNumerators();

        // Проверим capresource, почему то все это назвали артефактами
        GridDataOption options = new GridDataOption.Builder()
                .pagination(1, 50000)
                .addSort("artifactId", Sort.Direction.ASC)
                .addFilter("resourceTypeId", ArtifactKinds.NUMERATOR.getResourceType())
                .build();
        this.mockMvc.perform(post(buildUrl("artifact/getlist","admin","config"))
                .content(new ObjectMapper().writeValueAsString(options))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().string(containsString("Нумератор тестовый")))
                .andExpect(content().string(containsString("Нумератор тестовый 2")))
                .andExpect(content().string(containsString("Нумератор тестовый 3(изменен)")))
                .andExpect(content().string(not(containsString("Васядурак"))))
                .andExpect(content().string(not(containsString("Нумератор тестовый_3"))))
                .andExpect(status().isOk());
        // todo как то бы проверить заполнение capresourcenumberdoc
    }

    @Test
    @Transactional
    @Rollback
    public void testFillCapresourceReport() throws Exception {

        // Регистрируем репорты
        new ReportService0001Impl().registerReport(manager);
        new ReportService0002Impl().registerReport(manager);

        // todo доделать тест
        maintenanceSystemService.fillCapresourceReport();

        // Проверим capresource, почему то все это назвали артефактами
        GridDataOption options = new GridDataOption.Builder()
                .pagination(1, 50000)
                .addSort("artifactId", Sort.Direction.ASC)
                .addFilter("resourceTypeId", ArtifactKinds.REPORT.getResourceType())
                .build();
        this.mockMvc.perform(post(buildUrl("artifact/getlist","admin","config"))
                .content(new ObjectMapper().writeValueAsString(options))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().string(containsString("Список пользователей")))
                .andExpect(content().string(not(containsString("Васядурак"))))
                .andExpect(status().isOk());
        // Проверить capresourceapp
        int countRecords;
        List<Integer> applicationList = applicationRepository.getApplicationIdByUnitName("credential");
        countRecords = applicationList.stream()
                .map(id -> artifactRepository.getArtifactIdList(id))
                .mapToInt(artifactIdList -> applicationList.size()).sum();
        // Должны быть записи, иначе - беда
        assertNotEquals(countRecords, 0);
    }


}
