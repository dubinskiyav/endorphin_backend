package biz.gelicon.core.components.core.accessrolerole;

import biz.gelicon.core.service.BaseService;
import biz.gelicon.core.utils.GridDataOption;
import biz.gelicon.core.utils.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

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
    public static final String ALIAS_MAIN = "T";
    String mainSQL = ""
            + "SELECT * FROM (\n"
            + "SELECT ARR.accessrolerole_id,\n"
            + "       ARR.accessrole_id_parent,\n"
            + "       ARP.accessrole_name accessrole_name_parent,\n"
            + "       ARP.accessrole_note accessrole_note_parent,\n"
            + "       ARP.accessrole_visible accessrole_visible_parent,\n"
            + "       ARR.accessrole_id_child,\n"
            + "       ARC.accessrole_name accessrole_name_child,\n"
            + "       ARC.accessrole_note accessrole_note_child,\n"
            + "       ARC.accessrole_visible accessrole_visible_child\n"
            + "FROM   accessrolerole ARR,\n"
            + "       accessrole ARP,\n"
            + "       accessrole ARC\n"
            + "WHERE  ARP.accessrole_id = ARR.accessrole_id_parent\n"
            + "  AND  ARC.accessrole_id = ARR.accessrole_id_child\n"
            + ") T\n"
            + "       /*FullTextJoin*/\n"
            + "       /*FROM_PLACEHOLDER*/\n"
            + "/*ORDERBY_PLACEHOLDER*/";

    public List<AccessRoleRoleView> getMainList(GridDataOption gridDataOption) {
        // dav
        // FullTextJoin будем делать вручную
        Query.QueryBuilder<AccessRoleRoleView> queryBuilder =
                buildFullText(gridDataOption);
        return queryBuilder
                .setMainAlias(ALIAS_MAIN)
                .setPageableAndSort(gridDataOption.buildPageRequest())
                //.setFrom(gridDataOption.buildFullTextJoin("accessrolerole", ALIAS_MAIN))
                .setPredicate(gridDataOption.buildPredicate(AccessRoleRoleView.class, ALIAS_MAIN))
                .setParams(gridDataOption.buildQueryParams())
                .build(AccessRoleRoleView.class)
                .execute();
    }

    public int getMainCount(GridDataOption gridDataOption) {
        // dav
        // FullTextJoin будем делать вручную
        Query.QueryBuilder<AccessRoleRoleView> queryBuilder = buildFullText(gridDataOption);
        return queryBuilder
                .setMainAlias(ALIAS_MAIN)
                .setPageableAndSort(gridDataOption.buildPageRequest())
                //.setFrom(gridDataOption.buildFullTextJoin("accessrolerole", ALIAS_MAIN))
                .setPredicate(gridDataOption.buildPredicate(AccessRoleRoleView.class, ALIAS_MAIN))
                .setParams(gridDataOption.buildQueryParams())
                .build(AccessRoleRoleView.class)
                .count();
    }
    /**
     * Модифицирует запрос - вставляет полнотекстовый поиск
     * @param gridDataOption - опции выборки
     * @return - запрос
     */
    private Query.QueryBuilder<AccessRoleRoleView> buildFullText(
            GridDataOption gridDataOption) {
        Query.QueryBuilder<AccessRoleRoleView> queryBuilder = new Query.QueryBuilder<>(mainSQL);
        String fts = "";
        if (gridDataOption.isFullTextSearch()) {
            fts = gridDataOption.buildFullTextJoin("accessrolerole", ALIAS_MAIN)
                    + " AND lower(ft_.fulltext) like '%" + gridDataOption.getSearch() + "%'";
            queryBuilder.injectSQL("/*FullTextJoin*/", fts);
            gridDataOption.setSearch(null);
            // logger.info(fts);
        }
        return queryBuilder;
    }

    // Выборка одной записи по id
    public AccessRoleRoleView getOne(Integer id) {
        Query.QueryBuilder<AccessRoleRoleView> queryBuilder = new Query.QueryBuilder<>(mainSQL);
        AccessRoleRoleView ret = queryBuilder
                .setPredicate(ALIAS_MAIN+".accessrolerole_id = :accessroleroleId")
                .build(AccessRoleRoleView.class)
                .executeOne("accessroleroleId", id);
        return ret;
    }


}

