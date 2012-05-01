package ru.brandanalyst.analyzer.classifiers;

import org.springframework.beans.factory.annotation.Required;
import ru.brandanalyst.analyzer.util.ClassifierUtils;
import weka.core.Instance;
import weka.core.Instances;
import wlsvm.WLSVM;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import org.apache.log4j.Logger;

/**
 * Alexandra Mikhaylova mikhaylova@yandex-team.ru
 */
public class SVMClassifier implements SVM {
    private static final Logger log = Logger.getLogger(SVMClassifier.class);
    private static final long serialVersionUID = -6362247777972157008L;

    private WLSVM classifier = new WLSVM();
    private String trainingFileName;
    private ClassifierUtils.Type type;

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
        final boolean positive = this.type == ClassifierUtils.Type.SVM_POSITIVE;
        final Instances labeled = ClassifierUtils.getInstances(trainingFileName, positive);
        try {
            this.classifier.buildClassifier(labeled);
        } catch (Exception e) {
            log.error("Error: couldn't build SVM classifier! Got following exception: " + e.toString());
        }
    }

    @Override
    public double classifyInstance(final Instance instance) {
        try {
            return this.classifier.classifyInstance(instance);          // 0.0 = neutral, 1.0 = emotional
        } catch (Exception e) {
            log.error("Error: couldn't classify instance: " + instance.toString() + " !\tGot following exception: " + e.toString());
            return 0.0;
        }
    }

    @Override
    public Instances classify(final Instances unlabeled) {
        Instances labeled = new Instances(unlabeled);
        for (int i = 0; i < unlabeled.numInstances(); i++) {
            final double classLabel = classifyInstance(unlabeled.instance(i));
            labeled.instance(i).setClassValue(classLabel);
        }
        return labeled;
    }

    @Override
    public void save(String dir) {
        final String filename = type.getName();
        final File file = new File(dir, filename);
//        log.info("Saving" + this.getName() + "(filename: " + file.getName() + ")");
        try {
            final ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
            try {
                objectOutputStream.writeObject(this);
//                return filename;
            } finally {
                objectOutputStream.close();
            }
        } catch (IOException e) {
            log.error("Error during serialization: " + e.toString());
//            return null;
        }
    }
}