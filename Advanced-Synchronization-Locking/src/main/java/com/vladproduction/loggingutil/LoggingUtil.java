package com.vladproduction.loggingutil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingUtil {

    private static final Logger loggerRun = LoggerFactory.getLogger(LoggingUtil.class);
    private static final Logger loggerProcess = LoggerFactory.getLogger("ProcessLogger");

    public static void logError(String message, Throwable t) {
        loggerRun.error(message, t);
    }

    public static void logProcessInfo(String message) {
        loggerProcess.info(message);
    }
}
