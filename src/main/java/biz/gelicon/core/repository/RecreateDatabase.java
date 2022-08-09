package biz.gelicon.core.repository;

import biz.gelicon.core.components.core.accessrole.AccessRoleRepository;
import biz.gelicon.core.components.core.accessrolerole.AccessRoleRoleRepository;
import biz.gelicon.core.components.core.application.ApplicationRepository;
import biz.gelicon.core.components.core.applicationrole.ApplicationroleRepository;
import biz.gelicon.core.components.core.cal.CalRepository;
import biz.gelicon.core.components.core.capclass.CapClassRepository;
import biz.gelicon.core.components.core.capclasstype.CapClassTypeRepository;
import biz.gelicon.core.components.core.capcode.CapCodeRepository;
import biz.gelicon.core.components.core.capcodetype.CapCodeTypeRepository;
import biz.gelicon.core.components.core.capconfig.CapconfigRepository;
import biz.gelicon.core.components.core.capjob.CapjobRepository;
import biz.gelicon.core.components.core.capjobhistory.CapjobhistoryRepository;
import biz.gelicon.core.components.core.capresource.ArtifactRepository;
import biz.gelicon.core.components.core.capresourceapp.CapresourceappRepository;
import biz.gelicon.core.components.core.capresourcenumberdoc.CapresourcenumberdocRepository;
import biz.gelicon.core.components.core.capresourcerole.CapresourceroleRepository;
import biz.gelicon.core.components.core.address.AddressRepository;
import biz.gelicon.core.components.core.addressindex.AaddressindexRepository;
import biz.gelicon.core.components.core.building.BuildingRepository;
import biz.gelicon.core.components.core.company.CompanyRepository;
import biz.gelicon.core.components.core.controlobject.ControlObjectRepository;
import biz.gelicon.core.components.core.controlobjectrole.ControlObjectRoleRepository;
import biz.gelicon.core.components.core.daycal.DaycalRepository;
import biz.gelicon.core.components.core.dayofweek.DayofweekRepository;
import biz.gelicon.core.components.core.document.DocumentRepository;
import biz.gelicon.core.components.core.monthcal.MonthcalRepository;
import biz.gelicon.core.components.core.proguser.ProgUserRepository;
import biz.gelicon.core.components.core.proguserauth.ProgUserAuthRepository;
import biz.gelicon.core.components.core.proguserchannel.ProguserChannelRepository;
import biz.gelicon.core.components.core.progusercredential.ProguserCredentialRepository;
import biz.gelicon.core.components.core.progusergroup.ProgUserGroupRepository;
import biz.gelicon.core.components.core.proguserrole.ProgUserRoleRepository;
import biz.gelicon.core.components.core.proguserworker.ProguserWorkerRepository;
import biz.gelicon.core.components.core.resourcetrantype.ResourcetrantypeRepository;
import biz.gelicon.core.components.core.resourcetype.ResourcetypeRepository;
import biz.gelicon.core.components.core.sqlaction.SqlActionRepository;
import biz.gelicon.core.components.core.weeknumber.WeeknumberRepository;
import biz.gelicon.core.components.core.yearcal.YearcalRepository;
import biz.gelicon.core.components.core.attribute.AttributeRepository;
import biz.gelicon.core.components.core.clusterr.ClusterrRepository;
import biz.gelicon.core.components.core.companybranch.CompanybranchRepository;
import biz.gelicon.core.components.core.companycode.CompanycodeRepository;
import biz.gelicon.core.components.core.constant.ConstantRepository;
import biz.gelicon.core.components.core.constantvalue.ConstantValueRepository;
import biz.gelicon.core.components.core.country.CountryRepository;
import biz.gelicon.core.components.core.district.DistrictRepository;
import biz.gelicon.core.components.core.mycompany.MycompanyRepository;
import biz.gelicon.core.components.core.progusersubject.ProgusersubjectRepository;
import biz.gelicon.core.components.core.street.StreetRepository;
import biz.gelicon.core.components.core.streettype.StreetTypeRepository;
import biz.gelicon.core.components.core.subcluster.SubclusterRepository;
import biz.gelicon.core.components.core.subdistrict.SubdistrictRepository;
import biz.gelicon.core.components.core.subject.SubjectRepository;
import biz.gelicon.core.components.core.subjectattributevalue.SubjectattributevalueRepository;
import biz.gelicon.core.components.core.subjecttype.SubjectTypeRepository;
import biz.gelicon.core.components.core.town.TownRepository;
import biz.gelicon.core.components.core.townsubordinate.TownsubordinateRepository;
import biz.gelicon.core.components.core.towntype.TownTypeRepository;
import biz.gelicon.core.components.core.worker.WorkerRepository;
import biz.gelicon.core.components.erp.todo.appendix.AppendixRepository;
import biz.gelicon.core.components.erp.todo.documentagreement.DocumentAgreementRepository;
import biz.gelicon.core.components.erp.todo.documentagreementuser.DocumentAgreementUserRepository;
import biz.gelicon.core.components.erp.todo.documentappendixrole.DocumentAppendixRoleRepository;
import biz.gelicon.core.components.erp.todo.documentreal.DocumentRealRepository;
import biz.gelicon.core.components.erp.todo.documentrealagree.DocumentRealAgreeRepository;
import biz.gelicon.core.components.erp.todo.documentrealagreeInfo.DocumentRealAgreeInfoRepository;
import biz.gelicon.core.components.erp.todo.documentrealagreeuser.DocumentRealAgreeUserRepository;
import biz.gelicon.core.components.erp.todo.documentrealtransit.DocumentRealTransitRepository;
import biz.gelicon.core.components.erp.todo.documenttransit.DocumentTransitRepository;
import biz.gelicon.core.components.erp.todo.edizm.EdizmRepository;
import biz.gelicon.core.components.core.notification.NotificationRepository;
import biz.gelicon.core.components.erp.todo.notificationappendix.NotificationAppendixRepository;
import biz.gelicon.core.components.erp.todo.notificationsetting.NotificationSettingRepository;
import biz.gelicon.core.components.erp.todo.patternnotification.PatternNotificationRepository;
import biz.gelicon.core.components.erp.todo.pricesgoodsubject.PriceSgoodSubjectRepository;
import biz.gelicon.core.components.erp.todo.sgood.SgoodRepository;
import biz.gelicon.core.components.erp.todo.uniquetype.UniqueTypeRepository;
import biz.gelicon.core.utils.UsefulUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;


