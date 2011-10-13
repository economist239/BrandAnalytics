package ru.brandanalyst.miner2;

import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;

import static java.lang.System.out;

/**
 * Created by IntelliJ IDEA.
 * User: Obus
 * Date: 09.10.11
 * Time: 13:43
 * To change this template use File | Settings | File Templates.
 */
public class GrabberMain {
    private Set<Grabber> grabbers = null;
    private Set<ExactGrabber> exactGrabbers = null;
    GrabberMain(){
    }
    //TODO: made Grabber clonable
    public void setGrabbers(Set<Grabber> grabbers){
        this.grabbers =  new HashSet<Grabber>(grabbers.size());
        Iterator<Grabber> grabberIterator = grabbers.iterator();
        while(grabberIterator.hasNext()){
            this.grabbers.add(grabberIterator.next());
        }
    }
    public void setExactGrabbers(Set<ExactGrabber> exactGrabbers){
        this.exactGrabbers =  new HashSet<ExactGrabber>(exactGrabbers.size());
        Iterator<ExactGrabber> grabberIterator = exactGrabbers.iterator();
        while(grabberIterator.hasNext()){
            this.exactGrabbers.add(grabberIterator.next());
        }
    }
    public List<String> testGetGrabbed(){
        List<String> result = new ArrayList<String>();
        try{
            Iterator<Grabber> grabberIterator = grabbers.iterator();
            for(int i=0;i<grabbers.size();++i){
                //TODO: make normal result ( union string or go along grabbers and make Sring[] of all results (do not concatenate))
                List<String> oneGrabberResult = grabberIterator.next().grab();
                result.addAll(oneGrabberResult);

            }

        }
        catch (Exception e){
            result.add( e.toString());
            return result;
        }
        return result;
    }
    public List<String> testGetExactGrabbed(String[] brandNames){
        List<String> result = new ArrayList<String>();
        try{
            Iterator<ExactGrabber> grabberIterator = exactGrabbers.iterator();
            for(int i=0;i<exactGrabbers.size();++i){
                //TODO: make normal result ( union string or go along grabbers and make Sring[] of all results (do not concatenate))
                List<String> oneExactGrabberResult = grabberIterator.next().grab(brandNames);
                result.addAll(oneExactGrabberResult);
            }

        }
        catch (Exception e){
            result.add( e.toString());
            return result;
        }
        return result;
    }
    public static void main(String[] args) {
        // TODO code application logic here
        try{
            GrabberMain grabberMain = new GrabberMain();
            String[] stringUrls = new String[2];
            stringUrls[0] = "http://news.yandex.ru/computers.rss";
            stringUrls[1] = "http://news.yandex.ru/software.rss";
            String[] filePaths = new String[1];
            filePaths[0] = "config/config1.xml";
            GrabberYandex grabberYandex = new GrabberYandex();
            grabberYandex.setScriptFiles(filePaths);
            grabberYandex.setTargetUrls(stringUrls);
            Set<Grabber> testSetGrabbers= new HashSet<Grabber>();
            testSetGrabbers.add(grabberYandex);
            grabberMain.setGrabbers(testSetGrabbers);
            //TODO: uncomment this to grab from Grabbers too (not from ExactGrabbers only)
            //String[] testResult = grabberMain.testGetGrabbed().clone();
            //setting exact grabbers (twitter):
            GrabberTwitter grabberTwitter = new GrabberTwitter();
            Set<ExactGrabber> testSetExactGrabbers = new HashSet<ExactGrabber>();
            testSetExactGrabbers.add(grabberTwitter);
            grabberMain.setExactGrabbers(testSetExactGrabbers)   ;
            String[] brandNames = new String[1] ;
            brandNames[0] = "apple";
            List<String> testResult = grabberMain.testGetExactGrabbed(brandNames);
            out.println("Total amount of grabbed: "+testResult.size());
            for(String resultString : testResult){
                out.println(resultString);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }


    }
}
