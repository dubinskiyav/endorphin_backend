package biz.gelicon.core.components.erp.todo.documenttransit;

import biz.gelicon.core.service.BaseService;
import biz.gelicon.core.utils.GridDataOption;
import biz.gelicon.core.utils.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class DocumentTransitService extends BaseService<DocumentTransit> {
    private static final Logger logger = LoggerFactory.getLogger(DocumentTransitService.class);
    public static final String ALIAS_MAIN = "m0";

    @Autowired
    private DocumentTransitRepository documentTransitRepository;
    @Autowired
    private DocumentTransitValidator documentTransitValidator;

    // главный запрос. используется в главной таблице
    // в контроллере используется в getlist и save
    @Value("classpath:/query/documenttransit/mainSQL.sql")
    Resource mainSQL;


    @PostConstruct
    public void init() {
        init(documentTransitRepository, documentTransitValidator);
    }

    public List<DocumentTransitView> getMainList(GridDataOption gridDataOption) {
        return new Query.QueryBuilder<DocumentTransitView>(mainSQL)
                .setMainAlias(ALIAS_MAIN)
                .setPageableAndSort(gridDataOption.buildPageRequest())
                .setFrom(gridDataOption.buildFullTextJoin("documenttransit",ALIAS_MAIN))
                .setPredicate(gridDataOption.buildPredicate(DocumentTransitView.class,ALIAS_MAIN))
                .setParams(gridDataOption.buildQueryParams())
                .build(DocumentTransitView.class)
                .execute();
    }

    public int getMainCount(GridDataOption gridDataOption) {
        return new Query.QueryBuilder<DocumentTransitView>(mainSQL)
                .setMainAlias(ALIAS_MAIN)
                .setFrom(gridDataOption.buildFullTextJoin("documenttransit",ALIAS_MAIN))
                .setPredicate(gridDataOption.buildPredicate(DocumentTransitView.class,ALIAS_MAIN))
                .setParams(gridDataOption.buildQueryParams())
                .build(DocumentTransitView.class)
                .count();

    }


    public DocumentTransitView getOne(Integer id) {
        return new Query.QueryBuilder<DocumentTransitView>(mainSQL)
                .setPredicate(ALIAS_MAIN+".documenttransit_id=:documenttransitId")
                .build(DocumentTransitView.class)
                .executeOne("documenttransitId", id);
    }
}

