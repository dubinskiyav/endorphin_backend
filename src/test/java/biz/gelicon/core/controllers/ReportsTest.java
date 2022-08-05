package biz.gelicon.core.controllers;

import biz.gelicon.core.reports.ReportAndParams;
import biz.gelicon.core.reports.ReportManagerImpl;
import biz.gelicon.core.tests.reports.r0001.ReportService0001Impl;
import biz.gelicon.core.tests.reports.r0002.ReportService0002Impl;
import biz.gelicon.core.utils.GridDataOption;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ReportsTest extends IntergatedTest{
    @BeforeAll
    public static void setup() {
        token = "e9b3c034-fdd5-456f-825b-4c632f2053ac"; //root
    }

    @Autowired
    ReportManagerImpl manager;

    @BeforeEach
    public void before() {
        new ReportService0001Impl().registerReport(manager);
        new ReportService0002Impl().registerReport(manager);
    }


    @Test
    public void selectTest() throws Exception {
        GridDataOption options = new GridDataOption.Builder()
                .addFilter("module","edizm")
                .build();

        this.mockMvc.perform(post(buildUrl("getlist","reports"))
                .content(new ObjectMapper().writeValueAsString(options))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"rowCount\":1")));

    }
    @Test
    public void runTest() throws Exception {
        ReportAndParams report = new ReportAndParams();
        report.setCode("0001");

        this.mockMvc.perform(post(buildUrl("run","reports"))
                .content(new ObjectMapper().writeValueAsString(report))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"nameResult\":\"Список пользователей (тест)\"")));

    }
}
