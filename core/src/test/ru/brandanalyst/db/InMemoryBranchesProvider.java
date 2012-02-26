package ru.brandanalyst.db;

import ru.brandanalyst.core.db.provider.interfaces.BranchesProvider;
import ru.brandanalyst.core.model.Branch;

import java.util.ArrayList;
import java.util.List;

/**
 * User: dima
 * Date: 2/26/12
 * Time: 6:37 PM
 */
public class InMemoryBranchesProvider implements BranchesProvider {
    @Override
    public List<Branch> getAllBranches() {
        return new ArrayList<Branch>();
    }
}
