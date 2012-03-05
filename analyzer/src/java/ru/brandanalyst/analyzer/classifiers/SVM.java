package ru.brandanalyst.analyzer.classifiers;

import weka.core.Instance;
import weka.core.Instances;

/**
 * Alexandra Mikhaylova mikhaylova@yandex-team.ru
 */
public interface SVM {
    public void train();
    public double classifyInstance(final Instance instance);
    public Instances classify(final Instances unlabeled);

    // @todo: save & load classifier ?
}