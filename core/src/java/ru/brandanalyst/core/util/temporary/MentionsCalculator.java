package ru.brandanalyst.core.util.temporary;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import ru.brandanalyst.core.db.provider.interfaces.MentionProvider;
import ru.brandanalyst.core.model.*;
import ru.brandanalyst.core.util.Time;
import ru.brandanalyst.core.util.cortege.Pair;

import java.util.*;

/**
 * Created with IntelliJ IDEA, and God's blessing.
 * User: alexsen (Alexander Senov)
 * Date: 5/7/12
 * Time: 6:31 PM
 */
public class MentionsCalculator{
    protected List<Brand> brands;
    protected List<TickerPair> tickers;
    protected List<Mention> allMentions;
    protected MentionProvider mentionProvider;

    public MentionsCalculator(List<Brand> brands, List<TickerPair> tickers,MentionProvider provider){
        this.brands = brands;
        this.tickers = tickers;
        this.mentionProvider = provider;
        currentDate = LocalDate.now();
        this.allMentions = mentionProvider.getMentionsFrom(new LocalDate(currentDate.toDate().getTime() - 2*TIME_PERIOD));
    }

    public Pair<List<Mention>, List<Mention>> getRulesAndLosersByTicker(TickerPair t){
        List<Pair<Mention, Mention>> lpm = groupAllMentions(getMentionsByTicker(allMentions,t),t);
        List<Mention> converted = convert(lpm);
        List<Mention> rulers = getMentionRulers(converted);
        List<Mention> losers = getMentionLosers(converted);
        return new Pair<List<Mention>, List<Mention>>(rulers,losers);
    }

    protected List<Pair<Mention, Mention>> groupAllMentions(List<Mention> all,TickerPair t){
        LocalDate barrier = new LocalDate(currentDate.toDate().getTime() - TIME_PERIOD);
        List<Mention> mBefore = new ArrayList<Mention>();
        List<Mention> mAfter = new ArrayList<Mention>();
        for(Mention m : all){
            if(m.getDot().getDate().isAfter(barrier)){
                mAfter.add(m);
            } else{
                mBefore.add(m);
            }
        }
        return getTwoPeriodMentionByTicker(mBefore, mAfter, t);
    }

    protected List<Mention> getMentionsByTicker(List<Mention> mentions, TickerPair t){
        List<Mention> result = new ArrayList<Mention>();
        for(Mention m : mentions){
            if(m.getTickerId() == t.getId()){
                result.add(m);
            }
        }
        return result;
    }

    /**
     * unsafe function, it's suggests that all mentions are this same ticker
     *
     * @param mentionsFirst  - mentions in first time period
     * @param mentionsSecond - mentions in second tome period
     * @param ticker
     * @return - list of two mentions. first - count mentions in first time period (like day), second - inf second time period
     *         like day before yesterday and yesterday
     */
    private List<Pair<Mention, Mention>> getTwoPeriodMentionByTicker(List<Mention> mentionsFirst, List<Mention> mentionsSecond, TickerPair ticker){
        Map<String, Pair<Double, Double>> brandsResults = new HashMap<String, Pair<Double, Double>>(brands.size());
        for(Brand b : brands){
            brandsResults.put(b.getName(), new Pair<Double, Double>(0.0, 0.0));
        }

        for(Mention m : mentionsFirst){
            Pair<Double, Double> val = brandsResults.get(m.getBrand());
            val.first += m.getDot().getValue();
            brandsResults.put(m.getBrand(), val);
        }

        for(Mention m : mentionsSecond){
            Pair<Double, Double> val = brandsResults.get(m.getBrand());
            val.second += m.getDot().getValue();
            brandsResults.put(m.getBrand(), val);
        }

        List<Pair<Mention, Mention>> result = new ArrayList<Pair<Mention, Mention>>(brands.size());
        for(Brand b : brands){
            Pair<Double, Double> p = brandsResults.get(b.getName());
            result.add(
                    new Pair<Mention, Mention>(
                            new Mention(new SingleDot(currentDate, p.first), ticker, b),
                            new Mention(new SingleDot(currentDate, p.second), ticker, b)
                    ));
        }
        return result;
    }

    public List<Mention> getMentionRulers(List<Mention> mentions){
        List<Mention> result = new ArrayList<Mention>(RESULT_AMOUNT);
        Collections.sort(mentions, ascComparator);
        for(int i = 0; i < Math.min(RESULT_AMOUNT, mentions.size()); ++i){
            result.add(mentions.get(i));
        }
        return result;
    }

    public List<Mention> getMentionLosers(List<Mention> mentions){
        List<Mention> result = new ArrayList<Mention>(RESULT_AMOUNT);
        Collections.sort(mentions, descComparator);
        for(int i = 0; i < Math.min(RESULT_AMOUNT, mentions.size()); ++i){
            result.add(mentions.get(i));
        }
        return result;
    }

    protected List<Mention> convert(List<Pair<Mention, Mention>> mentions){
        List<Mention> result = new ArrayList<Mention>(mentions.size());
        for(Pair<Mention, Mention> pair : mentions){
            result.add(convert(pair));
        }
        return result;
    }

    protected Mention convert(Pair<Mention, Mention> pair){
        double val1 = pair.first.getDot().getValue();
        double val2 = pair.second.getDot().getValue();
        double val = (val1 != 0) ?
                (val2 - val1) / val1 :
                0;
        return new Mention(
                new SingleDot(pair.second.getDot().getDate(), val),
                pair.first.getTicker(),
                pair.first.getBrand(),
                pair.first.getTickerId(),
                pair.first.getBrandId()
        );
    }

    private static Comparator<Mention> ascComparator
            = new Comparator<Mention>(){
        @Override
        public int compare(Mention m1, Mention m2){
            return Double.compare(m1.getDot().getValue(), m2.getDot().getValue());
        }
    };

    private static Comparator<Mention> descComparator
            = new Comparator<Mention>(){
        @Override
        public int compare(Mention m1, Mention m2){
            return -Double.compare(m1.getDot().getValue(), m2.getDot().getValue());
        }
    };

    protected static final long TIME_PERIOD = 10000000;
    protected static final int RESULT_AMOUNT = 5;
    protected LocalDate currentDate;

}
