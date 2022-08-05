package biz.gelicon.core.controllers;

import biz.gelicon.core.dto.MeasureDTO;
import biz.gelicon.core.utils.GridDataOption;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MeasureControllerTest extends IntergatedTest {

    @BeforeAll
    public static void setup() {
        token = "e9b3c034-fdd5-456f-825b-4c632f2053ac"; //root
    }

    @Test
    public void selectTest() throws Exception {
        // базовая проверка
        GridDataOption options = new GridDataOption.Builder()
                .pagination(1, 25)
                .addSort("measureName", Sort.Direction.ASC)
                .build();

        this.mockMvc.perform(post(buildUrl("/apps/refbooks/measure/measure/getlist"))
                .content(new ObjectMapper().writeValueAsString(options))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"rowCount\":25")));

        // проверка быстрого фильтра eq
        options = new GridDataOption.Builder()
                .pagination(1, 25)
                .addSort("measureName", Sort.Direction.ASC)
                .addFilter("quick.measureName.eq","Время")
                .build();


        this.mockMvc.perform(post(buildUrl("/apps/refbooks/measure/measure/getlist"))
                .content(new ObjectMapper().writeValueAsString(options))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"rowCount\":1")));

        // проверка быстрого фильтра like
        options = new GridDataOption.Builder()
                .pagination(1, 25)
                .addSort("measureName", Sort.Direction.DESC)
                .addFilter("quick.measureName.like","ионизирующего")
                .build();


        this.mockMvc.perform(post(buildUrl("/apps/refbooks/measure/measure/getlist"))
                .content(new ObjectMapper().writeValueAsString(options))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"rowCount\":2")));

    }

    @Test
    @Transactional
    @Rollback
    public void updateTest() throws Exception {

        // проверка получения записи для редактирования
        MvcResult result = this.mockMvc.perform(post(buildUrl("/apps/refbooks/measure/measure/get"))
                .content("1")
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(not(containsString("\"errorMessage\":"))))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        MeasureDTO dto = new ObjectMapper().readValue(content,MeasureDTO.class);

        // проверка сохранения изменений
        dto.setMeasureName("---");
        this.mockMvc.perform(post(buildUrl("/apps/refbooks/measure/measure/save"))
                .content(new ObjectMapper().writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"measureName\":\"---\"")));

        // проверка ошибки Запись не найдена
        this.mockMvc.perform(post(buildUrl("/apps/refbooks/measure/measure/get"))
                .content("-100")
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"errorMessage\":")))
                .andReturn();

    }

    @Test
    public void insertAndDeleteTest() throws Exception {
        // проверка получения записи для вставки
        MvcResult result = this.mockMvc.perform(post(buildUrl("/apps/refbooks/measure/measure/get"))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(not(containsString("\"errorMessage\":"))))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        MeasureDTO dto = new ObjectMapper().readValue(content,MeasureDTO.class);

        // проверка сохранения c ошибками
        this.mockMvc.perform(post(buildUrl("/apps/refbooks/measure/measure/save"))
                .content(new ObjectMapper().writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"errorMessage\":")));

        // проверка сохранения без ошибкок
        dto.setMeasureName("x");
        result = this.mockMvc.perform(post(buildUrl("/apps/refbooks/measure/measure/save"))
                .content(new ObjectMapper().writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"measureName\":\"x\"")))
                .andReturn();
        content = result.getResponse().getContentAsString();
        dto = new ObjectMapper().readValue(content,MeasureDTO.class);

        //удаление записи
        this.mockMvc.perform(post(buildUrl("/apps/refbooks/measure/measure/delete"))
                .content("["+dto.getMeasureId().toString()+"]")
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(not(containsString("\"errorMessage\":"))));

    }

}