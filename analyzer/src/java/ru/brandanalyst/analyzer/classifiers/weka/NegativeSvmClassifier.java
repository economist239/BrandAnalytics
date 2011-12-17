package ru.brandanalyst.analyzer.classifiers.weka;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Senov
 * Date: 11.12.11
 * Time: 19:03
 */
public class NegativeSvmClassifier extends SvmClassifier {

    public NegativeSvmClassifier(List<String> variablesId){
        super(variablesId);
    }

    @Override
    public void initializeClassifier(){

    }
}
