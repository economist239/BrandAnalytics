package ru.brandanalyst.core.db.provider;

/**
 * Created by IntelliJ IDEA.
 * User: daddy-bear
 * Date: 2/18/12
 * Time: 3:33 PM
 */
public interface EntityVisitor<T> {

    void visitEntity(T e);

}
