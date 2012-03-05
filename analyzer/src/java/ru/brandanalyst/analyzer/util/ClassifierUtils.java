package ru.brandanalyst.analyzer.util;

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
        // @todo: paths to positive and bad dictionaries
        final List<String> dictionary = new ArrayList<String>();
        dictionary.addAll(getDictionary("positive"));
        dictionary.addAll(getDictionary("negative"));

        final String classifierName = positive ? "positive" : "negative";
        final FastVector featureVector = buildFeatureVector(dictionary);

        Instances instances = new Instances(classifierName, featureVector, 0); // initial capacity 0

        final int n = dictionary.size();
        try {
            FileReader fr = new FileReader(fileName); // @todo: path!
            BufferedReader reader = new BufferedReader(fr);
            String s;
            while ((s = reader.readLine()) != null) {
                double[] attrValues = new double[n];

                final int label = Integer.parseInt(s.substring(0, s.indexOf(";;")));
                final String tweet = s.substring(s.indexOf(";;") + 2);
                final List<String> tweetWords = Su.splitOnPunctuation(tweet);

                int i = 0;
                for (final String word : dictionary) {
                    int count = count(word, tweetWords);
                    attrValues[i] = count;
                    i ++;
                }

                attrValues = normalizeVector(attrValues);
                // @todo: add label
                instances.add(new Instance(1.0, attrValues)); // all instances have the same weight 1.0
            }
            return instances;
        } catch (IOException e) {
            log.info("Couldn't read training info from file: " + fileName);
            return null;
        }
    }

    public static FastVector buildFeatureVector(final List<String> dictionary) {
        FastVector featureVector = new FastVector();
        for (final String word : dictionary) {
            featureVector.addElement(word);
        }
        return featureVector;
    }

    public static double[] normalizeVector(final double[] vector) {
        double[] normalized = new double[vector.length];
        double sqrSum = squareSum(vector);
        for (int i = 0; i < vector.length; i ++) {
            normalized[i] = vector[i] / sqrSum;
        }
        return normalized;
    }

    public static double squareSum(final double[] vector) {
        double sum = 0.0;
        for (final double coord : vector) {
            sum += coord*coord;
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
            FileReader fr = new FileReader("");
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

    public static enum Type {
        SVM_NEGATIVE,
        SVM_POSITIVE
    }
}