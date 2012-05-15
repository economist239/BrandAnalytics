package ru.brandanalyst.analyzer.mention;

import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.ReadablePeriod;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.brandanalyst.common.scheduler.AbstractDelayedTimerTask;
import ru.brandanalyst.core.db.provider.ProvidersHandler;
import ru.brandanalyst.core.db.provider.interfaces.BrandProvider;
import ru.brandanalyst.core.db.provider.interfaces.GraphProvider;
import ru.brandanalyst.core.db.provider.interfaces.MentionProvider;
import ru.brandanalyst.core.model.*;
import ru.brandanalyst.core.util.Batch;

import java.util.*;

/**
 * @author daddy-bear
 *         Date: 03.05.12 - 19:22
 *         <p/>
 *         такие дела, чтобы у Mention был sourceTypeId надо чтобы он был у Graphs а значит и у Articles.
 *         то есть надо обновить пару таблиц в базе и сделать новую таблицу InfoSourceType.
 *         то есть и текущие аналайзоры переделать
 *         ну его, не принципиально, вижу два вариант
 *         1. убрать вообще выбор типа источников - просто рейтинг упоминаемости
 *         2. сделать его рандомным
 *         пока сделаю второй, проще будет убрать.
 */

public class MentionGeneratorTask extends AbstractDelayedTimerTask{

    private final static long BASE_TICKER = 1L;

    private ProvidersHandler providersHandler;
    private Map<Long, String> brandIdNameMap;

    @Required
    public void setProvidersHandler(ProvidersHandler providersHandler){
        this.providersHandler = providersHandler;
    }

    @Override
    protected void runTask(){
        init();
        final Map<Long, Graph> graphsByBrandId = providersHandler.getGraphProvider().getGraphsByTickerId(BASE_TICKER);
        final List<Mention> mentions = new ArrayList<Mention>(graphsByBrandId.size());
        final LocalDate now = LocalDate.now();
        MentionCalculator calculator = new MentionCalculator();

        for(final Map.Entry<Long, Graph> e : graphsByBrandId.entrySet()){
            final Graph graph = e.getValue();
            final long brandId = e.getKey();
            double value = calculator.calculate(graph);

            mentions.add(new Mention(
                    new SingleDot(now, value),
                    BASE_TICKER,
                    getBrandNameById(brandId)
            ));
        }
        providersHandler.getMentionProvider().writeListOfMentionsToDataStore(mentions);
    }

    private void init(){
        final List<Brand> brands = providersHandler.getBrandProvider().getAllBrands();
        brandIdNameMap = new HashMap<Long, String>(brands.size());
        for(Brand b : brands){
            brandIdNameMap.put(b.getId(), b.getName());
        }
    }

    protected String getBrandNameById(final Long brandId){
        return brandIdNameMap.get(brandId);
    }
}


