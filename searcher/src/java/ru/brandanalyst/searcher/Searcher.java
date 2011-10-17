package ru.brandanalyst.searcher;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.ru.RussianAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;
import ru.brandanalyst.core.model.Article;
import ru.brandanalyst.core.model.Brand;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

/**
 * Created by IntelliJ IDEA.
 * User: Nikolaj Karpov
 * Date: 11.10.11
 * Time: 12:24
 */
//TODO method searchByArticles
public class Searcher {
    private static final Logger log = Logger.getLogger(Searcher.class);
    private final int MAX_DOC = 10;

    private String indexDirBrand;
    private String indexDirArticle;
    private IndexSearcher indexSearcherBrand;
    private IndexSearcher indexSearcherArticle;

    public void setIndexDirBrand(String indexDirBrand) {
        this.indexDirBrand   = indexDirBrand;
	}
	
	public void setIndexDirArticle(String indexDirArticle) {
        this.indexDirArticle = indexDirArticle;
	}

    public void getReadyForSearch() {
        try {
            indexSearcherBrand = new IndexSearcher(new SimpleFSDirectory(new File(indexDirBrand)));
            indexSearcherArticle = new IndexSearcher(new SimpleFSDirectory(new File(indexDirArticle)));
        } catch (IOException e) {
            System.out.println("Надо бы индекс сначала..");
        //    e.printStackTrace();
        }
    }

    public List<Brand> searchBrandByDescription(String query) throws ParseException, IOException {

        Analyzer analyzer; // your can change version
        analyzer = new RussianAnalyzer(Version.LUCENE_30);
        QueryParser parser = new QueryParser(Version.LUCENE_30,"Description",analyzer);
        Query search = parser.parse(query);

        ScoreDoc[] hits = indexSearcherBrand.search(search,null,MAX_DOC).scoreDocs; // you maybe change null on filter;
        List<Brand> lst = new ArrayList<Brand>();
        for(int i = 0 ; i < hits.length ; i++ ){
            Document doc = indexSearcherBrand.doc(hits[i].doc);
            lst.add(brandMap(doc));
        }
        return  lst;
    }
    public List<Article> searchArticleByContent(String query) throws ParseException, IOException {

        Analyzer analyzer;
        analyzer = new RussianAnalyzer(Version.LUCENE_30); // your can change version
        QueryParser parser = new QueryParser(Version.LUCENE_30,"Content",analyzer);
        Query search = parser.parse(query);

        ScoreDoc[] hits = indexSearcherBrand.search(search,null,MAX_DOC).scoreDocs; // you maybe change null on filter;
        List<Article> lst = new ArrayList<Article>();
        for(int i = 0 ; i < hits.length ; i++ ){
            Document doc = indexSearcherBrand.doc(hits[i].doc);
            lst.add(articleMap(doc));
        }
        return  lst;
    }
    private Brand brandMap(Document doc) {
        return new Brand(
                Long.parseLong(doc.get("Id")),
                doc.get("Name"),
                doc.get("Description"),
                doc.get("Website"),
                Long.parseLong(doc.get("BranchId"))
        );
    }
    private Article articleMap(Document doc) {
        return new Article(
                Long.parseLong(doc.get("Id")),
                Long.parseLong(doc.get("BrandId")),
                Long.parseLong(doc.get("InfoSourceId")),
                doc.get("Title"),
                doc.get("Content"),
                doc.get("Link"),
                new Timestamp(Long.parseLong(doc.get("Tstamp"))),
                Integer.parseInt(doc.get("NumLikes"))
        );
    }
}
