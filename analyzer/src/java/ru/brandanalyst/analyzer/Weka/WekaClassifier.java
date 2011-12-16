package ru.brandanalyst.analyzer.Weka;

import org.apache.log4j.Logger;
import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

import java.io.*;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Senov
 * Date: 11.12.11
 * Time: 14:40
 */
public abstract class WekaClassifier implements ru.brandanalyst.analyzer.Global.Classifier{

    protected static final Logger log = Logger.getLogger(WekaClassifier.class);

    protected static final String modelFilePath = "files/analyzer/classification_models/";

    protected Classifier classifier;
    protected Instances data;

    protected List<String> variabalesId;



    public abstract void initializeClassifier();

    WekaClassifier(List<String> variablesId){
        this.variabalesId = variablesId;
    }

    public boolean isModelExists(){
        String fileName = "";
        try{
            fileName = modelFilePath + this.getClass().getName() + "_var" + ".txt";

            if( (new File(fileName).exists()) ){
                BufferedReader variablesIdReader = new BufferedReader(
                                    new FileReader(fileName)
                            );
                String previousVariablesId = variablesIdReader.readLine();

                if( !previousVariablesId.equals(variabalesId.toString()) ){
                    return false;
                }
            }
            else{
                return false;
            }
        }
        catch(Exception e){
            log.error("Ошибка при проверке содержимого файла с используемыми переменными", e);
        }
        return true;


    }

    /*@Override
    public void build() {
        try{
            initializeClassifier();
            classifier.buildClassifier(trainingData);
        }
        catch(Exception e){
            log.error("Ошибка при построении классификатора " + this.getClass().getName(),e);
        }
    }

    @Override
    public void saveClassifierModel(){
        try{
            ObjectOutputStream oos = new ObjectOutputStream(
                    new FileOutputStream(modelFilePath + this.getClass().getName() + ".model")
            );
            oos.writeObject(classifier);

            BufferedWriter variablesIdWriter = new BufferedWriter(
                    new FileWriter(
                        modelFilePath + this.getClass().getName() + "_var" + ".txt"
                    )
            );
            variablesIdWriter.write(variabalesId.toString());

            oos.close();
        }
        catch(Exception e){
            log.error("Ошибка при сохранении модели классификатора " + this.getClass().getName(),e);
        }
    }
*/
    @Override
    public void loadClassificationModel(){
        try{
            if( isModelExists() ){
                String modelFileName = modelFilePath + this.getClass().getName() + ".model";
                ObjectInputStream os = new ObjectInputStream(
                        new FileInputStream(modelFilePath + this.getClass().getName() + ".model")
                );
                classifier = (Classifier) os.readObject();
                os.close();
            }
            else{
                log.error("Не удалось загрузить, или отсутствует модель классификатора");
            }


        }
        catch(Exception e){
            log.error("Ошибка при чтении модели классификатора " + this.getClass().getName(),e);
        }
    }

    @Override
    public double analyse(List<Double> li){
        double result = 0;

        li.add(new Double(0.0));
        li.add(new Double(0.0));

        Instance instance = toInstance(li);
        data.add(instance);

        try{
            result = classifier.classifyInstance(data.lastInstance());
        }
        catch(Exception e){
            log.error("Ошибка при классификации",e);
        }

        return result;
    }

    @Override
    public void setDataForAnalyse(List<List<Double>> data){

        FastVector attributeInfo = new FastVector();

        for(int i = 0;i < data.get(0).size(); ++i){
            attributeInfo.addElement(new Attribute(Integer.toString(i)));
        }

        this.data = new Instances("training",attributeInfo,data.size()) ;

        addDataForAnalyse(data);
    }
    public void initializeDataForAnalyse(int n){
        FastVector attributeInfo = new FastVector();

        FastVector labels = new FastVector();
        labels.addElement("0");
        labels.addElement("1");
        Attribute attribute = new Attribute("class",labels);

        for(int i = 0;i < n+2; ++i){
            attributeInfo.addElement(new Attribute(Integer.toString(i)));
        }

        attributeInfo.addElement(attribute);

        this.data = new Instances("training",attributeInfo,2) ;
        data.setClassIndex(data.numAttributes()-1);


    }

    @Override
    public void addDataForAnalyse(List<List<Double>> data){
        for(List<Double> list : data){
            this.data.add(toInstance(list));
        }
    }


    //TODO: move it to WekaUtil or smth simular;
    public Instance toInstance(List<Double> in){
        double[] values = new double[in.size()];
        int i = 0;
        for(double value : in){
            values[i++] = value;
        }
        Instance instance = new Instance(1.0,values);

        return instance;
    }


}
