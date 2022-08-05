package biz.gelicon.core.components.core.town;

import biz.gelicon.core.config.Config;
import biz.gelicon.core.response.DataResponse;
import biz.gelicon.core.utils.GridDataOption;
import biz.gelicon.core.utils.QueryUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import biz.gelicon.core.annotations.CheckPermission;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@Tag(name = "Контроллер Населенный пункт (town)", description = "Контроллер для объектов \"Населенный пункт\" ")
@RequestMapping(value = "/v"+ Config.CURRENT_VERSION+"/apps/refbooks/town",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@Transactional
public class TownController {
    private static final Logger logger = LoggerFactory.getLogger(TownController.class);

    @Schema(description = "Параметры выборки данных в таблицу")
    public static class GridDataOptionTown extends GridDataOption {
        @Schema(description = "Фильтры для Населенный пункт:" +
                "<ul>" +
                "</ul>")
        @Override
        public Map<String, Object> getFilters() {
            return super.getFilters();
        }

    }

    @Autowired
    private TownService townService;

    @Operation(summary = "Список объектов \"Населенный пункт\"",
            description = "Возвращает постраничный список объектов \"Населенный пункт\"")
    @CheckPermission
    @RequestMapping(value = "town/getlist", method = RequestMethod.POST)
    public DataResponse<TownView> getlist(@RequestBody GridDataOptionTown gridDataOption) {

        List<TownView> result = townService.getMainList(gridDataOption);

        int total = 0;
        if(gridDataOption.getPagination().getPageSize()>0) {
            total = townService.getMainCount(gridDataOption);
        }
        return townService.buildResponse(result,gridDataOption,total);
    }

    @Operation(summary = "Получение списка объектов \"Населенный пункт\" по поисковой сроке",
            description = "Возвращает список объектов \"Населенный пункт\" в наименовании, коде или описании которых встречается поисковая строка")
    @CheckPermission
    @RequestMapping(value = "town/find", method = RequestMethod.POST)
    public List<TownView> find(@RequestBody SimpleSearchOption options) {
        //Защита от коротких поисковых строк
        if(options.getSearch()!=null && options.getSearch().length()<4) {
            return new ArrayList<>();
        }
        GridDataOption qopt = new GridDataOption.Builder()
                .search(options.getSearch())
                .addSort("townName", Sort.Direction.ASC)
                .pagination(1, QueryUtils.UNLIMIT_PAGE_SIZE)
                .build();
        return townService.getListForFind(qopt);
    }

    @Schema(description = "Параметры поиска")
    public static class SimpleSearchOption {
        @Schema(description = "Строка поиска")
        private String search;

        SimpleSearchOption(){}

        public String getSearch() {
            return search;
        }

        public void setSearch(String search) {
            this.search = search;
        }
    }

}
