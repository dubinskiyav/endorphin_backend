package biz.gelicon.core.tests.reports.r0002;

import biz.gelicon.annotation.ReportProvider;
import biz.gelicon.core.reports.ReportDescriptionImpl;
import biz.gelicon.services.OptionForSelectParam;
import biz.gelicon.services.ReportDescription;
import biz.gelicon.services.ReportManager;
import biz.gelicon.services.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ReportProvider
public class ReportService0002Impl implements ReportService {
    private static final Logger logger = LoggerFactory.getLogger(ReportService0002Impl.class);
    private static final String REPORT_CODE = "0002";
    private static final String REPORT_NAME = "Список единиц измерения (тест)";
    private static final String REPORT_UNIT = "edizm";
    private static final String REPORT_ENTITY = "edizm";

    @Override
    public void registerReport(ReportManager manager) {
        ReportDescription report = manager.registerReport(REPORT_CODE, REPORT_NAME);
        ((ReportDescriptionImpl)report).setHolder(this);
        report.forUnit(REPORT_UNIT);
        report.declareParam("onlyNotBlock", "Только не заблокированные",ReportDescription.ParamType.Boolean);
        report.paramInitValue("onlyNotBlock", true);
        report.declareOptionsForParam("onlyNotBlock",manager.createOption(false));
        report.declareParam("str1", "Test str1",ReportDescription.ParamType.String);
        report.declareOptionsForParam("str1",manager.createOption(false));
        report.declareParam("date1", "Test date1",ReportDescription.ParamType.Date);
        report.declareParam("daterange1", "Test daterange1",ReportDescription.ParamType.DateRange);

        report.declareParam("capcode1", "Test capcode1",ReportDescription.ParamType.Integer,ReportDescription.ParamSubType.CapCode);
        report.declareOptionsForParam("capcode1",manager.createOptionCapCode(13,false,false));

        report.declareParam("select1", "Test select1",ReportDescription.ParamType.Integer,ReportDescription.ParamSubType.Select);
        report.declareOptionsForParam("select1",manager.createOptionSelect("capcode/getlist","{\"capCodeType\":13}",
                "capCodeId","capCodeName",true,true));

        report.declareParam("select2", "Test select2",ReportDescription.ParamType.Integer,ReportDescription.ParamSubType.Select);

        List<OptionForSelectParam> values = new ArrayList<>();
        values.add(new OptionForSelectParamImpl(1, "Все"));
        values.add(new OptionForSelectParamImpl(2, "Только активные"));
        values.add(new OptionForSelectParamImpl(3, "Только пассивные"));
        report.declareOptionsForParam("select2",manager.createOptionSelect(values,false));

    }

    @Override
    public String runReport(String code, Map<String, Object> params) {
        //TODO здесь формируем репорт
        return "report code:"+code;
    }
}
