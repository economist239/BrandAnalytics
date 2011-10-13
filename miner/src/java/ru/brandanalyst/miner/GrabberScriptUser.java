package ru.brandanalyst.miner;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by IntelliJ IDEA.
 * User: Obus
 * Date: 09.10.11
 * Time: 11:01
 * To change this template use File | Settings | File Templates.
 */
public class GrabberScriptUser {
/*
*  Maybe later GrabberScriptUser would be divided into GrabberUrlUser and his extension GrabberScriptUser
 *  now it's not important, untill
 */
    protected File[] scriptFiles=null;
    protected URL[] targetUrls=null;
    GrabberScriptUser(){
    }
    GrabberScriptUser(String[] filePaths){
        setScriptFiles(filePaths);
    }
    GrabberScriptUser(File[] files){
        setScriptFiles(files);
    }
    public void setTargetUrls(String[] stringUrls)throws MalformedURLException{
        targetUrls = new URL[stringUrls.length];
        for(int i=0;i<stringUrls.length;++i){
            targetUrls[i] = new URL(stringUrls[i]);
        }
    }
    public void setTargetUrls(URL[] urls){
        targetUrls = urls.clone();
    }
    public void setScriptFiles(String[] filePaths){
        scriptFiles = new File[filePaths.length];
        for(int i=0;i<filePaths.length; ++i){
            scriptFiles[i] = new File(filePaths[i]);
        }
    }
    public void setScriptFiles(File[] files){
        scriptFiles = files.clone();
    }
}