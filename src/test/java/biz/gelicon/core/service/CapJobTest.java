package biz.gelicon.core.service;

import biz.gelicon.core.artifact.TestUserFunc;
import biz.gelicon.core.artifact.TestUserFuncOk;
import biz.gelicon.core.artifact.TestUserFuncWithError;
import biz.gelicon.core.artifacts.ArtifactManagerImpl;
import biz.gelicon.core.controllers.IntergatedTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import biz.gelicon.core.tasks.JobsTask;
import org.springframework.beans.factory.annotation.Autowired;

public class CapJobTest extends IntergatedTest {

    @Autowired
    JobsTask jobsTask;
    @Autowired
    ArtifactManagerImpl artifactManager;

    @BeforeEach
    public void before() {
        new TestUserFuncOk().registerManager(artifactManager);
        new TestUserFuncWithError().registerManager(artifactManager);
    }

    @Test
    public void testRunJob() {
        jobsTask.process();
    }
}
