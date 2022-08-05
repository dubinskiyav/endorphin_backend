package biz.gelicon.core.components.core.attribute;

import biz.gelicon.core.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class AttributeService extends BaseService<Attribute> {
    @Autowired
    private AttributeRepository attributeRepository;
    @Autowired
    private AttributeValidator attributeValidator;


    @PostConstruct
    public void init() {
        init(attributeRepository, attributeValidator);
    }

    public AttributeView getAttribute(Integer id) {
        List<AttributeView> list = attributeRepository.findQuery(AttributeView.class, "" +
                "SELECT c.*," +
                "cc.capclass_name, " +
                "cct.capclasstype_name, " +
                "ccd.capcode_name, " +
                "s.subject_name " +
                "FROM attribute c " +
                "INNER JOIN capclass cc ON cc.capclass_id=c.attributegroup_id " +
                "INNER JOIN capcode ccd ON ccd.capcode_id=c.capcode_id " +
                "LEFT OUTER JOIN capclasstype cct ON cct.capclasstype_id=c.capclasstype_id " +
                "LEFT OUTER JOIN subject s ON s.subject_id=c.subject_id " +
                "WHERE c.attribute_id=:attributeId", "attributeId", id);
        return list.isEmpty()?null:list.get(0);
    }


}
