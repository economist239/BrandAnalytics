import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
//import org.springframework.util.Assert;
import ru.brandanalyst.core.db.provider.GraphProvider;
import ru.brandanalyst.core.model.Graph;
import ru.brandanalyst.core.model.SingleDot;
import junit.framework.Assert;
import java.sql.Timestamp;
import java.util.List;
/**
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 10/17/11
 * Time: 7:25 PM.
 */
public class TestGraphsFromDB {

    public static void main(String[] args) {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost:3306/BAdirty?useUnicode=true&amp;characterEncoding=utf8");
        ds.setUsername("root");
        ds.setPassword("root");
        ds.setValidationQuery("select 1");
        SimpleJdbcTemplate jdbcTemplate = new SimpleJdbcTemplate(ds);
        GraphProvider dataStore = new GraphProvider(jdbcTemplate);

        Timestamp t = new Timestamp(90, 02, 29, 1, 1, 1, 1);
        double val = 21;
        dataStore.writeSingleDot(t, val, 1, 1);

        List<Graph> list = dataStore.getGraphsByBrandId(1);
        for(Graph g: list) {
            for(SingleDot d: g.getGraph()) {
                System.out.println(d.getDate());
            }
            System.out.println("конец графика");

        }
    }
}
