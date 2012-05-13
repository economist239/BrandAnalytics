package ru.brandanalyst.core.db.provider.mysql;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.brandanalyst.core.db.provider.interfaces.MentionProvider;
import ru.brandanalyst.core.db.provider.interfaces.TickerProvider;
import ru.brandanalyst.core.model.*;
import ru.brandanalyst.core.util.cortege.Pair;
import ru.brandanalyst.core.util.temporary.MentionsCalculator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
    public List<Mention> getLatestMentions(){

        //TODO
        //TODO -  это не код, а упячка

        //TODO для этого есть сеттеры
        MySQLBrandProvider brandProvider = new MySQLBrandProvider();
        //TODO для этого есть сеттеры
        brandProvider.setJdbcTemplate(jdbcTemplate);
        //TODO - раз сходили в базу
        List<Brand> brands = brandProvider.getAllBrands();
        //TODO для этого есть сеттеры
        MySQLTickerProvider provider = new MySQLTickerProvider();
        //TODO для этого есть сеттеры
        provider.setJdbcTemplate(jdbcTemplate);
        //TODO - два сходили в базу
        List<TickerPair> tickers = provider.getTickers();

        //TODO всю логику по подсчету либеров роста и падения вынести в таскер и сделать отдельную табличку подо все,где будут только актуальные упоминания
        //ToDO три сходили в базу - три раза - много за раз, есть джоины
        MentionsCalculator calc = new MentionsCalculator(brands,tickers,this);
        List<Mention> result = new ArrayList<Mention>();
        for(TickerPair t : tickers){
            Pair<List<Mention>,List<Mention>> mentions = calc.getRulesAndLosersByTicker(t);
            result.addAll(mentions.first);
            result.addAll(mentions.second);
        }

        return result;
    }

    @Override
    public List<Mention> getMentionsFrom(LocalDate date){
        return jdbcTemplate.query(
                "SELECT * FROM Graphs"
                        + " INNER JOIN Ticker ON TickerId = Ticker.Id"
                        + " INNER JOIN Brand ON BrandId = Brand.Id"
                        + " AND Tstamp > ?",
                MappersHolder.MENTION_MAPPER, new java.sql.Date(date.toDate().getTime())
        );
    }
}
