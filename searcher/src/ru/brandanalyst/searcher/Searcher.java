package ru.brandanalyst.searcher;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.util.Version;
import ru.brandanalyst.core.model.Brand;
import ru.brandanalyst.indexer.Indexer;

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
public class Searcher {
    final int MAX_DOC = 1234567890;
    //method return list of all name of brand find by keyword text
    public List<Brand> searchBrandByDescription(Indexer indexer, String text) {
        Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_30); // your can change version
        QueryParser parser = new QueryParser(Version.LUCENE_30,"description",analyzer);
        Query search = null;
        try {
            search = parser.parse(text);
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        ScoreDoc[] hits = new ScoreDoc[0]; // you maybe change null on filter
        try {
            hits = indexer.indexSearcher.search(search,null,MAX_DOC).scoreDocs;
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        List<Brand> lst = new ArrayList<Brand>();
        
        for(int i = 0 ; i < hits.length ; i++ ){
            Document doc = null;
            try {
                doc = indexer.indexSearcher.doc(hits[i].doc);
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            lst.add(indexer.brandProvider.getBrandById(Integer.parseInt(doc.get("id"))));
        }
        return  lst;
    }
}
