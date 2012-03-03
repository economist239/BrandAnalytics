package ru.brandanalyst.core.db.provider.mysql;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import ru.brandanalyst.core.model.*;
import ru.brandanalyst.core.util.TextConverter;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: dima
 * Date: 12/17/11
 * Time: 11:08 AM
 */
public final class MappersHolder {

    private MappersHolder() {

    }

    public final static ParameterizedRowMapper<Brand> BRAND_MAPPER = new ParameterizedRowMapper<Brand>() {
        @Override
        public Brand mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Brand(resultSet.getLong("Id"),
                    resultSet.getString("Name"),
                    resultSet.getString("Description"),
                    resultSet.getString("Website"),
                    resultSet.getLong("BranchId"), Params.parseFromString(resultSet.getString("JsonParams")));
        }
    };

    public final static ParameterizedRowMapper<InfoSource> INFO_SOURCE_MAPPER = new ParameterizedRowMapper<InfoSource>() {
        @Override
        public InfoSource mapRow(ResultSet resultSet, int i) throws SQLException {
            return new InfoSource(resultSet.getLong("Id"),
                    resultSet.getLong("TypeId"),
                    resultSet.getString("Title"),
                    resultSet.getString("Description"),
                    resultSet.getString("Website"),
                    resultSet.getString("RSSSource"));
        }
    };

    public final static ParameterizedRowMapper<ArticleForWeb> ARTICLE_FOR_WEB_MAPPER = new ParameterizedRowMapper<ArticleForWeb>() {
        @Override
        public ArticleForWeb mapRow(ResultSet resultSet, int i) throws SQLException {
            return new ArticleForWeb(resultSet.getString("Link"), resultSet.getString("a.Title"),
                    resultSet.getString("Content"), resultSet.getString("s.Title"),
                    resultSet.getString("Website"), resultSet.getDate("Tstamp"));
        }
    };

    public final static ParameterizedRowMapper<Article> ARTICLE_MAPPER = new ParameterizedRowMapper<Article>() {
        @Override
        public Article mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Article(resultSet.getLong("Id"),
                    resultSet.getLong("BrandId"),
                    resultSet.getLong("InfosourceId"),
                    resultSet.getString("Title"), resultSet.getString("Content"),
                    resultSet.getString("Link"), resultSet.getTimestamp("Tstamp"),
                    resultSet.getInt("NumLikes"));
        }
    };

    public final static ParameterizedRowMapper<Article> ARTICLE_WITH_SHORT_CONTENT_MAPPER = new ParameterizedRowMapper<Article>() {
        @Override
        public Article mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Article(resultSet.getLong("Id"),
                    resultSet.getLong("BrandId"),
                    resultSet.getLong("InfosourceId"),
                    resultSet.getString("Title"), TextConverter.firstPhrase(resultSet.getString("Content")),
                    resultSet.getString("Link"), resultSet.getTimestamp("Tstamp"),
                    resultSet.getInt("NumLikes"));
        }
    };

    public final static ParameterizedRowMapper<SemanticDictionaryItem> SEMANTIC_DICTIONARY_MAPPER = new ParameterizedRowMapper<SemanticDictionaryItem>() {
        @Override
        public SemanticDictionaryItem mapRow(ResultSet resultSet, int i) throws SQLException {
            return new SemanticDictionaryItem(resultSet.getString("Term"), resultSet.getDouble("SemanticValue"));
        }
    };
    
    public final static ParameterizedRowMapper<Branch> BRANCH_MAPPER = new ParameterizedRowMapper<Branch>() {
        @Override
        public Branch mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Branch(resultSet.getInt("Id"),
                    resultSet.getString("Name"));
        }
    };
}
