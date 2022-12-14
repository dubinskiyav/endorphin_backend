package biz.gelicon.core.components.core.progusercredential;

import biz.gelicon.core.components.core.capcode.CapCode;
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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProguserCredentialRepository implements TableRepository<ProguserCredential> {

    private static final Logger logger = LoggerFactory.getLogger(ProguserCredentialRepository.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ProguserCredential findByProguser(Integer proguserId, Integer authType, Integer tempFlag) {
        Map<String, Object> params = new HashMap<>();
        params.put("proguser_id",proguserId);
        params.put("type",authType);
        params.put("tempflag",tempFlag);
        List<ProguserCredential> list = findWhere("m0.proguser_id=:proguser_id and m0.progusercredential_type=:type " +
                "and m0.progusercredential_tempflag=:tempflag", params);
        return !list.isEmpty()?list.get(0):null;
    }

    @Override
    public void create() {
        Resource resource = new ClassPathResource("sql/400250-progusercredential.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.setSqlScriptEncoding("UTF-8");
        databasePopulator.execute(jdbcTemplate.getDataSource());
        logger.info("progusercredential created");
    }

    @Override
    public int load() {
        // это хеш от pswd
        final String pswd = "e572cec4ee0d79651b3f8b15339047e1e51600036cce3f65dfecc556e6cd1279edaba62938dba7f53475706679790797";
        // это хеш от masterkey  "e9b3c034-fdd5-456f-825b-4c632f2053ac"
        final String masterkey = "e572cec4ee0d79651b3f8b15339047e1c70f4ea0d43c3ca3287ff7ba623ca15fb4d0fa73ef2d04a51cf84f644aa1ee62";
        // admin  "token": "15a5a967-7a71-46f4-9af9-e3878b7fffac"
        final String admin = "e572cec4ee0d79651b3f8b15339047e1c61c62acf1ce5c9c1e5fb5b15bdd4582bd040efcf8984540ab1a77194165346c";
        // user  "token": "c85bcbdf-d4f8-434b-ab43-3308099ad561"
        final String user = "e572cec4ee0d79651b3f8b15339047e11264ed1a9d4e478a641df5237aad091d6b0d096e737d72fd69e4dc34ce8f23f7";
        // worker  "token": "bf528245-ce41-4ab4-9595-910191c0b1b1"
        final String worker = "e572cec4ee0d79651b3f8b15339047e114e4bab5365591748fc39e3ffc353220cc029b920795cd88f9d3c4877de8c1a7";
        // user1  "token": "bf528245-ce41-4ab4-9595-910191c0b1b1"
        final String user1 = "e572cec4ee0d79651b3f8b15339047e114e4bab5365591748fc39e3ffc353220cc029b920795cd88f9d3c4877de8c1a7";
        ProguserCredential[] data =  new ProguserCredential[] {
                new ProguserCredential(1,masterkey,1, CapCode.AUTH_BYPASSWORD,0,0), // SYSDBA
                new ProguserCredential(2,masterkey,2, CapCode.AUTH_BYPASSWORD,0,0), // FULL_ACCESS
                /*
                new ProguserCredential(2,admin,2, CapCode.AUTH_BYPASSWORD,0,0),
                new ProguserCredential(3,user,3, CapCode.AUTH_BYPASSWORD,0,0),
                new ProguserCredential(4,worker,4, CapCode.AUTH_BYPASSWORD,0,0),
                new ProguserCredential(5,user1,5, CapCode.AUTH_BYPASSWORD,0,0),

                 */
        };
        insert(Arrays.asList(data));
        logger.info(String.format("%d progusercredential loaded", data.length));
        DatabaseUtils.setSequence("progusercredential_id_gen", data.length+1);
        return data.length;
    }

}

