package ru.brandanalyst.analyzer.util;

import org.apache.log4j.Logger;
import ru.brandanalyst.analyzer.classifiers.SVMClassifier;
import ru.brandanalyst.core.util.Su;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Alexandra Mikhaylova mikhaylova@yandex-team.ru
 */
public class ClassifierUtils {
    private static final List<String> dictionary = new ArrayList<String>();
    private static final Logger log = Logger.getLogger(ClassifierUtils.class);

    public static Instances getInstances(final String fileName, final boolean positive) {   // on learning stage
        // TODO: maybe it's possible to make code of this method look better... Nevertheless, it does work!
        dictionary.addAll(getDictionary("tasker/dictionary/positive.txt"));
        dictionary.addAll(getDictionary("tasker/dictionary/negative.txt"));

        final String classifierName = positive ? "positive" : "negative";
        final FastVector featureVector = buildFeatureVector(dictionary);

        Instances instances = new Instances(classifierName, featureVector, 0);
        instances.setClassIndex(featureVector.size() - 1);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String s;
            s = reader.readLine(); // this is a KOSTYL. We need it because of the bad training set. :(
            while ((s = reader.readLine()) != null) {
                if (!s.contains(";;")) {   // this is one more KOSTYL. Our training set is still bad.
                    continue;
                }
                instances.add(createTrainingInstance(s, positive, instances));
            }
            reader.close();
            dictionary.clear(); // very bad code. TODO: think how to do it better - without a static dictionary
            return instances;
        } catch (IOException e) {
            throw new RuntimeException("Couldn't read training info from file: " + fileName);
        }
    }

    public static Instance createTrainingInstance(final String s, final boolean positive, Instances dataset) {
        String t = s.substring(0, s.indexOf(";;"));
        final int label = t.charAt(0) == '+' ? 1 : Integer.parseInt(t);
        final String tweet = s.substring(s.indexOf(";;") + 2);
        double[] attrValues = getAttrValues(tweet, dictionary.size() + 1);

        final String cls = (positive && (label == 1)) || (!positive && (label == -1)) ? "yes" : "no";
        final Instance instance = new Instance(1.0, attrValues); // all instances have the same weight 1.0
        instance.setDataset(dataset);
        instance.setClassValue(cls);
        return instance;
    }

    public static Instance createInstance(final String s) {
        double[] attrValues = getAttrValues(s, dictionary.size() + 1);
        attrValues[attrValues.length - 1] = 0.0;
        final Instance instance = new Instance(1.0, attrValues);
        return instance;
    }

    public static double[] getAttrValues(final String text, final int length) { // the last is the class index (might be empty)
        double attrValues[] = new double[length];
        final List<String> words = Su.splitOnPunctuation(text);
        int i = 0;
        for (final String word : dictionary) {
            int count = count(word, Su.allToLowerCase(words));
            attrValues[i] = count;
            i++;
        }
        attrValues[length - 1] = 0.0; // we may not know the class
        attrValues = normalizeVector(attrValues);
        return attrValues;
    }

    public static FastVector buildFeatureVector(final List<String> dictionary) {
        FastVector featureVector = new FastVector();
        for (final String word : dictionary) {
            featureVector.addElement(new Attribute(word));
        }
        // class: emotional (good / bad) or not emotional (= neutral)
        FastVector labels = new FastVector();
        labels.addElement("yes");     // yes = 0.0
        labels.addElement("no");      // no = 1.0
        Attribute cls = new Attribute("class", labels);
        featureVector.addElement(cls);
        return featureVector;
    }

    public static double[] normalizeVector(final double[] vector) {
        double sqrSum = squareSum(vector);
        if (sqrSum == 0) {
            return vector;
        }
        double[] normalized = new double[vector.length];
        for (int i = 0; i < vector.length; i++) {
            normalized[i] = vector[i] / sqrSum;
        }
        return normalized;
    }

    public static double squareSum(final double[] vector) {
        double sum = 0.0;
        for (final double coordinate : vector) {
            sum += coordinate * coordinate;
        }
        return sum;
    }

    public static int count(final String word, final List<String> tweetWords) {
        int count = 0;
        for (final String tweetWord : tweetWords) {
            if (tweetWord.contains(word)) {
                count++;
            }
        }
        return count;
    }

    public static List<String> getDictionary(final String path) {
        List<String> result = new ArrayList<String>();
        try {
            FileReader fr = new FileReader(path);
            BufferedReader reader = new BufferedReader(fr);
            String s;
            while ((s = reader.readLine()) != null) {
                // TODO: CLEAN dictionaries and remove this kostyl
                if (!result.contains(s)) {
                    result.add(s);
                }
            }
            Collections.sort(result);
            reader.close();
            return result;
        } catch (IOException e) {
            log.error("Error: couldn't read semantic dictionary info from file: " + path + " !\tGot following exception: " + e.toString());
            return null;
        }
    }

    public static SVMClassifier loadSVMClassifier(String filePath) {
        final File file = new File(filePath);
        log.info("Loading SVM classifier from file: " + filePath);
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(file));
            return (SVMClassifier) objectInputStream.readObject();
        } catch (IOException e) {
            log.error("Error during deserialization: " + e.toString());
        } catch (ClassNotFoundException e) {
            log.error("Error during deserialization: " + e.toString());
        } finally {
            try {
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
            } catch (IOException e) {
                log.error("Error during deserialization: " + e.toString());
            }
        }
        return null;
    }

    public static enum Type {
        SVM_NEGATIVE("svmNegative"),
        SVM_POSITIVE("svmPositive");

        private final String name;

        private Type(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}