import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.brandanalyst.core.db.provider.mysql.MySQLGraphProvider;
import ru.brandanalyst.core.model.Graph;
import ru.brandanalyst.core.model.SingleDot;

import java.sql.Timestamp;
import java.util.List;

//import org.springframework.util.Assert;

/**
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 10/17/11
 * Time: 7:25 PM.
 */
public class TestGraphsFromDB {

    public static void main(String[] args) {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("com.interfaces.jdbc.Driver");
        ds.setUrl("jdbc:interfaces://localhost:3306/BAdirty?useUnicode=true&amp;characterEncoding=utf8");
        ds.setUsername("root");
        ds.setPassword("root");
        ds.setValidationQuery("select 1");
        SimpleJdbcTemplate jdbcTemplate = new SimpleJdbcTemplate(ds);
        MySQLGraphProvider dataStore = new MySQLGraphProvider();
        dataStore.setJdbcTemplate(jdbcTemplate);

        Timestamp t = new Timestamp(90, 02, 29, 1, 1, 1, 1);
        double val = 21;
        dataStore.writeSingleDot(t, val, 1, 1);

        t = new Timestamp(91, 02, 29, 1, 1, 1, 1);
        val = 21;
        dataStore.writeSingleDot(t, val, 1, 1);

        t = new Timestamp(92, 02, 29, 1, 1, 1, 1);
        val = 22;
        dataStore.writeSingleDot(t, val, 1, 1);

        t = new Timestamp(93, 02, 29, 1, 1, 1, 1);
        val = 23;
        dataStore.writeSingleDot(t, val, 1, 1);

        t = new Timestamp(94, 02, 29, 1, 1, 1, 1);
        val = 24;
        dataStore.writeSingleDot(t, val, 1, 1);

        t = new Timestamp(95, 02, 29, 1, 1, 1, 1);
        val = 25;
        dataStore.writeSingleDot(t, val, 1, 1);

        List<Graph> list = dataStore.getGraphsByBrandId(1);
        for (Graph g : list) {
            for (SingleDot d : g.getGraph()) {
                System.out.println(d.getDate());
            }
            System.out.println("конец графика");

        }
    }
}
