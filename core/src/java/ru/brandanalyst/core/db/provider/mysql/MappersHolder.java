package ru.brandanalyst.core.db.provider.mysql;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import ru.brandanalyst.core.model.Article;
import ru.brandanalyst.core.model.Brand;
import ru.brandanalyst.core.model.InfoSource;
import ru.brandanalyst.core.model.SemanticDictionaryItem;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: dima
 * Date: 12/17/11
 * Time: 11:08 AM
 */
public final class MappersHolder {
    public final static ParameterizedRowMapper<Brand> BRAND_MAPPER = new ParameterizedRowMapper<Brand>() {
        @Override
        public Brand mapRow(ResultSet resultSet, int i) throws SQLException {
            try {
                return new Brand(Long.parseLong(resultSet.getString("Id")),
                        resultSet.getString("Name"),
                        resultSet.getString("Description"),
                        resultSet.getString("Website"),
                        Long.parseLong(resultSet.getString("BranchId")), resultSet.getString("FinamName"));
            } catch (Exception e) {
                return new Brand(Long.parseLong(resultSet.getString("Id")),
                        resultSet.getString("Name"),
                        resultSet.getString("Description"),
                        resultSet.getString("Website"), -1, "");
            }
        }
    };

    public final static ParameterizedRowMapper<InfoSource> INFO_SOURCE_MAPPER = new ParameterizedRowMapper<InfoSource>() {
        @Override
        public InfoSource mapRow(ResultSet resultSet, int i) throws SQLException {
            return new InfoSource(Long.parseLong(resultSet.getString("Id")),
                    Long.parseLong(resultSet.getString("TypeId")),
                    resultSet.getString("Title"),
                    resultSet.getString("Description"),
                    resultSet.getString("Website"),
                    resultSet.getString("RSSSource"));
        }
    };

    public final static ParameterizedRowMapper<Article> ARTICLE_MAPPER = new ParameterizedRowMapper<Article>() {
        @Override
        public Article mapRow(ResultSet resultSet, int i) throws SQLException {
            try {
                return new Article(Long.parseLong(resultSet.getString("Id")),
                        Long.parseLong(resultSet.getString("BrandId")),
                        Long.parseLong(resultSet.getString("InfosourceId")),
                        resultSet.getString("Title"), resultSet.getString("Content"),
                        resultSet.getString("Link"), resultSet.getTimestamp("Tstamp"),
                        Integer.parseInt(resultSet.getString("NumLikes")));
            } catch (Exception e) {
                return new Article(Long.parseLong(resultSet.getString("Id")),
                        Long.parseLong(resultSet.getString("BrandId")),
                        Long.parseLong(resultSet.getString("InfosourceId")),
                        resultSet.getString("Title"), resultSet.getString("Content"),
                        resultSet.getString("Link"), resultSet.getTimestamp("Tstamp"), -1);
            }
        }
    };

    public final static ParameterizedRowMapper<SemanticDictionaryItem> SEMANTIC_DICTIONARY_MAPPER = new ParameterizedRowMapper<SemanticDictionaryItem>() {
        @Override
        public SemanticDictionaryItem mapRow(ResultSet resultSet, int i) throws SQLException {
            return new SemanticDictionaryItem(resultSet.getString("Term"), Double.parseDouble(resultSet.getString("SemanticValue")));
        }
    };
}
