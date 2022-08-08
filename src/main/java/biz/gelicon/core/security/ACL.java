package biz.gelicon.core.security;

import biz.gelicon.core.components.core.accessrole.AccessRoleRepository;
import biz.gelicon.core.components.core.controlobjectrole.ObjectRoleView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ACL {
    private static final Logger logger = LoggerFactory.getLogger(ACL.class);

    public static final String SYSTEM_ROLE_CODE = "R#0";
    public static final String SYSTEM_ROLE_NAME = "SYSDBA";
    public static final int SYSTEM_ROLE_ID = 0;

    @Autowired
    AccessRoleRepository accessRoleRepository;

    private Map<String, List<String>> accessTable = new HashMap<>();

    /**
     * Проверяет доступ пользователю на объект доступа
     * @param accobject - объект
     * @param user - пользователь
     * @param permission - тип доступа
     * @return - да нет
     */
    public boolean checkPermission(String accobject, UserDetails user, Permission permission) {
        // системному пользователю можно все
        if(((UserDetailsImpl)user).containsSystemRole()) return true;
        // список ролей пользователя
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        if(authorities.isEmpty())
            return false;
        // получаем список ролей для объекта и операции
        List<String> objectRoles = accessTable.get(genKey(accobject, permission.ordinal()));
        // вообще нет ролей
        if(objectRoles == null) {
            return false;
        }
        // ищем пересечение
        GrantedAuthority intersect = authorities.stream()
                .filter(gauth -> objectRoles.contains(gauth.getAuthority()))
                .findFirst() // хотя бы одна роль найдена
                .orElse(null);
        return intersect != null;
    }

    public void buildAccessTable() {
        logger.info("Build ACL...");
        accessTable.clear();
        List<ObjectRoleView> list = accessRoleRepository.findAllObjectRoles();
        accessTable = list.stream()
                .collect(Collectors.groupingBy(orv->genKey(orv),
                            Collectors.mapping(orv->genRoleName(orv.accessRoleId),Collectors.toList())));
        logger.info("Build ACL [Ok]");
    }

    public static String genKey(ObjectRoleView orv) {
        return  genKey(orv.getControlObjectUri(),orv.getSqlActionId());
    }

    public static String genKey(String accobject,Integer sqlAction) {
        return  accobject+"::"+sqlAction;
    }

    public static String genRoleName(Integer roleId) {
        return "R#"+roleId;
    }


}
