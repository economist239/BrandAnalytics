package ru.brandanalyst.analyzer.classifiers;

import weka.core.Instance;
import weka.core.Instances;

import java.io.Serializable;

/**
 * Alexandra Mikhaylova mikhaylova@yandex-team.ru
 */
public interface SVM extends Serializable {
    public void train();
    public double classifyInstance(final Instance instance);
    public Instances classify(final Instances unlabeled);
    // save to file and load from file
//    public void save();
//    public SVM load();
}