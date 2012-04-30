package ru.brandanalyst.db;

import ru.brandanalyst.core.db.provider.interfaces.BranchesProvider;
import ru.brandanalyst.core.model.Branch;
import ru.brandanalyst.core.util.Cf;

import java.util.List;

/**
 * User: dima
 * Date: 2/26/12
 * Time: 6:37 PM
 */
public class InMemoryBranchesProvider implements BranchesProvider {
    @Override
    public List<Branch> getAllBranches() {
        return Cf.newArrayList(new Branch(1, "1"), new Branch(2, "2"));
    }
}
