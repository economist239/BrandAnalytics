package ru.brandanalyst.core.util;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: dima
 * Date: 12/30/11
 * Time: 8:41 PM
 */
public abstract class Batch<T> {
    private final static Logger log = Logger.getLogger(Batch.class);

    private final static int BATCH_SIZE = 1024;

    private final int batchSize;
    private List<T> batchList;

    public Batch() {
        this(BATCH_SIZE);
    }

    public Batch(final int size) {
        batchSize = size;
        batchList = new ArrayList<T>(size);
    }

    public synchronized void submit(T element) {
        batchList.add(element);
        log.info("[Batch] submitted");
        if (batchList.size() == batchSize) {
            log.info("[Batch] handling");
            handle(batchList);
            batchList.clear();
        }
    }

    public void flush() {
        log.info("[Batch] flushed");
        if (batchList.size() != 0) {
            handle(batchList);
            batchList.clear();
        }
    }

    public abstract void handle(List<T> list);
}
