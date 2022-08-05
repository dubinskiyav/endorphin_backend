package biz.gelicon.core.controllers;

import biz.gelicon.core.utils.GridDataOption;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class TownControllerTest extends IntergatedTest {
    private static final String CONTOURE = "refbooks";
    private static final String MODULE = "town";

    @BeforeAll
    public static void setup() {
        token = "e9b3c034-fdd5-456f-825b-4c632f2053ac"; //root
    }

    @Test
    @Transactional
    @Rollback
    public void getlistTest() throws Exception {
        // базовая проверка
        GridDataOption options = new GridDataOption.Builder()
                .pagination(1, 25)
                .addSort("townId", Sort.Direction.ASC)
                .build();

        this.mockMvc.perform(post(buildUrl("town/getlist",CONTOURE,MODULE))
                .content(new ObjectMapper().writeValueAsString(options))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"rowCount\":"+3)))
                .andExpect(content().string(containsString("\"towntypeName\":\"ГОРОД\"")))
                .andExpect(content().string(containsString("\"townsubordinateName\":\"Столица региона\"")))
                .andExpect(content().string(containsString("\"countryName\":\"РОССИЯ\"")));
    }

    @Test
    @Transactional
    @Rollback
    public void findTest() throws Exception{
        /*
        TownController.SimpleSearchOption option = new TownController.SimpleSearchOption();
        option.setSearch("ивост");

        this.mockMvc.perform(post(buildUrl("town/find",CONTOURE,MODULE))
                .content(new ObjectMapper().writeValueAsString(option))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Владивосток")))
                .andExpect(content().string(not(containsString("Пермь"))))
                .andExpect(content().string(not(containsString("Санкт-Петербург"))));

         */
    }

}

