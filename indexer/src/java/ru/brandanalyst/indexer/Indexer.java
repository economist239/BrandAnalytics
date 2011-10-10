package ru.brandanalyst.indexer;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.brandanalyst.core.model.Brand;
import ru.brandanalyst.storage.provider.BrandProvider;

import java.io.IOException;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Nikolaj Karpov
 * Date: 10.10.11
 * Time: 22:55
 */
public class Indexer {

    IndexWriter writer;
    RAMDirectory indexDirectory;
    IndexSearcher indexSearcher;
    SimpleJdbcTemplate jdbcTemplate;

    public Indexer(String dbName){
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");    //initialize database
        dataSource.setUrl("jdbc:mysql://localhost:3306/brandanalyticsdb?useUnicode=true&amp;characterEncoding=utf8");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        dataSource.setValidationQuery("select 1");
        jdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }

    public void index() throws IOException, ParseException { // method initialize IndexWriter

        indexDirectory = new RAMDirectory();
        Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_30); // used standart analyzer (your can change)
        writer = new IndexWriter(indexDirectory,analyzer, IndexWriter.MaxFieldLength.UNLIMITED); //create pre'index

        BrandProvider brandProvider = new BrandProvider(jdbcTemplate);

        List<Brand> brandList = brandProvider.getAllBrands();
        for(Brand brand:brandList){ //add to pre'index all brand's
            Document doc = createDocument(brand.getName(),brand.getDescription());
            writer.addDocument(doc);
        }
        writer.optimize();
        writer.close();
        indexSearcher = new IndexSearcher(indexDirectory,true); // create index :)
    }

    private Document createDocument(String title,String content) { //create document with two fields name and description
        Document doc = new Document();
        doc.add(new Field("id",title, Field.Store.YES, Field.Index.NO));
        doc.add(new Field("name",title, Field.Store.YES, Field.Index.NOT_ANALYZED));  // create name
        doc.add(new Field("description",content,Field.Store.YES, Field.Index.ANALYZED));  //create description
        return doc;
    }
}

