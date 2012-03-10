package ru.brandanalyst;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.junit.*;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;
import ru.brandanalyst.core.db.provider.interfaces.ArticleProvider;
import ru.brandanalyst.core.db.provider.interfaces.BrandProvider;
import ru.brandanalyst.core.db.provider.interfaces.GraphProvider;
import ru.brandanalyst.core.model.Brand;
import ru.brandanalyst.core.model.Graph;
import ru.brandanalyst.core.model.Params;
import ru.brandanalyst.core.model.SingleDot;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: dima
 * Date: 1/21/12
 * Time: 7:01 PM
 * To change this template use File | Params | File Templates.
 */
public class DbTest extends AbstractDependencyInjectionSpringContextTests {

    private ArticleProvider pureArticleProvider;
    private BrandProvider dirtyBrandProvider;
    private GraphProvider dirtyGraphProvider;

    public void setPureArticleProvider(ArticleProvider pureArticleProvider) {
        this.pureArticleProvider = pureArticleProvider;
    }

    public void setPureGraphProvider(GraphProvider dirtyGraphProvider) {
        this.dirtyGraphProvider = dirtyGraphProvider;
    }

    public void setPureBrandProvider(BrandProvider dirtyBrandProvider) {
        this.dirtyBrandProvider = dirtyBrandProvider;
    }

    public DbTest() {
        super();
        setAutowireMode(AUTOWIRE_BY_NAME);
    }

    @Override
    protected String[] getConfigLocations() {
        return new String[]{
                "classpath:dbhandler_pure.xml"
        };
    }

    public void testBrand() {
        List<Brand> l = new LinkedList<Brand>();
        l.add(new Brand(-1, "1", "2", "2", 1, Params.empty()));
        l.add(new Brand(-1, "2", "1", "1", 1, Params.empty()));
        dirtyBrandProvider.writeListOfBrandsToDataStore(l);
        Assert.assertTrue(dirtyBrandProvider.getAllBrands().size() > 0);
    }

    public void testGraph() {
        Graph graph = new Graph(Arrays.asList(
                new SingleDot(new LocalDate(new Date().getTime()), 4),
                new SingleDot(new LocalDate(new Date().getTime() + 10), 5),
                new SingleDot(new LocalDate(new Date().getTime() + 20), 67)));
        dirtyGraphProvider.writeGraph(graph, 1, 1);
    }
}
