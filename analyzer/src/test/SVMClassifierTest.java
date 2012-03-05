import org.springframework.test.AbstractDependencyInjectionSpringContextTests;
import ru.brandanalyst.analyzer.classifiers.SVMClassifier;
import ru.brandanalyst.analyzer.util.ClassifierUtils;

/**
 * Alexandra Mikhaylova mikhaylova@yandex-team.ru
 */
public class SVMClassifierTest extends AbstractDependencyInjectionSpringContextTests {
    public void testClassifiers() {
        String trainingFileName = "analyzer/samples/samples";
        String dir = "svmclassifiers";

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
