package biz.gelicon.core.controllers;

import biz.gelicon.core.components.core.company.CompanyController;
import biz.gelicon.core.components.core.company.CompanyDTO;
import biz.gelicon.core.dto.SelectDisplayData;
import biz.gelicon.core.components.core.capclass.CapClass;
import biz.gelicon.core.components.core.capclasstype.CapClassType;
import biz.gelicon.core.components.core.company.Company;
import biz.gelicon.core.components.core.subject.Subject;
import biz.gelicon.core.components.core.capclass.CapClassRepository;
import biz.gelicon.core.components.core.capclasstype.CapClassTypeRepository;
import biz.gelicon.core.components.core.subject.SubjectRepository;
import biz.gelicon.core.utils.GridDataOption;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CompanyTest extends IntergatedTest {

    @BeforeAll
    public static void setup() {
        token = "e9b3c034-fdd5-456f-825b-4c632f2053ac"; //root
    }


    @Autowired
    private CapClassRepository capClassRepository;
    @Autowired
    private CapClassTypeRepository capClassTypeRepository;
    @Autowired
    private SubjectRepository subjectRepository;

    @Test
    public void findTest() throws Exception {
        this.mockMvc.perform(post(buildUrl("/apps/refbooks/company/company/find"))
                .content("{\"search\":\"опытов\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Рогов")));
    }


    @Test
    @Transactional
    @Rollback
    public void findWithAttrTest() throws Exception {
        CompanyController.CompanySearchOption options = new CompanyController.CompanySearchOption();
        options.setSearch("опытов");
        options.setAttributeId(4);

        this.mockMvc.perform(post(buildUrl("/apps/refbooks/company/company/find"))
                .content(new ObjectMapper().writeValueAsString(options))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("[]")));
    }

    @Test
    @Transactional
    @Rollback
    public void saveTest() throws Exception{
        loadData();

        CompanyDTO dto = new CompanyDTO(null, new SelectDisplayData<>(1, "Пермь"),"Рогов и Копытов, ООО", null,
                    "ООО \"Рогов и Копытов\"", "ООО \"Рогов и Копытов\"", null, "г.Пермь, ул. Ленина 46",
                    "+79883456745", "test@mail.ru", null, "6836574365", "Продают все кроме родины",
                    null, 1, 0, 1,
                    new Date(), 0, 0, 0, 0, "г.Пермь, ул. Ленина 46", 1);

        this.mockMvc.perform(post(buildUrl("/apps/refbooks/company/company/save"))
                .content(new ObjectMapper().writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Рогов и Копытов")))
                .andExpect(content().string(containsString("\"companyId\":9")))
                .andExpect(content().string(containsString("\"companyInn\":\"6836574365\"")))
                .andExpect(content().string(containsString("\"companyExcavation\":1")))
                .andExpect(content().string(containsString("companyBank")))
                .andExpect(content().string(containsString("companyAddressFact")));

        dto = new CompanyDTO(9, new SelectDisplayData<>(1, "Пермь"),"Рогов и Копытов, ООО", null,
                "ООО \"Рогов и Копытов\"", "ООО \"Рогов и Копытов\"", null, "г.Пермь, ул. Ленина 46",
                "+79883456745", "test@mail.ru", null, "29854923458", "Продают все кроме родины",
                null, 1, 0, 0,
                new Date(), 0, 0, 0, 0, "г.Пермь, ул. Ленина 46", 1);
        dto.setCompanyKpp("7834726723");

        this.mockMvc.perform(post(buildUrl("/apps/refbooks/company/company/save"))
                .content(new ObjectMapper().writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Рогов и Копытов")))
                .andExpect(content().string(containsString("\"companyId\":9")))
                .andExpect(content().string(containsString("\"companyInn\":\"29854923458\"")))
                .andExpect(content().string(containsString("\"companyKpp\":\"7834726723\"")))
                .andExpect(content().string(containsString("\"companyExcavation\":0")))
                .andExpect(content().string(containsString("companyBlockFlag")))
                .andExpect(content().string(containsString("townFactId")));

        dto.setCompanyInn(null);
        dto.setCompanyKpp(null);

        this.mockMvc.perform(post(buildUrl("/apps/refbooks/company/company/save"))
                .content(new ObjectMapper().writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Рогов и Копытов")))
                .andExpect(content().string(containsString("\"companyId\":9")))
                .andExpect(content().string(containsString("\"companyInn\":null")))
                .andExpect(content().string(containsString("\"companyKpp\":null")));
    }

    @Test
    @Transactional
    @Rollback
    public void deleteTest() throws Exception{
        loadData();

        CompanyDTO dto = new CompanyDTO(null, new SelectDisplayData<>(1, "Пермь"),"Рогов и Копытов, ООО", null,
                "ООО \"Рогов и Копытов\"", "ООО \"Рогов и Копытов\"", null, "г.Пермь, ул. Ленина 46",
                "+79883456745", "test@mail.ru", null, "6836574365", "Продают все кроме родины",
                null, 1, 0, 1,
                new Date(), 0, 0, 0, 0, "г.Пермь, ул. Ленина 46", 1);

        this.mockMvc.perform(post(buildUrl("/apps/refbooks/company/company/save"))
                .content(new ObjectMapper().writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        // базовая проверка
        GridDataOption options = new GridDataOption.Builder()
                .pagination(1, 25)
                .addSort("companyId", Sort.Direction.ASC)
                .build();

        this.mockMvc.perform(post(buildUrl("/apps/refbooks/company/company/getlist"))
                .content(new ObjectMapper().writeValueAsString(options))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"rowCount\":"+4)))
                .andExpect(content().string(containsString("companyBank")))
                .andExpect(content().string(containsString("companyAddressFact")))
                .andExpect(content().string(containsString("companyBlockFlag")))
                .andExpect(content().string(containsString("townFactId")));

        int[] ids = {10};
        this.mockMvc.perform(post(buildUrl("/apps/refbooks/company/company/delete"))
                .content(new ObjectMapper().writeValueAsString(ids))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"status\": \"success\"")));

        // базовая проверка
        options = new GridDataOption.Builder()
                .pagination(1, 25)
                .addSort("companyId", Sort.Direction.ASC)
                .build();

        this.mockMvc.perform(post(buildUrl("/apps/refbooks/company/company/getlist"))
                .content(new ObjectMapper().writeValueAsString(options))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"rowCount\":"+3)))
                .andExpect(content().string(containsString("companyUpdDate")))
                .andExpect(content().string(containsString("companyDuplicate")))
                .andExpect(content().string(containsString("companyPhysicalPerson")));
    }

    @Test
    @Transactional
    @Rollback
    public void getTest() throws Exception{
        this.mockMvc.perform(post(buildUrl("/apps/refbooks/company/company/get"))
                .content(new ObjectMapper().writeValueAsString(6))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Рогов и Копытов")))
                .andExpect(content().string(not(containsString("Постоянные ребята"))))
                .andExpect(content().string(not(containsString("Лен и Ого"))))
                .andExpect(content().string(containsString("companyUpdDate")))
                .andExpect(content().string(containsString("companyDuplicate")))
                .andExpect(content().string(containsString("companyPhysicalPerson")));
    }

    private void loadData(){
        CapClassType[] capClassTypes = new CapClassType[]{
                new CapClassType(53, 4, "53", "Коды контрагента")
        };
        capClassTypeRepository.insert(Arrays.asList(capClassTypes));

        CapClass[] capClasses = new CapClass[]{
                new CapClass(Company.CAPCODE_INN, 53, "ИНН", "ИНН",
                        null, null, null, null,
                        0, null),
                new CapClass(Company.CAPCODE_KPP, 53, "КПП", "КПП",
                        null, null, null, null,
                        0, null)
        };
        capClassRepository.insert(Arrays.asList(capClasses));

        String sql = "INSERT INTO clusterr VALUES (:clusterId)";
        subjectRepository.executeSQL(sql, "clusterId", 28);

        Subject[] subjects = new Subject[]{
                subjectRepository.newFolder(Company.ROOT_SUBJECT_ID, 1, "Статьи БДР", 1),
                subjectRepository.newFolder(Company.PARENT_ID, 1, "Балансодержатели", 1)
        };
        subjectRepository.insert(Arrays.asList(subjects));
    }
}
