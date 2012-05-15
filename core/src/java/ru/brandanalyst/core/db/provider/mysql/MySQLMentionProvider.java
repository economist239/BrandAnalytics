package ru.brandanalyst.core.db.provider.mysql;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.brandanalyst.core.db.provider.interfaces.MentionProvider;
import ru.brandanalyst.core.model.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: alexsen
 * Date: 23.02.12
 * Time: 20:44
 */
public class MySQLMentionProvider implements MentionProvider{
    private SimpleJdbcTemplate jdbcTemplate;

    @Required
    public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void writeListOfMentionsToDataStore(final List<Mention> mentions){
        final Iterator<Mention> it = mentions.iterator();
        jdbcTemplate.getJdbcOperations().batchUpdate(
                "INSERT INTO Mention (BrandName, TickerId,Tstamp, Val) VALUES(?,?,?,?)",
                new BatchPreparedStatementSetter(){
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException{
                        Mention m = it.next();
                        ps.setString(1, m.getBrandName());
                        ps.setLong(2, m.getTickerId());
                        ps.setDate(3, new java.sql.Date(m.getDot().getDate().toDate().getTime()));
                        ps.setDouble(4, m.getDot().getValue());
                    }

                    @Override
                    public int getBatchSize(){
                        return mentions.size();
                    }
                }
        );
    }

    @Override
    public List<Mention> getLatestMentions(){

        return jdbcTemplate.query(
                "SELECT * FROM Mention"
                        + " WHERE Tstamp = (SELECT MAX(Tstamp) FROM Mention)",
                MappersHolder.MENTION_MAPPER
        );
    }

    @Override
    public List<Mention> getMentionsFrom(LocalDate date){
        return jdbcTemplate.query(
                "SELECT * FROM Mention"
                        + " INNER JOIN Ticker ON TickerId = Ticker.Id"
                        + " INNER JOIN Brand ON BrandId = Brand.Id"
                        + " AND Tstamp > ?",
                MappersHolder.MENTION_MAPPER, new java.sql.Date(date.toDate().getTime())
        );
    }
}
