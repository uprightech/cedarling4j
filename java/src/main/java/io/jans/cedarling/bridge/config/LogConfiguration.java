/*
 * Janssen Project software is available under the Apache License (2004). See http://www.apache.org/licenses/ for full text.
 *
 * Copyright (c) 2025, Janssen Project
 */

package io.jans.cedarling.bridge.config;

public class LogConfiguration {
    
    private LogType logType;
    private LogLevel logLevel;
    private MemoryLogConfiguration memoryLogConfiguration;

    public LogConfiguration() {

        logType  = LogType.OFF;
        logLevel = LogLevel.INFO;
        memoryLogConfiguration = null;
    }
    
    public LogConfiguration(final LogType logType, final LogLevel logLevel) {

        this.logType = logType;
        this.logLevel = logLevel;

    }

    public LogConfiguration(final LogType logType, final LogLevel logLevel, final MemoryLogConfiguration memoryLogConfiguration) {

        this.logType =  logType;
        this.logLevel = logLevel;
        this.memoryLogConfiguration = memoryLogConfiguration;
    }

    public LogType getLogType() {

        return logType;
    }

    public LogConfiguration setLogType(LogType logType) {

        this.logType = logType;
        return this;
    }

    public MemoryLogConfiguration getMemoryLogConfiguration() {

        return memoryLogConfiguration;
    }

    public LogConfiguration setMemoryConfiguration(MemoryLogConfiguration memoryConfiguration) {

        this.memoryLogConfiguration = memoryLogConfiguration;
        return this;
    }

    public LogLevel getLogLevel() {

        return logLevel;
    }

    public LogConfiguration setLogLevel(LogLevel loglevel) {

        this.logLevel = loglevel;
        return this;
    }

    public static LogConfiguration  noLogging() {

        return new LogConfiguration(LogType.OFF,LogLevel.INFO,null);
    }

    public static LogConfiguration memory(MemoryLogConfiguration memory_log_config, LogLevel log_level) {

        return new LogConfiguration(LogType.MEMORY,log_level,memory_log_config);
    }

    public static LogConfiguration stdOut(LogLevel log_level) {

        return new LogConfiguration(LogType.STDOUT,log_level,null);
    }

    public static LogConfiguration lock(LogLevel log_level) {

        return new LogConfiguration(LogType.LOCK,log_level,null);
    }
}
