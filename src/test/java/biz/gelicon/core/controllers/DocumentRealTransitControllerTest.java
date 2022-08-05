package biz.gelicon.core.controllers;

import biz.gelicon.core.components.erp.todo.documentrealtransit.DocumentRealTransitController;
import biz.gelicon.core.components.erp.todo.documentrealtransit.DocumentRealTransitDTO;
import biz.gelicon.core.utils.DateUtils;
import biz.gelicon.core.utils.GridDataOption;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class DocumentRealTransitControllerTest extends IntergatedTest {

    //TODO: определить контур
    private static final String CONTOURE = "documents";
    //TODO: определить модуль
    private static final String MODULE = "documentreal";

    @BeforeAll
    public static void setup() {
        token = "e9b3c034-fdd5-456f-825b-4c632f2053ac"; //root
    }

    @Test
    public void selectTest() throws Exception {

        // базовая проверка
        GridDataOption options = new GridDataOption.Builder()
                .pagination(1, 25)
                .addSort("documentRealTransitId", Sort.Direction.ASC)
                .addFilter("documentrealId", 1)
                .build();
        MvcResult result =
        this.mockMvc.perform(post(buildUrl("documentrealtransit/getlist", CONTOURE, MODULE))
                .content(new ObjectMapper().writeValueAsString(options))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"rowCount\":" + 3)))
                .andReturn();

        // Проверка установки обязательного фильтра
        options = new GridDataOption.Builder()
                .pagination(1, 25)
                .addSort("documentRealTransitId", Sort.Direction.ASC)
                .build();
        this.mockMvc.perform(post(buildUrl("documentrealtransit/getlist", CONTOURE, MODULE))
                .content(new ObjectMapper().writeValueAsString(options))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"errorCode\":100")))
                .andExpect(content()
                        .string(containsString("Требуется обязательный фильтр documentrealId")))
        ;

        // проверка быстрого фильтра eq
        options = new GridDataOption.Builder()
                .pagination(1, 25)
                .addSort("documentRealTransitId", Sort.Direction.ASC)
                .addFilter("documentrealId", 1)
                .addFilter("quick.documentRealTransitRemark" + ".eq", "Заявка №1 - Оформлена 2")
                .build();
        this.mockMvc.perform(post(buildUrl("documentrealtransit/getlist", CONTOURE, MODULE))
                .content(new ObjectMapper().writeValueAsString(options))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"rowCount\":" + 1)));

        // проверка быстрого фильтра like
        options = new GridDataOption.Builder()
                .pagination(1, 25)
                .addSort("documentRealTransitId", Sort.Direction.ASC)
                .addFilter("documentrealId", 1)
                .addFilter("quick.documentRealTransitRemark" + ".like", "П")
                .build();
        this.mockMvc.perform(post(buildUrl("documentrealtransit/getlist", CONTOURE, MODULE))
                .content(new ObjectMapper().writeValueAsString(options))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"rowCount\":" + 2)));

        // Проверка именованных фильтров
        options = new GridDataOption.Builder()
                .pagination(1, 25)
                .addSort("documentRealTransitId", Sort.Direction.ASC)
                .addFilter("documentrealId", 1)
                .addFilter("documenttransitId", 5)
                .build();
        this.mockMvc.perform(post(buildUrl("documentrealtransit/getlist", CONTOURE, MODULE))
                .content(new ObjectMapper().writeValueAsString(options))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"rowCount\":" + 1)));

        options = new GridDataOption.Builder()
                .pagination(1, 25)
                .addSort("documentRealTransitId", Sort.Direction.ASC)
                .addFilter("documentrealId", 1)
                .addFilter("proguserId", 18)
                .build();
        this.mockMvc.perform(post(buildUrl("documentrealtransit/getlist", CONTOURE, MODULE))
                .content(new ObjectMapper().writeValueAsString(options))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"rowCount\":" + 0)));

        options = new GridDataOption.Builder()
                .pagination(1, 25)
                .addSort("documentRealTransitId", Sort.Direction.ASC)
                .addFilter("documentrealId", 1)
                .addFilter("documentrealtransitFlag", 1)
                .build();
        this.mockMvc.perform(post(buildUrl("documentrealtransit/getlist", CONTOURE, MODULE))
                .content(new ObjectMapper().writeValueAsString(options))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"rowCount\":" + 1)));
    }

    @Test
    public void searchFullText() throws Exception {
        // Дата сегодня в формате 28.06.2021
        String search = DateUtils.dateToStr(new Date());

        GridDataOption options = new GridDataOption.Builder()
                .pagination(1, 25)
                .addFilter("documentrealId", 3)
                .search(search)
                .build();
        this.mockMvc.perform(post(buildUrl("documentrealtransit/getlist", CONTOURE, MODULE))
                .content(new ObjectMapper().writeValueAsString(options))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"rowCount\":" + 3)));
        // Дата левая
        search = "23.01.2020";
        // Ничего не должно выбрать
        options = new GridDataOption.Builder()
                .pagination(1, 25)
                .addFilter("documentrealId", 3)
                .search(search)
                .build();
        this.mockMvc.perform(post(buildUrl("documentrealtransit/getlist", CONTOURE, MODULE))
                .content(new ObjectMapper().writeValueAsString(options))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"rowCount\":" + 0)));
        // Текст
        search = "Счет на оплату";

        options = new GridDataOption.Builder()
                .pagination(1, 25)
                .addFilter("documentrealId", 3)
                .search(search)
                .build();
        this.mockMvc.perform(post(buildUrl("documentrealtransit/getlist", CONTOURE, MODULE))
                .content(new ObjectMapper().writeValueAsString(options))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"rowCount\":" + 3)));
    }

    @Test
    @Transactional
    @Rollback
    public void updateTest() throws Exception {
        // проверка получения записи для редактирования
        MvcResult result =
                this.mockMvc.perform(post(buildUrl("documentrealtransit/get", CONTOURE, MODULE))
                .content("1") // передаем id
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(not(containsString("\"errorMessage\":"))))
                .andExpect(content().string(containsString("Заявка №1 - Принята 1")))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        DocumentRealTransitDTO dto = new ObjectMapper()
                .readValue(content, DocumentRealTransitDTO.class);

        // проверка сохранения изменений
        String checkValue = "Заявка №1 - Принята 1 и будет отправлена в корзину";
        String fldName = "documentRealTransitRemark";
        dto.setDocumentRealTransitRemark(checkValue);

        // проверка ошибки Запись не найдена
        this.mockMvc.perform(post(buildUrl("documentrealtransit/get", CONTOURE, MODULE))
                .content("-1")
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "\"errorMessage\":\"Запись с идентификатором -1 не найдена\"")))
                .andReturn();
    }

    @Test
    @Transactional
    @Rollback
    public void insertAndDeleteTest() throws Exception {
        // проверка получения записи для вставки
        MvcResult result =
                this.mockMvc.perform(post(buildUrl("documentrealtransit/get", CONTOURE, MODULE))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(not(containsString("\"errorMessage\":"))))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        DocumentRealTransitDTO dto = new ObjectMapper()
                .readValue(content, DocumentRealTransitDTO.class);

        Date documentRealTransitDate = dto.getDocumentRealTransitDate();
        // Дата должна быть сегодняшняя
        String s1 = DateUtils.dateToStr(documentRealTransitDate);
        String s2 = DateUtils.dateToStr(DateUtils.getDate());
        Assertions.assertEquals(s1, s2);

        DocumentRealTransitController.DocumentRealTransitSetDTO set =
                new DocumentRealTransitController.DocumentRealTransitSetDTO();
        set.setDocumentRealIds(new int[]{2});
        set.setDocumentTransitId(7);
        set.setDocumentRealTransitDate(new Date());
        set.setDocumentRealTransitRemark("Проба");
        result = this.mockMvc.perform(post(buildUrl("documentrealtransit/set", CONTOURE, MODULE))
                .content(new ObjectMapper().writeValueAsString(set))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"documentRealId\":2")))
                .andReturn();
        String jsonOutput = result.getResponse().getContentAsString();
        Type listType =
                new TypeToken<List<DocumentRealTransitController.DocumentRealTransitSetRet>>(){}
                        .getType();
        Gson gson = new Gson();
        List<DocumentRealTransitController.DocumentRealTransitSetRet> retList =
                gson.fromJson(jsonOutput, listType);
        Assertions.assertEquals(retList.size(), 1);// Должен вернуть одну запись

        // Снятие статуса
        DocumentRealTransitController.DocumentRealTransitUnSetDTO d =
                new DocumentRealTransitController.DocumentRealTransitUnSetDTO();
        d.setDocumentRealIds(new int[]{2});
        d.setDocumentTransitId(6);

        this.mockMvc.perform(post(buildUrl("documentrealtransit/unset", CONTOURE, MODULE))
                .content(new ObjectMapper().writeValueAsString(d))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Снять возможно только текущий статус"))
                );

        d.setDocumentRealIds(new int[]{101});
        d.setDocumentTransitId(4);

        this.mockMvc.perform(post(buildUrl("documentrealtransit/unset", CONTOURE, MODULE))
                .content(new ObjectMapper().writeValueAsString(d))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                //.andExpect(content().string(containsString("Снять возможно только текущий статус")))
        ;
        jsonOutput = result.getResponse().getContentAsString();
        listType =
                new TypeToken<List<DocumentRealTransitController.DocumentRealTransitSetRet>>(){}
                        .getType();
        gson = new Gson();
        retList =
                gson.fromJson(jsonOutput, listType);
        Assertions.assertEquals(retList.size(), 1);// Должен вернуть одну запись

    }
}

