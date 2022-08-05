package biz.gelicon.core.tests.reports.r0001;

import biz.gelicon.annotation.ReportProvider;
import biz.gelicon.core.reports.ReportDescriptionImpl;
import biz.gelicon.services.ReportDescription;
import biz.gelicon.services.ReportManager;
import biz.gelicon.services.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@ReportProvider
public class ReportService0001Impl implements ReportService {
    private static final Logger logger = LoggerFactory.getLogger(ReportService0001Impl.class);
    private static final String REPORT_CODE = "0001";
    private static final String REPORT_NAME = "Список пользователей (тест)";
    private static final String REPORT_UNIT = "credential";
    private static final String REPORT_ENTITY = "proguser";

    @Override
    public void registerReport(ReportManager manager) {
        ReportDescription report = manager.registerReport(REPORT_CODE, REPORT_NAME);
        ((ReportDescriptionImpl)report).setHolder(this);
        report.forUnit(REPORT_UNIT);
        report.declareParam("status", "Статус пользователя",ReportDescription.ParamType.Integer, ReportDescription.ParamSubType.CapCode);
        report.declareOptionsForParam("status", manager.createOptionCapCode(13,true,true));
    }

    @Override
    public String runReport(String code, Map<String, Object> params) {
        //TODO здесь формируем репорт
        return "report code:"+code;
    }
}
