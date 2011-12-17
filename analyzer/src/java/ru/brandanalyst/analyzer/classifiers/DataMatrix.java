package ru.brandanalyst.analyzer.classifiers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: dima
 * Date: 12/16/11
 * Time: 10:17 PM
 */
public class DataMatrix {
    private final int vLength;
    private List<List<Double>> matrix;

    public DataMatrix(int vLength) {
        this.vLength = vLength;
    }

    //ArrayList - именно на массиве, чтобы всегда можно было бегать
    // по координатам быстро (мало ли кто пихнет LinkedList)
    public void addVector(ArrayList<Double> vector) {
        if (vector.size() == vLength) {
            matrix.add(vector);
        } else {
            throw new IllegalArgumentException("illegar vector size");
        }
    }

    //получаем итератор, чтобы пробегаться по нему в массиве
    public Iterator<List<Double>> getMatrix() {
        return matrix.iterator();
    }
    
    public ArrayList<Double> vectorValidate(ArrayList<Double> v) {
        if (v.size() == vLength) return v;
        throw new IllegalArgumentException("illegal vector size");
    }
}
