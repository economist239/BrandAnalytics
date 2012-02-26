package ru.brandanalyst.db;

import ru.brandanalyst.core.db.provider.interfaces.InformationSourceProvider;
import ru.brandanalyst.core.model.InfoSource;

import java.util.ArrayList;
import java.util.List;

/**
 * User: dima
 * Date: 2/26/12
 * Time: 6:15 PM
 */
public class InMemoryInfoSourceProvider implements InformationSourceProvider {
    private List<InfoSource> depot = new ArrayList<InfoSource>(2);

    {
        depot.add(new InfoSource(1, 1, "1", "1", "1", "1"));
        depot.add(new InfoSource(2, 2, "2", "2", "2", "2"));
    }

    @Override
    public InfoSource getInfoSourceById(long id) {
        return depot.get((int) id);
    }

    @Override
    public List<InfoSource> getAllInfoSources() {
        return new ArrayList<InfoSource>(depot);
    }
}
