package ru.brandanalyst.indexer;

import org.springframework.beans.factory.InitializingBean;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.brandanalyst.core.model.Brand;
import ru.brandanalyst.core.db.provider.BrandProvider;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Nikolaj Karpov
 * Date: 10.10.11
 * Time: 22:55
 */
<<<<<<< HEAD
public class Indexer {

    public IndexSearcher indexSearcher;
    IndexWriter writer;
    RAMDirectory indexDirectory;
    SimpleJdbcTemplate jdbcTemplate;
    public BrandProvider brandProvider;

    public Indexer(String dbName){
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");    //initialize database
        dataSource.setUrl("jdbc:mysql://localhost:3306/brandanalyticsdb?useUnicode=true&amp;characterEncoding=utf8");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        dataSource.setValidationQuery("select 1");
        jdbcTemplate = new SimpleJdbcTemplate(dataSource);
=======
public class Indexer implements InitializingBean {

    private IndexWriter writer;
    private SimpleJdbcTemplate jdbcTemplate;
    private String directory;

    public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
>>>>>>> ce6d3f20ac68c3130c8ea2d1bed3c750cb814332
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public void afterPropertiesSet() throws IOException, ParseException { // method initialize IndexWriter

        try{
            SimpleFSDirectory indexDirectory = new SimpleFSDirectory(new File(directory));
            writer = new IndexWriter(indexDirectory, new StandardAnalyzer(Version.LUCENE_30), IndexWriter.MaxFieldLength.UNLIMITED); //create pre'index
        } catch (Exception e) {
            e.printStackTrace();
        }

<<<<<<< HEAD
        brandProvider = new BrandProvider(jdbcTemplate);

        List<Brand> brandList = brandProvider.getAllBrands();
        for(Brand brand:brandList){ //add to pre'index all brand's
            Document doc = createDocument(new Integer(brand.getId()),brand.getName(),brand.getDescription());
            writer.addDocument(doc);
=======
        brandIndex(writer);

        writer.optimize();
        writer.close();
        System.out.println("finished.");
    }

    private void brandIndex(IndexWriter writer) {

        System.out.println("indexing brands");
        BrandProvider brandProvider = new BrandProvider(jdbcTemplate);

        List<Brand> brandList = brandProvider.getAllBrands();

        try{
            for(Brand brand:brandList){ //add to pre'index all brand's
                Document doc = createDocument(brand);
                writer.addDocument(doc);
            }
        } catch(Exception e) {
            e.printStackTrace();
>>>>>>> ce6d3f20ac68c3130c8ea2d1bed3c750cb814332
        }
    }
    private Document createDocument(Brand b) { //create document

<<<<<<< HEAD
    private Document createDocument(Integer id,String title,String content) { //create document with two fields name and description
        Document doc = new Document();
        doc.add(new Field("id",id.toString(), Field.Store.YES, Field.Index.NO));
        doc.add(new Field("name",title, Field.Store.YES, Field.Index.NOT_ANALYZED));  // create name
        doc.add(new Field("description",content,Field.Store.YES, Field.Index.ANALYZED));  //create description
=======
        Document doc = new Document();

        doc.add(new Field("id",Long.toString(b.getId()), Field.Store.YES, Field.Index.NOT_ANALYZED));
        doc.add(new Field("name",b.getName(), Field.Store.YES, Field.Index.ANALYZED));  // create name
        doc.add(new Field("description",b.getDescription(),Field.Store.YES, Field.Index.ANALYZED));  //create description
        doc.add(new Field("website",b.getWebsite(),Field.Store.YES, Field.Index.ANALYZED));
        doc.add(new Field("branch",b.getBranch(),Field.Store.YES, Field.Index.ANALYZED));
>>>>>>> ce6d3f20ac68c3130c8ea2d1bed3c750cb814332
        return doc;
    }


}

