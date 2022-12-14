package biz.gelicon.core.components.core.proguserauth;

import biz.gelicon.core.repository.TableRepository;
import biz.gelicon.core.utils.DatabaseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Repository
public class ProgUserAuthRepository implements TableRepository<ProguserAuth> {
    private static final int TOKEN_DEFAULT_EXPIRY_DURATION = 30;

    private static final Logger logger = LoggerFactory.getLogger(ProgUserAuthRepository.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void updateAccessToken(ProguserAuth auth, Date lastQuery) {
        Map<String, Object> params = new HashMap<>();
        params.put("proguserauthId",auth.getProguserAuthId());
        params.put("proguserauthLastQuery",lastQuery);
        executeSQL("" +
                "UPDATE proguserauth " +
                "SET proguserauth_lastquery=:proguserauthLastQuery " +
                "WHERE proguserauth_id=:proguserauthId",params);
    }

    public ProguserAuth findActiveLastByDate(Integer progUserId) {
        Map<String,Object> args = new HashMap<>();
        args.put("progUserId",progUserId);
        args.put("now",new Date());
        List<ProguserAuth> tokens = findWhere("m0.proguser_id=:progUserId and " +
                        "(m0.proguserauth_dateend is null or m0.proguserauth_dateend>:now) and " +
                        "m0.proguserauth_datecreate=(" +
                        "SELECT max(m1.proguserauth_datecreate) " +
                        "FROM proguserauth m1 " +
                        "WHERE m1.proguser_id=:progUserId and " +
                        "(m1.proguserauth_dateend is null or m1.proguserauth_dateend>:now)" +
                        ")",
                args);
        return !tokens.isEmpty()?tokens.get(0):null;
    }

    /**
     * ?????????????? ?????????? ??????????. ???????? ???????????????? ?? ????????
     * @param progUserId
     * @param duration ???????? ????????????????
     * @return
     */
    public ProguserAuth reNew(Integer progUserId, int duration) {
        String tok = UUID.randomUUID().toString();
        Date now = new Date();
        Date expiryDate =  new Date(now.getTime() + duration*24*60*60*1000l);
        ProguserAuth pua = new ProguserAuth(null, progUserId, new Date(), expiryDate, tok);
        insert(pua);
        return pua;
    }

    /**
     * ?????????? ???? TOKEN_DEFAULT_EXPIRY_DURATION ????????
     * @param progUserId
     * @return
     */
    public ProguserAuth reNew(Integer progUserId) {
        return reNew(progUserId,TOKEN_DEFAULT_EXPIRY_DURATION);
    }

    public ProguserAuth findByValue(String token) {
        List<ProguserAuth> list = findWhere("m0.proguserauth_token=:proguserauth_token", "proguserauth_token", token);
        return !list.isEmpty()?list.get(0):null;
    }

    @Override
    public void create() {
        Resource resource = new ClassPathResource("sql/400220-proguserauth.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.setSqlScriptEncoding("UTF-8");
        databasePopulator.execute(jdbcTemplate.getDataSource());
        logger.info("proguserauth created");
    }


    @Override
    public int load() {

        Date oldDate = null;
        try {
            oldDate = new SimpleDateFormat("dd.MM.yyyy").parse("01.01.2000");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ProguserAuth[] data =  new ProguserAuth[] {
                new ProguserAuth(1,1,new Date(), "e9b3c034-fdd5-456f-825b-4c632f2053ac"), // SYSDBA masterkey
                new ProguserAuth(2,2,new Date(), "15a5a967-7a71-46f4-9af9-e3878b7fffac"),// FULL_ACCESS admin
                /*
                new ProguserAuth(3,3,new Date(), "c85bcbdf-d4f8-434b-ab43-3308099ad561"),// USER user, ???????????? ????????????, ???? ???????????????????????? ????????????????????????
                new ProguserAuth(4,4,new Date(), "bf528245-ce41-4ab4-9595-910191c0b1b1"),// WORKER worker
                new ProguserAuth(5,4,new Date(), oldDate,"62e4e435-85da-48a4-adb0-d91ff1d26624"),// ???????????????????? ?? WORKER

                 */
        };
        insert(Arrays.asList(data));
        logger.info(String.format("%d proguser auth loaded", data.length));
        DatabaseUtils.setSequence("proguserauth_id_gen", data.length+1);
        return data.length;
    }


}
