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
 */
@ArtifactProvider
public class TestUserFuncWithError implements ArtifactService {

    private Map<String, ArtifactDescription> userfunction = new HashMap<>();
    @Override
    public void registerManager(ArtifactManager manager) {
        ArtifactDescription desc = manager.registerArtifact(ArtifactKinds.USER_FUNC.getResourceType(), "UDF_TEST_ERR", "Тестовая функция c ошибкой");
        ((ArtifactDescriptionImpl)desc).setHolder(this);
        desc.forEntity("test");  // для тестовой сущности
        userfunction.put(desc.getCode(),desc);
    }

    @Override
    public Object run(int kind, String code, Map<String, Object> params) {
        throw new RuntimeException("Случилась ошибка");
    }
}
