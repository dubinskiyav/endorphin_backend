package biz.gelicon.core.controllers;

import biz.gelicon.core.artifact.TestNumerator;
import biz.gelicon.core.artifact.TestUserFunc;
import biz.gelicon.core.artifacts.ArtifactKinds;
import biz.gelicon.core.artifacts.ArtifactManagerImpl;
import biz.gelicon.core.components.core.capresource.ArtifactDTO;
import biz.gelicon.core.components.core.capresource.ArtifactDescDTO;
import biz.gelicon.core.utils.GridDataOption;
import biz.gelicon.core.components.core.capresource.ArtifactView;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ArtifactTest extends IntergatedTest {
    private static final String CONTOURE = "admin";
    private static final String MODULE = "config";

    @Autowired
    ArtifactManagerImpl artifactManager;

    @BeforeAll
    public static void setup() {
        token = "e9b3c034-fdd5-456f-825b-4c632f2053ac"; //root
    }

    @BeforeEach
    public void before() {
        new TestNumerator().registerManager(artifactManager);
        new TestUserFunc().registerManager(artifactManager);
    }


    @Test
    public void selectTest() throws Exception {
        // базовая проверка
        GridDataOption options = new GridDataOption.Builder()
                .pagination(1, 25)
                .addSort("artifactName", Sort.Direction.ASC)
                .build();

        this.mockMvc.perform(post(buildUrl("artifact/getlist",CONTOURE,MODULE))
                .content(new ObjectMapper().writeValueAsString(options))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"rowCount\":4")));

        // базовая проверка
        options = new GridDataOption.Builder()
                .pagination(1, 25)
                .addSort("artifactName", Sort.Direction.ASC)
                .addFilter("resourceTypeId",ArtifactKinds.REPORT.getResourceType())
                .build();

        this.mockMvc.perform(post(buildUrl("artifact/getlist",CONTOURE,MODULE))
                .content(new ObjectMapper().writeValueAsString(options))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"rowCount\":1")));

    }

    @Test
    public void selectForRunTest() throws Exception {
        GridDataOption options = new GridDataOption.Builder()
                .addFilter("entity","test")
                .addFilter("kind", ArtifactKinds.NUMERATOR.getResourceType())
                .build();

        this.mockMvc.perform(post(buildUrl("list-of-call","artifacts"))
                .content(new ObjectMapper().writeValueAsString(options))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"rowCount\":1")));

    }

    @Test
    public void runTest() throws Exception {
        ArtifactDescDTO dto = new ArtifactDescDTO();
        dto.setCode("TST_01");
        dto.setKind(ArtifactKinds.NUMERATOR.getResourceType());
        Map<String,Object> params =  new HashMap<>();
        params.put("year",Integer.valueOf(2021));
        dto.setParams(params);

        this.mockMvc.perform(post(buildUrl("run","artifacts"))
                .content(new ObjectMapper().writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"value\":")));

    }

    @Test
    public void runTestUDF() throws Exception {
        ArtifactDescDTO dto = new ArtifactDescDTO();
        dto.setCode("UDF_SUM");
        dto.setKind(ArtifactKinds.USER_FUNC.getResourceType());
        Map<String,Object> params =  new HashMap<>();
        params.put("s1",Integer.valueOf(3));
        params.put("s2",Integer.valueOf(2));
        dto.setParams(params);

        this.mockMvc.perform(post(buildUrl("run","artifacts"))
                .content(new ObjectMapper().writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"value\":5")));

    }

    @Test
    public void searchFullText() throws Exception {

        GridDataOption options = new GridDataOption.Builder()
                .pagination(1, 25)
                .search("мератор")
                .build();

        this.mockMvc.perform(post(buildUrl("artifact/getlist",CONTOURE,MODULE))
                .content(new ObjectMapper().writeValueAsString(options))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"rowCount\":1")));

    }

    @Test
    @Transactional
    @Rollback
    public void updateTest() throws Exception {

        // проверка получения записи для редактирования
        MvcResult result = this.mockMvc.perform(post(buildUrl("artifact/get",CONTOURE,MODULE))
                .content("1")
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(not(containsString("\"errorMessage\":"))))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        ArtifactDTO dto = new ObjectMapper().readValue(content,ArtifactDTO.class);

        // проверка сохранения изменений
        dto.setArtifactVisibleFlag(0);

        this.mockMvc.perform(post(buildUrl("artifact/save",CONTOURE,MODULE))
                .content(new ObjectMapper().writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(not(containsString("\"errorMessage\":"))));

        // проверка ошибки измененеи кода для ПФ
        dto.setArtifactCode("тавкутлпкутп");

        this.mockMvc.perform(post(buildUrl("artifact/save",CONTOURE,MODULE))
                .content(new ObjectMapper().writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"errorMessage\":")));

    }

    @Test
    public void insertAndDeleteTest() throws Exception {
        // проверка получения записи для вставки
        MvcResult result = this.mockMvc.perform(post(buildUrl("artifact/get",CONTOURE,MODULE))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(not(containsString("\"errorMessage\":"))))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        ArtifactDTO dto = new ObjectMapper().readValue(content,ArtifactDTO.class);

        // проверка сохранения c ошибками
        this.mockMvc.perform(post(buildUrl("artifact/save",CONTOURE,MODULE))
                .content(new ObjectMapper().writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"errorMessage\":")));

        // проверка сохранения без ошибкок
        String checkValue = "xyxyxyx";
        String fldName = "artifactName";
        dto.setArtifactName(checkValue);
        dto.setArtifactCode("кодик");
        dto.setArtifactVisibleFlag(0);
        dto.setResourceTypeId(ArtifactKinds.CONST.getResourceType());
        dto.setConstantTypeId(1);
        dto.setConstantGroupId(1268);


        result = this.mockMvc.perform(post(buildUrl("artifact/save",CONTOURE,MODULE))
                .content(new ObjectMapper().writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(String.format("\"%s\":\"%s\"",fldName,checkValue))))
                .andReturn();
        content = result.getResponse().getContentAsString();
        ArtifactView view = new ObjectMapper().readValue(content,ArtifactView.class);

        //удаление записи
        this.mockMvc.perform(post(buildUrl("artifact/delete",CONTOURE,MODULE))
                .content("["+view.getArtifactId().toString()+"]")
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(not(containsString("\"errorMessage\":"))));

    }

    @Test
    public void insertConst() throws Exception {
        // проверка получения записи для вставки
        MvcResult result = this.mockMvc.perform(post(buildUrl("artifact/get",CONTOURE,MODULE))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(not(containsString("\"errorMessage\":"))))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        ArtifactDTO dto = new ObjectMapper().readValue(content,ArtifactDTO.class);

        // проверка сохранения без ошибкок
        String fldName = "artifactName";
        dto.setArtifactName("НДС");
        dto.setArtifactCode("NDS");
        dto.setArtifactVisibleFlag(1);
        dto.setResourceTypeId(ArtifactKinds.CONST.getResourceType());
        dto.setConstantTypeId(1);
        dto.setConstantGroupId(1268);

        result = this.mockMvc.perform(post(buildUrl("artifact/save",CONTOURE,MODULE))
                .content(new ObjectMapper().writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(not(containsString("\"errorMessage\""))))
                .andReturn();
        content = result.getResponse().getContentAsString();
        ArtifactView view = new ObjectMapper().readValue(content,ArtifactView.class);

        //удаление записи
        this.mockMvc.perform(post(buildUrl("artifact/delete",CONTOURE,MODULE))
                .content("["+view.getArtifactId().toString()+"]")
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(not(containsString("\"errorMessage\":"))));
    }

    @Test
    public void selectConstTest() throws Exception {
        // проверка получения записи для редактирования
        MvcResult result = this.mockMvc.perform(post(buildUrl("artifact/get",CONTOURE,MODULE))
                .content("3")
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(not(containsString("\"errorMessage\":"))))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        ArtifactDTO dto = new ObjectMapper().readValue(content,ArtifactDTO.class);

    }

    @Test
    public void selectAttributeTest() throws Exception {
        // проверка получения записи для редактирования
        MvcResult result = this.mockMvc.perform(post(buildUrl("artifact/get",CONTOURE,MODULE))
                .content("4")
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(not(containsString("\"errorMessage\":"))))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        ArtifactDTO dto = new ObjectMapper().readValue(content,ArtifactDTO.class);

    }

}
