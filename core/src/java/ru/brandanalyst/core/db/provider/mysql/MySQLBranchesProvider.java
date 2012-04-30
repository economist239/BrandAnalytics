package ru.brandanalyst.core.db.provider.mysql;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.brandanalyst.core.db.provider.interfaces.BranchesProvider;
import ru.brandanalyst.core.model.Branch;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Obus
 * Date: 18.02.12
 * Time: 9:59
 */


//TODO DO THIS IN ENUMERATION
public class MySQLBranchesProvider implements BranchesProvider {
    private SimpleJdbcTemplate jdbcTemplate;

    @Override
    public List<Branch> getAllBranches() {
        return jdbcTemplate.query("SELECT * FROM Branch", MappersHolder.BRANCH_MAPPER);
    }

    @Required
    public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
