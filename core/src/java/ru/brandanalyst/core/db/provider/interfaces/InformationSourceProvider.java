package ru.brandanalyst.core.db.provider.interfaces;

import ru.brandanalyst.core.model.InfoSource;

import java.util.List;

public interface InformationSourceProvider {
    public InfoSource getInfoSourceById(long id);

    public List<InfoSource> getAllInfoSources();
}
