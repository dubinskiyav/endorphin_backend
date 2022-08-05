package biz.gelicon.core.controllers;

import biz.gelicon.core.dialect.FireBirdDialect;
import biz.gelicon.core.components.core.proguser.Proguser;
import biz.gelicon.core.components.core.proguser.ProgUserRepository;
import biz.gelicon.core.security.AuthenticationBean;
import biz.gelicon.core.components.core.proguser.ProguserService;
import biz.gelicon.core.utils.DateUtils;
import biz.gelicon.core.utils.GridDataOption;
import biz.gelicon.core.utils.SecurityUtils;
import biz.gelicon.core.utils.UsefulUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.security.core.Authentication;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Disabled
@SpringBootTest(properties = {
        "gelicon.orm.recreatedatabase=false",
        "gelicon.report.restcrictOverlapping=false"
})
public class DebugTests {
    static Logger logger = LoggerFactory.getLogger(DebugTests.class);

    @Test
    public void testDataOption() {
        List<GridDataOption.OrderBy> sort = new ArrayList<>();
        sort.add(new GridDataOption.OrderBy("name", Sort.Direction.ASC));
        sort.add(new GridDataOption.OrderBy("measureName", Sort.Direction.DESC));
        Map<String,Object> filters =  new HashMap<>();
        filters.put("quick.name.like","test1");
        filters.put("name-filter-1","test2");

        GridDataOption gridDataOption = new GridDataOption();
        gridDataOption.getPagination().setCurrent(3);
        gridDataOption.getPagination().setPageSize(4);
        gridDataOption.setSort(sort);
        gridDataOption.setFilters(filters);

        logger.info(gridDataOption.toString());

        assertEquals(gridDataOption.toString(),"GridDataOption {pagination=Pagination{current=3, pageSize=4}, search=null, filters={name-filter-1=test2, quick.name.like=test1}, sort={OrderBy{field='name', direction=ASC},OrderBy{field='measureName', direction=DESC}}}");
        assertEquals(gridDataOption.getNamedFilters().size(),1);
        assertEquals(gridDataOption.getQuickFilters().size(),1);

    }

    @Test
    public void hashTest() {
        assertTrue(SecurityUtils.checkPassword("pswd",
                "e572cec4ee0d79651b3f8b15339047e1e51600036cce3f65dfecc556e6cd1279edaba62938dba7f53475706679790797"));
    }

    @Test
    public void dateTest() {
        logger.info(DateUtils.atStartOfMonth(new Date()).toString());
        logger.info(DateUtils.atEndOfMonth(new Date()).toString());
    }

    @Test
    public void dialectTest() {
        FireBirdDialect dialect = new FireBirdDialect();
        logger.info(
                dialect.extractTableNameFromForeignKeyMessage("SQL Error [335544466] [23000]: violation of FOREIGN KEY constraint " +
                "\"SGOODEDIZM_FK1\" on table \"SGOODEDIZM\"; Foreign key reference target does not exist; Problematic key " +
                "value is (\"SGOOD_ID\" = 10000) [SQLState:23000, ISC error code:335544466]"));
        logger.info(
                dialect.extractTableNameFromForeignKeyMessage("SQL Error [335544466] [23000]: violation of FOREIGN KEY constraint " +
                        "\"SDEPSGOODOUTDEF_FK2\" on table \"SDEPSGOODOUTDEF\"; Foreign key references are present for the record; Problematic " +
                        "key value is (\"SGOOD_ID\" = -1) [SQLState:23000, ISC error code:335544466]"));

        logger.info(
                dialect.extractColumnNameFromDuplicateMessage("SQL Error [335544665] [23000]: violation of PRIMARY or UNIQUE KEY constraint " +
                        "\"SGOODEDIZM_PK\" on table \"SGOODEDIZM\"; Problematic key value is (\"SGOOD_ID\" = -2) " +
                        "[SQLState:23000, ISC error code:335544665]"));

    }

    @Test
    public void dialectDuplicateTest() {
        FireBirdDialect dialect = new FireBirdDialect();
        logger.info(
                dialect.extractColumnNameFromDuplicateMessage("SQL Error [335544665] [23000]: violation of PRIMARY or UNIQUE KEY constraint " +
                        "\"SGOODEDIZM_PK\" on table \"SGOODEDIZM\"; Problematic key value is (\"SGOOD_ID\" = -2) " +
                        "[SQLState:23000, ISC error code:335544665]"));
        logger.info(
                dialect.extractColumnNameFromDuplicateMessage("SQL Error [335544665] [23000]: violation of PRIMARY or UNIQUE KEY constraint \"CORRBILL_AK1\" " +
                        "on table \"CORRBILL\"; Problematic key value is (\"SUBBILLDEBET_ID\" = 11569, \"SUBBILLCREDIT_ID\" = 11792) " +
                        "[SQLState:23000, ISC error code:335544665]"));
    }


    @Test
    public void calculateJobTime() {
        CronExpression expr = CronExpression.parse("0 */5 * * * *");
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime next = expr.next(now);
        now = next;
        next = expr.next(now);
        logger.info(""+Duration.between(now,next).get(ChronoUnit.SECONDS));
    }

    @Autowired
    AuthenticationBean authenticationBean;
    @Autowired
    ProguserService proguserService;
    @Autowired
    ProgUserRepository progUserRepository;

    @Test
    public void authenticate() {
        //UserDetails ud = proguserService.buildUserDetail(1);
        Proguser pu = progUserRepository.findById(1);
        Authentication auth = authenticationBean.authenticate(pu.toUserDetail(), "e9b3c034-fdd5-456f-825b-4c632f2053ac");
    }

    @Value("classpath:/query/worker/worker.sql")
    private Resource sqlFor;
    @Test
    public void testSQLInject() throws IOException {
        logger.info(UsefulUtils.load(sqlFor));
    }

    @Test
    public void hash() throws IOException, NoSuchAlgorithmException {
        logger.info(UsefulUtils.calculateHash(new ByteArrayInputStream("abcdef".getBytes())));
    }
}
