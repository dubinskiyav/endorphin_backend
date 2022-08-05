package biz.gelicon.core.controllers;

import biz.gelicon.core.components.erp.todo.appendix.AppendixDTO;
import biz.gelicon.core.components.core.controlobject.ControlObject;
import biz.gelicon.core.components.core.document.Document;
import biz.gelicon.core.components.erp.todo.documentreal.DocumentReal;
import biz.gelicon.core.components.core.accessrole.AccessRoleRepository;
import biz.gelicon.core.components.core.controlobject.ControlObjectRepository;
import biz.gelicon.core.components.erp.todo.documentappendixrole.DocumentAppendixRoleRepository;
import biz.gelicon.core.components.erp.todo.documentreal.DocumentRealRepository;
import biz.gelicon.core.security.ACL;
import biz.gelicon.core.security.Permission;
import biz.gelicon.core.utils.GridDataOption;
import biz.gelicon.core.components.erp.todo.appendix.AppendixView;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class AppendixControllerTest extends IntergatedTest {
    private static final String CONTOURE = "refbooks";
    private static final String MODULE = "files";

    @Autowired
    private DocumentAppendixRoleRepository documentAppendixRoleRepository;
    @Autowired
    private DocumentRealRepository documentRealRepository;
    @Autowired
    private AccessRoleRepository accessRoleRepository;
    @Autowired
    private ControlObjectRepository controlObjectRepository;
    @Autowired
    private ACL acl;

    @BeforeAll
    public static void setup() {
        token = "e9b3c034-fdd5-456f-825b-4c632f2053ac"; //root
    }

    @Test
    @Transactional
    @Rollback
    public void selectTest() throws Exception {
        // базовая проверка
        GridDataOption options = new GridDataOption.Builder()
                .pagination(1, 25)
                .addSort("appendixId", Sort.Direction.ASC)
                .addFilter("documentRealId", 101)
                .build();

        this.mockMvc.perform(post(buildUrl("appendix/getlist",CONTOURE,MODULE))
                .content(new ObjectMapper().writeValueAsString(options))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"rowCount\":"+2)));
    }

    @Test
    @Transactional
    @Rollback
    public void updateTest() throws Exception {

        AppendixDTO dto = new AppendixDTO(3, 211, null, "file.txt", "1",
                null, null, null, null,
                null, "txt", null, null, null, null);
        dto.setDocumentRealIds(List.of(1));

        this.mockMvc.perform(post(buildUrl("appendix/save",CONTOURE,MODULE))
                .content(new ObjectMapper().writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(not(containsString("\"errorMessage\":"))))
                .andExpect(content().string(containsString(String.format("\"%s\":%s","appendixId","3"))));

    }

    @Test
    @Transactional
    @Rollback
    public void accessTest() throws Exception {
        AppendixDTO dto;

        // проверка доступа на вставку от имени НЕ системного пользователя
        DocumentReal doc = new DocumentReal(101010, Document.DOCUUMENT_FOR_TEST, 3, 2, "Заявка тестовая", null, "2", new Date(), null,
                DocumentReal.DocumentLevel.SIMPLE_DOCUMENT.ordinal(), new Date(), new Date(),
                null, 0, 1, new Date(), new Date(), 0, 1);
        documentRealRepository.insert(doc);

        dto = new AppendixDTO(null, 211, null, "file3030303.txt", "1",
                null, null, null, null,
                null, "txt", null, null, null, null);
        dto.setDocumentRealIds(List.of(101010));

        // дадим доступ на save
        controlObjectRepository.insert(new ControlObject(50505050, "Сохранение ЭМ",
                        "/v1/apps/refbooks/files/appendix/save#upd"));
        accessRoleRepository.bindWithControlObject(1, 50505050, Permission.EXECUTE);
        acl.buildAccessTable();

        // вставляем от имени систеного юзера
        MvcResult result = this.mockMvc.perform(post(buildUrl("appendix/save",CONTOURE,MODULE))
                .content(new ObjectMapper().writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(not(containsString("\"errorMessage\":"))))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        AppendixView view = new ObjectMapper().readValue(content, AppendixView.class);
        dto.setAppendixId(view.getAppendixId());

        // обновляем от имени test1
        this.mockMvc.perform(post(buildUrl("appendix/save",CONTOURE,MODULE))
                .header("Authorization","Bearer 22222222-85da-48a4-2222-d91ff1d26624")
                .content(new ObjectMapper().writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"errorMessage\":")));

    }

    @Test
    @Transactional
    @Rollback
    public void uploadTest() throws Exception{
        MockMultipartFile file = new MockMultipartFile("upload", "hello.jpg", MediaType.APPLICATION_OCTET_STREAM_VALUE,
                new byte[]{0,1,2,3,4,5,6,7,8,9});
        this.mockMvc.perform(multipart(buildUrl("appendix/upload/3",CONTOURE,MODULE))
                .file(file)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(not(containsString("\"errorMessage\":"))));
    }

    @Test
    @Transactional
    @Rollback
    public void downloadTest() throws Exception{
        MockMultipartFile file = new MockMultipartFile("upload", "hello.jpg", MediaType.APPLICATION_OCTET_STREAM_VALUE,
                new byte[]{0,1,2,3,4,5,6,7,8,9});
        this.mockMvc.perform(multipart(buildUrl("appendix/upload/3",CONTOURE,MODULE))
                .file(file)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(not(containsString("\"errorMessage\":"))));

        this.mockMvc.perform(post(buildUrl("appendix/download/3",CONTOURE,MODULE))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("1f/1f825aa2f0020ef7cf91dfa30da4668d791c5d4824fc8e41354b89ec05795ab3")));
    }
}

