package ru.brandanalyst.core.db.provider.bdb;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;
import com.sleepycat.persist.model.Relationship;
import com.sleepycat.persist.model.SecondaryKey;
import org.json.JSONException;
import org.json.JSONObject;
import ru.brandanalyst.core.model.Article;

import java.sql.Timestamp;

/**
 * Created by IntelliJ IDEA.
 * User: daddy-bear
 * Date: 2/18/12
 * Time: 5:39 PM
 */
@Deprecated
@Entity
public class ArticleEntity {
    @PrimaryKey
    private String title;

    @SecondaryKey(relate = Relationship.MANY_TO_ONE)
    private long brandId;

    private String jsonArticle;

    public ArticleEntity() {
    }

    public ArticleEntity(Article article) {
        this.title = article.getTitle();
        this.brandId = article.getBrandId();
        this.jsonArticle = wrap(article);
    }

    public String getTitle() {
        return title;
    }

    public long getBrandId() {
        return brandId;
    }

    public Article getArticle() {
        return unwrap(jsonArticle);
    }

    private static String wrap(Article article) {
        try {
            JSONObject json = new JSONObject();
            json.put("id", article.getId());
            json.put("brand-id", article.getBrandId());
            json.put("title", article.getTitle());
            json.put("content", article.getContent());
            json.put("source-id", article.getSourceId());
            json.put("time-long", article.getTstamp().getTime());
            json.put("link", article.getLink());
            json.put("num-likes", article.getNumLikes());
            return json.toString();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private static Article unwrap(String jsonString) {
        try {
            JSONObject json = new JSONObject(jsonString);
            return new Article(Long.parseLong(json.get("id").toString()), Long.parseLong(json.get("brand-id").toString()),
                    json.get("title").toString(), json.get("content").toString(), json.get("link").toString(),
                    new Timestamp(Long.parseLong(json.get("time-long").toString())), Integer.parseInt(json.get("num-likes").toString()));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}