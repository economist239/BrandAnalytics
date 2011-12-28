package ru.brandanalyst.core.util;

/**
 * Created by IntelliJ IDEA.
 * User: dima
 * Date: 12/28/11
 * Time: 9:33 PM
 */
public class Pair<A, B> {
    public A first;
    public B second;

    public Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }
    
    public static <A, B> Pair<A, B> of(A first, B second) {
        return new Pair<A, B>(first, second);
    }
}
