package biz.gelicon.core.controllers;

import biz.gelicon.core.components.erp.todo.edizm.EdizmDTO;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class EdizmControllerTest extends IntergatedTest {

    @BeforeAll
    public static void setup() {
        token = "e9b3c034-fdd5-456f-825b-4c632f2053ac"; //root
    }

    @Test
    public void selectTest() throws Exception {
        // базовая проверка
        GridDataOption options = new GridDataOption.Builder()
                .pagination(1, 25)
                .addSort("edizmNotation", Sort.Direction.ASC)
                .build();

        this.mockMvc.perform(post(buildUrl("/apps/refbooks/edizm/edizm/getlist"))
                .content(new ObjectMapper().writeValueAsString(options))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"rowCount\":5")));

        // проверка быстрого фильтра eq
        options = new GridDataOption.Builder()
                .pagination(1, 25)
                .addSort("edizmCode", Sort.Direction.ASC)
                .addFilter("quick.edizmNotation.eq","шт")
                .build();


        this.mockMvc.perform(post(buildUrl("/apps/refbooks/edizm/edizm/getlist"))
                .content(new ObjectMapper().writeValueAsString(options))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"rowCount\":1")));

        // проверка быстрого фильтра like
        options = new GridDataOption.Builder()
                .pagination(1, 25)
                .addSort("edizmCode", Sort.Direction.DESC)
                .addFilter("quick.edizmName.like","рам")
                .build();


        this.mockMvc.perform(post(buildUrl("/apps/refbooks/edizm/edizm/getlist"))
                .content(new ObjectMapper().writeValueAsString(options))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"rowCount\":2")));

        // проверка именованного фильтра
        options = new GridDataOption.Builder()
                .pagination(1, 25)
                .addSort("edizmCode", Sort.Direction.DESC)
                .addFilter("onlyNotBlock",1)
                .build();


        this.mockMvc.perform(post(buildUrl("/apps/refbooks/edizm/edizm/getlist"))
                .content(new ObjectMapper().writeValueAsString(options))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"rowCount\":4")));
    }

    @Test
    public void searchFullText() throws Exception {
        GridDataOption options = new GridDataOption.Builder()
                .pagination(1, 25)
                .search("рам")
                .build();


        this.mockMvc.perform(post(buildUrl("/apps/refbooks/edizm/edizm/getlist"))
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
        MvcResult result = this.mockMvc.perform(post(buildUrl("/apps/refbooks/edizm/edizm/get"))
                .content("1")
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(not(containsString("\"errorMessage\":"))))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        EdizmDTO dto = new ObjectMapper().readValue(content,EdizmDTO.class);

        // проверка сохранения изменений
        dto.setEdizmCode("---");
        this.mockMvc.perform(post(buildUrl("/apps/refbooks/edizm/edizm/save"))
                .content(new ObjectMapper().writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"edizmCode\":\"---\"")));

        // проверка ошибки Запись не найдена
        this.mockMvc.perform(post(buildUrl("/apps/refbooks/edizm/edizm/get"))
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
        MvcResult result = this.mockMvc.perform(post(buildUrl("/apps/refbooks/edizm/edizm/get"))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(not(containsString("\"errorMessage\":"))))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        EdizmDTO dto = new ObjectMapper().readValue(content,EdizmDTO.class);

        // проверка сохранения c ошибками
        this.mockMvc.perform(post(buildUrl("/apps/refbooks/edizm/edizm/save"))
                .content(new ObjectMapper().writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"errorMessage\":")));

        // проверка сохранения без ошибкок
        dto.setEdizmCode("x");
        dto.setEdizmName("xxxxxxxxxxxxxxxxxxx");
        dto.setEdizmNotation("xx");
        dto.setEdizmBlockFlag(0);
        result = this.mockMvc.perform(post(buildUrl("/apps/refbooks/edizm/edizm/save"))
                .content(new ObjectMapper().writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"edizmCode\":\"x\"")))
                .andReturn();
        content = result.getResponse().getContentAsString();
        dto = new ObjectMapper().readValue(content,EdizmDTO.class);

        //удаление записи
        this.mockMvc.perform(post(buildUrl("/apps/refbooks/edizm/edizm/delete"))
                .content("["+dto.getEdizmId().toString()+"]")
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(not(containsString("\"errorMessage\":"))));

    }


}
