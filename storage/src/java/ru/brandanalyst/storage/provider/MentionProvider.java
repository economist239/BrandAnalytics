package ru.brandanalyst.storage.provider;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.brandanalyst.core.model.Mention;
import ru.brandanalyst.mapper.MentionMapper;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: 1
 * Date: 09.10.11
 * Time: 22:35
 * To change this template use File | Settings | File Templates.
 */
public class MentionProvider {
    private SimpleJdbcTemplate jdbcTemplate;
    private MentionMapper mentionMapper;

    public MentionProvider(SimpleJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        mentionMapper = new MentionMapper();
    }

    public void cleanDataStore() {
        jdbcTemplate.update("TRUNCATE TABLE Mention");
    }

    public void writeMentionToDataStore(Mention mention) {
        try {
            jdbcTemplate.update("INSERT INTO Mention (brand_id, article_id, mention) VALUES(?, ?, ?);", mention.getBrandId(), mention.getArticleId(),
                mention.getMention());
        } catch (Exception e) {}
    }

    public void writeListOfMentionsToDataStore(List<Mention> mentions) {
        for (Mention m : mentions) {
            writeMentionToDataStore(m);
        }
    }

    public Mention getMentionByBrandId(String name) {
        List<Mention> list = jdbcTemplate.getJdbcOperations().query("SELECT * FROM Mention WHERE brand_id = ?", new Object[]{name}, mentionMapper);
        return list.get(0);
    }

    public Mention getMentionById(long article_id) {
        List<Mention> list = jdbcTemplate.getJdbcOperations().query("SELECT * FROM Mention WHERE article_id = " + Long.toString(article_id) , mentionMapper);
        return list.get(0);
    }
}
