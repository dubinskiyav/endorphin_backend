package biz.gelicon.core.controllers;

import biz.gelicon.core.audit.AuditController;
import biz.gelicon.core.utils.GridDataOption;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Disabled
@SpringBootTest(properties = {
        "gelicon.orm.recreatedatabase=false",
        "spring.datasource.url=jdbc:postgresql://10.15.3.39:5432/GC_DEVELOP_TRUNK?currentSchema=dbo",
        "gelicon.report.restcrictOverlapping=false",
        "gelicon.artifact.restcrictOverlapping=false",
        "gelicon.audit=true"
})
public class TestWithSpringBoot extends IntergatedTest{
    @Autowired
    AuditController auditController;
    @BeforeAll
    public static void setup() {
        token = "ac68e892-3570-4929-86e7-1b5b0aa31c3d"; //SYSDBA
    }

    SimpleDateFormat sdf =  new SimpleDateFormat("dd.MM.yyyy");

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonConverter;

    @Test
    public void audit() throws Exception {

        AuditController.GridDataOptionAudit options = new GridDataOption.Builder()
                .pagination(1, 25)
                .addFilter("datebeg",sdf.parse("01.05.2021").getTime())
                .addFilter("dateend",sdf.parse("31.05.2021").getTime())
                .addFilter("proguserId",1)
                //.addFilter("kind", AuditKind.CALL_FOR_DELETE.ordinal())
                .addFilter("entity", "edi")
                //.addFilter("idValue", 1)
                .search("xx")
                .build(AuditController.GridDataOptionAudit.class);


        this.mockMvc.perform(post(buildUrl("/apps/admin/audit/log/getlist"))
                .content(new ObjectMapper().writeValueAsString(options))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(not(containsString("\"errorMessage\":"))));

    }


    public static class MessageWithDate {
        Date date;

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }
    }
    @Test
    public void ttest() throws JsonProcessingException {
        MessageWithDate obj = jacksonConverter.getObjectMapper().readValue("{\"date\":null}", MessageWithDate.class);
    }

}