import java.util.Arrays;

/**
 * Пересоздание всех таблиц базы данных endorphin
 */
@Repository
public class RecreateDatabase {

    static Logger logger = LoggerFactory.getLogger(RecreateDatabase.class);

    @Autowired
    private PlatformTransactionManager transactionManager;
    DefaultTransactionDefinition defaultTransactionDefinition;
    TransactionStatus transactionStatus;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    CapCodeTypeRepository capCodeTypeRepository;
    @Autowired
    CapCodeRepository capCodeRepository;

    @Autowired
    ProgUserRoleRepository progUserRoleRepository;

    @Autowired
    SqlActionRepository sqlActionRepository;
    @Autowired
    ControlObjectRoleRepository controlObjectRoleRepository;
    @Autowired
    ResourcetrantypeRepository resourcetrantypeRepository;
    @Autowired
    ResourcetypeRepository resourcetypeRepository;
    @Autowired
    CapconfigRepository capconfigRepository;
    @Autowired
    ArtifactRepository artifactRepository;
    @Autowired
    CapresourceroleRepository capresourceroleRepository;
    @Autowired
    CapresourceappRepository capresourceappRepository;
    @Autowired
    ApplicationRepository applicationRepository;
    @Autowired
    ApplicationroleRepository applicationroleRepository;
    @Autowired
    DocumentRepository documentRepository;
    @Autowired
    CapresourcenumberdocRepository capresourcenumberdocRepository;
    @Autowired
    ProgUserAuthRepository progUserAuthRepository;
    @Autowired
    ProguserChannelRepository proguserChannelRepository;
    @Autowired
    ProguserCredentialRepository proguserCredentialRepository;
    @Autowired
    CapClassTypeRepository capClassTypeRepository;
    @Autowired
    CapClassRepository capClassRepository;
    @Autowired
    DaycalRepository daycalRepository;
    @Autowired
    DayofweekRepository dayofweekRepository;
    @Autowired
    MonthcalRepository monthcalRepository;
    @Autowired
    YearcalRepository yearcalRepository;
    @Autowired
    WeeknumberRepository weeknumberRepository;
    @Autowired
    CalRepository calRepository;
    @Autowired
    CapjobRepository capjobRepository;
    @Autowired
    CapjobhistoryRepository capjobhistoryRepository;
    @Autowired
    NotificationRepository notificationRepository;

