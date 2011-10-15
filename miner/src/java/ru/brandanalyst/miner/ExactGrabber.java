package ru.brandanalyst.miner;
//

//* To change this template, choose Tools | Templates

//* and open the template in the editor.

//

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import java.util.List;

/**
 *
 * @author Александр
 */
public abstract class ExactGrabber {
    protected SimpleJdbcTemplate jdbcTemplate;
    protected String config;
    protected List<String> brandNames;
    public abstract void grab();
    public abstract void setConfig(String config);
    public abstract void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate);
    public abstract void setBrandNames(List<String> brandNames);
}
