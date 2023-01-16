package net.lucypoulton.pronouns.fabric;

import org.slf4j.event.Level;

import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class Slf4jLoggerShim extends Logger {

    private final org.slf4j.Logger slf4jLogger;

    public Slf4jLoggerShim(org.slf4j.Logger slf4jLogger) {
        super(slf4jLogger.getName(), null);
        this.slf4jLogger = slf4jLogger;
    }

    private Level fromJul(java.util.logging.Level level) {
        return switch (level.intValue()) {
            case 1000 -> Level.ERROR;
            case 900 -> Level.WARN;
            case 800 -> Level.INFO;
            case 700 -> Level.DEBUG;
            default -> Level.TRACE;
        };
    }

    @Override
    public void log(LogRecord record) {
        slf4jLogger
                .atLevel(fromJul(record.getLevel()))
                .log(Thread.currentThread().getName().startsWith("ProNouns ") ?
                        record.getMessage() :
                        "[ProNouns] " + record.getMessage());
    }
}