    // erp контур
    @Autowired
    SubjectTypeRepository subjectTypeRepository;
    @Autowired
    ClusterrRepository clusterrRepository;
    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    SubclusterRepository subclusterRepository;
    @Autowired
    ProgusersubjectRepository progusersubjectRepository;
    @Autowired
    WorkerRepository workerRepository;
    @Autowired
    AttributeRepository attributeRepository;
    @Autowired
    ConstantRepository constantRepository;
    @Autowired
    ConstantValueRepository constantValueRepository;
    @Autowired
    SubjectattributevalueRepository subjectattributevalueRepository;
    @Autowired
    TownTypeRepository townTypeRepository;
    @Autowired
    CountryRepository countryRepository;
    @Autowired
    TownsubordinateRepository townsubordinateRepository;
    @Autowired
    TownRepository townRepository;
    @Autowired
    StreetTypeRepository streetTypeRepository;
    @Autowired
    StreetRepository streetRepository;
    @Autowired
    BuildingRepository buildingRepository;
    @Autowired
    DistrictRepository districtRepository;
    @Autowired
    SubdistrictRepository subdistrictRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    AaddressindexRepository aaddressindexRepository;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    MycompanyRepository mycompanyRepository;
    @Autowired
    CompanybranchRepository companybranchRepository;
    @Autowired
    CompanycodeRepository companycodeRepository;
    @Autowired
    ProguserWorkerRepository proguserWorkerRepository;
    @Autowired
    AccessRoleRoleRepository accessRoleRoleRepository;


    @Autowired
    EdizmRepository edizmRepository;
    @Autowired
    private ProgUserRepository progUserRepository;
    @Autowired
    private ProgUserGroupRepository progUserGroupRepository;
    @Autowired
    private AccessRoleRepository accessRoleRepository;
    @Autowired
    private ControlObjectRepository controlObjectRepository;
    @Autowired
    private UniqueTypeRepository uniqueTypeRepository;
    @Autowired
    private NotificationSettingRepository notificationSettingRepository;
    @Autowired
    private PatternNotificationRepository patternNotificationRepository;
    @Autowired
    private DocumentRealRepository documentrealRepository;
    @Autowired
    private DocumentTransitRepository documentTransitRepository;
    @Autowired
    private DocumentRealTransitRepository documentRealTransitRepository;
    @Autowired
    private DocumentRealAgreeRepository documentRealAgreeRepository;
    @Autowired
    private DocumentRealAgreeUserRepository documentRealAgreeUserRepository;
    @Autowired
    private DocumentRealAgreeInfoRepository documentRealAgreeInfoRepository;
    @Autowired
    private DocumentAgreementRepository documentAgreementRepository;
    @Autowired
    private DocumentAgreementUserRepository documentAgreementUserRepository;
    @Autowired
    private NotificationAppendixRepository notificationAppendixRepository;
    @Autowired
    private SgoodRepository sgoodRepository;
    @Autowired
    private PriceSgoodSubjectRepository priceSgoodSubjectRepository;
    @Autowired
    private AppendixRepository appendixRepository;
    @Autowired
    private DocumentAppendixRoleRepository documentAppendixRoleRepository;

    protected TableRepository[] registerRepos;

    public void registerRepo() {
        registerRepos = new TableRepository[]{
                progUserGroupRepository
                , capCodeTypeRepository
                , capCodeRepository
                , progUserRepository
                , accessRoleRepository
                , progUserRoleRepository
                , controlObjectRepository
                , sqlActionRepository
                , controlObjectRoleRepository
                , resourcetrantypeRepository
                , resourcetypeRepository
                , capconfigRepository
                , artifactRepository
                , capresourceroleRepository
                , applicationRepository
                , applicationroleRepository
                , documentRepository
                , capresourcenumberdocRepository
                , capresourceappRepository
                , progUserAuthRepository
                , proguserChannelRepository
                , proguserCredentialRepository
                , capClassTypeRepository
                , capClassRepository
                , daycalRepository
                , dayofweekRepository
                , monthcalRepository
                , yearcalRepository
                , weeknumberRepository
                , calRepository
                , capjobRepository
                , capjobhistoryRepository
                , notificationRepository
                , accessRoleRoleRepository
                // erp контур
                , subjectTypeRepository
                , clusterrRepository
                , subjectRepository
                , subclusterRepository
                , progusersubjectRepository
                , workerRepository
                , attributeRepository
                , constantRepository
                , constantValueRepository
                , subjectattributevalueRepository
                , townTypeRepository
                , countryRepository
                , townsubordinateRepository
                , townRepository
                , streetTypeRepository
                , streetRepository
                , buildingRepository
                , districtRepository
                , subdistrictRepository
                , addressRepository
                ,aaddressindexRepository
                ,companyRepository
                ,mycompanyRepository
                ,companybranchRepository
                ,companycodeRepository
                ,proguserWorkerRepository
                /*
                  progUserAuthRepository,
                  edizmRepository,
                  measureRepository,
                  proguserChannelRepository,
                  applicationRepository,
                  documentRepository,
                  uniqueTypeRepository,
                  notificationSettingRepository,
                  patternNotificationRepository,
                  documentrealRepository,
                  documentTransitRepository,
                  documentRealTransitRepository,
                  testRequestRepository,
                  notificationAppendixRepository,
                  documentRealAgreeRepository,
                  documentRealAgreeUserRepository,
                  documentRealAgreeInfoRepository,
                  documentAgreementRepository,
                  documentAgreementUserRepository,
                  sgoodRepository,
                  priceSgoodSubjectRepository,
                  appendixRepository,
                  documentAppendixRoleRepository

                   */

                //TODO здесь регистриуются репозитории.
                // Внимание! В правильном порядке СОЗДАНИЯ таблиц
        };
    }

