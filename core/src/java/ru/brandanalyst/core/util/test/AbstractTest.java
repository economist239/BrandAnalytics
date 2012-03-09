package ru.brandanalyst.core.util.test;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

/**
 * @author Vanslov Evgeny (evans@yandex-team.ru)
 */
public class AbstractTest extends AbstractDependencyInjectionSpringContextTests {
    protected String[] configLocations = new String[]{};

    public AbstractTest() {
        setPopulateProtectedVariables(true);
    }

    @Override
    protected String[] getConfigLocations() {
        return configLocations;
    }
}
