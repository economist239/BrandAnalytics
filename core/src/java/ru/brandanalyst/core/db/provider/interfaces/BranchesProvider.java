package ru.brandanalyst.core.db.provider.interfaces;

import ru.brandanalyst.core.model.Branch;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Obus
 * Date: 18.02.12
 * Time: 9:55
 * To change this template use File | Settings | File Templates.
 */
public interface BranchesProvider {
    public List<Branch> getAllBranches();
}
