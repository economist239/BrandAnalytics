package ru.brandanalyst.miner.processor;

import org.jetbrains.annotations.Nullable;

/**
 * User: daddy-bear
 * Date: 29.04.12
 * Time: 20:44
 */
public interface PostProcessor<T> {

    @Nullable
    T process(T entity);

}
