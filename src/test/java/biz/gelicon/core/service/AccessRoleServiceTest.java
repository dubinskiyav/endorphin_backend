package biz.gelicon.core.service;

import biz.gelicon.core.components.core.accessrole.AccessRoleService;
import biz.gelicon.core.controllers.IntergatedTest;
import biz.gelicon.core.components.core.proguser.Proguser;
import biz.gelicon.core.components.core.proguser.ProgUserRepository;
import biz.gelicon.core.security.AuthenticationBean;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

public class AccessRoleServiceTest extends IntergatedTest {

    @Autowired
    AccessRoleService accessRoleService;

    @Autowired
    AuthenticationBean authenticationBean;

    @Autowired
    ProgUserRepository progUserRepository;

    @Test
    void hasRole() {

        //существующая роль
        Proguser proguser = progUserRepository.findById(1);
        authenticationBean.authenticate(proguser.toUserDetail(), "e9b3c034-fdd5-456f-825b-4c632f2053ac");
        Boolean flagTrue = accessRoleService.hasRole("SYSDBA");
        assertEquals(true, flagTrue);

        //несуществующая роль у пользователя
        Boolean flagFalse = accessRoleService.hasRole("TEST1");
        assertEquals(false, flagFalse);

        //несуществующая роль вообще
        Boolean flagg = accessRoleService.hasRole("somerole");
        assertEquals(false, flagg);
    }
}