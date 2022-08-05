package biz.gelicon.core.components.core.address;

import biz.gelicon.core.annotations.CheckPermission;
import biz.gelicon.core.config.Config;
import biz.gelicon.core.utils.GridDataOption;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@Tag(name = "Контроллер Адрес", description = "Контроллер для объектов \"Адрес\" ")
@RequestMapping(value = "/v"+ Config.CURRENT_VERSION+"/apps/refbooks/address",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@Transactional
public class AddressController {
    private static final Logger logger = LoggerFactory.getLogger(AddressController.class);
    // максимальное кол-во записе при аоиске. влияет на производительность
    private static final int MAX_ROW_COUNT = 20;

    @Schema(description = "Параметры выборки данных в таблицу")
    public static class GridDataOptionAddress extends GridDataOption {
        @Schema(description = "Фильтры для Адрес:" +
                "<ul>" +
                "</ul>")
        @Override
        public Map<String, Object> getFilters() {
            return super.getFilters();
        }

    }

    @Autowired
    private AddressService addressService;

    @Operation(summary = "Получение списка адресов по поисковой сроке",
            description = "Возвращает список адресов в наименовании, коде или описании которых встречается поисковая строка")
    @CheckPermission
    @RequestMapping(value = "address/find", method = RequestMethod.POST)
    public List<AddressView> find(@RequestBody SimpleSearchOption options) {

        GridDataOption qopt = new GridDataOption.Builder()
                .addFilter("findstr",options.getSearch())
                .pagination(1, MAX_ROW_COUNT)
                .build();
        return addressService.getListForFind(qopt);
    }

    public static class SimpleSearchOption {
        private String search;

        public String getSearch() {
            return search;
        }

        public void setSearch(String search) {
            this.search = search;
        }
    }

}
