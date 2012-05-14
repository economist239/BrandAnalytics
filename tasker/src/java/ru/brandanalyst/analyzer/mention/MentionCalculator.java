package ru.brandanalyst.analyzer.mention;

import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.ReadablePeriod;
import ru.brandanalyst.core.model.Graph;
import ru.brandanalyst.core.model.SingleDot;
import ru.brandanalyst.core.util.cortege.Pair;

/**
 * Created with IntelliJ IDEA, and God's blessing.
 * User: alexsen (Alexander Senov)
 * Date: 5/12/12
 * Time: 12:32 AM
 */
public class MentionCalculator{
    protected static final ReadablePeriod periodDuration = Days.days(10);
    /**
     * date from which to include graphs of first period
     */
    protected final LocalDate lastDate;
    /**
     * date till which include graphs for first period
     * date from which to include graphs of second period
     */
    protected final LocalDate middleDate;
    /**
     * date till which include graphs for second period
     */
    protected final LocalDate firstDate;

    public MentionCalculator(){
        lastDate = LocalDate.now();
        middleDate = lastDate.minus(periodDuration);
        firstDate = middleDate.minus(periodDuration);
    }

    public double calculate(Graph graph){
        Pair<Double, Double> pdd = splitInTwoPeriods(graph);
        return calculateMentionValue(pdd.first, pdd.second);
    }

    protected Pair<Double, Double> splitInTwoPeriods(Graph graph){
        double first = 0;
        double second = 0;
        for(SingleDot sd : graph.getGraph()){
            if(sd.getDate().isAfter(middleDate)){
                if(sd.getDate().isBefore(lastDate)){
                    second += sd.getValue();
                }
            } else{
                if(sd.getDate().isAfter(firstDate)){
                    first += sd.getValue();
                }
            }
        }
        return new Pair<Double, Double>(first, second);
    }

    /**
     * calculationg mention value, from amount of mentions in first and second periods
     *
     * @param first  amount of mentions in first period
     * @param second amount of mentions in second period
     * @return mention value (mention rate)
     */
    protected double calculateMentionValue(double first, double second){
        return (second - first) / (first + 1);
    }
}
