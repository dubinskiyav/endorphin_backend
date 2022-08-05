package biz.gelicon.core.controllers;

import biz.gelicon.core.components.core.address.AddressService;
import biz.gelicon.core.components.core.address.AddressView;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AddressTest extends IntergatedTest {

    @BeforeAll
    public static void setup() {
        token = "e9b3c034-fdd5-456f-825b-4c632f2053ac"; //root
    }

    @Autowired
    private AddressService addressService;


    @Test
    public void findTest() throws Exception {

        this.mockMvc.perform(post(buildUrl("/apps/refbooks/address/address/find"))
                .content("{\"search\":\"Лени\"}")
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("ЛЕНИНА 55")));

        this.mockMvc.perform(post(buildUrl("/apps/refbooks/address/address/find"))
                .content("{\"search\":\"Ленина 55\"}")
                .contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("ЛЕНИНА 55")));

    }

    @Test
    public void getOneTest() throws Exception {
        AddressView address = addressService.getOne(1);
        //logger.info(address.toString());
        assertEquals("РОССИЯ, Пермь, Ленина 55",address.getFullAddress());
    }
}
