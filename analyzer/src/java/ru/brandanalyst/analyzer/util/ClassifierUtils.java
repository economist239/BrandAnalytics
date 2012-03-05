package ru.brandanalyst.analyzer.util;

import ru.brandanalyst.analyzer.classifiers.SVMClassifier;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

/**
 * Alexandra Mikhaylova mikhaylova@yandex-team.ru
 */
public class ClassifierUtils {
    private static final Logger log = Logger.getLogger(ClassifierUtils.class.getName());

    public static Instances getInstances(final String fileName, final boolean positive) {
        final List<String> dictionary = new ArrayList<String>();
        // TODO: specify paths to positive and bad dictionaries
        dictionary.addAll(getDictionary("/home/mikhaylova/BrandAnalytics/analyzer/dictionary/positive.txt"));
        dictionary.addAll(getDictionary("/home/mikhaylova/BrandAnalytics/analyzer/dictionary/negative.txt"));

        final String classifierName = positive ? "positive" : "negative";
        final FastVector featureVector = buildFeatureVector(dictionary);

        Instances instances = new Instances(classifierName, featureVector, 0); // initial capacity 0

        final int n = featureVector.size(); // the last is class label
        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader reader = new BufferedReader(fr);
            String s;
            s = reader.readLine(); // this is a KOSTYL. We need it because of the bad training set. :(
            while ((s = reader.readLine()) != null) {
                double[] attrValues = new double[n];
                if (!s.contains(";;")) {   // this is one more KOSTYL. Our training set is still bad.
                    continue;
                }
                String t = s.substring(0, s.indexOf(";;"));
                final int label = t.charAt(0) == '+' ? 1 : Integer.parseInt(t);
                final String tweet = s.substring(s.indexOf(";;") + 2);
                final List<String> tweetWords = Su.splitOnPunctuation(tweet);
                int i = 0;
                for (final String word : dictionary) {
                    int count = count(word, Su.allToLowerCase(tweetWords));
                    attrValues[i] = count;
                    i ++;
                }

                attrValues = normalizeVector(attrValues);

                final String cls = (positive && (label == 1)) || (!positive && (label == -1)) ? "yes" : "no";
                attrValues[i] = instances.attribute(n - 1).indexOfValue(cls);
                instances.add(new Instance(1.0, attrValues)); // all instances have the same weight 1.0
            }

            instances.setClassIndex(instances.numAttributes() - 1);
            return instances;
        } catch (IOException e) {
            log.info("Couldn't read training info from file: " + fileName);
            return null;
        }
    }

    public static FastVector buildFeatureVector(final List<String> dictionary) {
        FastVector featureVector = new FastVector();
        for (final String word : dictionary) {
            featureVector.addElement(new Attribute(word));
        }
        // class: emotional (good / bad) or not emotional (= neutral)
        FastVector labels = new FastVector();
        labels.addElement("yes");
        labels.addElement("no");
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
        for (int i = 0; i < vector.length; i ++) {
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
            if (word.equals(tweetWord)) {
                count ++;
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
                result.add(s);
            }
            Collections.sort(result);
            return result;
        } catch (IOException e) {
            log.info("Couldn't read semantic dictionary info from file: " + path);
            return null;
        }
    }

    public SVMClassifier loadSVMClassifier(String filePath) {
        final File file = new File(filePath);
        log.info("Loading SVM classifier from file: " + filePath);
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(file));
            return (SVMClassifier) objectInputStream.readObject();
        } catch (IOException e) {
            log.info("Error: " + e.toString());
        } catch (ClassNotFoundException e) {
            log.info("Error: " + e.toString());
        } finally {
            try {
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
            } catch (IOException e) {
                log.info("Error: " + e.toString());
            }
        }
        return null;
    }

    public static enum Type {
        SVM_NEGATIVE,
        SVM_POSITIVE
    }
}