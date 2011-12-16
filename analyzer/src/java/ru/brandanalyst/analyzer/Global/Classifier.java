package ru.brandanalyst.analyzer.Global;

import ru.brandanalyst.core.model.Article;
import weka.core.Instance;

import java.util.Collection;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Senov
 * Date: 11.12.11
 * Time: 14:40
 */
public interface Classifier {

    /**
     * Метод, строящий классификатор
     */
    //public void build();

    /**
     * метод, сохраняющий построенную модель классификатора
     * (способ сохранения (в файл, в БД, ...) зависит от реализации )
     */
    //public void saveClassifierModel();

    /**
     * загрузка заранее созданной модели классификатора
     */
    public void loadClassificationModel();

    //TODO: сделать все это нармальна
    public void setDataForAnalyse(List<List<Double>> data);

    public void initializeDataForAnalyse(int n);

    public void addDataForAnalyse(List<List<Double>> data);

    public double analyse(List<Double> list);

}
