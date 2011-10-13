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

//TODO article indexing to another index dir.

public class Indexer implements InitializingBean {

    private IndexWriter writer;
    private SimpleJdbcTemplate jdbcTemplate;
    private String directory;

    public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public void afterPropertiesSet() { // method initialize IndexWriter

        try{
            SimpleFSDirectory indexDirectory = new SimpleFSDirectory(new File(directory));
            writer = new IndexWriter(indexDirectory, new StandardAnalyzer(Version.LUCENE_30), IndexWriter.MaxFieldLength.UNLIMITED); //create pre'index


            brandIndex(writer);

            writer.optimize();
            writer.close();
            System.out.println("Index created.");

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Cannot create index");
        }
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
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    private Document createDocument(Brand b) { //create document

        Document doc = new Document();

        doc.add(new Field("brand_id",Long.toString(b.getId()), Field.Store.YES, Field.Index.NOT_ANALYZED));
        doc.add(new Field("name",b.getName(), Field.Store.YES, Field.Index.ANALYZED));  // create name
        doc.add(new Field("description",b.getDescription(),Field.Store.YES, Field.Index.ANALYZED));  //create description
        doc.add(new Field("website",b.getWebsite(),Field.Store.YES, Field.Index.ANALYZED));
        doc.add(new Field("branch",b.getBranch(),Field.Store.YES, Field.Index.ANALYZED));
        return doc;
    }


}

