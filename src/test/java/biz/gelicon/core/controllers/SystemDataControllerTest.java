package biz.gelicon.core.controllers;

import biz.gelicon.core.components.core.capcodetype.CapCodeType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SystemDataControllerTest extends IntergatedTest {
    private static final String CONTOURE = "system";
    @BeforeAll
    public static void setup() {
        token = "e9b3c034-fdd5-456f-825b-4c632f2053ac"; //root
    }

    @Test
    public void selectCapCodeTest() throws Exception {
        SystemDataController.CapCodeRequest request = new SystemDataController.CapCodeRequest();
        request.setCapCodeTypeId(CapCodeType.USER_STATUS);

        this.mockMvc.perform(post(buildUrl("capcode/getlist",CONTOURE))
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"capCodeTypeId\":13")));

    }

    @Test
    public void selectUniqueType() throws Exception {

        this.mockMvc.perform(post(buildUrl("uniquetype/getlist",CONTOURE))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(not(containsString("\"errorMessage\":"))))
                .andExpect(content().string(containsString("\"rowCount\":6")));

    }

    @Test
    public void selectCapClassTest() throws Exception {
        SystemDataController.CapClassRequest request = new SystemDataController.CapClassRequest();
        request.setCapClassTypeId(21);

        this.mockMvc.perform(post(buildUrl("capclass/getlist",CONTOURE))
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"capClassId\":211")));

    }

    @Test
    public void selectCapClassTypeTest() throws Exception {
        this.mockMvc.perform(post(buildUrl("capclasstype/getlist",CONTOURE))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"capresourceId\":211")));

    }

    @Test
    public void selectDocumentTransitsTest() throws Exception {
        this.mockMvc.perform(post(buildUrl("document/getstatuslist",CONTOURE))
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"documentTransitColor\":")));

    }

    @Test
    public void selectDocumentRealNameTest() throws Exception {
        this.mockMvc.perform(post(buildUrl("documentreal/getname",CONTOURE))
                .content("1")
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"Корневой уровень документов\"")));

    }

}
