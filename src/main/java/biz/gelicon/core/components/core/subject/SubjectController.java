package biz.gelicon.core.components.core.subject;

import biz.gelicon.core.annotations.CheckPermission;
import biz.gelicon.core.config.Config;
import biz.gelicon.core.response.DataResponse;
import biz.gelicon.core.service.BaseService;
import biz.gelicon.core.utils.ConstantForControllers;
import biz.gelicon.core.utils.GridDataOption;
import biz.gelicon.core.utils.QueryUtils;
import biz.gelicon.core.view.TreeViewItem;
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
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@Tag(name = "Объекты аналитического учета", description = "Контроллер для справочника объектов аналитического учета")
@RequestMapping(value = "/v"+ Config.CURRENT_VERSION+"/apps/refbooks/subject",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@Transactional
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @Schema(description = "Параметры выборки данных в таблицу")
    public static class GridDataOptionSubject extends GridDataOption {
        @Schema(description = "Фильтры для ОАУ:" +
                "<ul>" +
                "<li>parentId - показывать объекты из данного уровня" +
                "</ul>")
        @Override
        public Map<String, Object> getFilters() {
            return super.getFilters();
        }

    }


    @Operation(summary = ConstantForControllers.GETLIST_OPERATION_SUMMARY,
            description = ConstantForControllers.GETLIST_OPERATION_DESCRIPTION)
    @CheckPermission
    @RequestMapping(value = "subject/getlist", method = RequestMethod.POST)
    public DataResponse<SubjectView> getlist(@RequestBody GridDataOptionSubject gridDataOption) {
        gridDataOption.setProcessNamedFilter(filters ->
                    filters.stream()
                    .map(f->{
                        switch (f.getName()) {
                            case "parentId":
                                Integer parentId = (Integer) f.getValue();
                                return SubjectService.ALIAS_MAIN+".parent_id="+parentId;
                            default:
                                return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.joining(" and "))
        );

        List<SubjectView> result = subjectService.getMainList(gridDataOption,new Date());

        int total = 0;
        if(gridDataOption.getPagination().getPageSize()>0) {
            total = subjectService.getMainCount(gridDataOption);
        }
        return BaseService.buildResponse(result,gridDataOption,total);
    }


    @Operation(summary = "Получение спиока уровней объектов аналитического учета",
            description = "Возвращает список уровней объектов аналитического учета в виде дерева")
    @CheckPermission
    @RequestMapping(value = "subject/gettree", method = RequestMethod.POST)
    public List<TreeViewSubjectItem> gettree(@RequestBody TreeOptions options,Principal principal) {
        List<SubjectView> clusters = subjectService.getAllClusters(options.getShowDate());
        TreeViewSubjectItem root = TreeViewSubjectItem.root();
        clusters.stream()
                .forEach(s->{
                    // не будем добавлять корневой уровень, так как он уже добавлен
                    if(s.getSubjectId()== Subject.SUBJECT_ROOT_ID) {
                        return;
                    }
                    TreeViewItem tv = new TreeViewSubjectItem(s.getSubjectId(), s.getSubjectName(),s.getParentId());
                    TreeViewItem parent = findClusterById(root,s.getParentId());
                    // связность дерева может нарушиться из-за условий на даты открытия/закрытия
                    if(parent!=null) {
                        parent.getChildren().add(tv);
                    }
                });
        return Collections.singletonList(root);
    }

    @Operation(summary = "Получение списка объектов аналитического учета по поисковой сроке",
            description = "Возвращает список объектов аналитического учета в наименовании, коде или описании которых встречается поисковая строка")
    @CheckPermission
    @RequestMapping(value = "subject/find", method = RequestMethod.POST)
    public List<SubjectView> find(@RequestBody SimpleSearchOption options) {
        //Защита от коротких поисковых строк
        if(options.getSearch()!=null && options.getSearch().length()<4) {
            return new ArrayList<>();
        }
        GridDataOption qopt = new GridDataOption.Builder()
                .search(options.getSearch())
                .addSort("subjectName", Sort.Direction.ASC)
                .pagination(1, QueryUtils.UNLIMIT_PAGE_SIZE)
                .build();
        return subjectService.getListForFind(qopt);
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
