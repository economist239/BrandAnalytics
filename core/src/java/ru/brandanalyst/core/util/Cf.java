package ru.brandanalyst.core.util;

import java.util.*;

/**
 * @author daddy-bear
 *         Date: 08.03.12
 */
public final class Cf {

    private Cf() {}

    public static <T> List<T> newList() {
        return new ArrayList<T>();
    }

    public static <T> List<T> newArrayList(int initialCapacity) {
        return new ArrayList<T>(initialCapacity);
    }

    public static <T> List<T> newArrayList(T... es) {
        List<T> list = new ArrayList<T>(es.length);
        Collections.addAll(list, es);
        return list;
    }
    
    public static <T> List<T> newArrayList() {
        return newList();
    }

    public static <T> List<T> newLinkedList() {
        return new LinkedList<T>();
    }

    public static <T> Set<T> newHashSet() {
        return new HashSet<T>();
    }

    public static <T> Set<T> newLinkedHashSet() {
        return new LinkedHashSet<T>();
    }
    
    public static <T> Set<T> newTreeSet() {
        return new TreeSet<T>();
    }
    
    public static <T> Set<T> newTreeSet(Comparator<T> comparator) {
        return new TreeSet<T>(comparator);
    }

    public static <K, V> Map<K, V> newHashMap() {
        return new HashMap<K, V>();
    }
}
