package ru.brandanalyst.analyzer.classifiers;

import org.springframework.beans.factory.annotation.Required;
import ru.brandanalyst.analyzer.util.ClassifierUtils;
import weka.core.Instance;
import weka.core.Instances;
import wlsvm.WLSVM;

import java.util.logging.Logger;

/**
 * Alexandra Mikhaylova mikhaylova@yandex-team.ru
 */
public class SVMClassifier implements SVM {
    private static final Logger log = Logger.getLogger(SVMClassifier.class.getName());

    private WLSVM classifier;
    private String trainingFileName;
    private ClassifierUtils.Type type;

    @Required
    public void setClassifier(final WLSVM classifier) {
        this.classifier = classifier;
    }

    @Required
    public void setTrainingFileName(final String fileName) {
        this.trainingFileName = fileName;
    }

    @Required
    public void setType(final ClassifierUtils.Type type) {
        this.type = type;
    }

    @Override
    public void train() {
        final Instances labeled = ClassifierUtils.getInstances(trainingFileName);
        try {
            this.classifier.buildClassifier(labeled);
        } catch (Exception e) {
            log.info("Couldn't build SVM classifier");
        }
    }

    @Override
    public double classifyInstance(final Instance instance) {
        try {
            return this.classifier.classifyInstance(instance);
        } catch (Exception e) {
            log.info("Couldn't classify instance: " + instance.toString());
            return 0.0;
        }
    }

    @Override
    public Instances classify(final Instances unlabeled) {
        Instances labeled = new Instances(unlabeled);
        for (int i = 0; i < unlabeled.numInstances(); i ++) {
            final double classLabel = classifyInstance(unlabeled.instance(i));
            labeled.instance(i).setClassValue(classLabel);
        }
        return labeled;
    }

    public String getName() {
        return this.type == ClassifierUtils.Type.SVM_NEGATIVE ? "svmNegative" : "svmPositive";
    }
}