package ru.brandanalyst.analyzer.Weka;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Senov
 * Date: 11.12.11
 * Time: 19:03
 */
public class NegativeSvmWekaClassifier extends SvmWekaClassifier {

    public NegativeSvmWekaClassifier(List<String> variablesId){
        super(variablesId);
    }

    @Override
    public void initializeClassifier(){

    }
}
