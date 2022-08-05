package biz.gelicon.core.controllers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SubjectTest  extends IntergatedTest {

    @BeforeAll
    public static void setup() {
        token = "e9b3c034-fdd5-456f-825b-4c632f2053ac"; //root
    }

    @Test
    public void selectTreeTest() throws Exception {
        this.mockMvc.perform(post(buildUrl("/apps/refbooks/subject/subject/gettree"))
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"Объекты аналитического учета\"")));
    }

    @Test
    public void findTest() throws Exception {
        this.mockMvc.perform(post(buildUrl("/apps/refbooks/subject/subject/find"))
                .content("{\"search\":\"опытов\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Рогов")));
    }

}
