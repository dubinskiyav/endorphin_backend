package biz.gelicon.core.controllers;

import biz.gelicon.core.components.core.constantvalue.ConstantValueDTO;
import biz.gelicon.core.components.core.constantvalue.ConstantValueView;
import biz.gelicon.core.utils.GridDataOption;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class ConstantValueControllerTest extends IntergatedTest {
    private static final String CONTOURE = "admin";
    private static final String MODULE = "resource";

    @BeforeAll
    public static void setup() {
        token = "e9b3c034-fdd5-456f-825b-4c632f2053ac"; //root
    }

    @Test
    public void selectTest() throws Exception {
        // базовая проверка
        GridDataOption options = new GridDataOption.Builder()
                .pagination(1, 25)
                .addSort("constantValueDatebeg", Sort.Direction.ASC)
                .addFilter("constantId",3)
                .build();

        this.mockMvc.perform(post(buildUrl("constantvalue/getlist",CONTOURE,MODULE))
                .content(new ObjectMapper().writeValueAsString(options))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"rowCount\":"+1)));

        // проверка быстрого фильтра eq
        options = new GridDataOption.Builder()
                .pagination(1, 25)
                .addSort("constantValueDatebeg", Sort.Direction.ASC)
                .addFilter("constantId",3)
                .addFilter("quick.constantValueDisplay.eq","1")
                .build();


        this.mockMvc.perform(post(buildUrl("constantvalue/getlist",CONTOURE,MODULE))
                .content(new ObjectMapper().writeValueAsString(options))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"rowCount\":1")));

        // проверка быстрого фильтра like
        options = new GridDataOption.Builder()
                .pagination(1, 25)
                .addSort("constantValueDatebeg", Sort.Direction.DESC)
                .addFilter("constantId",3)
                .addFilter("quick.constantValueDisplay.like","0")
                .build();


        this.mockMvc.perform(post(buildUrl("constantvalue/getlist",CONTOURE,MODULE))
                .content(new ObjectMapper().writeValueAsString(options))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"rowCount\":0")));

    }

    @Test
    public void searchFullText() throws Exception {
        GridDataOption options = new GridDataOption.Builder()
                .pagination(1, 25)
                .addFilter("constantId",3)
                .search("1")
                .build();


        this.mockMvc.perform(post(buildUrl("constantvalue/getlist",CONTOURE,MODULE))
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
        MvcResult result = this.mockMvc.perform(post(buildUrl("constantvalue/get",CONTOURE,MODULE))
                .content("1")
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(not(containsString("\"errorMessage\":"))))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        ConstantValueDTO dto = new ObjectMapper().readValue(content,ConstantValueDTO.class);

        // проверка сохранения изменений
        String checkValue = "33";
        String fldName = "constantValueDisplay";
        dto.setConstantValueInteger(33);

        this.mockMvc.perform(post(buildUrl("constantvalue/save",CONTOURE,MODULE))
                .content(new ObjectMapper().writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(String.format("\"%s\":\"%s\"",fldName,checkValue))));

        // проверка ошибки Запись не найдена
        this.mockMvc.perform(post(buildUrl("constantvalue/get",CONTOURE,MODULE))
                .content("-1")
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"errorMessage\":")))
                .andReturn();

    }

    @Test
    public void insertAndDeleteTest() throws Exception {
        // проверка получения записи для вставки
        MvcResult result = this.mockMvc.perform(post(buildUrl("constantvalue/get",CONTOURE,MODULE))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(not(containsString("\"errorMessage\":"))))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        ConstantValueDTO dto = new ObjectMapper().readValue(content,ConstantValueDTO.class);

        // проверка сохранения c ошибками
        this.mockMvc.perform(post(buildUrl("constantvalue/save",CONTOURE,MODULE))
                .content(new ObjectMapper().writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"errorMessage\":")));

        // проверка сохранения без ошибкок
        String checkValue = "44";
        String fldName = "constantValueDisplay";
        dto.setConstantId(3);
        dto.setConstantValueDatebeg(new SimpleDateFormat("dd.MM.yyyy").parse("01.01.2022"));
        dto.setConstantValueInteger(44);


        result = this.mockMvc.perform(post(buildUrl("constantvalue/save",CONTOURE,MODULE))
                .content(new ObjectMapper().writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(String.format("\"%s\":\"%s\"",fldName,checkValue))))
                .andReturn();
        content = result.getResponse().getContentAsString();
        ConstantValueView view = new ObjectMapper().readValue(content,ConstantValueView.class);

        //удаление записи
        this.mockMvc.perform(post(buildUrl("constantvalue/delete",CONTOURE,MODULE))
                .content("["+view.getConstantValueId().toString()+"]")
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(not(containsString("\"errorMessage\":"))));

    }


}

