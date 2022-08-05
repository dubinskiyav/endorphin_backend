package biz.gelicon.core.controllers;

import biz.gelicon.core.deltads.DataRecord;
import biz.gelicon.core.deltads.StatusRecord;
import biz.gelicon.core.components.erp.todo.documentappendixrole.DocumentAppendixRoleDTO;
import biz.gelicon.core.components.core.document.DocumentDTO;
import biz.gelicon.core.components.erp.todo.documenttransit.DocumentTransitDTO;
import biz.gelicon.core.dto.SelectDisplayData;
import biz.gelicon.core.components.core.sqlaction.SqlAction;
import biz.gelicon.core.utils.GridDataOption;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class DocumentControllerTest extends IntergatedTest {
    private static final String CONTOURE = "admin";
    private static final String MODULE = "config";

    @BeforeAll
    public static void setup() {
        token = "e9b3c034-fdd5-456f-825b-4c632f2053ac"; //root
    }

    @Test
    public void selectTest() throws Exception {
        // базовая проверка
        GridDataOption options = new GridDataOption.Builder()
                .pagination(1, 25)
                .addSort("documentId", Sort.Direction.ASC)
                .build();

        this.mockMvc.perform(post(buildUrl("document/getlist",CONTOURE,MODULE))
                .content(new ObjectMapper().writeValueAsString(options))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"rowCount\":5")));

        // проверка быстрого фильтра eq
        options = new GridDataOption.Builder()
                .pagination(1, 25)
                .addSort("documentId", Sort.Direction.ASC)
                .addFilter("quick.documentName.eq","Проводка")
                .build();


        this.mockMvc.perform(post(buildUrl("document/getlist",CONTOURE,MODULE))
                .content(new ObjectMapper().writeValueAsString(options))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"rowCount\":1")));

        // проверка быстрого фильтра like
        options = new GridDataOption.Builder()
                .pagination(1, 25)
                .addSort("documentId", Sort.Direction.DESC)
                .addFilter("quick.documentName.like","явк")
                .build();


        this.mockMvc.perform(post(buildUrl("document/getlist",CONTOURE,MODULE))
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
                .search("Пров")
                .build();


        this.mockMvc.perform(post(buildUrl("document/getlist",CONTOURE,MODULE))
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
        MvcResult result = this.mockMvc.perform(post(buildUrl("document/get",CONTOURE,MODULE))
                .content("1")
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(not(containsString("\"errorMessage\":"))))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        DocumentDTO dto = new ObjectMapper().readValue(content,DocumentDTO.class);

        // проверка сохранения изменений
        String fldName = "uniqueTypeId";
        dto.setUniqueTypeId(1);

        this.mockMvc.perform(post(buildUrl("document/save",CONTOURE,MODULE))
                .content(new ObjectMapper().writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(String.format("\"%s\":%d",fldName,1))));

        // проверка сохранения изменений c очисткой unqueType
        dto.setUniqueTypeId(null);
        
        this.mockMvc.perform(post(buildUrl("document/save",CONTOURE,MODULE))
                .content(new ObjectMapper().writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(String.format("\"%s\":%s",fldName,"null"))));

        // проверка ошибки Запись не найдена
        this.mockMvc.perform(post(buildUrl("document/get",CONTOURE,MODULE))
                .content("-1")
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"errorMessage\":")))
                .andReturn();

    }

    @Test
    @Transactional
    @Rollback
    public void updateStatusModel() throws Exception {
        // проверка получения записи для редактирования
        MvcResult result = this.mockMvc.perform(post(buildUrl("document/get",CONTOURE,MODULE))
                .content("1")
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(not(containsString("\"errorMessage\":"))))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        DocumentDTO dto = new ObjectMapper().readValue(content,DocumentDTO.class);


        List<DataRecord<DocumentTransitDTO>> deltaStatus = new ArrayList<>();
        DataRecord<DocumentTransitDTO> record;
        // изменени позиции №1
        record = DataRecord.newRecord();
        record.setStatus(StatusRecord.UPDATED);
        DocumentTransitDTO orig = dto.getTransits().getData().get(0);
        orig.setDocumentTransitColor(11);
        orig.getAccessRoleIds().add(new SelectDisplayData<>(3,"Тестовая роль"));
        record.setRecord(orig);
        record.setOldRecord(orig);
        deltaStatus.add(record);
        // удаление позиции №2
        record = DataRecord.newRecord();
        record.setStatus(StatusRecord.DELETED);
        record.setRecord(dto.getTransits().getData().get(1));
        deltaStatus.add(record);


        dto.getTransits().setDelta(deltaStatus);

        this.mockMvc.perform(post(buildUrl("document/save",CONTOURE,MODULE))
                .content(new ObjectMapper().writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(not(containsString("\"errorMessage\":"))));

        // проверка изменений
        result = this.mockMvc.perform(post(buildUrl("document/get",CONTOURE,MODULE))
                .content("1")
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(not(containsString("\"errorMessage\":"))))
                .andReturn();
        dto = new ObjectMapper().readValue(result.getResponse().getContentAsString(),DocumentDTO.class);

        assertEquals(dto.getTransits().getData().get(0).getDocumentTransitColor(),11);
        assertEquals(dto.getTransits().getData().size(),2);

    }


    @Test
    @Transactional
    @Rollback
    public void updateAppendixRoles() throws Exception {
        // проверка получения записи для редактирования
        MvcResult result = this.mockMvc.perform(post(buildUrl("document/get",CONTOURE,MODULE))
                .content("1")
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(not(containsString("\"errorMessage\":"))))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        DocumentDTO dto = new ObjectMapper().readValue(content,DocumentDTO.class);


        List<DataRecord<DocumentAppendixRoleDTO>> deltaStatus = new ArrayList<>();
        DataRecord<DocumentAppendixRoleDTO> record;
        // вставка позиции №1
        record = DataRecord.newRecord();
        record.setStatus(StatusRecord.INSERTED);

        DocumentAppendixRoleDTO dar1 = new DocumentAppendixRoleDTO();
        dar1.setPosNumber(1);
        dar1.setDocumentId(1);
        dar1.setSqlactionId(SqlAction.INSERT_ACTION);
        dar1.setAppendixType(new SelectDisplayData<>(555,"Файл"));
        dar1.setAccessRole(new SelectDisplayData<>(3,"Тестовая роль"));


        record.setRecord(dar1);
        deltaStatus.add(record);

        // вставка позиции №2
        record = DataRecord.newRecord();
        record.setStatus(StatusRecord.INSERTED);

        DocumentAppendixRoleDTO dar2 = new DocumentAppendixRoleDTO();
        dar2.setPosNumber(2);
        dar2.setDocumentId(1);
        dar2.setSqlactionId(SqlAction.UPDATE_ACTION);
        dar2.setAppendixType(new SelectDisplayData<>(555,"Файл"));
        dar2.setAccessRole(new SelectDisplayData<>(3,"Тестовая роль"));

        record.setRecord(dar2);
        deltaStatus.add(record);
        // обновление позиции №1
        record = DataRecord.newRecord();
        record.setStatus(StatusRecord.UPDATED);

        DocumentAppendixRoleDTO dar3 = new DocumentAppendixRoleDTO();
        BeanUtils.copyProperties(dar1, dar3);
        dar3.setAppendixType(new SelectDisplayData<>(556,"Изображение"));
        record.setRecord(dar3);
        record.setOldRecord(dar1);
        deltaStatus.add(record);

        // удаление позиции №2
        record = DataRecord.newRecord();
        record.setStatus(StatusRecord.DELETED);
        record.setRecord(dar2);
        deltaStatus.add(record);

        dto.getAppendixRoles().setDelta(deltaStatus);

        this.mockMvc.perform(post(buildUrl("document/save",CONTOURE,MODULE))
                .content(new ObjectMapper().writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(not(containsString("\"errorMessage\":"))));

        // проверка изменений
        result = this.mockMvc.perform(post(buildUrl("document/get",CONTOURE,MODULE))
                .content("1")
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(not(containsString("\"errorMessage\":"))))
                .andReturn();
        dto = new ObjectMapper().readValue(result.getResponse().getContentAsString(),DocumentDTO.class);

        assertEquals(dto.getAppendixRoles().getData().get(0).getAppendixType().getValue(),556);
        assertEquals(dto.getAppendixRoles().getData().size(),1);

    }

}

