package ru.brandanalyst;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

/**
 * @author Vanslov Evgeny (evans@yandex-team.ru)
 */
public class AbstractTest extends AbstractDependencyInjectionSpringContextTests {
    private String[] configLocations;

    public AbstractTest(final String... configLocations) {
        this.configLocations = configLocations.clone();
        setPopulateProtectedVariables(true);
    }

    @Override
    protected String[] getConfigLocations() {
        return configLocations;
    }
}
