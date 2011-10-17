package ru.brandanalyst.core.db.provider;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.brandanalyst.core.model.Graph;
import sun.plugin.dom.core.Document;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: dima
 * Date: 10/16/11
 * Time: 7:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class GraphProvider {
    private SimpleJdbcTemplate jdbcTemplate; //

    public GraphProvider(SimpleJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void cleanDataStore() {
        jdbcTemplate.update("TRUNCATE TABLE Graphs");
    }

    public List<Graph> getGraphsByBrandId(long brandId) {
        return new ArrayList<Graph>();
    }
}
