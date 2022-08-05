package biz.gelicon.core.components.core.worker;

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
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Service
public class WorkerService extends BaseService<Worker> {
    private static final Logger logger = LoggerFactory.getLogger(WorkerService.class);
    public static final String ALIAS_MAIN = "m0";

    @Autowired
    private WorkerRepository workerRepository;
    @Autowired
    private WorkerValidator workerValidator;

    // главный запрос. используется в главной таблице
    // в контроллере используется в getlist и save
    @Value("classpath:/query/worker/mainSQL.sql")
    Resource mainSQL;
    // главный запрос для получения одной записи. используется в get
    @Value("classpath:/query/worker/mainSQLForOne.sql")
    Resource mainSQLForOne;


    @PostConstruct
    public void init() {
        init(workerRepository, workerValidator);
    }

    public List<WorkerView> getMainList(GridDataOption gridDataOption) {
        return new Query.QueryBuilder<WorkerView>(mainSQL)
                .setMainAlias(ALIAS_MAIN)
                .setPageableAndSort(gridDataOption.buildPageRequest())
                .setFrom(gridDataOption.buildFullTextJoin("worker",ALIAS_MAIN))
                .setPredicate(gridDataOption.buildPredicate(WorkerView.class,ALIAS_MAIN))
                .setParamTransformer("workerBirthday", (Function<Long, Date>) arg -> new Date(arg))
                .setParams(gridDataOption.buildQueryParams())
                .build(WorkerView.class)
                .execute();
    }

    public int getMainCount(GridDataOption gridDataOption) {
        return new Query.QueryBuilder<WorkerView>(mainSQL)
                .setMainAlias(ALIAS_MAIN)
                .setFrom(gridDataOption.buildFullTextJoin("worker",ALIAS_MAIN))
                .setPredicate(gridDataOption.buildPredicate(WorkerView.class,ALIAS_MAIN))
                .setParamTransformer("workerBirthday", (Function<Long, Date>) arg -> new Date(arg))
                .setParams(gridDataOption.buildQueryParams())
                .build(WorkerView.class)
                .count();

    }

    public List<WorkerView> getListForFind(GridDataOption gridDataOption) {
        return new Query.QueryBuilder<WorkerView>(mainSQL)
                .setMainAlias(ALIAS_MAIN)
                .setPageableAndSort(gridDataOption.buildPageRequest())
                .setFrom(gridDataOption.buildFullTextJoin("worker",ALIAS_MAIN))
                .setPredicate(gridDataOption.buildPredicate(WorkerView.class,ALIAS_MAIN,null))
                .setParams(gridDataOption.buildQueryParams())
                .build(WorkerView.class)
                .execute();
    }

    public WorkerView getOne(Integer id) {
        return new Query.QueryBuilder<WorkerView>(mainSQLForOne)
                .setPredicate(ALIAS_MAIN+".worker_id=:workerId")
                .build(WorkerView.class)
                .executeOne("workerId", id);
    }

    public Worker getByUser(Integer userId) {
        return workerRepository.findWorkerLinkWithUser(userId);
    }
}

