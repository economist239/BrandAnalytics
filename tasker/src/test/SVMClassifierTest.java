import junit.framework.TestCase;
import ru.brandanalyst.analyzer.classifiers.SVMClassifier;
import ru.brandanalyst.analyzer.util.ClassifierUtils;

/**
 * Alexandra Mikhaylova mikhaylova@yandex-team.ru
 */
public class SVMClassifierTest extends TestCase {
    public void testClassifiers() {
        String trainingFileName = "tasker/samples/samples";
        String dir = "classifiers";

        SVMClassifier classifierNegative = new SVMClassifier();
        classifierNegative.setTrainingFileName(trainingFileName);
        classifierNegative.setType(ClassifierUtils.Type.SVM_NEGATIVE);
        classifierNegative.train();
        classifierNegative.save(dir);

        SVMClassifier classifierPositive = new SVMClassifier();
        classifierPositive.setTrainingFileName(trainingFileName);
        classifierPositive.setType(ClassifierUtils.Type.SVM_POSITIVE);
        classifierPositive.train();
        classifierPositive.save(dir);
    }
}
