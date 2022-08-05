package biz.gelicon.core.controllers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DocumentTransitControllerTest extends IntergatedTest {
    private static final String CONTOURE = "system";

    @BeforeAll
    public static void setup() {
        token = "e9b3c034-fdd5-456f-825b-4c632f2053ac"; //root
    }

    // Проверяем только один метод
    @Test
    public void selectTest() throws Exception {
        // Получение списка документов с массивами возможных статусов
        this.mockMvc.perform(post(buildUrl("documenttransit/getlist",CONTOURE))
                .content("[1, 2]")
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Счет составлен")))
                // У documentId = 2 есть один дочерний статус c documentId = 1
                .andExpect(content().string(containsString("\"transitChildIds\":[1]")))
        ;
    }


}

