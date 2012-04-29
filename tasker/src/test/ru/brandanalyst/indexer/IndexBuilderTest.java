package ru.brandanalyst.indexer;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

/**
 * User: daddy-bear
 * Date: 29.04.12
 * Time: 22:57
 */
public class IndexBuilderTest extends AbstractDependencyInjectionSpringContextTests {

    private BuildIndexTask buildIndexTask;

    @Required
    public void setBuildIndexTask(BuildIndexTask buildIndexTask) {
        this.buildIndexTask = buildIndexTask;
    }

    @Override
    protected String[] getConfigLocations() {
        return new String[] {"classpath:indexer-tasker-beans.xml", "classpath:dbhandler_pure.xml"};
    }

    public void testBuildIndex() {
        buildIndexTask.runTask();
    }
}
