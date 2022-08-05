package biz.gelicon.core.components.core.address;

import biz.gelicon.core.config.Config;
import biz.gelicon.core.config.EditionTag;
import biz.gelicon.core.utils.GridDataOption;
import biz.gelicon.core.utils.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Сервис не полный, не наследуется от BaseService
 */

@Service
public class AddressService {

    private static final Logger logger = LoggerFactory.getLogger(AddressService.class);
    public static final String ALIAS_MAIN = "m0";
    // максимальное кол-во частей адреса в поисковом индексе
    private static final int MAX_ADDRESS_PART = 10;

    @Autowired
    private AddressRepository addressRepository;
    /**
     * Внимание! Здесь НЕ применяем стандартный метод поиска через представление ft_ Причина в
     * орбъемах данных. Нужны индексы. Поэтому ищем по индексированным полям и через OR
     */
    // главный запрос. используется в главной таблице
    // в контроллере используется в getlist и save
    @Value("classpath:/query/address/mainSQL_GC.sql")
    Resource mainSQL_GC;

    @Value("classpath:/query/address/mainSQL_ERP.sql")
    Resource mainSQL_ERP;

    @Value("classpath:/query/address/mainSQLForOne_GC.sql")
    Resource mainSQLForOne_GC;

    @Value("classpath:/query/address/mainSQLForOne_ERP.sql")
    Resource mainSQLForOne_ERP;

    public List<AddressView> getListForFind(GridDataOption gridDataOption) {
        String findstr = (String) gridDataOption.getFilters().get("findstr");
        // все компоненты адреса должны хранится в индексе в UPPER CASE
        // разбиваем по строкам и определяем сколько строк поиска будет
        String[] findstrParts = findstr.split(" ");
        String[] likestrParts = new String[findstrParts.length];
        for (int i = 0; i < findstrParts.length; i++) {
            likestrParts[i] = "likestr" + i;
            gridDataOption.addFilter(likestrParts[i], findstrParts[i].toUpperCase() + "%");
        }
        return new Query.QueryBuilder<AddressView>(getMainSQL())
                .setMainAlias(ALIAS_MAIN)
                .setPageableAndSort(gridDataOption.buildPageRequest())
                .injectSQL("/*FIND_PLACEHOLDER*/", buildFindString(likestrParts))
                .setPredicate(gridDataOption.buildPredicate(AddressView.class, ALIAS_MAIN, null))
                .setParams(gridDataOption.buildQueryParams())
                .build(AddressView.class)
                .execute();
    }

    private String buildFindString(String[] paramNames) {
        return Stream.of(paramNames)
                .map(parName -> IntStream.range(1, MAX_ADDRESS_PART)
                        .mapToObj(idx -> String.format("ai.ADDRESS_FIELD%d like :%s", idx, parName))
                        .collect(Collectors.joining(" OR ", "(", ")")))
                .collect(Collectors.joining(" AND "));
    }

    public AddressView getOne(Integer id) {
        return new Query.QueryBuilder<AddressView>(getForOneSQL())
                .build(AddressView.class)
                .executeOne("addressId", id);
    }

    private Resource getMainSQL() {
        return Config.CURRENT_EDITION_TAG == EditionTag.GELICON_UTILITIES ? mainSQL_GC
                : mainSQL_ERP;
    }

    private Resource getForOneSQL() {
        return Config.CURRENT_EDITION_TAG == EditionTag.GELICON_UTILITIES ? mainSQLForOne_GC
                : mainSQLForOne_ERP;
    }

    public AddressView getSubjectAddress(Integer subjectId) {
        Address address = addressRepository.getSubjectFactAddress(subjectId);
        if (address == null) {
            address = addressRepository.getSubjectAddress(subjectId);
        }
        return address != null ? getOne(address.getAddressId()) : null;
    }

}
