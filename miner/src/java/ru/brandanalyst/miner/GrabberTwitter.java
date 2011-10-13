package ru.brandanalyst.miner;

import twitter4j.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Obus
 * Date: 10.10.11
 * Time: 14:14
 * To change this template use File | Settings | File Templates.
 */
public class GrabberTwitter implements ExactGrabber{
    GrabberTwitter(){

    };
    public List<String> grab(String[] brandNames){

        List<String> result = new ArrayList<String>();
        try{
            //TODO: make this piece of shit, named as code, BETTER (get rid of <code>pageNumber</code>, for example)
            Twitter twitter = new TwitterFactory().getInstance();
            int pageNumber=1;
            Query query = new Query("apple");
            query.setSince("2011"+"-"+"10"+"-"+"11");
            //query.setRpp(100); // it's ok
            List<Tweet> resultTweets;
            QueryResult queryResult;
            do{
                query.setPage(pageNumber);
                queryResult = twitter.search(query);
                resultTweets = queryResult.getTweets();

                for (int i =0;i<resultTweets.size();++i){
                    result.add(resultTweets.get(i).getText());
                }
                pageNumber++;

            }while(15==resultTweets.size());
            /*for(int i=0;i<brandNames.length;++i){
            Twitter twitter = new TwitterFactory().getInstance();
            for(int j=0;j<brandNames.length;++j){
            Query query = new Query(brandNames[j]);
            QueryResult queryResult = twitter.search(query);
            for (Tweet tweet : (List<Tweet>)queryResult.getTweets()) {
                 result += tweet.getText();
               }
            }*/

        }
        catch (Exception e){
            result.add(e.toString());
        }
        return result;
    }
    public List<String> grab(String brandName){
        String[] brandNames = new String[1] ;
        brandNames[0] = brandName;
        return grab(brandNames);
    }
}
