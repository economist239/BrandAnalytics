package ru.brandanalyst.core.util;

import org.apache.log4j.Logger;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * @author daddy-bear
 *         Date: 05.05.12 - 18:48
 */

public class Starter {
    private static final Logger log = Logger.getLogger(Starter.class);

    public static void main(final String[] args) {
        if (args.length != 1) {
            throw new RuntimeException("invalid jvm arguments");
        }
        log.info("Application started");
        new FileSystemXmlApplicationContext(args[0]);
    }
}
