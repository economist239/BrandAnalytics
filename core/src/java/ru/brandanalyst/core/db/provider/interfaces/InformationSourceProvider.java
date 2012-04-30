package ru.brandanalyst.core.db.provider.interfaces;

import ru.brandanalyst.core.model.InfoSource;
import ru.brandanalyst.core.model.InformationSourceType;

import java.util.List;

public interface InformationSourceProvider {
    public InfoSource getInfoSourceById(long id);

    public List<InfoSource> getAllInfoSources();

    public List<InfoSource> getAllInfoSources(InformationSourceType type);
}
