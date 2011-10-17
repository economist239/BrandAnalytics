import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.brandanalyst.core.db.provider.GraphProvider;

/**
 * Created by IntelliJ IDEA.
 * User: dima
 * Date: 10/17/11
 * Time: 7:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestGraphsFromDB {

    public static void Main(String[] args) {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost:3306/BAdirty?useUnicode=true&amp;characterEncoding=utf8");
        ds.setUsername("root");
        ds.setPassword("root");
        ds.setValidationQuery("select 1");
        SimpleJdbcTemplate jdbcTemplate = new SimpleJdbcTemplate(ds);
        GraphProvider dataStore = new GraphProvider(jdbcTemplate);
    }
}
