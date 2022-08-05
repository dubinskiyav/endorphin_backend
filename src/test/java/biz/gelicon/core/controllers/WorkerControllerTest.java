package biz.gelicon.core.controllers;

import biz.gelicon.core.components.core.worker.WorkerDTO;
import biz.gelicon.core.components.core.worker.WorkerView;
import biz.gelicon.core.utils.GridDataOption;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class WorkerControllerTest extends IntergatedTest {
    private static final String CONTOURE = "refbooks";
    private static final String MODULE = "person";

    @BeforeAll
    public static void setup() {
        token = "e9b3c034-fdd5-456f-825b-4c632f2053ac"; //root
    }

    @Test
    public void selectTest() throws Exception {
        // базовая проверка
        GridDataOption options = new GridDataOption.Builder()
                .pagination(1, 25)
                .addSort("workerFamilyname", Sort.Direction.ASC)
                .build();

        this.mockMvc.perform(post(buildUrl("worker/getlist",CONTOURE,MODULE))
                .content(new ObjectMapper().writeValueAsString(options))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"rowCount\":3")));

        // проверка быстрого фильтра eq
        options = new GridDataOption.Builder()
                .pagination(1, 25)
                .addSort("workerFamilyname", Sort.Direction.ASC)
                .addFilter("quick.workerFamilyname.eq","Иванов")
                .build();


        this.mockMvc.perform(post(buildUrl("worker/getlist",CONTOURE,MODULE))
                .content(new ObjectMapper().writeValueAsString(options))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"rowCount\":1")));

        // проверка быстрого фильтра like
        options = new GridDataOption.Builder()
                .pagination(1, 25)
                .addSort("workerId", Sort.Direction.DESC)
                .addFilter("quick.workerFamilyname.like","ов")
                .build();


        this.mockMvc.perform(post(buildUrl("worker/getlist",CONTOURE,MODULE))
                .content(new ObjectMapper().writeValueAsString(options))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"rowCount\":3")));

    }

    @Test
    public void searchFullText() throws Exception {
        GridDataOption options = new GridDataOption.Builder()
                .pagination(1, 25)
                .search("зах")
                .build();


        this.mockMvc.perform(post(buildUrl("worker/getlist",CONTOURE,MODULE))
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
        MvcResult result = this.mockMvc.perform(post(buildUrl("worker/get",CONTOURE,MODULE))
                .content("1")
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(not(containsString("\"errorMessage\":"))))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        WorkerDTO dto = new ObjectMapper().readValue(content,WorkerDTO.class);

        // проверка сохранения изменений
        String checkValue = "Богдан";
        String fldName = "workerFirstname";
        dto.setWorkerFirstname(checkValue);

        this.mockMvc.perform(post(buildUrl("worker/save",CONTOURE,MODULE))
                .content(new ObjectMapper().writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(String.format("\"%s\":\"%s\"",fldName,checkValue))));

        // проверка ошибки Запись не найдена
        this.mockMvc.perform(post(buildUrl("worker/get",CONTOURE,MODULE))
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
        MvcResult result = this.mockMvc.perform(post(buildUrl("worker/get",CONTOURE,MODULE))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(not(containsString("\"errorMessage\":"))))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        WorkerDTO dto = new ObjectMapper().readValue(content,WorkerDTO.class);

        // проверка сохранения c ошибками
        this.mockMvc.perform(post(buildUrl("worker/save",CONTOURE,MODULE))
                .content(new ObjectMapper().writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"errorMessage\":")));

        // проверка сохранения без ошибкок

        String checkValue = "01";
        dto.setWorkerFirstname("Богдан");
        dto.setWorkerFamilyname("Гондонопольский");
        dto.setWorkerTabnumber("666");
        dto.setWorkerSex(0);
        dto.setWorkerEmail("xxxxxxxxxxxxxxxxxxx@rrr.com");
        dto.setWorkerContactphone(checkValue);
        String fldName = "workerContactphone";


        result = this.mockMvc.perform(post(buildUrl("worker/save",CONTOURE,MODULE))
                .content(new ObjectMapper().writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(String.format("\"%s\":\"%s\"",fldName,checkValue))))
                .andReturn();
        content = result.getResponse().getContentAsString();
        WorkerView view = new ObjectMapper().readValue(content,WorkerView.class);

        //удаление записи
        this.mockMvc.perform(post(buildUrl("worker/delete",CONTOURE,MODULE))
                .content("["+view.getWorkerId().toString()+"]")
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(not(containsString("\"errorMessage\":"))));

    }

    @Test
    public void findTest() throws Exception {
        this.mockMvc.perform(post(buildUrl("worker/find",CONTOURE,MODULE))
                        .content("{\"search\":\"Сидоров\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Захар")));
    }


}

