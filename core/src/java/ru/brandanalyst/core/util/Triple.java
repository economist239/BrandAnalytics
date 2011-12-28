package ru.brandanalyst.core.util;

/**
 * Created by IntelliJ IDEA.
 * User: dima
 * Date: 12/28/11
 * Time: 10:24 AM
 */
public class Triple<A, B, C> {
   
    public final A first;
    public final B second;
    public final C third;

    public Triple(A first, B second, C third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }
    
    public static <A, B, C> Triple<A, B, C> of(A first, B second, C third) {
        return new Triple(first, second, third);
    }
}
