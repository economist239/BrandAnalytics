package ru.brandanalyst.indexer;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: 1
 * Date: 11.10.11
 * Time: 1:54
 * To change this template use File | Settings | File Templates.
 */
public class Test {

    public static void main(String[] args) {
        Indexer ind = new Indexer("TODO_this");
        try {
            ind.index();
            System.out.println("indexed");
        } catch (Exception e) {
            System.out.println("exception");
        }
    }
}
