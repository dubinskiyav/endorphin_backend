package biz.gelicon.core.controllers;

import biz.gelicon.core.artifact.TestNumerator;
import biz.gelicon.core.artifacts.ArtifactKinds;
import biz.gelicon.core.artifacts.ArtifactManagerImpl;
import biz.gelicon.core.dto.AllowOrDenyArtifact;
import biz.gelicon.core.utils.GridDataOption;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ArtifactRoleTest extends IntergatedTest {
    private static final String CONTOURE = "admin";
    private static final String MODULE = "credential";

    @Autowired
    ArtifactManagerImpl artifactManager;

    @BeforeAll
    public static void setup() {
        token = "e9b3c034-fdd5-456f-825b-4c632f2053ac"; //root
    }

    @BeforeEach
    public void before() {
        new TestNumerator().registerManager(artifactManager);
    }

        @Test
    public void selectForRolesTest() throws Exception {
        // базовая проверка
        GridDataOption options = new GridDataOption.Builder()
                .pagination(1, 25)
                .addSort("artifactId", Sort.Direction.ASC)
                .addFilter("accessRoleId",1)
                .build();

        this.mockMvc.perform(post(buildUrl("artifactrole/getlist",CONTOURE,MODULE))
                .content(new ObjectMapper().writeValueAsString(options))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"rowCount\":4")));

        // проверка быстрого фильтра eq
        options = new GridDataOption.Builder()
                .pagination(1, 25)
                .addSort("artifactId", Sort.Direction.ASC)
                .addFilter("quick.artifactCode.eq","TST_01")
                .addFilter("accessRoleId",1)
                .build();


        this.mockMvc.perform(post(buildUrl("artifactrole/getlist",CONTOURE,MODULE))
                .content(new ObjectMapper().writeValueAsString(options))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"rowCount\":1")));

        // проверка быстрого фильтра like
        options = new GridDataOption.Builder()
                .pagination(1, 25)
                .addSort("artifactId", Sort.Direction.DESC)
                .addFilter("quick.artifactCode.like","TST")
                .addFilter("accessRoleId",1)
                .build();


        this.mockMvc.perform(post(buildUrl("artifactrole/getlist",CONTOURE,MODULE))
                .content(new ObjectMapper().writeValueAsString(options))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"rowCount\":1")));

    }


    @Test
    public void searchFullText() throws Exception {
        GridDataOption options = new GridDataOption.Builder()
                .pagination(1, 25)
                .addFilter("accessRoleId",1)
                .search("TST")
                .build();


        this.mockMvc.perform(post(buildUrl("artifactrole/getlist",CONTOURE,MODULE))
                .content(new ObjectMapper().writeValueAsString(options))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"rowCount\":1")));

    }

    @Test
    @Transactional
    @Rollback
    public void allowAndDrop()  throws Exception {
        AllowOrDenyArtifact param = new AllowOrDenyArtifact();
        param.setAccessRoleId(1);
        param.setArtifactIds(new Integer[]{1,2});

        // ограничиваем
        this.mockMvc.perform(post(buildUrl("artifactrole/allow",CONTOURE,MODULE))
                .content(new ObjectMapper().writeValueAsString(param))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(not(containsString("\"errorMessage\":"))));

        //порбуем выбраьт и там 1
        GridDataOption options = new GridDataOption.Builder()
                .addFilter("entity","test")
                .addFilter("kind", ArtifactKinds.NUMERATOR.getResourceType())
                .build();

        this.mockMvc.perform(post(buildUrl("list-of-call","artifacts"))
                .header("Authorization","Bearer 22222222-85da-48a4-2222-d91ff1d26624")
                .content(new ObjectMapper().writeValueAsString(options))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"rowCount\":1")));


        // снимаем ограничение
        this.mockMvc.perform(post(buildUrl("artifactrole/drop",CONTOURE,MODULE))
                .content(new ObjectMapper().writeValueAsString(param))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(not(containsString("\"errorMessage\":"))));

    }



}
