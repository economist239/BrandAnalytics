package ru.brandanalyst.core.searcher;

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
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Required;
import ru.brandanalyst.core.model.Article;
import ru.brandanalyst.core.model.Brand;
import ru.brandanalyst.core.model.Params;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: dima
 * Date: 2/9/12
 * Time: 3:20 PM
 */
public class Searcher implements InitializingBean {
    /**
     * максимальное количество документов, которые заполняют выдачу
     */
    private static final int MAX_DOC = 1000;

    private String indexDirBrand;
    private String indexDirArticle;
    private IndexSearcher indexSearcherBrand;
    private IndexSearcher indexSearcherArticle;

    @Required
    public void setIndexDirBrand(String indexDirBrand) {
        this.indexDirBrand = indexDirBrand;
    }

    @Required
    public void setIndexDirArticle(String indexDirArticle) {
        this.indexDirArticle = indexDirArticle;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        indexSearcherBrand = new IndexSearcher(new SimpleFSDirectory(new File(indexDirBrand)));
        indexSearcherArticle = new IndexSearcher(new SimpleFSDirectory(new File(indexDirArticle)));
    }

    /**
     * Поиск по брендам на основе их описания
     *
     */
    public List<Brand> searchBrandByDescription(String query) {
        try {
            Analyzer analyzer; // your can change version
            analyzer = new RussianAnalyzer(Version.LUCENE_34);
            QueryParser parser = new QueryParser(Version.LUCENE_34, "Description", analyzer);
            Query search = parser.parse(query);

            ScoreDoc[] hits = indexSearcherBrand.search(search, null, MAX_DOC).scoreDocs; // you maybe change null on filter;
            List<Brand> lst = new ArrayList<Brand>();
            for (ScoreDoc hit : hits) {
                Document doc = indexSearcherBrand.doc(hit.doc);
                lst.add(brandMap(doc));
            }
            return lst;
        } catch (IOException e) {
            throw new RuntimeException("index access failed", e);
        } catch (ParseException e) {
            throw new RuntimeException("invalid query", e);
        }
    }

    /**
     * Поиск по новостям на основе их содержания
     *
     */
    public List<Article> searchArticleByContent(String query) {
        try {
            Analyzer analyzer;
            analyzer = new RussianAnalyzer(Version.LUCENE_34); // your can change version
            QueryParser parser = new QueryParser(Version.LUCENE_34, "Content", analyzer);
            Query search = parser.parse(query);

            ScoreDoc[] hits = indexSearcherArticle.search(search, null, MAX_DOC).scoreDocs; // you maybe change null on filter;
            List<Article> lst = new ArrayList<Article>();
            for (ScoreDoc hit : hits) {
                Document doc = indexSearcherArticle.doc(hit.doc);
                lst.add(articleMap(doc));
            }
            return lst;
        } catch (IOException e) {
            throw new RuntimeException("index access failed", e);
        } catch (ParseException e) {
            throw new RuntimeException("invalid query", e);
        }
    }

    private Brand brandMap(Document doc) {
        return new Brand(
                Long.parseLong(doc.get("Id")),
                doc.get("Name"),
                doc.get("Description"),
                doc.get("Website"),
                Long.parseLong(doc.get("BranchId")), Params.empty()
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
                new LocalDateTime(Long.parseLong(doc.get("Tstamp"))),
                Integer.parseInt(doc.get("NumLikes"))
        );
    }
}
