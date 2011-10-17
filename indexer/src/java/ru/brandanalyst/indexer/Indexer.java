package ru.brandanalyst.indexer;

import org.apache.lucene.analysis.ru.RussianAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.brandanalyst.core.db.provider.ArticleProvider;
import ru.brandanalyst.core.db.provider.BrandProvider;
import ru.brandanalyst.core.model.Article;
import ru.brandanalyst.core.model.Brand;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Nikolaj Karpov
 * Date: 10.10.11
 * Time: 22:55
 */
public class Indexer implements InitializingBean {

    private IndexWriter brandwriter;
    private IndexWriter articlewriter;
    private SimpleJdbcTemplate jdbcTemplate;
    private String directoryBrand;
    private String directoryArticle;

    public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate   = jdbcTemplate;
    }

    public void setDirectoryBrand(String directoryBrand) {
        this.directoryBrand   = directoryBrand;
    }

    public void setDirectoryArticle(String directoryArticle) {
        this.directoryArticle  = directoryArticle;
    }

    public void afterPropertiesSet() { // method initialize IndexWriter
        try{
            SimpleFSDirectory indexDirectoryBrand = new SimpleFSDirectory(new File(directoryBrand));
            brandwriter = new IndexWriter(indexDirectoryBrand, new RussianAnalyzer(Version.LUCENE_30), IndexWriter.MaxFieldLength.UNLIMITED); //create pre'index
            SimpleFSDirectory indexDirectoryArticle = new SimpleFSDirectory(new File(directoryArticle));
            articlewriter = new IndexWriter(indexDirectoryArticle, new RussianAnalyzer(Version.LUCENE_30), IndexWriter.MaxFieldLength.UNLIMITED); //create pre'index

            brandIndex(brandwriter);
            articleIndex(articlewriter);

            articlewriter.optimize();
            brandwriter.optimize();

            articlewriter.close();
            brandwriter.close();
            System.out.println("Index of created.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Cannot create index");
        }
    }

    private void articleIndex(IndexWriter writer) {

        System.out.println("indexing articles");
        ArticleProvider provider = new ArticleProvider(jdbcTemplate);

        List<Article> list = provider.getAllBrands();

        try{
            for(Article item:list){ //add to pre'index all brand's
                Document doc = createDocument(item);
                writer.addDocument(doc);
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    private void brandIndex(IndexWriter writer) {

        System.out.println("indexing brands");
        BrandProvider provider = new BrandProvider(jdbcTemplate);

        List<Brand> list = provider.getAllBrands();

        try{
            for(Brand item:list){ //add to pre'index all brand's
                Document doc = createDocument(item);
                writer.addDocument(doc);
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    private Document createDocument(Article a) {
        Document doc = new Document();

        doc.add(new Field("Id",Long.toString(a.getId()),Field.Store.YES, Field.Index.NOT_ANALYZED));
        doc.add(new Field("InfoSourceId",Long.toString(a.getSourceId()),Field.Store.YES, Field.Index.NOT_ANALYZED));
        doc.add(new Field("BrandId",Long.toString(a.getSourceId()),Field.Store.YES, Field.Index.NOT_ANALYZED));
        doc.add(new Field("NumLikes",Long.toString(a.getNumLikes()),Field.Store.YES,Field.Index.NOT_ANALYZED));
        doc.add(new Field("Link",a.getLink(),Field.Store.YES,Field.Index.ANALYZED));
        doc.add(new Field("Tstamp",a.getTstamp(),Field.Store.YES,Field.Index.ANALYZED));
        doc.add(new Field("Content",a.getContent(),Field.Store.YES,Field.Index.ANALYZED));
        doc.add(new Field("Title",a.getTitle(),Field.Store.YES,Field.Index.ANALYZED));

        return doc;
    }
    private Document createDocument(Brand b) { //create document

        Document doc = new Document();

        doc.add(new Field("Id",Long.toString(b.getId()), Field.Store.YES, Field.Index.NOT_ANALYZED));
        doc.add(new Field("Name",b.getName(), Field.Store.YES, Field.Index.ANALYZED));  // create name
        doc.add(new Field("Description",b.getDescription(),Field.Store.YES, Field.Index.ANALYZED));  //create description
        doc.add(new Field("Website",b.getWebsite(),Field.Store.YES, Field.Index.ANALYZED));
        doc.add(new Field("BranchId",Long.toString(b.getBranchId()),Field.Store.YES, Field.Index.NOT_ANALYZED));
        return doc;
    }


}

