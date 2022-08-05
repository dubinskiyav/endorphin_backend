package biz.gelicon.core.artifact;

import biz.gelicon.annotation.ArtifactProvider;
import biz.gelicon.artifacts.ArtifactDescription;
import biz.gelicon.artifacts.ArtifactManager;
import biz.gelicon.artifacts.ArtifactService;
import biz.gelicon.core.artifacts.ArtifactDescriptionImpl;
import biz.gelicon.core.artifacts.ArtifactKinds;
import biz.gelicon.core.artifacts.ArtifactManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * ТЕСТОВЫЙ (Делает вид что нумератор)
 * Сущность: test
 * Параметры:
 *  - year - год
 *  - format (опционно) - формат номера, если не указан то по умолчанию %d-%d
 */
@ArtifactProvider
public class TestNumerator implements ArtifactService {
    // формат по умолчанию
    static final String FORMAT_NUMBER = "%d-%d";
    private Map<String,ArtifactDescription> numerators = new HashMap<>();

    @Override
    public void registerManager(ArtifactManager manager) {
        ArtifactDescription desc = manager.registerArtifact(ArtifactKinds.NUMERATOR.getResourceType(), "TST_01", "Тестовый");
        ((ArtifactDescriptionImpl)desc).setHolder(this);
        desc.forEntity("test");  // для тестовой сущности
        desc.declareProperty("defaultFormat",FORMAT_NUMBER);
        numerators.put(desc.getCode(),desc);
    }

    @Override
    public Object run(int kind, String code, Map<String, Object> params) {
        ArtifactDescription desk = numerators.get(code);
        if(desk==null)
            throw new RuntimeException(String.format("Unknown numerator %s",code));
        if(params==null || params.get("year")==null) {
            throw new RuntimeException("Нумератору требуется параметр \"year\"");
        }
        Integer year = (Integer)params.get("year");
        String sequanceName=String.format("numerator_invoice_%s_%d",desk.getCode(),year).toLowerCase();

        String format = (String) params.get("format");
        if(format==null)
            format = FORMAT_NUMBER;

        return String.format(format,year,1);
    }

}
