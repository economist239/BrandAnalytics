package ru.brandanalyst.frontend.yalets;

import net.sf.xfresh.core.Yalet;
import org.springframework.beans.factory.annotation.Required;
import ru.brandanalyst.frontend.model.*;

public abstract class AbstractYalet implements Yalet {

    protected GeneralManager manager;

    @Required
    public void setManager(GeneralManager manager) {
        this.manager = manager;
    }
}
