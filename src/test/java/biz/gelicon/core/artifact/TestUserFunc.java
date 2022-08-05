package biz.gelicon.core.artifact;

import biz.gelicon.annotation.ArtifactProvider;
import biz.gelicon.artifacts.ArtifactDescription;
import biz.gelicon.artifacts.ArtifactManager;
import biz.gelicon.artifacts.ArtifactService;
import biz.gelicon.core.artifacts.ArtifactDescriptionImpl;
import biz.gelicon.core.artifacts.ArtifactKinds;

import java.util.HashMap;
import java.util.Map;

/**
 * ТЕСТОВАЯ (Делает вид что функция пользователя)
 * Сущность: test
 * Параметры:
 *  - s1 - число
 *  - s2 - число
 */
@ArtifactProvider
public class TestUserFunc implements ArtifactService {

    private Map<String, ArtifactDescription> userfunction = new HashMap<>();
    @Override
    public void registerManager(ArtifactManager manager) {
        ArtifactDescription desc = manager.registerArtifact(ArtifactKinds.USER_FUNC.getResourceType(), "UDF_SUM", "Тестовая функция");
        ((ArtifactDescriptionImpl)desc).setHolder(this);
        desc.forEntity("test");  // для тестовой сущности
        userfunction.put(desc.getCode(),desc);
    }

    @Override
    public Object run(int kind, String code, Map<String, Object> params) {
        // этот колд необязателен
        ArtifactDescription desk = userfunction.get(code);
        if(desk==null)
            throw new RuntimeException(String.format("Unknown user function %s",code));

        if(params==null || params.get("s1")==null|| params.get("s1")==null) {
            throw new RuntimeException("Требуется параметры \"s1\" и \"s2\" ");
        }
        return ((Integer)params.get("s1")).intValue()+((Integer)params.get("s2")).intValue();
    }
}
