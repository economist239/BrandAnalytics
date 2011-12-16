package ru.brandanalyst.analyzer.Weka;

import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Senov
 * Date: 11.12.11
 * Time: 17:18
 */
public abstract class SvmWekaClassifier extends WekaClassifier{

    public SvmWekaClassifier(List<String> variablesId){
        super(variablesId);
    }


    protected static Instances attributeToNominal(Instances insts)
    {
        FastVector labels = new FastVector();
        labels.addElement("0");
        labels.addElement("1");
        Attribute attribute = new Attribute("class",labels);

        FastVector attributes = new FastVector();
        attributes.addElement(attribute);

        Instances classInsts = new Instances("class",attributes,insts.numInstances());

        for(int i=0;i<insts.numInstances();++i){
            double value = insts.instance(i).value(insts.numAttributes()-1);
            double[] instValues = new double[1];
            double weight = 1;

            instValues[0] = classInsts.attribute(0).indexOfValue(Integer.toString((int) value));
            Instance newInst = new Instance(weight,instValues);
            classInsts.add(newInst);

        }

        insts.deleteAttributeAt(insts.numAttributes()-1);
        insts = Instances.mergeInstances(insts,classInsts);

        return insts;
    }


    private static Instances cheating(Instances insts){
        int countZeros = 0;
        for(int i=0;i<insts.numInstances();++i){
            double value = insts.instance(i).value(insts.numAttributes()-1);
            if(0==value){
                ++countZeros;
            }
        }
        insts.sort(insts.numAttributes()-1);

        Instances insts0 = new Instances(insts,0,countZeros);
        List<Double> values0 = getAttributeValues(insts0,insts0.numAttributes()-1);

        Instances insts1 = new Instances(insts,countZeros,insts.numInstances()-countZeros);
        List<Double> values1 = getAttributeValues(insts1,insts1.numAttributes()-1);

        insts = insts0;

        for(int k = 0; k < insts1.numInstances();++k){
            for(int i = 0; i < insts0.numInstances()/insts1.numInstances();++i ){
                insts.add(insts1.instance(k));
            }
        }

        return insts;
    }

    public static List<Double> getAttributeValues(Instances instances,int num){
        List<Double> result = new ArrayList<Double>(instances.numInstances());
        for(int i = 0;i<instances.numInstances();++i){
            result.add(instances.instance(i).value(num));
        }
        return result;
    }
}
