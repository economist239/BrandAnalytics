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

    /**
     * Метод, устанавливающий данные для анализа
     * @param data - данные для анализа каждый List<Double> - координаты твита в пространстве признаков (слов)
     */
    public void setDataForAnalyse(List<List<Double>> data);

    /**
     * инициализируем размерность входных данных для анализа
     * @param n - размерность входных данных для анализа
     */
    public void initializeDataForAnalyse(int n);

    /**
     * добавляет данные для анализа
     * @param data - данные для анализа каждый List<Double> - координаты твита в пространстве признаков (слов)
     */
    public void addDataForAnalyse(List<List<Double>> data);


    /**
     * анализирует твит
     * @param list  - координаты твита в пространстве признаков (слов)
     * @return результат классификации
     */
    public double analyse(List<Double> list);

}
