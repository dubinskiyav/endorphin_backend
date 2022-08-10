package biz.gelicon.core.components.core.accessrolerole;

import biz.gelicon.core.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class AccessRoleRoleService extends BaseService<AccessRoleRole> {
    @Autowired
    AccessRoleRoleRepository accessRoleRoleRepository;
    @Autowired
    AccessRoleRoleValidator accessRoleRoleValidator;
    @PostConstruct
    public void init() {
        init(accessRoleRoleRepository, accessRoleRoleValidator);
    }

}

