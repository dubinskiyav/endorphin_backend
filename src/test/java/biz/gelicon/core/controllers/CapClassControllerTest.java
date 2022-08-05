package biz.gelicon.core.controllers;

import biz.gelicon.core.components.core.capclass.CapClassDTO;
import biz.gelicon.core.components.core.capclasstype.CapClassType;
import biz.gelicon.core.components.core.capclass.CapClassView;
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


public class CapClassControllerTest extends IntergatedTest {
    private static final String CONTOURE = "refbooks";
    private static final String MODULE = "capclass";

    @BeforeAll
    public static void setup() {
        token = "e9b3c034-fdd5-456f-825b-4c632f2053ac"; //root
    }

    @Test
    public void selectTest() throws Exception {
        // базовая проверка
        GridDataOption options = new GridDataOption.Builder()
                .pagination(1, 25)
                .addSort("capClassId", Sort.Direction.ASC)
                .build();

        this.mockMvc.perform(post(buildUrl("capclass/getlist",CONTOURE,MODULE))
                .content(new ObjectMapper().writeValueAsString(options))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"rowCount\":6")));

        // проверка быстрого фильтра eq
        options = new GridDataOption.Builder()
                .pagination(1, 25)
                .addSort("capClassId", Sort.Direction.ASC)
                .addFilter("quick.capClassCode.eq","НАЛОГ")
                .build();


        this.mockMvc.perform(post(buildUrl("capclass/getlist",CONTOURE,MODULE))
                .content(new ObjectMapper().writeValueAsString(options))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"rowCount\":1")));

        // проверка быстрого фильтра like
        options = new GridDataOption.Builder()
                .pagination(1, 25)
                .addSort("capClassId", Sort.Direction.DESC)
                .addFilter("quick.capClassName.like","Группа")
                .build();


        this.mockMvc.perform(post(buildUrl("capclass/getlist",CONTOURE,MODULE))
                .content(new ObjectMapper().writeValueAsString(options))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"rowCount\":2")));

        // проверка именованного фильтра
        options = new GridDataOption.Builder()
                .pagination(1, 25)
                .addSort("capClassId", Sort.Direction.DESC)
                .addFilter("capClassTypeId", CapClassType.CONSTANT_GROUP)
                .build();


        this.mockMvc.perform(post(buildUrl("capclass/getlist",CONTOURE,MODULE))
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
                .search("заявок")
                .build();


        this.mockMvc.perform(post(buildUrl("capclass/getlist",CONTOURE,MODULE))
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
        MvcResult result = this.mockMvc.perform(post(buildUrl("capclass/get",CONTOURE,MODULE))
                .content("211")
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(not(containsString("\"errorMessage\":"))))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        CapClassDTO dto = new ObjectMapper().readValue(content,CapClassDTO.class);

        // проверка сохранения изменений
        String checkValue = "---";
        String fldName = "capClassName";
        dto.setCapClassName(checkValue);

        this.mockMvc.perform(post(buildUrl("capclass/save",CONTOURE,MODULE))
                .content(new ObjectMapper().writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(String.format("\"%s\":\"%s\"",fldName,checkValue))));

        // проверка ошибки Запись не найдена
        this.mockMvc.perform(post(buildUrl("capclass/get",CONTOURE,MODULE))
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
        MvcResult result = this.mockMvc.perform(post(buildUrl("capclass/get",CONTOURE,MODULE))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(not(containsString("\"errorMessage\":"))))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        CapClassDTO dto = new ObjectMapper().readValue(content,CapClassDTO.class);

        // проверка сохранения c ошибками
        this.mockMvc.perform(post(buildUrl("capclass/save",CONTOURE,MODULE))
                .content(new ObjectMapper().writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"errorMessage\":")));

        // проверка сохранения без ошибкок

        dto.setCapClassTypeId(CapClassType.ATTRIBUTE_GROUP);
        dto.setCapClassName("xxxxxxxxxxxxxxxxxxx");
        dto.setCapClassBlockflag(0);

        String checkValue = "---";
        String fldName = "capClassCode";
        dto.setCapClassCode(checkValue);


        result = this.mockMvc.perform(post(buildUrl("capclass/save",CONTOURE,MODULE))
                .content(new ObjectMapper().writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(String.format("\"%s\":\"%s\"",fldName,checkValue))))
                .andReturn();
        content = result.getResponse().getContentAsString();
        CapClassView view = new ObjectMapper().readValue(content,CapClassView.class);

        //удаление записи
        this.mockMvc.perform(post(buildUrl("capclass/delete",CONTOURE,MODULE))
                .content("["+view.getCapClassId().toString()+"]")
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(not(containsString("\"errorMessage\":"))));

    }


}

