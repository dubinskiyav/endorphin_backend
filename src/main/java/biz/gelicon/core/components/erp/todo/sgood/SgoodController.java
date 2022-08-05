package biz.gelicon.core.components.erp.todo.sgood;

import biz.gelicon.core.annotations.CheckPermission;
import biz.gelicon.core.config.Config;
import biz.gelicon.core.response.DataResponse;
import biz.gelicon.core.service.BaseService;
import biz.gelicon.core.utils.ConstantForControllers;
import biz.gelicon.core.utils.GridDataOption;
import biz.gelicon.core.utils.ProcessNamedFilter;
import biz.gelicon.core.utils.QueryUtils;
import biz.gelicon.core.view.TreeViewItem;
import biz.gelicon.core.view.TreeViewSgoodItem;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

//@RestController
@Tag(name = "Справочник ТМЦ", description = "Контроллер для справочника товарно-материальных ценностей")
@RequestMapping(value = "/v"+ Config.CURRENT_VERSION+"/apps/refbooks/sgood",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@Transactional
public class SgoodController {

    @Autowired
    private SgoodService sgoodService;

    @Schema(description = "Параметры выборки данных в таблицу")
    public static class GridDataOptionSgood extends GridDataOption {
        @Schema(description = "Фильтры для ТМЦ:" +
                "<ul>" +
                "<li>parentId - показывать объекты из данного уровня" +
                "<li> attributes - массив идентификаторов признаков, ктороые следует отображать " +
                "</ul>")
        @Override
        public Map<String, Object> getFilters() {
            return super.getFilters();
        }

    }

    @Operation(summary = ConstantForControllers.GETLIST_OPERATION_SUMMARY,
            description = ConstantForControllers.GETLIST_OPERATION_DESCRIPTION)
    @CheckPermission
    @RequestMapping(value = "sgood/getlist", method = RequestMethod.POST)
    public DataResponse<SgoodView> getlist(@RequestBody SgoodController.GridDataOptionSgood gridDataOption) {
        ProcessNamedFilter fprocessor = filters ->
                filters.stream()
                        .map(f->{
                            switch (f.getName()) {
                                case "parentId":
                                    Integer parentId = (Integer) f.getValue();
                                    return SgoodService.ALIAS_MAIN+".parent_id="+parentId;
                                default:
                                    return null;
                            }
                        })
                        .filter(Objects::nonNull)
                        .collect(Collectors.joining(" and "));

        List<SgoodView> result = sgoodService.getMainList(gridDataOption,new Date(),fprocessor);

        int total = 0;
        if(gridDataOption.getPagination().getPageSize()>0) {
            total = sgoodService.getMainCount(gridDataOption,fprocessor);
        }
        return BaseService.buildResponse(result,gridDataOption,total);
    }


    @Operation(summary = "Получение спиока уровней ТМЦ",
            description = "Возвращает список уровней ТМЦ в виде дерева")
    @CheckPermission
    @RequestMapping(value = "sgood/gettree", method = RequestMethod.POST)
    public List<TreeViewSgoodItem> gettree(@RequestBody SgoodController.TreeOptions options, Principal principal) {
        List<SgoodView> clusters = sgoodService.getAllClusters(options.getShowDate());
        TreeViewSgoodItem root = TreeViewSgoodItem.root();
        clusters.stream()
                .forEach(s->{
                    // не будем добавлять корневой уровень, так как он уже добавлен
                    if(s.getSgoodId()== Sgood.SGOOD_ROOT_ID) {
                        return;
                    }
                    TreeViewItem tv = new TreeViewSgoodItem(s.getSgoodId(), s.getSgoodName(),s.getParentId());
                    TreeViewItem parent = findClusterById(root,s.getParentId());
                    // связность дерева может нарушиться из-за условий на даты открытия/закрытия
                    if(parent!=null) {
                        parent.getChildren().add(tv);
                    }
                });
        return Collections.singletonList(root);
    }

    @Operation(summary = "Получение списка объектов ТМЦ по поисковой сроке",
            description = "Возвращает список объектов ТМЦ в наименовании, коде или описании которых встречается поисковая строка")
    @CheckPermission
    @RequestMapping(value = "sgood/find", method = RequestMethod.POST)
    public List<SgoodView> find(@RequestBody SgoodController.SimpleSearchOption options) {
        //Защита от коротких поисковых строк
        if(options.getSearch()!=null && options.getSearch().length()<4) {
            return new ArrayList<>();
        }
        GridDataOption qopt = new GridDataOption.Builder()
                .search(options.getSearch())
                .addSort("sgoodName", Sort.Direction.ASC)
                .pagination(1, QueryUtils.UNLIMIT_PAGE_SIZE)
                .build();
        return sgoodService.getListForFind(qopt);
    }


    private TreeViewItem findClusterById(TreeViewItem root, Integer clusterId) {
        if(root.getValue().equals(clusterId)) {
            return root;
        }
        for (TreeViewItem child:root.getChildren()) {
            TreeViewItem result = findClusterById(child, clusterId);
            if(result!=null) {
                return result;
            }
        }
        return null;
    }

    @Schema(description = "Параметры поиска")
    public static class SimpleSearchOption {
        @Schema(description = "Строка поиска")
        private String search;

        public String getSearch() {
            return search;
        }

        public void setSearch(String search) {
            this.search = search;
        }
    }

    public static class TreeOptions {
        private Date showDate;

        public Date getShowDate() {
            return showDate;
        }

        public void setShowDate(Date showDate) {
            this.showDate = showDate;
        }
    }


}
