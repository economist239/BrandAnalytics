package ru.brandanalyst.analyzer;

import java.io.*;
import java.util.StringTokenizer;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.brandanalyst.core.db.provider.SemanticDictionaryProvider;
import ru.brandanalyst.core.model.SemanticDictionaryItem;

/**
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 11/4/11
 * Time: 10:03 AM
 */
public class DictionaryInitialyzer {
    private static StringTokenizer stringTokenizer;
    private static BufferedReader in;

    public static void main(String[] args) throws IOException {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/BAdirty?useUnicode=true&amp;characterEncoding=utf8");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        dataSource.setValidationQuery("select 1");
        SimpleJdbcTemplate jdbcTemplate = new SimpleJdbcTemplate(dataSource);
        SemanticDictionaryProvider provider = new SemanticDictionaryProvider(jdbcTemplate);

        in = new BufferedReader(new FileReader("analyzer/dictionary/positive.txt"));
        stringTokenizer = new StringTokenizer(in.readLine());
        while (stringTokenizer.hasMoreTokens()) {
            String term = stringTokenizer.nextToken();
            System.out.println(term);
            provider.setSemanticDictionaryItem(new SemanticDictionaryItem(term, 1.0));
        }
        in.close();

        in = new BufferedReader(new FileReader("analyzer/dictionary/negative.txt"));
        stringTokenizer = new StringTokenizer(in.readLine());
        while (stringTokenizer.hasMoreTokens()) {
            String term = stringTokenizer.nextToken();
            System.out.println(term);
            provider.setSemanticDictionaryItem(new SemanticDictionaryItem(term, 1.0));
        }
        in.close();
    }
}
