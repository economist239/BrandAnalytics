package ru.brandanalyst.searcher;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;
import ru.brandanalyst.core.model.Brand;
import ru.brandanalyst.indexer.Indexer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Filter;

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

    private String indexDir;
    private IndexSearcher indexSearcher;

    public void setIndexDir(String indexDir) {
        this.indexDir = indexDir;
   }

    public void getReadyForSearch() {
        try {
            indexSearcher = new IndexSearcher(new SimpleFSDirectory(new File(indexDir)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Brand> searchByDescription(String query) throws ParseException, IOException {

        Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_30); // your can change version
        QueryParser parser = new QueryParser(Version.LUCENE_30,"description",analyzer);
        Query search = parser.parse(query);

        ScoreDoc[] hits = indexSearcher.search(search,null,MAX_DOC).scoreDocs; // you maybe change null on filter;

        List<Brand> lst = new ArrayList<Brand>();
        for(int i = 0 ; i < hits.length ; i++ ){
            Document doc = indexSearcher.doc(hits[i].doc);
            lst.add(brandMap(doc));
        }
        return  lst;
    }

    private Brand brandMap(Document doc) {
        return new Brand(Long.parseLong(doc.get("brand_id")), doc.get("name"),doc.get("description"),doc.get("website"),doc.get("branch"));
    }
}
