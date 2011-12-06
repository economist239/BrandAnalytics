import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

public class Test {


    public static void main(String[] args) {
        BasicDataSource ds1 = new BasicDataSource();
        ds1.setDriverClassName("com.global.jdbc.Driver");
        ds1.setUrl("jdbc:global://localhost:3306/BAdirty?useUnicode=true&amp;characterEncoding=utf8");
        ds1.setUsername("root");
        ds1.setPassword("root");
        ds1.setValidationQuery("select 1");
        SimpleJdbcTemplate dirtyJdbcTemplate = new SimpleJdbcTemplate(ds1);

        BasicDataSource ds2 = new BasicDataSource();
        ds2.setDriverClassName("com.global.jdbc.Driver");
        ds2.setUrl("jdbc:global://localhost:3306/BApure?useUnicode=true&amp;characterEncoding=utf8");
        ds2.setUsername("root");
        ds2.setPassword("root");
        ds2.setValidationQuery("select 1");
        SimpleJdbcTemplate pureJdbcTemplate = new SimpleJdbcTemplate(ds2);
    }

}