    public void common() {
        // dav 01.08.2022
        /*
        Resource resource = new ClassPathResource("sql/400150-common.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.setSqlScriptEncoding("UTF-8");
        databasePopulator.execute(jdbcTemplate.getDataSource());
        logger.info("common script success");

         */
    }

    public void dropCommon() {
        // dav 01.08.2022
        /*
        Resource resource = new ClassPathResource("sql/common_drop.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.setSqlScriptEncoding("UTF-8");
        databasePopulator.execute(jdbcTemplate.getDataSource());
        logger.info("common script success");

         */
    }

    /**
     * User Defined Function
     */
    public void udf() {
        Resource resource = new ClassPathResource("sql/400160-udf.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.setSqlScriptEncoding("UTF-8");
        // https://ru.stackoverflow.com/questions/909894/%D0%9F%D1%80%D0%BE%D0%B1%D0%BB%D0%B5%D0%BC%D0%B0-%D1%81%D0%BE-%D0%B7%D0%BD%D0%B0%D0%BA%D0%BE%D0%BC-%D0%B4%D0%BE%D0%BB%D0%BB%D0%B0%D1%80%D0%B0-%D0%BF%D1%80%D0%B8-%D1%81%D0%BE%D0%B7%D0%B4%D0%B0%D0%BD%D0%B8%D0%B8-%D1%84%D1%83%D0%BD%D0%BA%D1%86%D0%B8%D0%B8-psqlexception

        databasePopulator.execute(jdbcTemplate.getDataSource());
        logger.info("400160-udf.sql script success");
    }

    public void dropUdf() {
        Resource resource = new ClassPathResource("sql/400161-udf-drop.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.setSqlScriptEncoding("UTF-8");
        databasePopulator.execute(jdbcTemplate.getDataSource());
        logger.info("400161-udf-drop.sql script success");
    }

    /**
     * Создание всех таблиц базы данных
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void create() {
        logger.info("Creating tables ...");
        Arrays.asList(registerRepos).forEach(TableRepository::create);
        logger.info("Creating tables ... Ok");
    }

    /**
     * Создание всех представлений для полнотекстового поиска
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void view() {
        logger.info("Creating view ...");
        Arrays.asList(registerRepos).forEach(TableRepository::createFullTextView);
        logger.info("Creating view ... Ok");
    }

    /**
     * Удаление всех таблиц базы данных
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void drop() {
        logger.info("Dropping tables ...");
        // удаляем таблицы в обратном порядке
        Arrays.stream(registerRepos)
                .collect(UsefulUtils.toListReversed()).forEach(TableRepository::dropForTest);
        // завершаем вызовом скрипта удаления общих данных
        dropCommon();
        // Удаляем UDF
        dropUdf();
        logger.info("Dropping tables ... Ok");
    }

    /**
     * Загрузка начальных данных в таблицы
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void load() {
        logger.info("Loading tables ...");
        Arrays.asList(registerRepos).forEach(TableRepository::load);
        logger.info("Loading tables ... Ok");
    }

    /**
     * Удаление данных из всех таблиц
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete() {
        logger.info("Deleting tables ...");
        Arrays.asList(registerRepos).forEach(TableRepository::deleteAll);
        logger.info("Deleting tables ... Ok");
    }

    /**
     * Пересоздание всех таблиц и прогрузка данными
     */
    public void recreate() {
        // Открываем таранзакцию
        defaultTransactionDefinition = new DefaultTransactionDefinition();
        transactionStatus = transactionManager.getTransaction(defaultTransactionDefinition);
        try {
            registerRepo();
            drop();
            udf();
            common();
            create();
            view();
            load();
            transactionManager.commit(transactionStatus);
        } catch (Exception e) {
            String errText = "Error. Transaction will be rolled back";
            logger.error(errText, e);
            transactionManager.rollback(transactionStatus);
            throw new RuntimeException(errText, e);
        }
        logger.info("Database recreated");
    }



}
